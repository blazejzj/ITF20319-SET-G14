package org.doProject.tests.unitTests.DatabaseTests;

import org.doProject.infrastructure.domain.LocalDatabase;

import org.doProject.infrastructure.domain.LocalDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LocalDatabaseTablesTests {

    private LocalDatabase localDatabase;
    private LocalDatabaseConnection localDatabaseConnection;
    private Connection mockConnection;
    private Statement mockStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        localDatabaseConnection = mock(LocalDatabaseConnection.class);
        localDatabase = spy(new LocalDatabase(localDatabaseConnection));

        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        doReturn(mockConnection).when(localDatabaseConnection).connect();
    }

    @Test
    @DisplayName("Tables (Projects, User, Tasks) are created if not already")
    public void testCreateTablesExecutesSQL() throws SQLException {
        localDatabase.createTables();

        verify(mockConnection).createStatement();
        verify(mockStatement, times(3)).execute(anyString());
    }

    @Test
    @DisplayName("Projects table has all the necessary columns")
    public void testProjectTableAllColumnsExist() throws SQLException {
        localDatabase.createTables();

        // verify the columns are there
        verify(mockStatement).execute(
            "CREATE TABLE IF NOT EXISTS Projects (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT, " +
                    "userID INTEGER, " +
                    "FOREIGN KEY (userID) REFERENCES Users(id))"
        );
    }

    @Test
    @DisplayName("Tasks table has all the necessary columns")
    public void testTasksTableAllColumnsExist() throws SQLException {
        localDatabase.createTables();

        verify(mockStatement).execute(
                "CREATE TABLE IF NOT EXISTS Tasks (" +
                        "id INTEGER PRIMARY KEY, " +
                        "title VARCHAR(45), " +
                        "description TEXT, " +
                        "dueDate DATE, " +
                        "isFinished INTEGER, " +
                        "isRepeating INTEGER, " +
                        "repeatDays INTEGER, " +
                        "project_id INTEGER, " +
                        "FOREIGN KEY (project_id) REFERENCES Projects(id))"
        );
    }
    
    @Test
    @DisplayName("User table has all the necessary columns")
    public void testUserTableAllColumnsExist() throws SQLException {
        localDatabase.createTables();
        verify(mockStatement).execute(
            "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(45))"
        );
    }
}
