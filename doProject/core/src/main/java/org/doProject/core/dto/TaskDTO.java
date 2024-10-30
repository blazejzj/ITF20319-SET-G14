package org.doProject.core.dto;

import java.time.LocalDate;


/**
 * TaskDTO is a Data Transfer Object for handling task information.
 * This DTO includes Task ID, title, description, completion status, repeat status, due date,
 * and repeat "interval" in days.
 * It is used to transfer task data between different parts of the app.
 */
public class TaskDTO {

    private int taskID;
    private String title;
    private String description;
    private int isFinished;
    private int isRepeating;
    private LocalDate dueDate;
    private int repeatDays;


    /**
     * Create a TaskDTO without an ID, useful when a task ID is not yet assigned.
     * Assigning the ID is normally going to occur within the database.
     *
     * @param title       The title of the task.
     * @param description A brief description of the task.
     * @param dueDate     The due date of the task.
     * @param isFinished Indicator if task is finished. (0 -> False, 1 -> True)
     * @param isRepeating Indicator if task is set to repeat. (0 -> False, 1 -> True)
     * @param repeatDays Number of days after which the task repeats if isRepeating set to 1
     */
    public TaskDTO(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    /**
     * Creates a TaskDTO with all details, now including the task ID.
     *
     * @param id          The unique identifier of the task.
     * @param title       The title of the task.
     * @param description A brief description of the task.
     * @param dueDate     The due date of the task.
     * @param isFinished Indicator if task is finished. (0 -> False, 1 -> True)
     * @param isRepeating Indicator if task is set to repeat. (0 -> False, 1 -> True)
     * @param repeatDays Number of days after which the task repeats if isRepeating set to 1
     */
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
