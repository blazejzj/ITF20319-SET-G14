package org.doProject.core.dto;


import java.util.ArrayList;



public class ProjectDTO {
    private int id;
    private String title;
    private String description;
    private int userID;
    private ArrayList<TaskDTO> taskDTOs;
    // For future reference, the arraylist could hold simple taskIDs instead of whole DTO
    // objects. This is yet to be decided. It could be beneficial later, for easier
    // data managment.

    public ProjectDTO(int id, String title, String description, int userId, ArrayList<TaskDTO> taskDTOs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = taskDTOs;
    }

    public ProjectDTO(String title, String description, int userId, ArrayList<TaskDTO> taskDTOs) {
        this.title = title;
        this.description = description;
        this.userID = userId;
        this.taskDTOs = taskDTOs;
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
