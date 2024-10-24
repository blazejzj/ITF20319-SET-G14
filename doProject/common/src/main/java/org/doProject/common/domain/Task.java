package org.doProject.common.domain;

import java.time.LocalDate;

public class Task {

    // Global Variables
    private int taskID;
    private String title;
    private String description;
    private int isDone;
    private int repeats;
    private LocalDate dueDate;
    private int repeatDays;

    // Constructors
    public Task(String title, String description, LocalDate dueDate, int isDone, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.repeats = 0;
        this.repeatDays = repeatDays;
    }

    public Task(int id, String title, String description, LocalDate dueDate, int isDone, int repeatDays) {
        this.taskID = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.repeats = 0;
        this.repeatDays = repeatDays;
    }

    // Getters
    public int getId() {return taskID;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getIsDone() {return isDone;}
    public int getRepeats() {return repeats;}
    public LocalDate getDueDate() {return dueDate;}
    public int getRepeatDays() {return repeatDays;}

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setTaskID(int taskID) {this.taskID = taskID;}
    public void setDescription(String description) { this.description = description; }
    public void setIsDone(int isDone) { this.isDone = isDone; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public void toggleRepeat() {this.repeats = this.repeats == 0 ? 1 : 0;}
    public void toggleDone() {
        if (this.isDone != 0) {
            this.isDone = 0;
        } else {
            this.isDone = 1;
        }
    }

    public void updateDueDateByDays(int byDays) {
        this.dueDate = dueDate.plusDays(byDays);
    }


    @Override
    public String toString() {
        return "Gjoeremaal\n" +
                "ID: " + taskID + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Is Done: " + (isDone != 0 ? "Yes" : "No") + "\n" +
                "Repeats: " + (repeats != 0 ? "Yes, every " + repeatDays + " days" : "No") + "\n" +
                "Due Date: " + dueDate + "\n";
    }
}
