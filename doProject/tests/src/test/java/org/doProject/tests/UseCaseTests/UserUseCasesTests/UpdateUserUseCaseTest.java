package org.doProject.tests.UseCaseTests.UserUseCasesTests;

import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;
import org.doProject.core.usecases.UpdateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserUseCase = new UpdateUserUseCase(userRepository);
    }

    @Test
    @DisplayName("Execute with valid user ID and name -> Update user successfully")
    public void executeWithValidUserIdAndName() throws Exception {
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "Barb Dwyer");
        User existingUser = new User(userId, "Mike Rotch");

        when(userRepository.loadUser(userId)).thenReturn(existingUser);

        updateUserUseCase.execute(userId, userDTO);

        verify(userRepository, times(1)).loadUser(userId);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).updateUser(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(userId, capturedUser.getId());
        assertEquals("Barb Dwyer", capturedUser.getUserName());
    }

    @Test
    @DisplayName("Execute with non-existing user ID -> Should throw Exception")
    public void executeWithNonExistingUserId() throws Exception {
        int userId = 2;
        UserDTO userDTO = new UserDTO(userId, "Hai Howie Yu");

        when(userRepository.loadUser(userId)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            updateUserUseCase.execute(userId, userDTO);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).loadUser(userId);
        verify(userRepository, never()).updateUser(any(User.class));
    }

    @Test
    @DisplayName("Execute with empty user name -> Should throw IllegalArgumentException")
    public void executeWithEmptyUserName() throws Exception {
        int userId = 1;

        UserDTO userDTO = new UserDTO(userId, "  ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateUserUseCase.execute(userId, userDTO);
        });

        assertEquals("User name can't be empty", exception.getMessage());
        verify(userRepository, never()).loadUser(anyInt());
        verify(userRepository, never()).updateUser(any(User.class));
    }
}
