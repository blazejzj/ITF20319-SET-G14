package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";

    // sql queries
    private static final String CREATE_PROJECTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Projects (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT)";

    private static final String CREATE_TASKS_TABLE =
            "CREATE TABLE IF NOT EXISTS Tasks (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT, " +
                    "project_id INTEGER, " +
                    "FOREIGN KEY (project_id) REFERENCES Projects(id))";


    public Database(String dbName) {
        this.dbName = dbName;
    }

    public String getDbURL() {return dbURL + dbName;}

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(getDbURL());
    }

    public void createTables() throws SQLException {
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.execute(CREATE_PROJECTS_TABLE);
            statement.execute(CREATE_TASKS_TABLE);
        }
    }

}

