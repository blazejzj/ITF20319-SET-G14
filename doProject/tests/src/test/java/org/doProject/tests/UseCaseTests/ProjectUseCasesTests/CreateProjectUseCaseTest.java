package org.doProject.tests.UseCaseTests.ProjectUseCasesTests;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;
import org.doProject.core.usecases.CreateProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProjectUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;
    private CreateProjectUseCase createProjectUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createProjectUseCase = new CreateProjectUseCase(projectRepository);
    }

    @Test
    @DisplayName("Execute with valid project title -> Creates project successfully")
    public void executeWithValidProjectTitle() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO("New Project", "Fun Description", 1);
        int userId = 1;
        int projectId = 123;
        when(projectRepository.saveProject(any(Project.class))).thenReturn(projectId);

        ProjectDTO result = createProjectUseCase.execute(projectDTO, userId);

        assertNotNull(result);
        assertEquals(projectId, result.getId());
        verify(projectRepository, times(1)).saveProject(any(Project.class));
    }

    @Test
    @DisplayName("Execute with empty project title -> Should throw IllegalArgumentException")
    public void executeWithEmptyProjectTitle() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO("  ", "Project Descriptionne", 1);
        int userId = 1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createProjectUseCase.execute(projectDTO, userId);
        });
        assertEquals("Project title cannot be empty", exception.getMessage());
        verify(projectRepository, never()).saveProject(any(Project.class));
    }
}
