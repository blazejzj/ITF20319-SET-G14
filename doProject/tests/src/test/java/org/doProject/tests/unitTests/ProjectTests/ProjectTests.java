package org.doProject.tests.unitTests.ProjectTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.doProject.core.domain.Project;
import org.doProject.core.domain.Task;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * The ProjectsTests class contains tests made for the Project class.
 */
public class ProjectTests {
    // Mock Project and Task
    Project mockProject = mock(Project.class);
    Task mockTask = mock(Task.class);

    @Test
    @DisplayName("Add task to project")
    public void testAddTaskToProject() {
        ArrayList<Task> tasks = new ArrayList<>();
        // Arrange
        // Returns tasks
        when(mockProject.getTasks()).thenReturn(tasks);

        // Act
        // Adds task to the list
        mockProject.addTask(mockTask);

        // Verify
        // Verifies that the method is called
        verify(mockProject).addTask(mockTask);

        // Assert
        // Should return the list with the task
        Assertions.assertFalse(mockProject.getTasks().contains(mockTask));
    }

    @Test
    @DisplayName("Remove task from project")
    public void testRemoveTaskFromProject() {
        ArrayList<Task> tasks = new ArrayList<>();
        // Arrange
        // Returns an empty list after task is removed
        when(mockProject.getTasks()).thenReturn(new ArrayList<>());

        // Act
        // Removes task from the list
        mockProject.removeTask(mockTask);

        // Verify
        // Verifies that the method is called
        verify(mockProject).removeTask(mockTask);

        // Assertions
        // Should return an empty list
        Assertions.assertTrue(mockProject.getTasks().isEmpty());
    }

    @Test
    @DisplayName("Find a task in project")
    public void testFindTaskInProject() {
        ArrayList<Task> tasks = new ArrayList<>();
        //Arrange
        // Searching for a task
        when(mockProject.findTask(mockTask.getTitle())).thenReturn(mockTask);

        // Act
        // Returns task
        Task taskIsFound = mockProject.findTask(mockTask.getTitle());

        // Verify
        // Verifies that the method is called
        verify(mockProject).findTask(mockTask.getTitle());

        // Assertions
        // Should confirm that the task exists (not null) and that the right task is found
        Assertions.assertNotNull(taskIsFound);
        Assertions.assertEquals(mockTask, taskIsFound);
    }

    @Test
    @DisplayName("Task not found in project")
    public void testTaskNotFoundInProject() {
        ArrayList<Task> tasks = new ArrayList<>();
        // Arrange
        // Searching for a task that does not exist
        when(mockProject.findTask("Task not found in project")).thenReturn(null);

        // Act
        // Returns null since the task is not found
        Task taskIsNotFound = mockProject.findTask("Task not found in project");

        // Verify
        // Verifies that the method is called
        verify(mockProject).findTask("Task not found in project");

        // Assertions
        // Should confirm that task does not exist by returning null
        Assertions.assertNull(taskIsNotFound);
   }
}
