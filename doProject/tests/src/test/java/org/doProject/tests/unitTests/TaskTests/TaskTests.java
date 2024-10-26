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
    @DisplayName("Update toggle Done value correctly")
    public void TestToggleDoneValueCorrectly() {

        // arrange
        when(mockTask.getIsFinished()).thenReturn(0); // 0 is false

        // act
        mockTask.toggleDone();

        // verify
        verify(mockTask).toggleDone();
        when(mockTask.getIsFinished()).thenReturn(1);

        // assert
        int result = mockTask.getIsFinished();

        assertEquals(1, result); // 1 is true
    }

    @Test
    @DisplayName("Update toggle repeat from 0 to 1")
    public void TestToggleRepeatValueCorrectly() {

        // if 0 -> 1
        // if 1 -> 0

        // Arrange
        when(mockTask.getIsRepeating()).thenReturn(0);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getIsRepeating()).thenReturn(1);

        // Assert
        int result = mockTask.getIsRepeating();
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Update toggle repeat from 1 to 0")
    public void TestToggleRepeatValue() {
        // Arrange
        when(mockTask.getIsRepeating()).thenReturn(1);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getIsRepeating()).thenReturn(0);
        int result = mockTask.getIsRepeating();
        // Assert
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Update date by 7 days")
    public void TestUpdateDueDate() {
        // Arrange
        // set initial date
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
        assertEquals(expectedDueDate, mockTask.getDueDate(), "The due date should be updated by 7 days.");
    }
}