package org.doProject.core.port;
import org.doProject.core.domain.User;
import java.sql.SQLException;

public interface UserRepository {
    /**
     * Saves a new user to the Users table.
     * @param name is the name of the user.
     * @return the generated user ID.
     * @throws SQLException if a database access error occurs.
     */
    int saveUser(String name) throws SQLException;

    /**
     * Saves a new user to the Users table.
     * @param user The User object to save.
     * @return The generated user ID.
     * @throws SQLException if a database access error occurs.
     */
    int saveUser(User user) throws SQLException;

    /**
     * Loads a user by their ID from the Users table.
     * @param userId the ID of the user to load.
     * @return the User object if found, or null if user is not found.
     * @throws SQLException if a database access error occurs.
     */
    User loadUser(int userId) throws SQLException;

    /**
     * Updates an existing users name in the Users table.
     * @param id the ID of the user to update.
     * @param newName the new name for the user.
     * @throws SQLException if the update fails or no user is found with the given ID.
     */
    void updateUser(int id, String newName) throws SQLException;

    /**
     * Updates an existing user's name in the Users table.
     * @param user The User object containing updated information.
     * @throws SQLException if the update fails or no user is found with the given ID.
     */
    void updateUser(User user) throws SQLException;

    /**
     * Deletes a user from the database together with their associated projects and tasks.
     * @param userId the ID of the user to delete.
     * @throws SQLException if no user with the specified ID exists.
     */
    void deleteUser(int userId) throws SQLException;

}
