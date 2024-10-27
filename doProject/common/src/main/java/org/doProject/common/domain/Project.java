package org.doProject.common.domain;

import java.util.ArrayList;
import org.doProject.common.domain.Task;

/**
 * Project class represents a project in the calendar application DO.
 * A Project object contains details such as the project's ID, title, description, a list of tasks and
 * the ID of the user that the project belongs to.
 * This class contains methods to manage tasks connected to a project.
 */
public class Project {
    // Variables
    private int projectID;
    private String projectTitle;
    private String projectDescription;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int userID;

    /**
     * Constructs a Project object with a specified ID, title, description and the users ID.
     * @param projectID ID of the project.
     * @param projectTitle Title of the project.
     * @param projectDescription Description of the Project.
     * @param userID ID of the user that the project belongs to.
     */
    // Constructor 1
    public Project(int projectID, String projectTitle, String projectDescription, int userID) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.userID = userID;
    }

    /**
     * Constructs a Project object with a specified title, description and user ID.
     * @param projectTitle Title of the project.
     * @param projectDescription Description of the project.
     * @param userID ID of the user that the project belongs to.
     */
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
     * Adds a specified task to the project's task list.
     * @param task Task object to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a specified task from the project's task list.
     * @param task Task object to remove.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Searches for a specific task by title in the list.
     * @param title Title of the task to search for.
     * @return the task object if found in the list, else return null.
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
