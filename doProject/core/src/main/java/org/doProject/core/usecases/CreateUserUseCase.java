package org.doProject.core.usecases;

import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;

/**
 * Creates a new user in the repository, validating user details.
 *
 * Ensures:
 * - User name is not empty.
 * - Associates the new user with an empty project list.
 *
 * Throws IllegalArgumentException if validation fails and returns the created user as UserDTO.
 */
public class CreateUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs CreateUserUseCase with the provided repository.
     *
     * @param userRepository UserRepository for user data handling.
     */
    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param userDTO the UserDTO containing user details.
     * @return a UserDTO with the created user's details.
     * @throws Exception if an error occurs during creation or if the user name is empty.
     */
    public UserDTO execute(UserDTO userDTO) throws Exception {
        System.out.println("Executing CreateUserUseCase with userDTO: " + userDTO.getUserName());
        if (userDTO.getUserName() == null || userDTO.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name can't be empty");
        }

        User user = new User(userDTO.getUserName());
        int userId = userRepository.saveUser(user);
        userDTO.setId(userId);
        return userDTO;
    }
}
