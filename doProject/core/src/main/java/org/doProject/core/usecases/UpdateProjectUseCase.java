package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

/**
 * Updates an existing project with new values, validating inputs.
 *
 * Ensures:
 * - Project title is not empty.
 * - Project exists in the repository before updating.
 *
 * Throws IllegalArgumentException if validation fails or project not found.
 */
public class UpdateProjectUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructs UpdateProjectUseCase with the given repository.
     *
     * @param projectRepository ProjectRepository for data handling.
     */
    public UpdateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Updates a project with given ID and details in ProjectDTO.
     *
     * @param projectId the ID of the project to update.
     * @param projectDTO the ProjectDTO with updated project information.
     * @throws Exception if there is an issue with updating.
     * @throws IllegalArgumentException if the title is empty or project is not found.
     */
    public void execute(int projectId, ProjectDTO projectDTO) throws Exception {
        if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Project title cannot be empty");
        }

        Project existingProject = projectRepository.getProjectById(projectId);
        if (existingProject == null) {
            throw new IllegalArgumentException("Project not found");
        }

        int userId = existingProject.getUserID();

        Project updatedProject = new Project(
                projectId,
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                userId
        );

        projectRepository.updateProject(updatedProject);
    }
}
