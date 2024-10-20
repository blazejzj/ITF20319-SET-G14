package DatabaseTests;

import database.Database;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    // We use real User here, because its a simple bulletproof class that only uses getters
    @Test
    @DisplayName("Read existing user from Database")
    public void testLoadExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Blazej");

        int userId = 1;
        User user = database.loadUser(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertEquals(1, user.getId());
        assertEquals("Blazej", user.getUserName());
    }

    // We use real User here, because its a simple bulletproof class that only uses getters
    @Test
    @DisplayName("Read non-existing user from Database")
    public void testLoadNonExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        int userId = 999;
        User user = database.loadUser(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertNull(user);
    }


    @Test
    @DisplayName("Read existing projects for a user from Database")
    public void testLoadUsersExistingProjects() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false); // first, second project, then no more

        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2); // First return 1, then 2 for the second project
        when(mockResultSet.getString("title")).thenReturn("Project 1").thenReturn("Project 2");
        when(mockResultSet.getString("description")).thenReturn("Description 1").thenReturn("Description 2");
        when(mockResultSet.getInt("userID")).thenReturn(1); // Same user ID for both projects

        int userId = 1;
        var projects = database.getUserProjects(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertEquals(2, projects.size());
        assertEquals(1, projects.get(0).getId());
        assertEquals("Project 1", projects.get(0).getTitle());
        assertEquals("Description 1", projects.get(0).getDescription());

        assertEquals(2, projects.get(1).getId());
        assertEquals("Project 2", projects.get(1).getTitle());
        assertEquals("Description 2", projects.get(1).getDescription());
    }

    @Test
    @DisplayName("Read non-existing projects for a user from Database")
    public void testLoadUsersNonExistingProjects() throws SQLException {
        when(mockResultSet.next()).thenReturn(false); // no project available

        int userId = 999; // non existent user
        var projects = database.getUserProjects(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertEquals(0, projects.size()); // expect no projects for this non existent user
    }
}

