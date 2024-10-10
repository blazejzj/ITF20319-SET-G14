package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String dbName;

    public Database(String dbName) {
        this.dbName = dbName;
    }

    public Connection connect() throws SQLException {
        String dbURL = "jdbc:sqlite:" + dbName;
        return DriverManager.getConnection(dbURL);
    }

}

