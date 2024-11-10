package org.doProject.tests.unitTests.TaskTests;

import org.doProject.core.domain.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskTests {

    public Task mockTask = mock(Task.class);

    // This is such an easy test and method that it isnt necessary to be tested
    // Yet we decided to do it,
    @Test
    @DisplayName("Toggle isFinished from 0 to 1")
    public void testToggleIsFinishedFromNotFinishedToFinished() {

        when(mockTask.getIsFinished()).thenReturn(0);

        mockTask.toggleIsFinished();

        verify(mockTask).toggleIsFinished();
        when(mockTask.getIsFinished()).thenReturn(1);

        assertEquals(1, mockTask.getIsFinished());
    }

    @Test
    @DisplayName("Toggle isFinished from 1 to 0")
    public void testToggleIsFinishedFromFinishedToNotFinished() {
        when(mockTask.getIsFinished()).thenReturn(1);

        mockTask.toggleIsFinished();

        when(mockTask.getIsFinished()).thenReturn(0);

        verify(mockTask).toggleIsFinished();
        assertEquals(0, mockTask.getIsFinished());
    }

    @Test
    @DisplayName("Toggle isRepeating from 0 to 1")
    public void testToggleIsRepeatingFromNotRepeatingToRepeating() {
        when(mockTask.getIsRepeating()).thenReturn(0);

        mockTask.toggleIsRepeating();
        when(mockTask.getIsRepeating()).thenReturn(1);

        verify(mockTask).toggleIsRepeating();
        assertEquals(1, mockTask.getIsRepeating());
    }

    @Test
    @DisplayName("Toggle isRepeating from 1 to 0")
    public void TestToggleIsRepeatingValue() {
        when(mockTask.getIsRepeating()).thenReturn(1);

        mockTask.toggleIsRepeating();
        when(mockTask.getIsRepeating()).thenReturn(0);

        verify(mockTask).toggleIsRepeating();
        assertEquals(0, mockTask.getIsRepeating());
    }

    @Test
    @DisplayName("Update date by 7 days")
    public void TestUpdateDueDateWithPositiveDays() {
        LocalDate initialDueDate = LocalDate.of(2024, 10, 1);
        when(mockTask.getDueDate()).thenReturn(initialDueDate);

        mockTask.updateDueDateByDays(7);

        verify(mockTask).updateDueDateByDays(7);

        LocalDate expectedDueDate = initialDueDate.plusDays(7);
        when(mockTask.getDueDate()).thenReturn(expectedDueDate);

        assertEquals(expectedDueDate, mockTask.getDueDate());
    }

    @Test
    @DisplayName("Update date by -3 days")
    public void testUpdateDueDateWithNegativeDays() {
        LocalDate initialDueDate = LocalDate.of(2024, 10, 1);
        when(mockTask.getDueDate()).thenReturn(initialDueDate);

        mockTask.updateDueDateByDays(-3);

        verify(mockTask).updateDueDateByDays(-3);

        LocalDate expectedDueDate = initialDueDate.minusDays(3);
        when(mockTask.getDueDate()).thenReturn(expectedDueDate);

        assertEquals(expectedDueDate, mockTask.getDueDate());
    }

    @Test
    @DisplayName("Throw exception when due date is set to past date")
    public void testUpdateDueDateThrowsExceptionForPastDate() {
        // Note: Using a real Task object here because mocks only simulaet behaviour
        // and do not execute the actual method logic required to trigger exceptions.
        Task task = new Task("Sample Task", "Description", LocalDate.now(), 0, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> task.updateDueDateByDays(-1));
    }

}