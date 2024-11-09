package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.infrastructure.domain.LocalDatabase;
import org.doProject.infrastructure.domain.LocalDatabaseConnection;

// We need Jackson dependency to format and map things correctly
// Wont work without it.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.json.JavalinJackson;

/**
 * This class is just for demo purposes of testing with PostMan.
 * It isn't a "part" of the main application. Made just for testing and
 * experimenting purposes.
 */
public class App {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
        }).start(7000);

        LocalDatabaseConnection connectionHandler = new LocalDatabaseConnection("test.db");
        LocalDatabase localDatabase = new LocalDatabase(connectionHandler);

        try {
            localDatabase.createTables();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserController userController = new UserController(localDatabase);
        ProjectController projectController = new ProjectController(localDatabase, localDatabase);
        TaskController taskController = new TaskController(localDatabase);

        userController.registerRoutes(app);
        projectController.registerRoutes(app);
        taskController.registerRoutes(app);

        // Simple test endpoint
        app.get("/", ctx -> ctx.result("Hello!!!!! Server is running."));
    }
}
