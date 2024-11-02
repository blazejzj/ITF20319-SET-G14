package org.doProject.api.controller;

import io.javalin.Javalin;
import org.doProject.core.domain.User;
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

        // get user by their ID
        app.get("api/users/{id}", context -> {
           int userId = Integer.parseInt(context.pathParam("id")); // get id
           // load from database
           User user = localDatabase.loadUser(userId);
           if (user == null) {
               context.status(404).result("User has not been found!");
           }
           else {
               UserDTO userDTO = new UserDTO(user.getId(), user.getUserName());
           }
        });


    }
}
