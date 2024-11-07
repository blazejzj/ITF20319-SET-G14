package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

import java.util.ArrayList;


/**
 * Retrieves all projects associated with a specific user.
 *
 * Returns an empty list if no projects exist for the user.
 */
public class GetProjectsByUserUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructs GetProjectsByUserUseCase with the given repository.
     *
     * @param projectRepository ProjectRepository for data handling.
     */
    public GetProjectsByUserUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Fetches projects for a specific user ID.
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return a list of ProjectDTOs representing the user's projects.
     * @throws Exception if there is an issue with retrieval.
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
