package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {

    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";

    // sql queries
    private static final List<String> CREATE_TABLE_STATEMENTS = List.of(
            "CREATE TABLE IF NOT EXISTS Projects (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT, " +
                    "userID INTEGER, " +
                    "FOREIGN KEY (userID) REFERENCES Users(id))",

            "CREATE TABLE IF NOT EXISTS Tasks (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT, " +
                    "dueDate DATE, " + // Stored in format (sqlite) yyyy-mm-dd
                    "isFinished INTEGER, " + // "boolean" value 0, og 1 for false, true
                    "isRepeating INTEGER, " + // stored in days (1 -> 1 day, 7 -> week)
                    "project_id INTEGER, " +
                    "FOREIGN KEY (project_id) REFERENCES Projects(id))",

            "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(45))"
    );


    public Database(String dbName) {
        this.dbName = dbName;
    }

    public String getDbURL() {return dbURL + dbName;}

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(getDbURL());
    }

    public void createTables() throws SQLException {
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            for (String sqlQuery : CREATE_TABLE_STATEMENTS) {
                statement.execute(sqlQuery);
            }
        }
    }

}

