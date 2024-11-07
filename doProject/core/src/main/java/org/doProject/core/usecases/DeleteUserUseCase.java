// src/main/java/org/doProject/core/usecases/DeleteUserUseCase.java
package org.doProject.core.usecases;


import org.doProject.core.domain.User;
import org.doProject.core.port.UserRepository;

/**
 * Delettes a user by their ID, ensuring the user exists before deletion.
 *
 * Throws an exception if the user is not found.
 */
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs DeleteUserUseCase with the provided repository.
     *
     * @param userRepository UserRepository for user data handling.
     */
    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete.
     * @throws Exception if the user is not found or if an error occurs during deletion.
     */
    public void execute(int userId) throws Exception {
        User existingUser = userRepository.loadUser(userId);
        if (existingUser == null) {
            throw new Exception("User not found");
        }

        userRepository.deleteUser(userId);
    }
}
