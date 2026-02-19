package edu.ezip.ing1.pds.connectionPoolV3;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceV3 {

    private static final JDBCConnectionPoolV3 pool = new JDBCConnectionPoolV3();

    private DataSourceV3() {}


    public static Connection getConnection() throws InterruptedException {
        return pool.prendreCo();
    }

    public static void releaseConnection(Connection conn) {
        pool.remettreConn(conn);
    }


    public static void closeAll() throws SQLException {
        pool.fermerTout();
    }


}
