package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";


    public Database(String dbName) {
        this.dbName = dbName;
    }

    public String getDbURL() {return dbURL + dbName;}

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(getDbURL());
    }

    public void createTables() throws SQLException {
        // query for projectstable and taskstable -> create
        String projectsTable = "CREATE TABLE IF NOT EXISTS Projects (id INTEGER PRIMARY KEY, title TEXT)";
        String tasksTable = "CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY, title TEXT)";

        try (Connection connec = connect(); Statement state = connec.createStatement()) {
            state.execute(projectsTable);
            state.execute(tasksTable);
        }
    }

}
