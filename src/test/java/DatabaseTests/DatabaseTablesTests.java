package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DatabaseTablesTests {

    Database database;
    Connection mockConnection;
    Statement mockStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        // Spy on db instance
        database = spy(new Database("test.db"));

        // mock the connection and statemnt objects from SQL
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);

        // return mocks
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        doReturn(mockConnection).when(database).connect();
    }

    @Test
    @DisplayName("Tables (Projects, User, Tasks) are created if not already")
    public void testCreateTablesExecutesSQL() throws SQLException {
        database.createTables();

        verify(mockConnection).createStatement();
        verify(mockStatement, times(3)).execute(anyString());
    }

    @Test
    @DisplayName("Projects table has all the necessary columns")
    public void testProjectTableAllColumnsExist() throws SQLException {
        database.createTables();

        // verify the columns are there
        verify(mockStatement).execute(
            "CREATE TABLE IF NOT EXISTS Projects (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title VARCHAR(45), " +
                    "description TEXT)"
        );
    }

    @Test
    @DisplayName("Tasks table has all the necessary columns")
    public void testTasksTableAllColumnsExist() throws SQLException {
        database.createTables();

        // verify the columns are there
        verify(mockStatement).execute(
                "CREATE TABLE IF NOT EXISTS Tasks (" +
                        "id INTEGER PRIMARY KEY, " +
                        "title VARCHAR(45), " +
                        "description TEXT, " +
                        "dueDate DATE, " +
                        "isFinished INTEGER, " +
                        "isRepeating INTEGER, " +
                        "project_id INTEGER, " +
                        "FOREIGN KEY (project_id) REFERENCES Projects(id))"
        );
    }

    @Test
    @DisplayName("User table has all the necessary columns")
    public void testUserTableAllColumnsExist() throws SQLException {
        database.createTables();

        // veruify the columns are there
        verify(mockStatement).execute(
            "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(45))"
        );

    }
}
