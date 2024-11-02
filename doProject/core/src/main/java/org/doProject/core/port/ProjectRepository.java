package org.doProject.core.port;

import org.doProject.core.domain.Project;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProjectRepository {

    /**
     * Inserts a new project for a specified user into the Projects table.
     * @param title the title of the project.
     * @param description the description of the project.
     * @param userId the ID of the user associated with the project.
     * @return the generated project ID.
     * @throws SQLException if a database access error occurs.
     */
    int saveProject(String title, String description, int userId) throws SQLException;

    /**
     * Inserts a new project for a specified user into the Projects table.
     * @param project The Project object to save.
     * @return The generated project ID.
     * @throws SQLException if a database access error occurs.
     */
    int saveProject(Project project) throws SQLException;


    /**
     * Retrieves all projects associated with a specific user ID from the Projects table.
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return a list of Project objects associated with the user.
     * @throws SQLException if a database access error occurs.
     */
    ArrayList<Project> loadUserProjects(int userId) throws SQLException;


    /**
     * Updates an existing projects title and description.
     * @param projectId the ID of the project to update.
     * @param newTitle the new title for the project.
     * @param newDescription the new description for the project.
     * @throws SQLException if the update fails or no project is found with the given ID.
     */
    void updateProject(int projectId, String newTitle, String newDescription) throws SQLException;

    /**
     * Updates an existing projects title and description.
     * @param project The Project object containing updated information.
     * @throws SQLException if the update fails or no project is found with the given ID.
     */
    void updateProject(Project project) throws SQLException;

    /**
     * Deletes a project from the database, together with any associated tasks.
     * @param projectID the ID of the project to delete.
     * @throws SQLException if the project does not exist or a database access error occurs.
     */
    void deleteProject(int projectID) throws SQLException;
}
