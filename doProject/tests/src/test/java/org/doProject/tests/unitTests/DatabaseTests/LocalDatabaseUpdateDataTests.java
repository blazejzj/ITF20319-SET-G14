package org.doProject.tests.unitTests.DatabaseTests;

import org.doProject.infrastructure.domain.LocalDatabase;

import org.doProject.infrastructure.domain.LocalDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LocalDatabaseUpdateDataTests {

    private LocalDatabase localDatabase;
    private LocalDatabaseConnection localDatabaseConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        localDatabaseConnection = mock(LocalDatabaseConnection.class);
        localDatabase = spy(new LocalDatabase(localDatabaseConnection));

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // expect atleast 1 row to be affected

        doReturn(mockConnection).when(localDatabaseConnection).connect();
    }

    // USER UPDATE TESTS
    @Test
    @DisplayName("Update an existing user name")
    public void testUpdateExistingUser() throws SQLException {
        int id = 1;
        String newName = "Amy Stake"; // pun intended

        // execute the query
        localDatabase.updateUser(id, newName);

        // verify query has been executed
        verify(mockConnection).prepareStatement("UPDATE Users SET name = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newName);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Update an non-existing user name")
    public void testUpdateNonExistingUser() throws SQLException {
        int id = 111111;
        String newName = "Amy Stake"; // pun intended

        // mock the executeUpdate to return 0
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(SQLException.class, () -> localDatabase.updateUser(id, newName));

        // verify query has been executed
        verify(mockConnection).prepareStatement("UPDATE Users SET name = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newName);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    // PROJECT UPDATE TESTS
    @Test
    @DisplayName("Update an existing project's title and description")
    public void testUpdateExistingProject() throws SQLException {
        int projectId = 1;
        String newTitle = "Updated Project Title";
        String newDescription = "Updated Project Description";

        localDatabase.updateProject(projectId, newTitle, newDescription);

        verify(mockConnection).prepareStatement("UPDATE Projects SET title = ?, description = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newTitle);
        verify(mockPreparedStatement).setString(2, newDescription);
        verify(mockPreparedStatement).setInt(3, projectId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Attempt to update a non-existing project")
    public void testUpdateNonExistingProject() throws SQLException {
        int projectId = 999; // Assume this project does not exist
        String newTitle = "Updated Project Title";
        String newDescription = "Updated Project Description";

        // mock to return 0
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(SQLException.class, () -> localDatabase.updateProject(projectId, newTitle, newDescription));

        verify(mockConnection).prepareStatement("UPDATE Projects SET title = ?, description = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newTitle);
        verify(mockPreparedStatement).setString(2, newDescription);
        verify(mockPreparedStatement).setInt(3, projectId);
        verify(mockPreparedStatement).executeUpdate();
    }


    // TASKS UPDATE TESTS
    @Test
    @DisplayName("Update an existing task's details")
    public void testUpdateExistingTask() throws SQLException {

        // for simplicity we are going to simulate these are going to be sent in
        // as arguments
        int taskId = 1;
        String newTitle = "Updated Task Title";
        String newDescription = "Updated Task Description";
        LocalDate newDueDate = LocalDate.of(2023, 12, 31);
        int isFinished = 1;
        int isRepeating = 0;
        int repeatDays = 0;

        localDatabase.updateTask(taskId, newTitle, newDescription, newDueDate, isFinished, isRepeating, repeatDays);

        verify(mockConnection).prepareStatement("UPDATE Tasks SET title = ?, description = ?, dueDate = ?, isFinished = ?, isRepeating = ?, repeatDays = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newTitle);
        verify(mockPreparedStatement).setString(2, newDescription);
        verify(mockPreparedStatement).setDate(3, Date.valueOf(newDueDate));
        verify(mockPreparedStatement).setInt(4, isFinished);
        verify(mockPreparedStatement).setInt(5, isRepeating);
        verify(mockPreparedStatement).setInt(6, repeatDays);
        verify(mockPreparedStatement).setInt(7, taskId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Attempt to update a non-existing task")
    public void testUpdateNonExistingTask() throws SQLException {
        int taskId = 999;
        String newTitle = "Updated Task Title";
        String newDescription = "Updated Task Description";
        LocalDate newDueDate = LocalDate.of(2023, 12, 31);
        int isFinished = 1;
        int isRepeating = 0;
        int repeatDays = 0;

        // mock to retrun 0
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(SQLException.class, () -> localDatabase.updateTask(taskId, newTitle, newDescription, newDueDate, isFinished, isRepeating, repeatDays));

        verify(mockConnection).prepareStatement("UPDATE Tasks SET title = ?, description = ?, dueDate = ?, isFinished = ?, isRepeating = ?, repeatDays = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newTitle);
        verify(mockPreparedStatement).setString(2, newDescription);
        verify(mockPreparedStatement).setDate(3, Date.valueOf(newDueDate));
        verify(mockPreparedStatement).setInt(4, isFinished);
        verify(mockPreparedStatement).setInt(5, isRepeating);
        verify(mockPreparedStatement).setInt(6, repeatDays);
        verify(mockPreparedStatement).setInt(7, taskId);
        verify(mockPreparedStatement).executeUpdate();
    }


}
