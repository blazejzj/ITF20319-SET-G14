package org.doProject.common.domain;
import java.util.ArrayList;


//Instance variables
public class User {
    private int id;
    private String userName;
    private ArrayList<Project> projects = new ArrayList<>();


    //Constructors
    public User(String userName) {
        this.userName = userName;
    }

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    // Getters
    public String getUserName() {
        return userName;}

    public ArrayList<Project> getProjects() {
        return projects;}

    public int getId() {
        return id;
    }

    //Setters
    public void setUserName(String userName) {
        this.userName = userName;}

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;}

    public void setId(int id) {
        this.id = id;
    }

    //Methods
    public void addProject(Project project) {
        this.projects.add(project);}

    public void removeProject(Project project) {
        this.projects.remove(project);}

    public void showProjects() {
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
        }
        else {
            System.out.println("Projects: ");
            for (Project project : projects) {
                System.out.println("Project ID: " + project.getId() + ", Title: " + project.getTitle());}
        }
    }

}