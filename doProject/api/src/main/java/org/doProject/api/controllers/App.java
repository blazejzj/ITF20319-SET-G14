package org.doProject.api.controllers;

import io.javalin.Javalin;
import org.doProject.infrastructure.domain.LocalDatabase;
import org.doProject.infrastructure.domain.LocalDatabaseConnection;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        LocalDatabaseConnection connectionHandler = new LocalDatabaseConnection("test.db");
        LocalDatabase localDatabase = new LocalDatabase(connectionHandler);

        try {
            localDatabase.createTables();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserController userController = new UserController(localDatabase);
        ProjectController projectController = new ProjectController(localDatabase);
        TaskController taskController = new TaskController(localDatabase);

        userController.registerRoutes(app);
        projectController.registerRoutes(app);
        taskController.registerRoutes(app);

        // Simple test endpoint
        app.get("/", ctx -> ctx.result("Hello!!!!! Server is running."));
    }
}
