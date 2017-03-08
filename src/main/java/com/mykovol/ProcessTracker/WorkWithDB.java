package com.mykovol.ProcessTracker;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Created by MykoVol on 2/24/2017.
 */
final class WorkWithDB {
    private static Logger logger = Logger.getLogger(WorkWithDB.class);
    private BoneCP connectionPool;
    private int machineID;
    private static WorkWithDB ourInstance = new WorkWithDB();

    static WorkWithDB getInstance() {
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
            logger.error("getConnectionPool failed", e);
        }

        return conPool;

    }

    private int getMachineID(Connection connection) throws SQLException {
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

    boolean addTrack(ProcessDetails procDet) {
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
            logger.error("error with p_add_track, set machineID = 0", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("error on connection final close", e);
                }
            }
        }
        return goodTransaction;
    }
}
