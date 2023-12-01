package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDatabase implements Database{
    final String postgres_user = "userTree";
    final String postgres_password = "pass";
    final String postgres_url = "jdbc:postgresql://localhost:5432/treeDB";
    @Override
    public Connection getConnection() throws SQLException {
        registerDriver();
        return DriverManager.getConnection(postgres_url, postgres_user, postgres_password);

    }
    @Override
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection close Postgres");
            connection.close();
        }
    }
    @Override
    public void registerDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
