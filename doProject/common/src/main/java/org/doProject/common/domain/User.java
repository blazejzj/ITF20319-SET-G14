package org.doProject.common.domain;

import org.doProject.common.domain.Project;
import java.util.ArrayList;

//Instance variables
public class User {
    private String fullName;
    private String userName;
    private ArrayList<Project> projects;


//Constructors
    public User(String userName, String fullName) {
        this.fullName = fullName;
        this.userName = userName;
        this.projects = new ArrayList<>();}


// Getters
    public String getFullName() {
        return fullName;}

    public String getUserName() {
        return userName;}

    public ArrayList<Project> getProjects() {
        return projects;}


//Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;}

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
