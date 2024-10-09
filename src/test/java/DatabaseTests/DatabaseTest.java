package DatabaseTests;

import database.Database;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    // create instance of database
    Database database = new Database("test.db");

    @Test
    @DisplayName("Test if SQLite db file is created and connection made")
    public void testDatabaseConnection() {
        assertTrue(database.connect(), "Connection method should return true");
    }

}
