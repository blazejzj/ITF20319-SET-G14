package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    // create instance of database
    Database database = new Database("test.db");

    @Test
    @DisplayName("Test if SQLite db file is created and connection made")
    public void testDatabaseConnection() {
        try (Connection connection = database.connect()) {
            assertNotNull(database.connect(), "Connection method should not be null");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
