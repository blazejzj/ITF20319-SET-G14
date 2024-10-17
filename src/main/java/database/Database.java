package database;

import java.sql.*;
import java.util.List;

public class Database {

    // Variables
    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";

    // sql queries for table creation
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

    // sql queries
    private static final String INSERT_USER_QUERY = "INSERT INTO Users (name) VALUES (?)";


    // Constructor/s
    public Database(String dbName) {
        this.dbName = dbName;
    }

    // Getters/Setters
    public String getDbURL() {return dbURL + dbName;}


    // Methods
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

    public int saveUser(String name) throws SQLException {
        try (PreparedStatement preparedStatement = connect()
                .prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        }
    }

    public int getGeneratedKey(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Somethings went wrong. Cant generate user ID -> no id returned");
            }
        }
    }

    public String loadUser(int userId) {
        return "x";
    }
}

