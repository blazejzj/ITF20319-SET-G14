package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.infrastructure.domain.LocalDatabase;

import java.util.ArrayList;

/**
 * TaskController is responsible for handling API requests related to tasks.
 * This includes creating, retrieving, updating, and deleting task records.
 *
 * The controller defines routes and logic for each endpoint, using a LocalDatabase
 * instance to store and manage task data. This makes it easier to interact with
 * task information through API calls.
 *
 * Endpoints this API provides:
 * - POST /api/projects/{projectId}/tasks      : Creates a new task within a project.
 * - GET /api/projects/{projectId}/tasks       : Retrieves all tasks for a project.
 * - PUT /api/tasks/{id}                       : Updates an existing task.
 * - DELETE /api/tasks/{id}                    : Deletes a task by its ID.
 */

public class TaskController {

    private final LocalDatabase localDatabase;

    /**
     * Constructor for TaskController.
     *
     * @param localDatabase An instance of LocalDatabase that handles task data.
     */
    public TaskController(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    /**
     * Registers routes related to task operations on the Javalin application.
     *
     * @param app The Javalin application where routes are registered.
     */
    public void registerRoutes(Javalin app) {

        // CREATE -> new task
        /**
         * POST /api/projects/{projectId}/tasks
         *
         * This endpoint will allow the creation of a new task within a project.
         * We expect a JSON body with task details.
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
         *
         * On success, returns 201 Created and the new tasks details.
         */
        app.post("/api/projects/{projectId}/tasks", context -> {
            int projectId = Integer.parseInt(context.pathParam("projectId"));

            TaskDTO taskDTO = context.bodyAsClass(TaskDTO.class);

            // ultimately we dont want tasks with empty names
            if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
                context.status(400).result("Task title cant be empty");
                return;
            }

            Task task = new Task(
                    taskDTO.getTitle(),
                    taskDTO.getDescription(),
                    taskDTO.getDueDate(),
                    taskDTO.getIsFinished(),
                    taskDTO.getIsRepeating(),
                    taskDTO.getRepeatDays()
            );

            try {
                int taskId = localDatabase.saveTask(task, projectId);
                task.setTaskID(taskId);
                taskDTO.setTaskID(taskId);
                context.status(201).json(taskDTO);
            }
            catch (Exception e) {
                context.status(500).result("Error with a creating task");
            }
        });

        // READ -> Retrieve tasks by project ID
        /**
         * GET /api/projects/{projectId}/tasks
         *
         * Retrieves all tasks associated with a specific project ID.
         *
         * Path Parameter:
         * - {projectId} : ID of the project whose tasks are to be retrieved.
         *
         * On success, returns the list of tasks as JSON.
         * If the project has no tasks or does not exist return an empty list.
         */
        app.get("/api/projects/{projectId}/tasks", context -> {
            int projectId = Integer.parseInt(context.pathParam("projectId"));

            try {
                ArrayList<Task> tasks = localDatabase.loadTasks(projectId);

                ArrayList<TaskDTO> taskDTOs = new ArrayList<>();
                for (Task task : tasks) {
                    TaskDTO taskDTO = new TaskDTO(
                            task.getId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getDueDate(),
                            task.getIsFinished(),
                            task.getIsRepeating(),
                            task.getRepeatDays()
                    );
                    taskDTOs.add(taskDTO);
                }
                context.json(taskDTOs);
            } catch (Exception e) {
                context.status(500).result("Error with retrieving tasks");
            }
        });

        // UPDATE -> existing task
        /**
         * PUT /api/tasks/{id}
         *
         * Updates the details of an existing task by ID.
         * Expects a JSON body with updated task details.
         *
         * Path Parameter:
         * - {id} : ID of the task to update.
         *
         * On success, return 204.
         * If an error occurs on update return server error.
         */
        app.put("/api/tasks/{id}", context -> {
            int taskId = Integer.parseInt(context.pathParam("id"));

            TaskDTO taskDTO = context.bodyAsClass(TaskDTO.class);

            // we dont want to allow empty names in tasks
            if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
                context.status(400).result("Task title cant be empty");
                return;
            }

            Task task = new Task(
                    taskId,
                    taskDTO.getTitle(),
                    taskDTO.getDescription(),
                    taskDTO.getDueDate(),
                    taskDTO.getIsFinished(),
                    taskDTO.getIsRepeating(),
                    taskDTO.getRepeatDays()
            );

            try {
                localDatabase.updateTask(task);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with updating task");
            }
        });

        // DELETE -> task by ID
        /**
         * DELETE /api/tasks/{id}
         *
         * Deletes a task from the database by its ID.
         *
         * Path Parameter:
         * - {id} : ID of the task to delete.
         *
         * On success, returns 204 No Content.
         * If an error occurs during deletion, returns 500 Internal Server Error.
         */
        app.delete("/api/tasks/{id}", context -> {
            int taskId = Integer.parseInt(context.pathParam("id"));

            try {
                localDatabase.deleteTask(taskId);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with deleting task");
            }
        });
    }
}
