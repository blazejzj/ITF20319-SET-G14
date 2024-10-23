package TaskTests;

import models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTests {

    @Test
    @DisplayName("Testing Toggle Done True/False")
    public void toggleDoneValueCorrectly() {
        // Arrange
        Task testTask = new Task("Test", "Test Description", LocalDate.now(), 0, 0, 0);

        // Start
        assertEquals(0, testTask.getIsDone(), "isDone should be false (0)");

        // Act
        testTask.toggleDone();

        // Assert
        assertEquals(1, testTask.getIsDone(), "isDone should be true (1)");

        // Act
        testTask.toggleDone();

        // Assert
        assertEquals(0, testTask.getIsDone(), "isDone should be false (0)");
    }

    @Test
    @DisplayName("Task Without ID")
    public void taskNoIdCorrect() {

        // Arrange
        Task testTaskNoID = new Task("Test", "Description", LocalDate.now(), 0, 1, 5);

        // Assert
        assertEquals("Test", testTaskNoID.getTitle());
        assertEquals("Description", testTaskNoID.getDescription());
        assertEquals(0, testTaskNoID.getIsDone());
        assertEquals(1, testTaskNoID.getRepeats());
    }

    @Test
    @DisplayName("Task With ID")
    public void taskWithIdCorrect() {
        int expectedId = 0; //burde kanskje testes med tall men trenger muligens mer?
        LocalDate dueDate = LocalDate.now();

        // Arrange
        Task testTaskWithID = new Task("Test", "Description", dueDate, 0, 1, expectedId);

        // Assert
        assertEquals(expectedId, testTaskWithID.getIsDone());
        assertEquals("Test", testTaskWithID.getTitle());
        assertEquals("Description", testTaskWithID.getDescription());
        assertEquals(0, testTaskWithID.getIsDone());
        assertEquals(1, testTaskWithID.getRepeats());
    }

    @Test
    @DisplayName("Test for Getters/Setters")
    public void taskGettersSettersCorrect() {
        LocalDate dueDate = LocalDate.now();
        Task testGettersSetters = new Task("Test", "Description", dueDate, 0, 1, 0);

        // Testing Set and Get for Title
        testGettersSetters.setTitle("Test");
        assertEquals("Test", testGettersSetters.getTitle());

        // Testing Set and Get for Description
        testGettersSetters.setDescription("Description");
        assertEquals("Description", testGettersSetters.getDescription());

        // Testing Set and Get for DueDate
        testGettersSetters.setDueDate(dueDate);
        assertEquals(dueDate, testGettersSetters.getDueDate());

        // Test Set and Get for isDone
        testGettersSetters.setIsDone(0);
        assertEquals(0, testGettersSetters.getIsDone());

        // Test Set and Get for repeats
        testGettersSetters.setRepeats(0);
        assertEquals(0, testGettersSetters.getRepeats());

        // Ignorerer repeatdays foreløpig
    }
}
=======

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TaskTests {
    @Test
    @DisplayName("Update toggle Done value correctly")
    public void toggleDoneValueCorrectly() {
        Task mockTask = mock(Task.class);

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
}


>>>>>>> 9ceb6425ac6ad71dc87c0aff3fd0c966d262c306
