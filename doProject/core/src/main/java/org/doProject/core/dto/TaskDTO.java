package org.doProject.core.dto;

import java.time.LocalDate;



public class TaskDTO {

    private int taskID;
    private String title;
    private String description;
    private int isFinished;
    private int isRepeating;
    private LocalDate dueDate;
    private int repeatDays;


    public TaskDTO(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    public TaskDTO(int id, String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.taskID = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    // Getters
    public int getTaskID() {return taskID;}
    public int getRepeatDays() {return repeatDays;}
    public LocalDate getDueDate() {return dueDate;}
    public int getIsRepeating() {return isRepeating;}
    public int getIsFinished() {return isFinished;}
    public String getDescription() {return description;}
    public String getTitle() {return title;}

    // Setters
    public void setTaskID(int taskID) {this.taskID = taskID;}
    public void setRepeatDays(int repeatDays) {this.repeatDays = repeatDays;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}
    public void setIsRepeating(int isRepeating) {this.isRepeating = isRepeating;}
    public void setIsFinished(int isFinished) {this.isFinished = isFinished;}
    public void setDescription(String description) {this.description = description;}
    public void setTitle(String title) {this.title = title;}
}
