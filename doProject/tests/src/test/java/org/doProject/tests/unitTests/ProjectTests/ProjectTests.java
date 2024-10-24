import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class ProjectTests {

    public Project mockProject = mock(Project.class);
    Task mockTask = mock(Task.class);

    @Test
    @DisplayName("Add task to project")
    public void testAddTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(mockTask);

        // Arrange
        when(mockProject.getTasks()).thenReturn(tasks);

        // Act
        mockProject.addTask(mockTask);

        // Assertions
        Assertions.assertTrue(mockProject.getTasks().contains(mockTask));

    }

    @Test
    @DisplayName("Remove task from project")
    public void testRemoveTask() {

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(mockTask);

        // Arrange
        when(mockProject.getTasks()).thenReturn(tasks);

        // Act
        mockProject.removeTask(mockTask);

        // Assertions
        Assertions.assertFalse(mockProject.getTasks().isEmpty());

    }


    @Test
    @DisplayName("Find a task in project")
    public void testFindTask() {

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(mockTask);

        //Arrange
        when(mockProject.getTasks()).thenReturn(tasks);

        // Act
        Task taskFound = mockProject.findTask(mockTask.getTitle());

        // Assertions
        Assertions.assertNotNull(taskFound);

    }


//    @Test
//    @DisplayName("Check if task is done")
//    public void testIfTaskIsDone() {
//
//        ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(mockTask);
//
//        // Arrange
//        when(mockProject.taskIsDone());
//
//        // Act
//        Task taskDone = mockProject.taskIsDone();
//
//        // Assertions
//        Assertions.assertTrue(mockProject.taskIsDone());
//    }


    @Test
    @DisplayName("Get project summary")
    public void testProjectSummary() {

        // Arrange


        // Act

        // Assertions
    }
}
