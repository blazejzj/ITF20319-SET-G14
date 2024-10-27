package org.doProject.infrastructure.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the connection to a local SQLite database.
 * Provides methods to establish connection to the database and to enable foreign key constraints.
 */
public class LocalDatabaseConnection {

    // Variables
    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";

    // Constructor/s
    /**
     * Constructs a LocalDatabaseConnection with the specified database name.
     * @param dbName the name of the database file.
     */
    public LocalDatabaseConnection(String dbName) {
        this.dbName = dbName;
    }

    // Getters / Setters

    /**
     * Returns the complete URL for the SQLite database.
     * @return the database URL as a String.
     */
    public String getDbURL() {return dbURL + dbName;}

    // Methods

    /**
     * Establishes a connection to the SQLite database and enables foreign key constraints.
     * @return an active connection to the SQLite database.
     * @throws SQLException if a database access error occurs.
     */
    public Connection connect() throws SQLException {
        Connection connect = DriverManager.getConnection(getDbURL());
        try(Statement statement = connect.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        }
        return connect;
    }

}
