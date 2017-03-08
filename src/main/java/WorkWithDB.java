import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MykoVol on 2/24/2017.
 */
public class WorkWithDB {
    private static Logger log = Logger.getLogger(WorkWithDB.class.getName());
    private BoneCP connectionPool;
    private int machineID;
    private static WorkWithDB ourInstance = new WorkWithDB();

    public static WorkWithDB getInstance() {
        return ourInstance;
    }

    public WorkWithDB() {
        this.connectionPool = getConnectionPool();
    }

    @org.jetbrains.annotations.Nullable
    private BoneCP getConnectionPool() {
        BoneCP conPool = null;

        try {
            // setup the connection pool
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:Tracker");
            config.setUsername("AppTracker_Owner");
            config.setPassword("viagnl43gfdFR");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            conPool = new BoneCP(config); // setup the connection pool
        } catch (SQLException e) {
            log.log(Level.SEVERE, "getConnection failed", e);
        }

        return conPool;

    }

    public int getMachineID(Connection connection) throws SQLException {
        if (this.machineID != 0) return this.machineID;

        String query = "{ ? = call f_get_machine_id(?)}"; //return machine id adding machine if such does not exist
        CallableStatement stmt = connection.prepareCall(query);
        stmt.registerOutParameter(1, java.sql.Types.NUMERIC);
        stmt.setString(2, System.getProperty("user.name"));
        stmt.execute();
        this.machineID = stmt.getInt(1);
        stmt.close();
        return this.machineID;
    }

    public boolean addTrack(ProcessDetails procDet) {
        Connection connection = null;
        boolean goodTransaction = false;
        try {
            connection = connectionPool.getConnection(); // fetch a connection

            if (connection != null) {
                String query = "{call p_add_track(?,?,?,?)}";
                CallableStatement stmt = connection.prepareCall(query);
                stmt.setInt(1, getMachineID(connection));
                stmt.setTimestamp(2, procDet.getDateTime());
                stmt.setString(3, procDet.getProcName());
                stmt.setString(4, procDet.getProcTitle());
                stmt.execute();
                stmt.close();
                goodTransaction = true;
            }

        } catch (SQLException e) {
            this.machineID = 0;
            log.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        return goodTransaction;
    }
}
