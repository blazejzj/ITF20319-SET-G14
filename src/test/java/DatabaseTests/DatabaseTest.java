package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    // create a spy on DB
    Database database = spy(new Database("test.db"));

    @Test
    @DisplayName("SQLite database file is created and connection made")
    public void testDatabaseConnection() {
        try (Connection connection = database.connect()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            fail("Database connection failed!" + e.getMessage());
        }
    }

    @Test
    @DisplayName("Project table is created if it does not exist")
    public void testCreateTables() throws SQLException {

        // mock the connection and statemnt objects from SQL
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        // mock the connnection
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // override connect to return mock connect
        doReturn(mockConnection).when(database).connect();

        database.createTables(); // create necessary tables

        verify(mockConnection).createStatement(); // make sure createStatement and Execute were called
        verify(mockStatement, times(2)).execute(anyString());
    }

    @Test
    @DisplayName("Make sure Projects have the necessary columns")
    public void testProjectColumns() throws SQLException {
        // mock the connection and statemnt objects from SQL
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        // mock the connnection
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // override connect to return mock connect
        doReturn(mockConnection).when(database).connect();

        database.createTables(); // create necessary tables

        verify(mockConnection).createStatement(); // make sure createStatement and Execute were called

        // verify the columns are there
        verify(mockStatement).execute("CREATE TABLE IF NOT EXISTS Projects (id INTEGER PRIMARY KEY, title TEXT, description TEXT)");
    }

    @Test
    @DisplayName("Make sure Projects have the necessary columns")
    public void testTasksTable() throws SQLException {
        // mock the connection and statemnt objects from SQL
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        // mock the connnection
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // override connect to return mock connect
        doReturn(mockConnection).when(database).connect();

        database.createTables(); // create necessary tables

        verify(mockConnection).createStatement(); // make sure createStatement and Execute were called

        // verify the columns are there
        verify(mockStatement).execute("CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY, title TEXT, description TEXT, project_id INTEGER, FOREIGN KEY (project_id) REFERENCES Projects(id))");
    }
}
