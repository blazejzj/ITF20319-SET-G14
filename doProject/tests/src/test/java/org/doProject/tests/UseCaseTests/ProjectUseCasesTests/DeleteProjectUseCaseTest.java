package org.doProject.tests.UseCaseTests.ProjectUseCasesTests;

import org.doProject.core.port.ProjectRepository;
import org.doProject.core.usecases.DeleteProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProjectUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;
    private DeleteProjectUseCase deleteProjectUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteProjectUseCase = new DeleteProjectUseCase(projectRepository);
    }

    @Test
    @DisplayName("Execute with valid project ID -> Deletes project successfully")
    public void executeWithValidProjectId() throws Exception {
        int projectId = 420;

        deleteProjectUseCase.execute(projectId);

        verify(projectRepository, times(1)).deleteProject(projectId);
    }

    @Test
    @DisplayName("Execute with non-existing project ID -> Should throw SQLException")
    public void executeWithNonExistingProjectId() throws Exception {
        int projectId = 999;
        doThrow(new SQLException("Project not found")).when(projectRepository).deleteProject(projectId);

        SQLException exception = assertThrows(SQLException.class, () -> {
            deleteProjectUseCase.execute(projectId);
        });
        assertEquals("Project not found", exception.getMessage());
        verify(projectRepository, times(1)).deleteProject(projectId);
    }
}
