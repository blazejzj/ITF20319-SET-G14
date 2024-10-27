package org.doProject.tests.unitTests.DatabaseTests;

import org.doProject.infrastructure.domain.LocalDatabase;
import org.doProject.common.domain.*;

import org.doProject.infrastructure.domain.LocalDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class LocalDatabaseLoadDataTests {

    private LocalDatabase localDatabase;
    private LocalDatabaseConnection localDatabaseConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private LocalDate localDate;

    @BeforeEach
    public void setUp() throws SQLException {

        localDatabaseConnection = mock(LocalDatabaseConnection.class);
        localDatabase = spy(new LocalDatabase(localDatabaseConnection)); // create a spy

        // Mock connection and prepared statementss
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(localDatabaseConnection.connect()).thenReturn(mockConnection);
    }

    // USER LOADING TESTS
    // We use real User here, because its a simple bulletproof class that only uses getters
    @Test
    @DisplayName("Read existing user from Database")
    public void testLoadExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Blazej");

        int userId = 1;
        User user = localDatabase.loadUser(userId);

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
        User user = localDatabase.loadUser(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertNull(user);
    }

    // PROJECT LOADING TESTS
    @Test
    @DisplayName("Read existing projects for a user from Database")
    public void testLoadUsersExistingProjects() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false); // first, second project, then no more

        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2); // First return 1, then 2 for the second project
        when(mockResultSet.getString("title")).thenReturn("Project 1").thenReturn("Project 2");
        when(mockResultSet.getString("description")).thenReturn("Description 1").thenReturn("Description 2");
        when(mockResultSet.getInt("userID")).thenReturn(1); // Same user ID for both projects

        int userId = 1;
        var projects = localDatabase.loadUserProjects(userId);

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
        var projects = localDatabase.loadUserProjects(userId);

        verify(mockPreparedStatement).setInt(1, userId);

        assertEquals(0, projects.size()); // expect no projects for this non existent user
    }

    // TASKS LOADING TESTS
    // We use real Task object here here
    @Test
    @DisplayName("Read existing tasks for a project from Database")
    public void testLoadExistingTasksForProject() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false); // onnly testing for 2 tasks

        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("title")).thenReturn("Task 1").thenReturn("Task 2");
        when(mockResultSet.getString("description")).thenReturn("Description 1").thenReturn("Description 2");
        when(mockResultSet.getDate("dueDate"))
                .thenReturn(Date.valueOf(LocalDate.of(2023, 10, 20)))
                .thenReturn(Date.valueOf(LocalDate.of(2023, 10, 21)));
        when(mockResultSet.getInt("isFinished")).thenReturn(0).thenReturn(1);
        when(mockResultSet.getInt("isRepeating")).thenReturn(0).thenReturn(0);
        when(mockResultSet.getInt("repeatDays")).thenReturn(0).thenReturn(0);

        int projectId = 1;
        var tasks = localDatabase.loadTasks(projectId);

        verify(mockPreparedStatement).setInt(1, projectId);

        assertEquals(2, tasks.size());

        // first task
        assertEquals(1, tasks.get(0).getId());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Description 1", tasks.get(0).getDescription());
        assertEquals(LocalDate.of(2023, 10, 20), tasks.get(0).getDueDate());
        assertEquals(0, tasks.get(0).getIsFinished());
        assertEquals(0, tasks.get(0).getIsRepeating());
        assertEquals(0, tasks.get(0).getRepeatDays());

        // second task
        assertEquals(2, tasks.get(1).getId());
        assertEquals("Task 2", tasks.get(1).getTitle());
        assertEquals("Description 2", tasks.get(1).getDescription());
        assertEquals(LocalDate.of(2023, 10, 21), tasks.get(1).getDueDate());
        assertEquals(1, tasks.get(1).getIsFinished());
        assertEquals(0, tasks.get(1).getIsRepeating());
        assertEquals(0, tasks.get(1).getRepeatDays());
    }

    @Test
    @DisplayName("Read non-existing tasks for a project from Database")
    public void testLoadNonExistingTasksForProject() throws SQLException {
        when(mockResultSet.next()).thenReturn(false); // no tasks available

        int projectId = 999; // were assuming this id doesnt have any tasks
        var tasks = localDatabase.loadTasks(projectId);

        verify(mockPreparedStatement).setInt(1, projectId);

        assertEquals(0, tasks.size());
    }

}

