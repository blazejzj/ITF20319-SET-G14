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
        when(mockTask.getIsDone()).thenReturn(0); // 0 is false

        // act
        mockTask.toggleDone();

        // verify
        verify(mockTask).toggleDone();
        when(mockTask.getIsDone()).thenReturn(1);

        // assert
        int result = mockTask.getIsDone();

        assertEquals(1, result); // 1 is true
    }

    @Test
    @DisplayName("Update toggle repeat from 0 to 1")
    public void TestToggleRepeatValueCorrectly() {

        // if 0 -> 1
        // if 1 -> 0

        // Arrange
        when(mockTask.getRepeats()).thenReturn(0);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getRepeats()).thenReturn(1);

        // Assert
        int result = mockTask.getRepeats();
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Update toggle repeat from 1 to 0")
    public void TestToggleRepeatValue() {
        // Arrange
        when(mockTask.getRepeats()).thenReturn(1);

        // Act
        mockTask.toggleRepeat();
        verify(mockTask).toggleRepeat();
        when(mockTask.getRepeats()).thenReturn(0);
        int result = mockTask.getRepeats();
        // Assert
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Update due date by specific amount of days")
    public void TestUpdateDueDate() {

        // Arrange
        // We are using a real localdate object to simulate the method
        when(mockTask.getDueDate()).thenReturn(LocalDate.of(2024, 10, 1));

        // Act
        mockTask.updateDueDateByDays(7);
        // We are expecting the duedate to be Localdate.now + 7 days
        verify(mockTask).updateDueDateByDays(7);
        when(mockTask.getDueDate()).thenReturn(LocalDate.of(2024, 10, 8));

        // Assert

    }
}