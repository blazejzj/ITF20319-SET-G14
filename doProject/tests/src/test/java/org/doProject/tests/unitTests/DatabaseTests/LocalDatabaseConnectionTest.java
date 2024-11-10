package org.doProject.tests.unitTests.DatabaseTests;

import org.doProject.infrastructure.domain.LocalDatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.spy;

public class LocalDatabaseConnectionTest {

    LocalDatabaseConnection localDatabaseConnection = spy(new LocalDatabaseConnection("test.db"));

    @Test
    @DisplayName("SQLite database file is created and connection made")
    public void testDatabaseConnection() {
        try (Connection connection = localDatabaseConnection.connect()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            fail("Database connection failed!" + e.getMessage());
        }
    }
}
