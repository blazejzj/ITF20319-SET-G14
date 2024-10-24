package models;

import java.util.ArrayList;

//Instance variables
public class User {
    private int id;
    private String userName;
    private ArrayList<Project> projects;


    //Constructors
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
        this.projects = new ArrayList<>();}


    // Getters
    public int getId() {
        return id;
    }
    public String getUserName() {
        return userName;}

    public ArrayList<Project> getProjects() {
        return projects;}


    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;}

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;}


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

