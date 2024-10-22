package TaskTests;

import models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        int expectedId = 0;
        LocalDate dueDate = LocalDate.now();

        // Arrange
        Task testTaskWithID = new Task("Test", "Description", dueDate, 0, 1, expectedId);

        assertEquals(expectedId, testTaskWithID.getIsDone());
        assertEquals("Test", testTaskWithID.getTitle());
        assertEquals("Description", testTaskWithID.getDescription());
        assertEquals(0, testTaskWithID.getIsDone());
        assertEquals(1, testTaskWithID.getRepeats());
    }
}
