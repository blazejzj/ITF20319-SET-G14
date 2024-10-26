package org.doProject.tests.unitTests.TaskTests;

import org.doProject.common.domain.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskTests {

    public Task mockTask = mock(Task.class);

    @Test
    @DisplayName("Toggle isFinished from 0 to 1")
    public void testToggleDoneFromNotFinishedToFinished() {

        // Arrange
        when(mockTask.getIsFinished()).thenReturn(0); // initial state to not finished

        // Act
        mockTask.toggleDone();

        // Verify
        verify(mockTask).toggleDone();
        when(mockTask.getIsFinished()).thenReturn(1); // we expect finished

        // Assert
        assertEquals(1, mockTask.getIsFinished());    }

    @Test
    @DisplayName("Toggle isFinished from 1 to 0")
    public void testToggleDoneFromFinishedToNotFinished() {

        // Arrange
        when(mockTask.getIsFinished()).thenReturn(1); // initial state to not finished

        // Act
        mockTask.toggleDone();

        // Verify
        verify(mockTask).toggleDone();
        when(mockTask.getIsFinished()).thenReturn(0); // we expect finished

        // Assert
        assertEquals(0, mockTask.getIsFinished());
    }

    @Test
    @DisplayName("Toggle isRepeating from 0 to 1")
    public void testToggleRepeatFromNotRepeatingToRepeating() {

        // Arrange
        when(mockTask.getIsRepeating()).thenReturn(0);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getIsRepeating()).thenReturn(1);

        // Assert
        assertEquals(1, mockTask.getIsRepeating());
    }

    @Test
    @DisplayName("Toggle isRepeating from 1 to 0")
    public void TestToggleRepeatValue() {
        // Arrange
        when(mockTask.getIsRepeating()).thenReturn(1);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getIsRepeating()).thenReturn(0);

        // Assert
        assertEquals(0, mockTask.getIsRepeating());
    }

    @Test
    @DisplayName("Update date by 7 days")
    public void TestUpdateDueDateWithPositiveDays() {
        // Arrange
        LocalDate initialDueDate = LocalDate.of(2024, 10, 1);
        when(mockTask.getDueDate()).thenReturn(initialDueDate);

        // Act
        mockTask.updateDueDateByDays(7);

        // verify the method has been caleld
        verify(mockTask).updateDueDateByDays(7);

        // We expecting initialduedate to be +7 days now
        LocalDate expectedDueDate = initialDueDate.plusDays(7);
        when(mockTask.getDueDate()).thenReturn(expectedDueDate);

        // Assert
        assertEquals(expectedDueDate, mockTask.getDueDate());
    }

    @Test
    @DisplayName("Update date by -3 days")
    public void testUpdateDueDateWithNegativeDays() {

        // Arrage
        LocalDate initialDueDate = LocalDate.of(2024, 10, 1);
        when(mockTask.getDueDate()).thenReturn(initialDueDate);

        // Act
        mockTask.updateDueDateByDays(-3);

        // Verify
        verify(mockTask).updateDueDateByDays(-3);

        // Set the expected date after subtracting days
        LocalDate expectedDueDate = initialDueDate.minusDays(3);
        when(mockTask.getDueDate()).thenReturn(expectedDueDate);

        // Assert
        assertEquals(expectedDueDate, mockTask.getDueDate());

    }
}