package TaskTests;

import models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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


