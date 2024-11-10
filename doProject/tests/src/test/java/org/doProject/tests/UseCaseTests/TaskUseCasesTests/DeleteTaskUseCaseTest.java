package org.doProject.tests.UseCaseTests.TaskUseCasesTests;

import org.doProject.core.port.TaskRepository;
import org.doProject.core.usecases.DeleteTaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;
    private DeleteTaskUseCase deleteTaskUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteTaskUseCase = new DeleteTaskUseCase(taskRepository);
    }

    @Test
    @DisplayName("Execute with valid task ID -> Deletes task successfully")
    public void executeWithValidTaskId() throws Exception {
        int taskId = 123123;

        deleteTaskUseCase.execute(taskId);

        verify(taskRepository, times(1)).deleteTask(taskId);
    }

    @Test
    @DisplayName("Execute with non-existing task ID -> Should throw SQLException")
    public void executeWithNonExistingTaskId() throws Exception {
        int taskId = 11223344;

        doThrow(new SQLException("Task not found")).when(taskRepository).deleteTask(taskId);

        SQLException exception = assertThrows(SQLException.class, () -> {
            deleteTaskUseCase.execute(taskId);
        });
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).deleteTask(taskId);
    }
}
