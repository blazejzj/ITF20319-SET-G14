import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectTests {

    @Test
    @DisplayName("Each project has a unique ID")
    public void controlUniqueID() {
        // Arrange
        Project project = new Project("ProjectName", "TestingStuff hello");
        Project project2 = new Project("ProjectName", "TestingStuff hello");

        // Act
        int idProject1 = project.getId(); // supposed to be 0
        int idProject2 = project2.getId(); // supposed to be 1

        System.out.println(idProject1);
        System.out.println(idProject2);
        // Assert
        // If the two id's do not match -> Success
        Assertions.assertEquals(idProject1, idProject2);
    }
}
