package org.doProject.core.domain;

import java.time.LocalDate;

/**
 * Task class represents a Task with various attributes. Including title, description, due date, and
 * various of flags indicating completion and repetition status. Provides also methods to toggle task states
 * and update due dates.
 */
public class Task {

    // Global Variables
    private int taskID;
    private String title;
    private String description;
    private int isFinished;
    private int isRepeating;
    private LocalDate dueDate;
    private int repeatDays;
//    private int priority; Priority could with later implementation be necessary.
    // Priority would control the tasks priority, and display how important it is to
    // do this specific task. (1), (2), (3). Green, Orange, Red.

    // Constructors
    /**
     * Constructs a Task object with specified title, description, due date, completion status,
     * repetition status, and repetition by days intervals.
     * ! Id is not provided, due to being automatically generated inside the local database.
     * @param title Title of the task
     * @param description Brief description of the task
     * @param dueDate Due date of the task
     * @param isFinished Indicator if task is finished. (0 -> False, 1 -> True)
     * @param isRepeating Indicator if task is set to repeat. (0 -> False, 1 -> True)
     * @param repeatDays Number of days after which the task repeats if isRepeating set to 1
     */
    public Task(String title, String description, LocalDate dueDate, int isFinished, int isRepeating, int repeatDays) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isFinished = isFinished;
        this.isRepeating = isRepeating;
        this.repeatDays = repeatDays;
    }

    /**
     * Constructs a Task object with the specified ID, title, description, due date, completion status,
     * repetition status, and repetition by days intervals.
     * @param id Unique identifier, manually set.
     * @param title Title of the task
     * @param description Brief description of the task
     * @param dueDate Due date of the task
     * @param isFinished Indicator if task is finished. (0 -> False, 1 -> True)
     * @param isRepeating Indicator if task is set to repeat. (0 -> False, 1 -> True)
     * @param repeatDays Number of days after which the task repeats if isRepeating set to 1
     */
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

    /**
     * Toggles the repeating status of a task. If task is currently repeating, set to no-repeating,
     * and vice versa
     */
    public void toggleIsRepeating() {
        this.isRepeating = this.isRepeating == 0 ? 1 : 0;
    }

    /**
     * Toggles teh completion status of the task. If the task is currently not done, marked as
     * unfinished (0), set to isFinished (1
     */
    public void toggleIsFinished() {
        this.isFinished = this.isFinished == 0 ? 1 : 0;
    }

    /**
     * Updates the task due date by the specified number of days. Positive values
     * move the due date forward, while negative values move it backward.
     * Throws an IllegalArgumentException if the new due date is in the past.
     *
     * @param byDays Number of days to adjust the due date can be positive or negative.
     */
    public void updateDueDateByDays(int byDays) {
        LocalDate newDate = dueDate.plusDays(byDays);
        if (newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be set to a past date.");
        }
        this.dueDate = newDate;
    }

}
