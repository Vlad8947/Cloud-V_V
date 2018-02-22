package server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStatement {

    private Connection connection;
    private PreparedStatement prepStat;
    private ResultSet resultSet;

    private final int NOT_FOUND = -1;

    public DataStatement () {
        connection = DataBaseConnector.getConnection();
    }

    public int isAuthorized (String login, String password) {

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

    public void close () {
        try {
            prepStat.close();
            resultSet.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("!!!DataStatement sqlEx");
        }
    }

}
