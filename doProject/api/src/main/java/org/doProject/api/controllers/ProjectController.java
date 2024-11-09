package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.TaskRepository;
import org.doProject.core.usecases.*;
import org.doProject.core.port.ProjectRepository;


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
    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectsByUserUseCase getProjectsByUserUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    /**
     * Constructor for ProjectController.
     * Initializes each use case with the provided ProjectRepository.
     *
     * @param projectRepository an instance of ProjectRepository to handle project data.
     */
    public ProjectController(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.createProjectUseCase = new CreateProjectUseCase(projectRepository);

        GetTasksByProjectUseCase getTasksByProjectUseCase = new GetTasksByProjectUseCase(taskRepository);
        this.getProjectsByUserUseCase = new GetProjectsByUserUseCase(projectRepository, getTasksByProjectUseCase);

        this.updateProjectUseCase = new UpdateProjectUseCase(projectRepository);
        this.deleteProjectUseCase = new DeleteProjectUseCase(projectRepository);
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
        * Creates a new project for a specific user. Expects a JSON body with project details.
        *
        * Path Parameter:
        * - {userId} : ID of the user to whom the project belongs.
        *
        * Example request body:
        * {
        *   "title": "Project Title",
        *   "description": "Project Description"
        * }
        * On success, returns 201 Created and the new project's details.
        */
        app.post("/api/users/{userId}/projects", context -> {
            int userId = Integer.parseInt(context.pathParam("userId"));
            ProjectDTO projectDTO = context.bodyAsClass(ProjectDTO.class);
            try {
                ProjectDTO createdProjectDTO = createProjectUseCase.execute(projectDTO, userId);
                context.status(201).json(createdProjectDTO);
            } catch (Exception e) {
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
         * On success, returns 204 No Content.
         * If the project is not found or validation fails, returns an appropriate error response.
         * If an error occurs during update, returns 500 Internal Server Error.
         */

        app.get("/api/users/{userId}/projects", context -> {
            int userId = Integer.parseInt(context.pathParam("userId"));

            try {
                ArrayList<ProjectDTO> projectDTOs = getProjectsByUserUseCase.execute(userId);
                context.json(projectDTOs);
            } catch (Exception e) {
                context.status(500).result("Error retrieving projects");
            }
        });

        // UPDATE -> an existing project
        /**
        * PUT /api/projects/{id}
        *
        * Updates an existing project by ID. Expects a JSON body with updated project details.
        * Path Parameter:
        * - {id} : ID of the project to update.
        *
        * Example request body:
        * {
        *   "title": "Updated Project Title",
        *   "description": "Updated Project Description"
        * }
        * On success, returns 204 No Content. Returns 500 if an error occurs.
        */
        app.put("/api/projects/{id}", context -> {
            int projectId = Integer.parseInt(context.pathParam("id"));
            ProjectDTO projectDTO = context.bodyAsClass(ProjectDTO.class);

            try {
                updateProjectUseCase.execute(projectId, projectDTO);
                context.status(204);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals("Project not found")) {
                    context.status(404).result(e.getMessage());
                } else {
                    context.status(400).result(e.getMessage());
                }
            } catch (Exception e) {
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
                deleteProjectUseCase.execute(projectId);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with deleting project");
            }
        });
    }
}
