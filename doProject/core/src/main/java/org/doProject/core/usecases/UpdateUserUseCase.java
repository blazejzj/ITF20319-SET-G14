package org.doProject.core.usecases;

import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;

/**
 * Updates an existing user's details, validating input and user existence.
 *
 * Ensures:
 * - User name is not empty.
 * - User exists in the repository before updating.
 *
 * Throws IllegalArgumentException if the name is empty and an exception if the user is not found.
 */
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs UpdateUserUseCase with the provided repository.
     *
     * @param userRepository UserRepository for user data handling.
     */
    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates a user by ID with the details provided in UserDTO.
     *
     * @param userId  the ID of the user to update.
     * @param userDTO UserDTO containing updated user details.
     * @throws Exception if an error occurs during update, if the name is empty, or if the user is not found.
     */
    public void execute(int userId, UserDTO userDTO) throws Exception {
        if (userDTO.getUserName() == null || userDTO.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name can't be empty");
        }

        User existingUser = userRepository.loadUser(userId);
        if (existingUser == null) {
            throw new Exception("User not found");
        }

        User updatedUser = new User(userId, userDTO.getUserName());
        userRepository.updateUser(updatedUser);
    }
}
