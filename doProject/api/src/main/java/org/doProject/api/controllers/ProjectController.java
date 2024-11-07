package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.infrastructure.domain.LocalDatabase;

import java.util.ArrayList;

/**
 * ProjectController is responsible for handling API requests related to projects.
 * This includes creating, retrieving, updating and deleting project records.
 *
 * The controller defines routes and logic for each endpoint, using a LocalDatabase
 * instance to store and manage project data. This makes it easier to interact with project information
 * through API calls.
 *
 * Endpoints this API provides:
 * - POST /api/users/{userId}/projects     : Creates a new project for a user.
 * - GET /api/users/{userId}/projects      : Retrieves all projects for a user.
 * - PUT /api/projects/{id}                : Updates an existing project.
 * - DELETE /api/projects/{id}             : Deletes a project by its ID.
 */
public class ProjectController {
    private final LocalDatabase localDatabase;

    /**
     * Constructor for ProjectController.
     * @param localDatabase An instance of LocalDatabase that handles project data.
     */
    public ProjectController(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    /**
     * Registers routes related to project operations on the Javalin application.
     *
     * @param app the Javalin application where routes are registered.
     */
    public void registerRoutes(Javalin app) {

        // CREATE -> a new project
        /**
         * POST /api/users/{userId}/projects
         *
         * This endpoint allows the creation of a new project for a user.
         * Expects a JSON body with project details.
         *
         * Path Parameter:
         * - {userId} : ID of the user to who the project belongs.
         *
         * Example request body:
         * {
         *   "title": "Project Title",
         *   "description": "Project Description"
         * }
         *
         * On success return 201 - Created and the new projects details.
         */
        app.post("/api/users/{userId}/projects", context -> {
           int userId = Integer.parseInt(context.pathParam("userId"));

           ProjectDTO projectDTO = context.bodyAsClass(ProjectDTO.class);

           // we dont allow projects with empty names
            if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
                context.status(400).result("Project name cant be empty");
            }

            Project project = new Project(
                    projectDTO.getTitle(),
                    projectDTO.getDescription(),
                    userId
            );

            try {
                int projetId = localDatabase.saveProject(project);
                project.setId(projetId);
                projectDTO.setId(projetId);
                context.status(201).json(projectDTO);
            }
            catch (Exception e) {
                context.status(500).result("Error with creating projects");
            }
        });

        // READ -> Retrieve projects by user ID
        /**
         * GET /api/users/{userId}/projects
         *
         * Retrieves all projects associated with a specific user ID.
         *
         * Path Parameter:
         * - {userId} : ID of the user whose projects are to be retrieved.
         *
         * On success, returns the list of projects as JSON.
         * If the user has no projects or does not exist, returns an empty list.
         */

        app.get("/api/users/{userId}/projects", context -> {
            int userId = Integer.parseInt(context.pathParam("userId"));

            try {
                ArrayList<Project> projects = localDatabase.loadUserProjects(userId);

                ArrayList<ProjectDTO> projectDTOs = new ArrayList<>();

                for (Project project : projects) {
                    ProjectDTO projectDTO = new ProjectDTO (
                            project.getId(),
                            project.getTitle(),
                            project.getDescription(),
                            userId
                    );
                    projectDTOs.add(projectDTO);
                }

                context.json(projectDTOs);
            } catch (Exception e) {
                context.status(500).result("Error retrieving projects");
            }
        });

        // UPDATE -> an existing project
        /**
         * PUT /api/projects/{id}
         *
         * Updates the details of an existing project by ID.
         * Expects a JSON body with updated project details.
         *
         * Path Parameter:
         * - {id} : ID of the project to update.
         *
         * On success, returns 204 No Content.
         * If an error occurs during update, returns 500 Internal Server Error.
         */
        app.put("/api/projects/{id}", context -> {
            int projectId = Integer.parseInt(context.pathParam("id"));

            ProjectDTO projectDTO = context.bodyAsClass(ProjectDTO.class);

            if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
                context.status(400).result("Project title cant be empty");
                return;
            }

            try {
                // Retrieve the existing project to get the userId
                Project existingProject = localDatabase.getProjectById(projectId);

                if (existingProject == null) {
                    context.status(404).result("Project not found");
                    return;
                }

                // Use the userId from the existing project
                int userId = existingProject.getUserID();

                // Create a Project object with the existing userId
                Project updatedProject = new Project(
                        projectId,
                        projectDTO.getTitle(),
                        projectDTO.getDescription(),
                        userId
                );

                // Update the project in the database
                localDatabase.updateProject(updatedProject);

                // Return 204 No Content on successful update
                context.status(204);
            } catch (Exception e) {
                // Handle exceptions and return 500 Internal Server Error
                context.status(500).result("Error updating project");
            }
        });



        // DELETE -> a project by ID
        /**
         * DELETE /api/projects/{id}
         *
         * Deletes a project from the database by its ID.
         *
         * Path Parameter:
         * - {id} : ID of the project to delete.
         *
         * On success, returns 204 No Content.
         * If an error occurs during deletion, returns 500 Internal Server Error.
         */
        app.delete("/api/projects/{id}", context -> {
            int projectId = Integer.parseInt(context.pathParam("id"));

            try {
                localDatabase.deleteProject(projectId);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with deleting project");
            }
        });
    }
}
