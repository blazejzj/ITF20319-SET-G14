package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.infrastructure.domain.LocalDatabase;

/**
 * UserController is mainly responsible for handling API requests related to users.
 * This includes creating, retrieving, updating, and deleting user records.
 *
 * The controller is definning routes and logic for each endpoint, using a LocalDatabase
 * instance to store and control/manage user data. This makes it easier to interact with
 * user information through API calls.
 *
 * Endpoints this API provides:
 * - POST /api/users      : Creates a new user.
 * - GET /api/users/{id}  : Retrieves user details by user ID.
 * - PUT /api/users/{id}  : Updates an existing user.
 * - DELETE /api/users/{id} : Deletes a user by their ID.
 */
public class UserController {

    private final LocalDatabase localDatabase;

    /**
     * Constructor for UserController.
     *
     * @param localDatabase An instance of LocalDatabase that handles user data.
     */
    public UserController(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    /**
     * Registers routes related to user operations on the Javalin application.
     *
     * @param app The Javalin application where routes are registered.
     */
    public void registerRoutes(Javalin app) {

        // 1. CREATE a new user
        /**
         * POST /api/users
         *
         * This endpoint allows the creation of a new user.
         * Expects a JSON body with user details.
         *
         * Example request body:
         * {
         *   "userName": "JohnDoe"
         * }
         *
         * On success, returns 201 Created and the new user's details.
         */
        app.post("api/users", context -> {
            UserDTO userDTO = context.bodyAsClass(UserDTO.class);
            int userId = localDatabase.saveUser(userDTO.getUserName());
            userDTO.setId(userId);
            context.status(201).json(userDTO);
        });

        // 2. READ: Retrieve user by ID
        /**
         * GET /api/users/{id}
         *
         * Retrieves the details of a user by their ID.
         *
         * Path Parameter:
         * - {id} : ID of the user to retrieve.
         *
         * On success, returns the user details as JSON.
         * If the user is not found, returns 404 Not Found with an error message.
         */
        app.get("api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            User user = localDatabase.loadUser(userId);
            if (user == null) {
                context.status(404).result("User has not been found!");
            } else {
                UserDTO userDTO = new UserDTO(user.getId(), user.getUserName());
                context.json(userDTO);
            }
        });

        // 3. UPDATE an existing user
        /**
         * PUT /api/users/{id}
         *
         * Updates the details of an existing user by ID.
         * Expects a JSON body with updated user details.
         *
         * Path Parameter:
         * - {id} : ID of the user to update.
         *
         * On success, returns 204 No Content.
         * If an error occurs during update, returns 500 Internal Server Error.
         */
        app.put("api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            UserDTO userDTO = context.bodyAsClass(UserDTO.class);
            User user = new User(userId, userDTO.getUserName());

            try {
                localDatabase.updateUser(user);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with updating the user");
            }
        });

        // 4. DELETE a user by ID
        /**
         * DELETE /api/users/{id}
         *
         * Deletes a user from the database by their ID.
         *
         * Path Parameter:
         * - {id} : ID of the user to delete.
         *
         * On success, returns 204 No Content.
         * If an error occurs during deletion, returns 500 Internal Server Error.
         */
        app.delete("api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            try {
                localDatabase.deleteUser(userId);
                context.status(204);
            } catch (Exception e) {
                context.status(500).result("Error with deleting the user");
            }
        });
    }
}
