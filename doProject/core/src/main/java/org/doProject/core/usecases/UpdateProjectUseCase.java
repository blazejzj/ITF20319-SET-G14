package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

/**
 * UpdateProjectUseCase is responsible for updating an existing project in the repository.
 * This use case checks for valid input, retrieves the existing project, and updates it
 * with new values from the provided ProjectDTO.
 *
 * This use case ensures:
 * - Project title is not empty.
 * - The project exists in the repository before updating.
 *
 * If validation fails or the project does not exist, an IllegalArgumentException is thrown.
 */
public class UpdateProjectUseCase {

    private final ProjectRepository projectRepository;


    /**
     * Constructor for UpdateProjectUseCase.
     *
     * @param projectRepository an instance of ProjectRepository to handle project data operations.
     */
    public UpdateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    /**
     * Executes the update operation for a given project ID and ProjectDTO.
     *
     * @param projectId the ID of the project to update.
     * @param projectDTO a ProjectDTO containing updated project information.
     * @throws Exception if there is an issue with updating the project.
     * @throws IllegalArgumentException if the project title is empty or the project is not found.
     */
    public void execute(int projectId, ProjectDTO projectDTO) throws Exception {
        if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Project title can't be empty");
        }

        Project existingProject = projectRepository.getProjectById(projectId);
        if (existingProject == null) {
            throw new IllegalArgumentException("Project not found");
        }

        int userId = existingProject.getUserID();

        Project updatedProject = new Project (
                projectId,
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                userId
        );

        projectRepository.updateProject(updatedProject);
    }
}
