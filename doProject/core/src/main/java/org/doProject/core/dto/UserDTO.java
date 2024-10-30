package org.doProject.core.dto;
import java.util.ArrayList;



public class UserDTO {

    private int id;
    private String userName;
    private ArrayList<ProjectDTO> projectDTOs;
    // For future reference, the list of ProjectDTO's could for example be refactored
    // to just hold the project IDs for simple data managment.


    public UserDTO(int id, String userName, ArrayList<ProjectDTO> projectDTOs) {
        this.id = id;
        this.userName = userName;
        this.projectDTOs = projectDTOs;
    }

    public String getUserName() {return userName;}
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setProjectsDTOs(ArrayList<ProjectDTO> projectDTOs) {this.projectDTOs = projectDTOs;}

}
