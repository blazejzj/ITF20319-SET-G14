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
    Project mockProject = mock(Project.class);
    Task mockTask = mock(Task.class);

    @Test
    @DisplayName("Add task to project")
    public void testAddTaskToProject() {
        ArrayList<Task> tasks = new ArrayList<>();

        when(mockProject.getTasks()).thenReturn(tasks);

        mockProject.addTask(mockTask);

        verify(mockProject).addTask(mockTask);

        Assertions.assertFalse(mockProject.getTasks().contains(mockTask));
    }

    @Test
    @DisplayName("Remove task from project")
    public void testRemoveTaskFromProject() {
        when(mockProject.getTasks()).thenReturn(new ArrayList<>());

        mockProject.removeTask(mockTask);

        verify(mockProject).removeTask(mockTask);

        Assertions.assertTrue(mockProject.getTasks().isEmpty());
    }

    @Test
    @DisplayName("Find a task in project")
    public void testFindTaskInProject() {
        when(mockProject.findTask(mockTask.getTitle())).thenReturn(mockTask);

        Task taskIsFound = mockProject.findTask(mockTask.getTitle());

        verify(mockProject).findTask(mockTask.getTitle());

        Assertions.assertNotNull(taskIsFound);
        Assertions.assertEquals(mockTask, taskIsFound);
    }

    @Test
    @DisplayName("Task not found in project")
    public void testTaskNotFoundInProject() {
        when(mockProject.findTask("Task not found in project")).thenReturn(null);

        Task taskIsNotFound = mockProject.findTask("Task not found in project");

        verify(mockProject).findTask("Task not found in project");

        Assertions.assertNull(taskIsNotFound);
   }
}
