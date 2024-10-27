package org.doProject.common.domain;

import java.util.ArrayList;

/**
 * Represents a user with an ID, userName, and a list of their projects.
 * User class provides different methods for managing projects, including adding
 * removing, and clearing projects from the users project list
 */
public class User {

    // Instance variables
    private int id;
    private String userName;
    private ArrayList<Project> projects = new ArrayList<>();

    //Constructors

    /**
     * Constructs a User with a specified username.
     * ID is not assigned in this constructor and may be set later.
     *
     * @param userName The username of the user.
     */
    public User(String userName) {
        this.userName = userName;
    }

    /**
     * Constructs a User with a specified ID and username.
     *
     * @param id       Unique identifier for the user.
     * @param userName The username of the user.
     */
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    // Getters
    public String getUserName() {return userName;}
    public int getId() {return id;}
    public ArrayList<Project> getProjects() {return projects;}

    // Setters
    public void setUserName(String userName) {this.userName = userName;}
    public void setId(int id) {this.id = id;}
    public void setProjects(ArrayList<Project> projects) {this.projects = projects;}

    // Methods

    /**
     * Adds a project to the users projects.
     *
     * @param project The project to add to the user's projects.
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Removes a specific project from the users projects.
     *
     * @param project The project to remove from the user projects.
     */
    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    /**
     * Removes a project from the user project list by projectID.
     * If multiple projects have the same ID, all will be removed.
     *
     * @param projectId The ID of the project to remove.
     */
    public void removeProjectById(int projectId) {
        projects.removeIf(project -> project.getId() == projectId);
    }

    /**
     * Clears all projects from the user project list.
     */
    public void removeAllProjects() {
        this.projects.clear();
    }
}


