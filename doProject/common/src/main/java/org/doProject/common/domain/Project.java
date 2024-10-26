package org.doProject.common.domain;

import java.util.ArrayList;
import org.doProject.common.domain.Task;

/**
 * The Project class represents a project in the calendar application DO.
 * A Project object contains details such as ID, title, description, tasks and user ID.
 * The class contains methods for handling tasks connected to a project.
 */

public class Project {
    // Variables
    private int projectID;
    private String projectTitle;
    private String projectDescription;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int userID;

    // Constructor 1
    public Project(int projectID, String projectTitle, String projectDescription, int userID) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.userID = userID;
    }

    // Constructor 2
    public Project(String projectTitle, String projectDescription, int userID) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.userID = userID;
    }

    // Getters
    public String getTitle(){ return projectTitle;}
    public String getDescription(){ return projectDescription;}
    public ArrayList<Task> getTasks(){ return tasks;}
    public int getUserID(){ return userID;}
    public int getId() {return projectID;}

    // Setters
    public void setTitle(String Title) {this.projectTitle = Title;}
    public void setDescription(String Description) {this.projectDescription = Description;}
    public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}
    public void setUserID(int userID) {this.userID = userID;}
    public void setId(int id) {this.projectID = id;}

    // Methods
    /**
     * Adds a task to the project's list using the following method.
     * @param task the name of the task.
     */
    public void addTask(Task task) {
        tasks.add(task);
        //System.out.println(task.getTitle() + " is added to the project");
    }

    /**
     * Removes a task from the project's list using the following method.
     * @param task the name of the task.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Checks if a task is completed using the following method.
     * @param task the name of the task
     */
    public void taskIsDone(Task task) {
        if (tasks.contains(task)) {
            task.setIsFinished(1);
        }
        else task.setIsFinished(0);
    }

    /**
     * Searches for a specific task using the following method.
     * @param title of the task.
     * @return the task if found in the list, else return null.
     */
    public Task findTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }
}
