package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.usecases.*;
import org.doProject.core.port.TaskRepository;

import java.util.ArrayList;

/**
 * Manages API requests for tasks, including creation, retrieval, updating, and deletion.
 *
 * Endpoints:
 * - POST /api/projects/{projectId}/tasks : Create a new task within a project.
 * - GET /api/projects/{projectId}/tasks  : Retrieve all tasks for a project.
 * - PUT /api/tasks/{id}                  : Update an existing task.
 * - DELETE /api/tasks/{id}               : Delete a task by its ID.
 */
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTasksByProjectUseCase getTasksByProjectUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    /**
     * Initializes the TaskController with the provided TaskRepository.
     *
     * @param taskRepository TaskRepository for task data operations.
     */
    public TaskController(TaskRepository taskRepository) {
        this.createTaskUseCase = new CreateTaskUseCase(taskRepository);
        this.getTasksByProjectUseCase = new GetTasksByProjectUseCase(taskRepository);
        this.updateTaskUseCase = new UpdateTaskUseCase(taskRepository);
        this.deleteTaskUseCase = new DeleteTaskUseCase(taskRepository);
    }

    /**
     * Registers task-related API routes.
     *
     * @param app Javalin application for route registration.
     */
    public void registerRoutes(Javalin app) {

        // CREATE -> new task
        /**
        * POST /api/projects/{projectId}/tasks
        *
        * Creates a new task within a project. Expects a JSON body with task details.
        *
        * Path Parameter:
        * - {projectId} : ID of the project to which the task belongs.
        *
        * Example request body:
        * {
        *   "title": "Task Title",
        *   "description": "Task Description",
        *   "dueDate": "2023-11-05",
        *   "isFinished": 0,
        *   "isRepeating": 0,
        *   "repeatDays": 0
        * }
        * On success, returns 201 Created and the new task's details.
        * */
        app.post("/api/projects/{projectId}/tasks", context -> {
            int projectId = Integer.parseInt(context.pathParam("projectId"));
            TaskDTO taskDTO = context.bodyAsClass(TaskDTO.class);

            try {
                TaskDTO createdTaskDTO = createTaskUseCase.execute(taskDTO, projectId);
                context.status(201).json(createdTaskDTO);
            } catch (Exception e) {
                context.status(500).result("Error creating task");
            }
        });

        // READ -> Retrieve tasks by project ID
        /**
         * GET /api/projects/{projectId}/tasks
         * Retrieves tasks for a specific project.
         * Path Parameter: {projectId} - Project ID.
         * Returns a list of tasks as JSON, or an empty list if no tasks exist.
         */
        app.get("/api/projects/{projectId}/tasks", context -> {
            int projectId = Integer.parseInt(context.pathParam("projectId"));

            try {
                ArrayList<TaskDTO> taskDTOs = getTasksByProjectUseCase.execute(projectId);
                context.json(taskDTOs);
            } catch (Exception e) {
                context.status(500).result("Error retrieving tasks");
            }
        });

        // UPDATE -> existing task
        /**
        * PUT /api/tasks/{id}
        *
        * Updates an existing task by ID with the data provided in the request body.
        * Path Parameter:
        * - {id} : ID of the task to update.
        *
        * Example request body:
        * {
        *   "title": "Updated Task Title",
        *   "description": "Updated Task Description",
        *   "dueDate": "2023-12-10",
        *   "isFinished": 1,
        *   "isRepeating": 0,
        *   "repeatDays": 0
        * }
        * On success, returns 204 No Content. Returns 500 on server error.
        */
        app.put("/api/tasks/{id}", context -> {
            int taskId = Integer.parseInt(context.pathParam("id"));
            TaskDTO taskDTO = context.bodyAsClass(TaskDTO.class);

            try {
                updateTaskUseCase.execute(taskId, taskDTO);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error updating task");
            }
        });

        // DELETE -> task by ID
        /**
         * DELETE /api/tasks/{id}
         * Deletes a task by ID.
         * Path Parameter: {id} - Task ID.
         * Returns 204 No Content on success or 500 for server error.
         */
        app.delete("/api/tasks/{id}", context -> {
            int taskId = Integer.parseInt(context.pathParam("id"));

            try {
                deleteTaskUseCase.execute(taskId);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error deleting task");
            }
        });
    }
}
