package database;

import models.Project;
import models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

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
                    "repeatDays INTEGER, " + // every x amount of days the task is going to be repeating
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

    public int getGeneratedKey(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Somethings went wrong. Can't generate unique ID");
            }
        }
    }

    // USER METHODS

    public int saveUser(String name) throws SQLException {
        try (PreparedStatement preparedStatement = connect()
                .prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        }
    }

    public String loadUser(int userId) throws SQLException {
        String query = "SELECT name FROM Users WHERE id = ?";

        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name"); // get the name by id
            } else { return null; } // if no user with "userId" exists return null
        }
    }

    public void updateUser(int id, String newName) throws SQLException {
        String query = "UPDATE Users SET name = ? WHERE id = ?";
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected");
            }
        }
    }

    public void deleteUser(int userId) throws SQLException {}

    // PROJECT METHODS

    public int saveProject(String title, String description, int userId) throws SQLException {
        String query = "INSERT INTO Projects (title, description, userID) VALUES (?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, userId);
                preparedStatement.executeUpdate();
                return getGeneratedKey(preparedStatement);
        }
    }

    public ArrayList<Project> getUserProjects(int userId) throws SQLException {
        String query = "SELECT * FROM Projects WHERE userID = ?";
        ArrayList<Project> projects = new ArrayList<>(); // temp list to store projetcs

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int userID = resultSet.getInt("userID");

                    Project project = new Project(id, title, description, userID);
                    projects.add(project);
                }
        }
        return projects;
    }

    public void deleteProject(int projectID) throws SQLException {
        String deleteProjectQuery = "DELETE FROM Projects WHERE id = ?";
        String deleteTasksQuery = "DELETE FROM Tasks WHERE project_id = ?";
        try (Connection connection = connect()) {
            try (PreparedStatement deleteTasksStatement = connection.prepareStatement(deleteTasksQuery);
                 PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectQuery)) {
                deleteProjectStatement.setInt(1, projectID);
                deleteProjectStatement.executeUpdate();

                deleteTasksStatement.setInt(1, projectID);
                deleteTasksStatement.executeUpdate();
            }
        }
    }

    // TASK METHODS

    public int saveTask(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays, int projectId) throws SQLException {
        String query = "INSERT INTO Tasks (title, description, dueDate, isFinished, isRepeating, repeatDays, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dueDate));
            preparedStatement.setInt(4, isFinished);
            preparedStatement.setInt(5, isRepeating);
            preparedStatement.setInt(6, repeatDays);
            preparedStatement.setInt(7, projectId);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        }
    }

    public ArrayList<Task> getAllProjectTasks (int projectId) throws SQLException {
        String query  = "SELECT * FROM Tasks WHERE project_id = ?"; // based on project id get all the tasks
        ArrayList<Task> tasks = new ArrayList<>(); // temp list to store tasks

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setInt(1, projectId);
                 ResultSet resultSet = preparedStatement.executeQuery();

                 while (resultSet.next()) { // for each result create an object of task and add it to the array
                     int id = resultSet.getInt("id"); // autogenerated
                     String title = resultSet.getString("title");
                     String description = resultSet.getString("description");
                     LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                     int isFinished = resultSet.getInt("isFinished");
                     int isRepeating = resultSet.getInt("isRepeating");
                     int repeatDays = resultSet.getInt("repeatDays");

                     Task newTask = new Task(id, title, description, dueDate, isFinished, isRepeating, repeatDays);
                     tasks.add(newTask);
                 }
        }
        return tasks;
    }

    public void deleteTask(int taskID) throws SQLException {
        String query = "DELETE FROM Tasks WHERE id = ?";
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, taskID);
            preparedStatement.executeUpdate();
        }
    }


}

