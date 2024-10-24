package org.doProject.common.domain;
import java.util.ArrayList;


//Instance variables
public class User {
    private int id;
    private String userName;
    private ArrayList<Project> projects = new ArrayList<>();

    //Constructors
    public User(String userName) {this.userName = userName;}
    public User(int id, String userName) {this.id = id;
        this.userName = userName;}

    // Getters
    public String getUserName() {return userName;}
    public int getId() {return id;}

    //Setters
    public void setUserName(String userName) {this.userName = userName;}
    public void setId(int id) {this.id = id;}

    //Methods
    public void addProject(Project project) {this.projects.add(project);}
    public void removeProject(Project project) {this.projects.remove(project);} //fjerne et enkelt prosjekt
    public void removeAllProjects() {this.projects.clear();} //fjerne alle brukerens prosjekter

    //overload
    public void removeProjectById(int projectId) {projects.removeIf(project -> project.getId() == projectId);}
}


