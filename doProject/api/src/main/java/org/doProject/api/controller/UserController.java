package org.doProject.api.controller;

import io.javalin.Javalin;
import org.doProject.core.dto.UserDTO;
import org.doProject.infrastructure.domain.LocalDatabase;

public class UserController {

    private final LocalDatabase localDatabase;

    public UserController(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    public void registerRoutes(Javalin app) {

        // creating an user
        app.post("api/users", context -> {
            UserDTO userDTO = context.bodyAsClass(UserDTO.class);
            int userId = localDatabase.saveUser(userDTO.getUserName());
            userDTO.setId(userId);
            context.status(201).json(userDTO);
        });

        // get user


    }
}
