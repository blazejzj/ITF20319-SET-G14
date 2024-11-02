package org.doProject.core.dto;


import java.util.ArrayList;


/**
 * ProjectDTO is a Data Transfer Object for handling project information.
 * This DTO includes project ID, title, description, the ID of the user who owns the project,
 * and a list of associated tasksDTOs (Tasks).
 * This class is going to be used for transferring project data between different parts of the app.
 */
public class ProjectDTO {
    private int id;
    private String title;
    private String description;
    private int userID;
    private ArrayList<TaskDTO> taskDTOs;
    // For future reference, the arraylist could hold simple taskIDs instead of whole DTO
    // objects. This is yet to be decided. It could be beneficial later, for easier
    // data managment.

    /**
     * Creates a ProjectDTO with all project details, including ID, title, description,
     * user ID, and a list of TaskDTO objects associated with the project.
     *
     * @param id          The unique identifier of the project.
     * @param title       The title of the project.
     * @param description A brief description of the project.
     * @param userId      The ID of the user who owns this project.
     * @param taskDTOs    A list of TaskDTOs linked to the project.
     */
    public ProjectDTO(int id, String title, String description, int userId, ArrayList<TaskDTO> taskDTOs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = taskDTOs;
    }

    /**
     * Creates a ProjectDTO with all project details, including ID, title, description,
     * user ID, and a list of TaskDTO objects associated with the project.
     *
     * @param id          The unique identifier of the project.
     * @param title       The title of the project.
     * @param description A brief description of the project.
     * @param userId      The ID of the user who owns this project.
     */
    public ProjectDTO(int id, String title, String description, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = new ArrayList<>();
    }

    /**
     * Creates a ProjectDTO without an ID, which is useful for creating new projects
     * before an ID is assigned (which is normally going to occur in the database).
     *
     * @param title       The title of the project.
     * @param description A brief description of the project.
     * @param userId      The ID of the user who owns this project.
     * @param taskDTOs    A list of TaskDTOs linked to the project.
     */
    public ProjectDTO(String title, String description, int userId, ArrayList<TaskDTO> taskDTOs) {
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = taskDTOs;
    }

    /**
     * Creates a ProjectDTO without an ID, which is useful for creating new projects
     * before an ID is assigned (which is normally going to occur in the database).
     *
     * @param title       The title of the project.
     * @param description A brief description of the project.
     * @param userId      The ID of the user who owns this project.
     */
    public ProjectDTO(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = new ArrayList<>();
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public ArrayList<TaskDTO> getTaskDTOs() {return taskDTOs;}
    public int getUserID() {return userID;}
    public String getDescription() {return description;}

    public void setId(int id) {this.id = id;}
    public void setTaskDTOs(ArrayList<TaskDTO> taskDTOs) {this.taskDTOs = taskDTOs;}
    public void setUserID(int userID) {this.userID = userID;}
    public void setDescription(String description) {this.description = description;}
    public void setTitle(String title) {this.title = title;}
}
