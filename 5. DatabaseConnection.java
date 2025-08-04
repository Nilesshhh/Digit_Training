package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import util.CredentialReader;

public class DatabaseConnection {
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            Map<String, String> credentials = CredentialReader.readCredentials();
            String url = credentials.get("URL");
            String username = credentials.get("USERNAME");
            String password = credentials.get("PASSWORD");
            
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
