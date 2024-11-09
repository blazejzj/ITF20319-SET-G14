package org.doProject.tests.UseCaseTests.UserUseCasesTests;

import org.doProject.core.domain.User;
import org.doProject.core.port.UserRepository;
import org.doProject.core.usecases.DeleteUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteUserUseCase = new DeleteUserUseCase(userRepository);
    }

    @Test
    @DisplayName("Execute with valid user ID -> Delete user successfully")
    public void executeWithValidUserId() throws Exception {
        // arrange
        int userId = 1;
        User user = new User(userId, "Massive Joe");
        when(userRepository.loadUser(userId)).thenReturn(user);

        // act
        deleteUserUseCase.execute(userId);

        //assert
        verify(userRepository, times(1)).loadUser(userId);
        verify(userRepository, times(1)).deleteUser(userId);
    }

    @Test
    @DisplayName("Execute with non-existing user ID -> Should throw Exception")
    public void executeWithNonExistingUserId() throws Exception {
        // arrange
        int userId = 2;
        when(userRepository.loadUser(userId)).thenReturn(null);

        // act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            deleteUserUseCase.execute(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).loadUser(userId);
        verify(userRepository, never()).deleteUser(anyInt());
    }

    @Test
    @DisplayName("Execute with invalid user ID (negative ID) -> Should throw Exception")
    public void executeWithInvalidUserId() throws Exception {

        // arrrange
        int userId = -1;

        // act and asser
        Exception exception = assertThrows(Exception.class, () -> {
            deleteUserUseCase.execute(userId);
        });

        assertEquals("Invalid user id", exception.getMessage());
        verify(userRepository, never()).loadUser(anyInt());
        verify(userRepository, never()).deleteUser(anyInt());
    }
}
