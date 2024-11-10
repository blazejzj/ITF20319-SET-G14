package org.doProject.tests.unitTests.DatabaseTests;

import org.doProject.infrastructure.domain.LocalDatabase;

import org.doProject.infrastructure.domain.LocalDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class LocalDatabaseSaveDataTests {

    private LocalDatabase localDatabase;
    private LocalDatabaseConnection localDatabaseConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    private ResultSet createMockResultSetForGeneratedKeys(int generatedId) throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(generatedId);
        return mockResultSet;
    }

    @BeforeEach
    public void setUp() throws SQLException {
        localDatabaseConnection = mock(LocalDatabaseConnection.class);
        localDatabase = spy(new LocalDatabase(localDatabaseConnection));

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(localDatabaseConnection.connect()).thenReturn(mockConnection);
    }

    // USER CREATION TEST
    @Test
    @DisplayName("Save an user; insert a new row into Users table")
    public void testSaveUserIntoDatabase() throws SQLException {
        ResultSet mockResultSet = createMockResultSetForGeneratedKeys(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);

        localDatabase.saveUser("Blazej");

        verify(mockConnection).prepareStatement(
                "INSERT INTO Users (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS
        );
        verify(mockPreparedStatement).setString(1, "Blazej");
        verify(mockPreparedStatement).executeUpdate();
    }

    // TEST ID UNIQUENESS (TEST WORKS FOR USER/PROJECT/TASKS ALSO)
    @Test
    @DisplayName("Saved user returns newly generated ID")
    public void testSaveUserReturnsNewlyGeneratedId() throws SQLException {
        ResultSet mockResultSet1 = createMockResultSetForGeneratedKeys(1);
        ResultSet mockResultSet2 = createMockResultSetForGeneratedKeys(2);

        when(mockPreparedStatement.getGeneratedKeys())
                .thenReturn(mockResultSet1)
                .thenReturn(mockResultSet2);

        int userID1 = localDatabase.saveUser("Blazej");
        int userID2 = localDatabase.saveUser("Kamilla");

        assertNotEquals(userID1, userID2);
    }

    // PROJECT CREATION TEST
    @Test
    @DisplayName("Save a project into the database")
    public void testSaveProject() throws  SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        int projectId = localDatabase.saveProject("ProjectName", "ProjectDescription", 1);

        verify(mockPreparedStatement).setString(1, "ProjectName");
        verify(mockPreparedStatement).setString(2, "ProjectDescription");
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, projectId);
    }

    // TASK CREATION TEST
    @Test
    @DisplayName("Save a task into the database")
    public void testSaveTask() throws SQLException {
        ResultSet mockResultSet = createMockResultSetForGeneratedKeys(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);

        LocalDate mockLocalDate = mock(LocalDate.class);

        int taskId = localDatabase.saveTask("TaskName", "TaskDescription", mockLocalDate, 0, 1, 7, 1);

        verify(mockPreparedStatement).setString(1, "TaskName");
        verify(mockPreparedStatement).setString(2, "TaskDescription");
        verify(mockPreparedStatement).setDate(3, java.sql.Date.valueOf(mockLocalDate));
        verify(mockPreparedStatement).setInt(4, 0);
        verify(mockPreparedStatement).setInt(5, 1);
        verify(mockPreparedStatement).setInt(6, 7);
        verify(mockPreparedStatement).setInt(7, 1);
        verify(mockPreparedStatement).executeUpdate();

        assertEquals(1, taskId);
    }
}
