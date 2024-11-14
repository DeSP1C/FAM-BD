package model;

import SQLError.DataBaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class StockExchangeDB {
    public static final String USER = "postgres";
    public static final String PASSWORD = "P30SQL09";
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
}

public class ConnectionToDB {
    private Connection Connection;

    public Connection connect()
    {
        if (Connection != null)
            return Connection;

        try {
            Connection = DriverManager.getConnection(StockExchangeDB.URL, StockExchangeDB.USER, StockExchangeDB.PASSWORD);
            System.out.println("Connection with DBMS is done!");

            return Connection;
        } catch (SQLException e) {
            throw new DataBaseException("SQL error! Unexpected problems when connecting to a local server");
        }
    }
}