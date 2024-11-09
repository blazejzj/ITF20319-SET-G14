package org.doProject.tests.UseCaseTests.ProjectUseCasesTests;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;
import org.doProject.core.usecases.GetProjectsByUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProjectsByUserUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;
    private GetProjectsByUserUseCase getProjectsByUserUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getProjectsByUserUseCase = new GetProjectsByUserUseCase(projectRepository);
    }

    @Test
    @DisplayName("Execute with valid user ID -> Gets projects successfully")
    public void executeWithValidUserId() throws Exception {
        // Arrange
        int userId = 1;
        List<Project> projects = Arrays.asList(
                new Project(1, "Title 1", "Description 1", userId),
                new Project(2, "Title 2", "Description 2", userId)
        );
        when(projectRepository.loadUserProjects(userId)).thenReturn(new ArrayList<>(projects));

        // Act
        List<ProjectDTO> result = getProjectsByUserUseCase.execute(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Title 2", result.get(1).getTitle());
        verify(projectRepository, times(1)).loadUserProjects(userId);
    }

    @Test
    @DisplayName("Execute with user ID that has no projects -> Should return empty list")
    public void executeWithUserIdHavingNoProjects() throws Exception {
        // Arrange
        int userId = 1;
        when(projectRepository.loadUserProjects(userId)).thenReturn(new ArrayList<>());

        // Act
        List<ProjectDTO> result = getProjectsByUserUseCase.execute(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).loadUserProjects(userId);
    }
}
