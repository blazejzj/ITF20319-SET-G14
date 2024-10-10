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

    // create instance of database
    Database database = new Database("test.db");

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

        when(mockConnection.createStatement()).thenReturn(mockStatement); // mock the connnection

        Database database1 = spy(new Database("test.db")); // spy on our db instance
        doReturn(mockConnection).when(database1).connect();

        database1.createTables(); // create necessary tables


        verify(mockConnection).createStatement(); // make sure createStatement and Execute were called
        verify(mockStatement, times(2)).execute(anyString());
    }

}
