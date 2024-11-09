package org.doProject.tests.UseCaseTests.UserUseCasesTests;

import org.doProject.core.domain.User;
import org.doProject.core.dto.UserDTO;
import org.doProject.core.port.UserRepository;
import org.doProject.core.usecases.GetUserByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;
    private GetUserByIdUseCase getUserByIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getUserByIdUseCase = new GetUserByIdUseCase(userRepository);
    }

    @Test
    @DisplayName("Execute with valid user ID -> Retrieve user successfully")
    public void executeWithValidUserId() throws Exception {
        // Arrange
        User user = new User(1, "Zoltan Pepper");
        when(userRepository.loadUser(1)).thenReturn(user);

        // Act
        UserDTO result = getUserByIdUseCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Zoltan Pepper", result.getUserName());
        verify(userRepository, times(1)).loadUser(1);
    }

    @Test
    @DisplayName("Execute with non-existing user Id -> Should throw Exception")
    public void executeWithNonExistingUserId() throws Exception {
        // Arrange
        when(userRepository.loadUser(2)).thenReturn(null);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            getUserByIdUseCase.execute(2);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).loadUser(2);
    }

    @Test
    @DisplayName("Execute with invalid user ID (negative ID) -> Should throw Exception")
    public void executeWithInvalidUserId() throws Exception {

        // act and asert
        Exception exception = assertThrows(Exception.class, () -> {
            getUserByIdUseCase.execute(-1);
        });

        assertEquals("Invalid user id", exception.getMessage());
        verify(userRepository, never()).loadUser(anyInt());
    }
}
