package org.doProject.tests.UseCaseTests.TaskUseCasesTests;

import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;
import org.doProject.core.usecases.GetTasksByProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetTasksByProjectUseCaseTest {

    @Mock
    private TaskRepository taskRepository;
    private GetTasksByProjectUseCase getTasksByProjectUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getTasksByProjectUseCase = new GetTasksByProjectUseCase(taskRepository);
    }

    @Test
    @DisplayName("Execute with valid project ID -> Gerts tasks successfully")
    public void executeWithValidProjectId() throws Exception {
        // Arrange
        int projectId = 1;
        List<Task> tasks = Arrays.asList(
                new Task(1, "Task 1", "Description 1", LocalDate.now(), 0, 1, 7),
                new Task(2, "Task 2", "Description 2", LocalDate.now(), 1, 0, 0)
        );
        when(taskRepository.loadTasks(projectId)).thenReturn(new ArrayList<>(tasks));

        // Act
        List<TaskDTO> result = getTasksByProjectUseCase.execute(projectId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskRepository, times(1)).loadTasks(projectId);
    }

    @Test
    @DisplayName("Execute with project ID that has no tasks -> Should return empty list")
    public void executeWithProjectIdHavingNoTasks() throws Exception {
        // Arrange
        int projectId = 1;
        when(taskRepository.loadTasks(projectId)).thenReturn(new ArrayList<>());

        // Act
        List<TaskDTO> result = getTasksByProjectUseCase.execute(projectId);

        // Assert
        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).loadTasks(projectId);
    }
}
