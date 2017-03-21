package com.mykovol.ProcessTracker;


import java.sql.*;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.log4j.Logger;

/**
 * Created by MykoVol on 2/24/2017.
 */
final class WorkWithDB {
    private static Logger logger = Logger.getLogger(WorkWithDB.class);

    private static DataSource datasource;

    private int machineID;
    private static WorkWithDB ourInstance = new WorkWithDB();

    static WorkWithDB getInstance() {
        return ourInstance;
    }

    private static DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(AppProperties.getJdbcUrl());
            config.setUsername(AppProperties.getJdbcUsername());
            config.setPassword(AppProperties.getJdbcPassword());

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }

    private int getMachineID(Connection connection) throws SQLException {
//        todo refresh machine id on logon
        if (this.machineID == 0) {
            String query = "{ ? = call AppTracker_Owner.f_get_machine_id(?)}"; //return machine id adding one if such does not exist
            CallableStatement stmt = connection.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.NUMERIC);
            stmt.setString(2, System.getProperty("user.name"));
            stmt.execute();
            this.machineID = stmt.getInt(1);
            stmt.close();
        }
        return this.machineID;
    }

    private void addTrack(Connection connection, ProcessDetails prcDtl) throws SQLException {
        String query = "{call AppTracker_Owner.p_add_track(?,?,?,?)}";
        CallableStatement stmt = connection.prepareCall(query);
        stmt.setInt(1, getMachineID(connection));
        stmt.setTimestamp(2, prcDtl.getDateTime());
        stmt.setString(3, prcDtl.getProcName());
        stmt.setString(4, prcDtl.getProcTitle());
        stmt.execute();
        stmt.close();
    }

    void insertTrackList(List<ProcessDetails> listToSync) {
        Connection connection = null;
        try {
            DataSource dataSource = getDataSource();
            connection = dataSource.getConnection();

            for (Iterator<ProcessDetails> i = listToSync.iterator(); i.hasNext(); ) {
                ProcessDetails item = i.next();
//            try to sync and remove if successful
                try {
                    addTrack(connection, item);
                } catch (SQLException e) {
                    i.remove();
                    logger.error("Error on executing DB stored procedure. Skip item", e);
                }
            }
        } catch (Exception e) {
            listToSync.clear();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error("Error on connection rollback", e);
                }
            }
            listToSync.clear();
            logger.error("Error with DB connection. Skip sync", e);
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error on connection final close", e);
                }
            }
        }


    }
}
