package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

import java.util.ArrayList;


/**
 * GetProjectByUserUseCase is responsible for retrieving all projects associated
 * with a specific user from the repository.
 *
 * This use case fetches all projects for the provided user ID, transforming each project
 * into a ProjectDTO for easier data handling in the presentation layer.
 *
 * If no projects exist for the specified user, an empty list is returned.
 */
public class GetProjectsByUserUseCase {

    private final ProjectRepository projectRepository;


    /**
     * Constructor for GetProjectByUserUseCase.
     *
     * @param projectRepository an instance of ProjectRepository to handle project data operations.
     */
    public GetProjectsByUserUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Executes the retrieval of projects for a specified user ID.
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return an ArrayList of ProjectDTOs representing the user's projects.
     * @throws Exception if there is an issue with retrieving the projects.
     */
    public ArrayList<ProjectDTO> execute(int userId) throws Exception {
        ArrayList<Project> projects = projectRepository.loadUserProjects(userId);
        ArrayList<ProjectDTO> projectDTOs = new ArrayList<>();

        for (Project project : projects) {
            ProjectDTO projectDTO = new ProjectDTO(
                    project.getId(),
                    project.getTitle(),
                    project.getDescription(),
                    userId
            );
            projectDTOs.add(projectDTO);
        }
        return projectDTOs;
    }
}
