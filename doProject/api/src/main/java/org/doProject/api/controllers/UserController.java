package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;
import org.doProject.core.usecases.CreateUserUseCase;
import org.doProject.core.usecases.DeleteUserUseCase;
import org.doProject.core.usecases.GetUserByIdUseCase;
import org.doProject.core.usecases.UpdateUserUseCase;

/**
 * Handles API requests for user operations including creating, retrieving,
 * updating, and deleting users. Defines API routes for managing user data.
 *
 * Endpoints:
 * - POST /api/users        : Create a new user.
 * - GET /api/users/{id}    : Retrieve user details by ID.
 * - PUT /api/users/{id}    : Update an existing user.
 * - DELETE /api/users/{id} : Delete a user by ID.
 */
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /**
     * Initializes the UserController with the provided UserRepository for managing user data.
     *
     * @param userRepository UserRepository for data storage operations.
     */
    public UserController(UserRepository userRepository) {
        this.createUserUseCase = new CreateUserUseCase(userRepository);
        this.getUserByIdUseCase = new GetUserByIdUseCase(userRepository);
        this.updateUserUseCase = new UpdateUserUseCase(userRepository);
        this.deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    /**
     * Registers user-related API routes with the provided Javalin application.
     *
     * @param app The Javalin application for route registration.
     */
    public void registerRoutes(Javalin app) {

        // CREATE -> new user
        /**
        * POST /api/users
        *
        * Creates a new user with the provided details in the request body.
        * Expects a JSON body with user information.
        *
        * Example request body:
        * {
        *   "userName": "Amy Stake"
        * }
        * On success, returns 201 Created with the new user's details.
        */
        app.post("/api/users", context -> {
            UserDTO userDTO = context.bodyAsClass(UserDTO.class);
            try {
                UserDTO createdUserDTO = createUserUseCase.execute(userDTO);
                context.status(201).json(createdUserDTO);
            } catch (Exception e) {
                e.printStackTrace();
                context.status(500).result("Error creating user: " + e.getMessage());
            }
        });

        // 2. READ: Retrieve user by ID
        /**
         * GET /api/users/{id}
         * Fetches user details by ID.
         * Path parameter: {id} - User ID.
         * Returns user details as JSON, or 404 if not found.
         */
        app.get("/api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            try {
                UserDTO userDTO = getUserByIdUseCase.execute(userId);
                context.json(userDTO);
            }
            catch (Exception e) {
                context.status(404).result(e.getMessage());
            }});

        // UPDATE -> existing user
        /**
        * PUT /api/users/{id}
        *
        * Updates an existing user's details. Expects a JSON body with updated user details.
        * Path Parameter:
        * - {id} : ID of the user to update.
        *
        * Example request body:
        * {
        *   "userName": "UpdatedUserName"
        * }
        * On success, returns 204 No Content. Returns 500 if an error occurs.
        */
        app.put("/api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            UserDTO userDTO = context.bodyAsClass(UserDTO.class);
            try {
                updateUserUseCase.execute(userId, userDTO);
                context.status(204);
            }
            catch (Exception e) {
                context.status(500).result("Error updating user");
            }
        });

        // 4. DELETE a user by ID
        /**
         * DELETE /api/users/{id}
         * Removes a user by ID.
         * Path parameter: {id} - User ID.
         * Returns 204 No Content on success or 500 if an error occurs.
         */
        app.delete("/api/users/{id}", context -> {
            int userId = Integer.parseInt(context.pathParam("id"));
            try {
                deleteUserUseCase.execute(userId);
                context.status(204);
            }
            catch (Exception e) {
                context.status(500).result("Error deleting user");
            }
        });
    }
}
