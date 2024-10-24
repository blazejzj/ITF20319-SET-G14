package org.doProject.common.domain;

import java.time.LocalDate;

public class Task {

    // Global Variables
    private int taskID;
    private String title;
    private String description;
    private int isFinished;
    private int isRepeating;
    private LocalDate dueDate;
    private int repeatDays;

    // Constructors
    public Task(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    public Task(int id, String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.taskID = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    // Getters
    public int getId() {return taskID;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getIsFinished() {return isFinished;}
    public int getIsRepeating() {return isRepeating;}
    public LocalDate getDueDate() {return dueDate;}
    public int getRepeatDays() {return repeatDays;}

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setTaskID(int taskID) {this.taskID = taskID;}
    public void setDescription(String description) { this.description = description; }
    public void setIsFinished(int isFinished) { this.isFinished = isFinished; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }


    // Methods
    public void toggleRepeat() {
        this.isRepeating = this.isRepeating == 0 ? 1 : 0;
    }

    public void toggleDone() {
        this.isFinished = this.isFinished == 0 ? 1 : 0;
    }

    public void updateDueDateByDays(int byDays) {
        this.dueDate = dueDate.plusDays(byDays);
    }
}
