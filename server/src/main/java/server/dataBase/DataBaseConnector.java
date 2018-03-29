package server.dataBase;

import java.sql.*;

/**
 * Класс-синглтон для подключения к базе данных
 */

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
    private static DataBaseConnector dataBaseConnector;

    private DataBaseConnector () {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            close();
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
        if (connection==null) {
            dataBaseConnector = new DataBaseConnector();
        }
        return connection;
    }

}
