package org.doProject.api.controllers;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // Simple test endpoint
        app.get("/", ctx -> ctx.result("Hello, Javalin! Server is running."));
    }
}