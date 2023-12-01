package org.example.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class H2Database implements Database{

    final String h2_user = "userTree";
    final String h2_password = "pass";
    final String h2_url = "jdbc:h2:~/treeDB";

    @Override
    public Connection getConnection() throws SQLException {
        registerDriver();
        return DriverManager.getConnection(h2_url, h2_user, h2_password);

    }
    @Override
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection close H2");
            connection.close();
        }
    }
    @Override
    public void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
