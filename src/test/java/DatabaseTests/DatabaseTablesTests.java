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
    @DisplayName("Project table is created if it does not exist")
    public void testCreateTablesExecutesSQL() throws SQLException {
        database.createTables();

        verify(mockConnection).createStatement();
        verify(mockStatement, times(2)).execute(anyString());
    }

    @Test
    @DisplayName("Make sure Projects table has all the necessary columns")
    public void testProjectTableAllColumnsExist() throws SQLException {
        database.createTables();

        // verify the columns are there
        verify(mockStatement).execute("CREATE TABLE IF NOT EXISTS Projects (id INTEGER PRIMARY KEY, title TEXT, description TEXT)");
    }

    @Test
    @DisplayName("Make sure Tasks table has all the necessary columns")
    public void testTasksTableAllColumnsExist() throws SQLException {
        database.createTables();

        // verify the columns are there
        verify(mockStatement).execute("CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY, title TEXT, description TEXT, project_id INTEGER, FOREIGN KEY (project_id) REFERENCES Projects(id))");
    }
}
