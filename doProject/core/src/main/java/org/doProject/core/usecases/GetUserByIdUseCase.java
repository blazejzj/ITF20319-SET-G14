package org.doProject.core.usecases;

import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;

/**
 * Retrieves a user by their ID, returning the user’s details.
 *
 * Throws an exception if the user is not found.
 */
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs GetUserByIdUseCase with the provided repository.
     *
     * @param userRepository UserRepository for user data handling.
     */
    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return a UserDTO with the user’s details.
     * @throws Exception if the user is not found or an error occurs during retrieval.
     */
    public UserDTO execute(int userId) throws Exception {
        if (userId < 0) {
            throw new Exception("Invalid user id");
        }

        User user = userRepository.loadUser(userId);
        if (user == null) {
            throw new Exception("User not found");
        }


        return new UserDTO(user.getId(), user.getUserName());
    }
}
