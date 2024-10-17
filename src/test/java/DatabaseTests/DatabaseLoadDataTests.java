package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class DatabaseLoadDataTests {

    private Database database;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;


    @BeforeEach
    public void setUp() throws SQLException {

        database = spy(new Database("test.db")); // create a spy

        // Mock connection and prepared statementss
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        doReturn(mockConnection).when(database).connect();
    }

    @Test
    @DisplayName("Read existing user from Database")
    public void testLoadExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Blazej");

        int userId = 1;
        String userName = database.loadUser(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertEquals("Blazej", userName);
    }
}
