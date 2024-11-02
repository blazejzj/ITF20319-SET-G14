package org.doProject.core.dto;
import java.util.ArrayList;


/**
 * UserDTO is a Data Transfer Object representing user information.
 * Its includes the users ID, username, and a list of ProjectDTO objects that represent
 * the projects associated with this user. This class is used for transferring user data
 * between different parts of the app.
 */
public class UserDTO {

    private int id;
    private String userName;
    private ArrayList<ProjectDTO> projectDTOs;
    // For future reference, the list of ProjectDTO's could for example be refactored
    // to just hold the project IDs for simple data managment.

    /**
     * Creates a UserDTO with the specified ID, username, and a list of associated projectDTOs (projects).
     *
     * @param id           The unique identifier of the user.
     * @param userName     The name of the user.
     * @param projectDTOs  A list of ProjectDTOs linked to this user.
     */
    public UserDTO(int id, String userName, ArrayList<ProjectDTO> projectDTOs) {
        this.id = id;
        this.userName = userName;
        this.projectDTOs = projectDTOs;
    }

    public UserDTO(int id, String userName) {
        this.id = id;
        this.userName = userName;
        this.projectDTOs = new ArrayList<>();
    }

    public String getUserName() {return userName;}
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setProjectsDTOs(ArrayList<ProjectDTO> projectDTOs) {this.projectDTOs = projectDTOs;}

}
