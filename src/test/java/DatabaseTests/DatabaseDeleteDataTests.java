package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DatabaseDeleteDataTests {

    private Database database;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        database = spy(new Database("test.db"));

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Here we are goign to assume only 1 row is goign to be affected by delete
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        doReturn(mockConnection).when(database).connect();
    }

    @Test
    @DisplayName("Delete an existing project from the database")
    public void testDeleteExistingProject() throws SQLException {
        // assume id projetc
        int id = 1;

        // delete this project
        database.deleteProject(id);

        // verify the query has been executed
        verify(mockPreparedStatement).setInt(1, id);
        verify(mockPreparedStatement).executeUpdate();
    }

}
