package org.doProject.tests.UseCaseTests.ProjectUseCasesTests;


import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;
import org.doProject.core.usecases.UpdateProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProjectUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;
    private UpdateProjectUseCase updateProjectUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        updateProjectUseCase = new UpdateProjectUseCase(projectRepository);
    }

    @Test
    @DisplayName("Execute with valid project ID and title -> Updates project successfully")
    public void executeWithValidProjectIdAndTitle() throws Exception {
        int projectId = 1;

        ProjectDTO projectDTO = new ProjectDTO("Update Title", "Updated some text", 1);
        Project existingProject = new Project(projectId, "Ol' Title", "Ol' Description", 1);
        when(projectRepository.getProjectById(projectId)).thenReturn(existingProject);

        updateProjectUseCase.execute(projectId, projectDTO);

        verify(projectRepository, times(1)).getProjectById(projectId);
        verify(projectRepository, times(1)).updateProject(any(Project.class));
    }

    @Test
    @DisplayName("Execute with empty project title -> Should throw IllegalArgumentException")
    public void executeWithEmptyProjectTitle() throws Exception {
        int projectId = 1;

        ProjectDTO projectDTO = new ProjectDTO("  ", "Updated Description", 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateProjectUseCase.execute(projectId, projectDTO);
        });
        assertEquals("Project title cannot be empty", exception.getMessage());
        verify(projectRepository, never()).getProjectById(anyInt());
        verify(projectRepository, never()).updateProject(any(Project.class));
    }

    @Test
    @DisplayName("Execute with non-existing project ID -> Should throw SQLException")
    public void executeWithNonExistingProjectId() throws Exception{
        int projectId = 69696969;

        ProjectDTO projectDTO = new ProjectDTO("Super Awesome Title", "Fresh Description, like me", 1);
        when(projectRepository.getProjectById(projectId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateProjectUseCase.execute(projectId, projectDTO);
        });
        assertEquals("Project not found", exception.getMessage());
        verify(projectRepository, times(1)).getProjectById(projectId);
        verify(projectRepository, never()).updateProject(any(Project.class));
    }
}
