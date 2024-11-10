package org.doProject.tests.UseCaseTests.TaskUseCasesTests;

import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;
import org.doProject.core.usecases.CreateTaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;
    private CreateTaskUseCase createTaskUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createTaskUseCase = new CreateTaskUseCase(taskRepository);
    }

    @Test
    @DisplayName("Execute with valid task title -> Creates task successfully")
    public void executeWithValidTaskTitle() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Task tittleninio", "I do indeed like Sushi m8", LocalDate.now(), 0, 0, 0);
        int projectId = 1;
        int taskId = 111111;

        when(taskRepository.saveTask(any(Task.class), eq(projectId))).thenReturn(taskId);

        TaskDTO result = createTaskUseCase.execute(taskDTO, projectId);

        assertNotNull(result);
        assertEquals(taskId, result.getTaskID());
        verify(taskRepository, times(1)).saveTask(any(Task.class), eq(projectId));
    }

    @Test
    @DisplayName("Execute with empty task title -> Should throw Exception")
    public void executeWithEmptyTaskTitle() throws Exception {
        TaskDTO taskDTO = new TaskDTO("  ", "Description", LocalDate.now(), 0, 0, 0);
        int projectId = 1;

        Exception exception = assertThrows(Exception.class, () -> {
            createTaskUseCase.execute(taskDTO, projectId);
        });
        assertEquals("Task title can't be empty", exception.getMessage());
        verify(taskRepository, never()).saveTask(any(Task.class), anyInt());
    }
}
