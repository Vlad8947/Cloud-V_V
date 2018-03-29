package server.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс-синглтон для передачи запросов базе данных
 */

public class DataBaseStatement {

    private static Connection connection;
    private static PreparedStatement prepStat;
    private static ResultSet resultSet;
    private static DataBaseStatement dataBaseStatement;

    private static final int NOT_FOUND = -1;

    private DataBaseStatement() {
        connection = DataBaseConnector.getConnection();
    }

    public static synchronized DataBaseStatement getDataBaseStatement() {
        if (dataBaseStatement==null) {
            dataBaseStatement = new DataBaseStatement();
        }
        return dataBaseStatement;
    }

    public static synchronized int isAuthorized (String login, String password) {

        try {
            prepStat = connection.prepareStatement(
                    "SELECT id_client FROM clients WHERE login = ? AND password = ?");
            prepStat.setString(1, login);
            prepStat.setString(2, password);
            resultSet = prepStat.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            else {
                return NOT_FOUND;
            }

        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return 0;
        }
    }

    public static synchronized void registration(String login, String password) throws SQLException {

        prepStat = connection.prepareStatement(
                "INSERT INTO clients (login, password) VALUES (?, ?)");
        prepStat.setString(1, login);
        prepStat.setString(2, password);
        prepStat.executeUpdate();

    }

    public static void close () {
        try {
            prepStat.close();
            resultSet.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("!!!DataBaseStatement sqlEx");
        }
    }

}
