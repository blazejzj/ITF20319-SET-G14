package database;

import models.Project;
import models.Task;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

public class Database {

    // Variables
    private final String dbName;
    private final String dbURL = "jdbc:sqlite:";

    // TABLE CREATION QUERIES
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

    // USER TABLE QUERIES
    private static final String QUERY_INSERT_USER = "INSERT INTO Users (name) VALUES (?)";
    private static final String QUERY_SELECT_USER_BY_ID = "SELECT name FROM Users WHERE id = ?";
    private static final String QUERY_UPDATE_USER = "UPDATE Users SET name = ? WHERE id = ?";
    private static final String QUERY_DELETE_USER = "DELETE FROM Users WHERE id = ?";

    // PROJECT TABLE QUERIES
    private static final String QUERY_SELECT_PROJECTS_BY_USERID = "SELECT * FROM Projects WHERE userID = ?";
    private static final String QUERY_SAVE_PROJECT = "INSERT INTO Projects (title, description, userID) VALUES (?, ?, ?)";
    private static final String QUERY_DELETE_PROJECT = "DELETE FROM Projects WHERE id = ?";
    private static final String QUERY_DELETE_PROJECT_BY_USERID = "DELETE FROM Projects WHERE userID = ?";
    private static final String QUERY_DELETE_TASKS_BY_PROJECT = "DELETE FROM Tasks WHERE project_id = ?";
    private static final String QUERY_UPDATE_PROJECT = "UPDATE Projects SET title = ?, description = ? WHERE id = ?";

    // TASK TABLE QUERIES
    private static final String QUERY_SAVE_TASK = "INSERT INTO Tasks (title, description, dueDate, isFinished, isRepeating, repeatDays, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_SELECT_TASKS_BY_PROJECT = "SELECT * FROM Tasks WHERE project_id = ?";
    private static final String QUERY_DELETE_TASK = "DELETE FROM Tasks WHERE id = ?";
    private static final String QUERY_DELETE_TASKS_BY_USERID = "DELETE FROM Tasks WHERE project_id IN (SELECT id FROM Projects WHERE userID = ?)";
    private static final String QUERY_UPDATE_TASK = "UPDATE Tasks SET title = ?, description = ?, dueDate = ?, isFinished = ?, isRepeating = ?, repeatDays = ? WHERE id = ?";


    // Constructor/s
    public Database(String dbName) {
        this.dbName = dbName;
    }

    // Getters/Setters
    public String getDbURL() {return dbURL + dbName;}

    // Methods
    public Connection connect() throws SQLException {
        Connection connect = DriverManager.getConnection(getDbURL());
        try(Statement statement = connect.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        }
        return connect;
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
                .prepareStatement(QUERY_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        }
    }

    public User loadUser(int userId) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                return new User(id, name);
            } else { return null; } // if no user with "userId" exists return null
        }
    }

    public void updateUser(int id, String newName) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_USER)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected");
            }
        }
    }

    public void deleteUser(int userId) throws SQLException {
        // The deletion of a certain user is much more complicated than probably
        // anticipated because we have to delete Tasks, Projects, and then the user itself.
        try (Connection connection = connect()) {

            // DELETE TASKS
            try (PreparedStatement deleteTasksStatement = connection.prepareStatement(QUERY_DELETE_TASKS_BY_USERID)) {
                deleteTasksStatement.setInt(1, userId);
                deleteTasksStatement.executeUpdate();
            }

            // DELETE PROJECTS
            try (PreparedStatement deleteProjectsStatement = connection.prepareStatement(QUERY_DELETE_PROJECT_BY_USERID)) {
                deleteProjectsStatement.setInt(1, userId);
                deleteProjectsStatement.executeUpdate();
            }

            // DELETE USER
            try (PreparedStatement deleteUsersStatement = connection.prepareStatement(QUERY_DELETE_USER)) {
                deleteUsersStatement.setInt(1, userId);
                int affectedRows = deleteUsersStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("No rows affected");
                }
            }
        }

    }

    // PROJECT METHODS
    public int saveProject(String title, String description, int userId) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SAVE_PROJECT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, userId);
                preparedStatement.executeUpdate();
                return getGeneratedKey(preparedStatement);
        }
    }

    public ArrayList<Project> loadUserProjects(int userId) throws SQLException {
        ArrayList<Project> projects = new ArrayList<>(); // temp list to store projetcs

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_PROJECTS_BY_USERID)) {

                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    Project project = new Project(id, title, description, userId);
                    projects.add(project);
                }
        }
        return projects;
    }

    public void deleteProject(int projectID) throws SQLException {
        try (Connection connection = connect()) {
            try (PreparedStatement deleteTasksStatement = connection.prepareStatement(QUERY_DELETE_TASKS_BY_PROJECT);
                 PreparedStatement deleteProjectStatement = connection.prepareStatement(QUERY_DELETE_PROJECT)) {

                deleteTasksStatement.setInt(1, projectID);
                deleteTasksStatement.executeUpdate();

                deleteProjectStatement.setInt(1, projectID);
                int affectedRows =  deleteProjectStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("No rows affected");
                }
            }
        }
    }

    // were including id on purpose, to get even more control over the object
    // project is going to have an id anyways, but incase we want to update it we have teh possiblity
    public void updateProject(int projectId, String newTitle, String newDescription) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_PROJECT)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setInt(3, projectId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. Project with ID " + projectId + " may not exist.");
            }
        }
    }

    // TASK METHODS
    public int saveTask(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays, int projectId) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SAVE_TASK, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    public ArrayList<Task> loadTasks(int projectId) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>(); // temp list to store tasks

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_TASKS_BY_PROJECT)) {
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
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_TASK)) {
            preparedStatement.setInt(1, taskID);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No rows affected");
            }
        }
    }

    // same idea as the project when it comes to the updating the id
    public void updateTask(int taskId, String newTitle, String newDescription, LocalDate newDueDate, int isFinished, int isRepeating, int repeatDays) throws SQLException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_TASK)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setDate(3, Date.valueOf(newDueDate));
            preparedStatement.setInt(4, isFinished);
            preparedStatement.setInt(5, isRepeating);
            preparedStatement.setInt(6, repeatDays);
            preparedStatement.setInt(7, taskId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. Task with ID " + taskId + " may not exist.");
            }
        }
    }



}

