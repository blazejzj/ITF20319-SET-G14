package org.doProject.tests.UseCaseTests.TaskUseCasesTests;

import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;
import org.doProject.core.usecases.UpdateTaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;
    private UpdateTaskUseCase updateTaskUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        updateTaskUseCase = new UpdateTaskUseCase(taskRepository);
    }

    @Test
    @DisplayName("Execute with valid task ID and title -> Should update task successfully")
    public void executeWithValidTaskIdAndTitle() throws Exception {
        // Arrange
        int taskId = 1;
        TaskDTO taskDTO = new TaskDTO(taskId, "Updated my title", "Updated some text here", LocalDate.now(), 0, 1, 7);
        Task existingTask = new Task(taskId, "Good ol' title", "Not good ol' description", LocalDate.now(), 0, 1, 7);
        when(taskRepository.getTaskById(taskId)).thenReturn(existingTask);

        // Act
        updateTaskUseCase.execute(taskId, taskDTO);

        // Assert
        verify(taskRepository, times(1)).getTaskById(taskId);
        verify(taskRepository, times(1)).updateTask(any(Task.class));
    }

    @Test
    @DisplayName("Execute with empty task title -> Should throw Exception")
    public void executeWithEmptyTaskTitle() throws  Exception {
        // Arrange
        int taskId = 1;
        TaskDTO taskDTO = new TaskDTO(taskId, "  ", "description", LocalDate.now(), 0, 1, 7);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            updateTaskUseCase.execute(taskId, taskDTO);
        });
        assertEquals("Task title can't be empty", exception.getMessage());
        verify(taskRepository, never()).getTaskById(anyInt());
        verify(taskRepository, never()).updateTask(any(Task.class));
    }

    @Test
    @DisplayName("Execute with non-existing task ID -> Should throw Exception")
    public void executeWithNonExistingTaskId() throws Exception {
        // Arrange
        int taskId = 999;
        TaskDTO taskDTO = new TaskDTO("Updated Title", "Description", LocalDate.now(), 0, 1, 7);
        when(taskRepository.getTaskById(taskId)).thenReturn(null);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            updateTaskUseCase.execute(taskId, taskDTO);
        });
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).getTaskById(taskId);
        verify(taskRepository, never()).updateTask(any(Task.class));
    }
}
