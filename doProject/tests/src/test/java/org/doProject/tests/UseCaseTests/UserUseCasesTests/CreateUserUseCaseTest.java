package org.doProject.tests.UseCaseTests.UserUseCasesTests;


import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;
import org.doProject.core.usecases.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    @Mock // we can do this instead of Mockito.mock(...)
    private UserRepository userRepository;
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserUseCase = new CreateUserUseCase(userRepository);
    }

    @Test
    @DisplayName("Execute with Valid DTO -> Create user succesfull")
    public void executeWithValidUserDTO() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO("Amy Stake");
        when(userRepository.saveUser(any(User.class))).thenReturn(1);

        // Act
        UserDTO result = createUserUseCase.execute(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Amy Stake", result.getUserName());
        verify(userRepository, times(1)).saveUser(any(User.class));
    }

    @Test
    @DisplayName("Execute with empty user name -> Should throw IllegalArgumentException")
    public void executeWithEmptyUserName() throws SQLException {
        // Arrange
        UserDTO userDTO = new UserDTO("");

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userDTO);
        });

        assertEquals("User name can't be empty", exception.getMessage());
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    @DisplayName("Execute with empty/null name -> Should throw Exception")
    public void executeWithNullUserName() throws SQLException {
        // Arrange
        UserDTO userDTO = new UserDTO();

        // Act and assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userDTO);
        });

        assertEquals("User name can't be empty", exception.getMessage());
        verify(userRepository, never()).saveUser(any(User.class));
    }
}
