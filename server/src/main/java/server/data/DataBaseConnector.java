package server.data;

import java.sql.*;

public class DataBaseConnector {

    private static final String url = "jdbc:mysql://localhost:3306/cloud_vv" +
                                                "?verifyServerCertificate=false" +
                                                "&useSSL=false" +
                                                "&requireSSL=false" +
                                                "&useLegacyDatetimeCode=false" +
                                                "&amp" +
                                                "&serverTimezone=UTC";

    private final String user = "cloud_server";
    private final String password = "0000";

    private static Connection connection;

    public DataBaseConnector () {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {

        }
    }


    public void close () {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection () {
        return connection;
    }

}
