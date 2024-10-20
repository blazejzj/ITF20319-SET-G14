package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DatabaseUpdateDataTests {

    private Database database;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        database = spy(new Database("test.db"));

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // expect atleast 1 row to be affected

        doReturn(mockConnection).when(database).connect();
    }

    // USER UPDATE TESTS
    @Test
    @DisplayName("Update an existing user name")
    public void testUpdateExistingUser() throws SQLException {
        int id = 1;
        String newName = "Amy Stake"; // pun intended

        // execute the query
        database.updateUser(id, newName);

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

        assertThrows(SQLException.class, () -> database.updateUser(id, newName));

        // verify query has been executed
        verify(mockConnection).prepareStatement("UPDATE Users SET name = ? WHERE id = ?");
        verify(mockPreparedStatement).setString(1, newName);
        verify(mockPreparedStatement).setInt(2, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    // PROJECT UPDATE TESTS

    // TASKS UPDATE TESTS
}
