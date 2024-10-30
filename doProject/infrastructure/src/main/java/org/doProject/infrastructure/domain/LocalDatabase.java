package org.doProject.infrastructure.domain;

import org.doProject.core.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

/**
 * Provides operations for managing and interacting with database tables for Users, Projects, and Tasks.
 * Uses the LocalDatabaseConnection to handle database connections and executes SQL statements to manage data.
 * Supports CRUD operations
 */
public class LocalDatabase {

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

    // CONNECTION OBJECT
    private final LocalDatabaseConnection connectionHandler;


    // Constructor/s
    /**
     * Constructs a LocalDatabase using the specified LocalDatabaseConnection.
     * @param connectionHandler the connection handler that provides database connections.
     */
    public LocalDatabase(LocalDatabaseConnection connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    // Methods

    /**
     * Creates the necessary tables -> Projects, Tasks, Users in the database if they do not already exist.
     * @throws SQLException if a database access error occurs.
     */
    public void createTables() throws SQLException {
        try (Connection connection = connectionHandler.connect(); Statement statement = connection.createStatement()) {
            for (String sqlQuery : CREATE_TABLE_STATEMENTS) {
                statement.execute(sqlQuery);
            }
        }
    }


    /**
     * Retrieves the generated key (ID) after insert.
     * @param preparedStatement the statement used for the insert.
     * @return the generated key as an integer.
     * @throws SQLException if no key was generated or a database access error occurs.
     */
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

    /**
     * Saves a new user to the Users table.
     * @param name is the name of the user.
     * @return the generated user ID.
     * @throws SQLException if a database access error occurs.
     */
    public int saveUser(String name) throws SQLException {
        try (PreparedStatement preparedStatement = connectionHandler.connect()
                .prepareStatement(QUERY_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            return getGeneratedKey(preparedStatement);
        }
    }

    /**
     * Loads a user by their ID from the Users table.
     * @param userId the ID of the user to load.
     * @return the User object if found, or null if user is not found.
     * @throws SQLException if a database access error occurs.
     */
    public User loadUser(int userId) throws SQLException {
        try (Connection connection = connectionHandler.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                return new User(id, name);
            } else { return null; }
        }
    }

    /**
     * Updates an existing users name in the Users table.
     * @param id the ID of the user to update.
     * @param newName the new name for the user.
     * @throws SQLException if the update fails or no user is found with the given ID.
     */
    public void updateUser(int id, String newName) throws SQLException {
        try (Connection connection = connectionHandler.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_USER)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows affected");
            }
        }
    }

    /**
     * Deletes a user from the database together with their associated projects and tasks.
     * @param userId the ID of the user to delete.
     * @throws SQLException if no user with the specified ID exists.
     */
    public void deleteUser(int userId) throws SQLException {
        // The deletion of a certain user is much more complicated than probably
        // anticipated because we have to delete Tasks, Projects, and then the user itself.
        try (Connection connection = connectionHandler.connect()) {

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

    /**
     * Inserts a new project for a specified user into the Projects table.
     * @param title the title of the project.
     * @param description the description of the project.
     * @param userId the ID of the user associated with the project.
     * @return the generated project ID.
     * @throws SQLException if a database access error occurs.
     */
    public int saveProject(String title, String description, int userId) throws SQLException {
        try (Connection connection = connectionHandler.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SAVE_PROJECT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, userId);
                preparedStatement.executeUpdate();
                return getGeneratedKey(preparedStatement);
        }
    }

    /**
     * Retrieves all projects associated with a specific user ID from the Projects table.
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return a list of Project objects associated with the user.
     * @throws SQLException if a database access error occurs.
     */
    public ArrayList<Project> loadUserProjects(int userId) throws SQLException {
        ArrayList<Project> projects = new ArrayList<>(); // temp list to store projetcs

        try (Connection connection = connectionHandler.connect();
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

    /**
     * Deletes a project from the database, together with any associated tasks.
     * @param projectID the ID of the project to delete.
     * @throws SQLException if the project does not exist or a database access error occurs.
     */
    public void deleteProject(int projectID) throws SQLException {
        try (Connection connection = connectionHandler.connect()) {
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

    /**
     * Updates an existing projects title and description.
     * @param projectId the ID of the project to update.
     * @param newTitle the new title for the project.
     * @param newDescription the new description for the project.
     * @throws SQLException if the update fails or no project is found with the given ID.
     */
    public void updateProject(int projectId, String newTitle, String newDescription) throws SQLException {
        try (Connection connection = connectionHandler.connect();
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

    /**
     * Inserts a new task associated with a specific project into the Tasks table.
     * @param title the title of the task.
     * @param description the description of the task.
     * @param dueDate the due date for the task.
     * @param isFinished whether the task is marked as finished.
     * @param isRepeating whether the task is set to repeat.
     * @param repeatDays the number of days between task repetitions.
     * @param projectId the ID of the project associated with the task.
     * @return the generated task ID.
     * @throws SQLException if a database access error occurs.
     */
    public int saveTask(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays, int projectId) throws SQLException {
        try (Connection connection = connectionHandler.connect();
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

    /**
     * Retrieves all tasks associated with a specific project ID from the Tasks table.
     * @param projectId the ID of the project whose tasks are to be retrieved.
     * @return a list of Task objects associated with the project.
     * @throws SQLException if a database access error occurs.
     */
    public ArrayList<Task> loadTasks(int projectId) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>(); // temp list to store tasks

        try (Connection connection = connectionHandler.connect();
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

    /**
     * Deletes a task from the Tasks table by its ID.
     * @param taskID the ID of the task to delete.
     * @throws SQLException if no task with the specified ID exists or if a database access error occurs.
     */
    public void deleteTask(int taskID) throws SQLException {
        try (Connection connection = connectionHandler.connect(); PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_TASK)) {
            preparedStatement.setInt(1, taskID);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No rows affected");
            }
        }
    }

    /**
     * Updates an existing tasks title, description, due date, and repetition settings.
     * @param taskId the ID of the task to update.
     * @param newTitle the new title for the task.
     * @param newDescription the new description for the task.
     * @param newDueDate the new due date for the task.
     * @param isFinished the new finished status for the task.
     * @param isRepeating the new repetition status for the task.
     * @param repeatDays the new number of days for task repetition.
     * @throws SQLException if the update fails or no task is found with the given ID.
     */
    public void updateTask(int taskId, String newTitle, String newDescription, LocalDate newDueDate, int isFinished, int isRepeating, int repeatDays) throws SQLException {
        try (Connection connection = connectionHandler.connect();
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

