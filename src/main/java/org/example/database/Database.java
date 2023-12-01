package org.example.database;


import java.sql.Connection;
import java.sql.SQLException;

interface Database {

    public abstract void closeConnection(Connection connection)throws SQLException;

    public abstract Connection getConnection() throws SQLException;
    public abstract void registerDriver();
}
