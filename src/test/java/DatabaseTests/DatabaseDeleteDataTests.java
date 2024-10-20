package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DatabaseDeleteDataTests {

    private Database database;
    private Connection mockConnection;
    private PreparedStatement mockDeleteUserStmt;
    private PreparedStatement mockDeleteProjectsStmt;
    private PreparedStatement mockDeleteTasksStmt;
    private PreparedStatement mockDeleteProjectStmt;
    private PreparedStatement mockDeleteTaskStmt;

    @BeforeEach
    public void setUp() throws SQLException {
        database = spy(new Database("test.db"));

        mockConnection = mock(Connection.class);

        mockDeleteUserStmt = mock(PreparedStatement.class);
        mockDeleteProjectsStmt = mock(PreparedStatement.class);
        mockDeleteTasksStmt = mock(PreparedStatement.class);
        mockDeleteProjectStmt = mock(PreparedStatement.class);
        mockDeleteTaskStmt = mock(PreparedStatement.class);

        // prepping some statements before hand and setting "default" behaviuor
        when(mockConnection.prepareStatement("DELETE FROM Users WHERE id = ?")).thenReturn(mockDeleteUserStmt);
        when(mockConnection.prepareStatement("DELETE FROM Projects WHERE userID = ?")).thenReturn(mockDeleteProjectsStmt);
        when(mockConnection.prepareStatement("DELETE FROM Tasks WHERE project_id IN (SELECT id FROM Projects WHERE userID = ?)")).thenReturn(mockDeleteTasksStmt);
        when(mockConnection.prepareStatement("DELETE FROM Tasks WHERE project_id = ?")).thenReturn(mockDeleteTasksStmt);
        when(mockConnection.prepareStatement("DELETE FROM Projects WHERE id = ?")).thenReturn(mockDeleteProjectStmt);
        when(mockConnection.prepareStatement("DELETE FROM Tasks WHERE id = ?")).thenReturn(mockDeleteTaskStmt);

        when(mockDeleteUserStmt.executeUpdate()).thenReturn(1);
        when(mockDeleteProjectsStmt.executeUpdate()).thenReturn(1);
        when(mockDeleteTasksStmt.executeUpdate()).thenReturn(1);
        when(mockDeleteProjectStmt.executeUpdate()).thenReturn(1);
        when(mockDeleteTaskStmt.executeUpdate()).thenReturn(1);

        doReturn(mockConnection).when(database).connect();
    }

    @Test
    @DisplayName("Delete an existing User")
    public void testDeleteExistingUser() throws SQLException {
        int userId = 1;

        when(mockDeleteUserStmt.executeUpdate()).thenReturn(1);

        database.deleteUser(userId);

        verify(mockDeleteTasksStmt).setInt(1, userId);
        verify(mockDeleteTasksStmt).executeUpdate();

        verify(mockDeleteProjectsStmt).setInt(1, userId);
        verify(mockDeleteProjectsStmt).executeUpdate();

        verify(mockDeleteUserStmt).setInt(1, userId);
        verify(mockDeleteUserStmt).executeUpdate();
    }

    @Test
    @DisplayName("Attempt to delete a non-existing User")
    public void testDeleteNonExistingUser() throws SQLException {
        int userId = 999;

        when(mockDeleteUserStmt.executeUpdate()).thenReturn(0);

        assertThrows(SQLException.class, () -> database.deleteUser(userId));

        verify(mockDeleteTasksStmt).setInt(1, userId);
        verify(mockDeleteTasksStmt).executeUpdate();

        verify(mockDeleteProjectsStmt).setInt(1, userId);
        verify(mockDeleteProjectsStmt).executeUpdate();

        verify(mockDeleteUserStmt).setInt(1, userId);
        verify(mockDeleteUserStmt).executeUpdate();
    }


    @Test
    @DisplayName("Delete an existing project from the database and its tasks")
    public void testDeleteExistingProjectAndTasks() throws SQLException {
        // assume id projetc
        int id = 1;

        when(mockDeleteProjectStmt.executeUpdate()).thenReturn(1);
        when(mockDeleteTasksStmt.executeUpdate()).thenReturn(2);

        // delete this project
        database.deleteProject(id);

        // verify the quereis has been executed
        // We can't forget that the deletion of project also should delete all tasks that
        // are inside this specific project
        verify(mockDeleteTasksStmt).setInt(1, id);
        verify(mockDeleteTasksStmt).executeUpdate();

        verify(mockDeleteProjectStmt).setInt(1, id);
        verify(mockDeleteProjectStmt).executeUpdate();
    }

    @Test
    @DisplayName("Delete an existing task from database")
    public void testDeleteExistingTask() throws SQLException {
        int id = 1;

        when(mockDeleteTaskStmt.executeUpdate()).thenReturn(1);

        // delete this task
        database.deleteTask(id);

        // verify the query has been exectued
        verify(mockDeleteTaskStmt).setInt(1, id);
        verify(mockDeleteTaskStmt).executeUpdate();
    }

}