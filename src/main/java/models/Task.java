package models;

public class Task {
    // Instance variables
    private static int taskAmount = 0; //

    private int taskID;
    private String title;
    private String description;
    private Boolean isDone;
    private Boolean repeats;

    // Constructor
    public Task(String title, String description, Boolean isDone) {
        this.title = title;
        this.description = description;
        this.taskID = taskAmount++; // Each task has a unique ID starting from 0
        this.isDone = false;
        this.repeats = false;
    }

    // Getters

    public int getId() {return taskID;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Boolean getDone() {return isDone;}
    public Boolean getRepeats() {return repeats;}

    // Setters
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setDone(Boolean isDone) {this.isDone = isDone;}
    public void setRepeats(Boolean repeats) {this.repeats = repeats;}

    // Methods
    public void toggleDone() {this.isDone = !isDone;}
    public void toggleRepeats() {this.repeats = !repeats;}
}
