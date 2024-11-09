package org.doProject.core.port;

import org.doProject.core.domain.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface TaskRepository {
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
    int saveTask(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays, int projectId) throws SQLException;

    /**
     * Inserts a new task associated with a specific project into the Tasks table.
     * @param task The Task object to save.
     * @param projectId The ID of the project associated with the task.
     * @return The generated task ID.
     * @throws SQLException if a database access error occurs.
     */
    int saveTask(Task task, int projectId) throws SQLException;

    /**
     * Retrieves all tasks associated with a specific project ID from the Tasks table.
     * @param projectId the ID of the project whose tasks are to be retrieved.
     * @return a list of Task objects associated with the project.
     * @throws SQLException if a database access error occurs.
     */
    ArrayList<Task> loadTasks(int projectId) throws SQLException;

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
    void updateTask(int taskId, String newTitle, String newDescription, LocalDate newDueDate, int isFinished, int isRepeating, int repeatDays) throws SQLException;

    /**
     * Updates an existing tasks title, description, due date, and repetition settings.
     * @param task The Task object containing updated information.
     * @throws SQLException if the update fails or no task is found with the given ID.
     */
    void updateTask(Task task) throws SQLException;

    /**
     * Deletes a task from the Tasks table by its ID.
     * @param taskID the ID of the task to delete.
     * @throws SQLException if no task with the specified ID exists or if a database access error occurs.
     */
    void deleteTask(int taskID) throws SQLException;

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task.
     * @return the task if found, null otherwise.
     * @throws Exception if an error occurs during retrieval.
     */
    Task getTaskById(int taskId) throws Exception;

}
