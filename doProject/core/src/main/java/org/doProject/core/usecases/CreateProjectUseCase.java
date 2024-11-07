package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;


/**
 * Handles the creation of a new project in the repository, validating inputs.
 *
 * Ensures:
 * - Project title is not empty.
 * - Project is associated with a specific user ID.
 *
 * Throws IllegalArgumentException if validation fails. Returns the created project as a ProjectDTO.
 */
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructs CreateProjectUseCase with the given repository.
     *
     * @param projectRepository ProjectRepository for data handling.
     */
    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Creates a new project for a specified user.
     *
     * @param projectDTO the ProjectDTO with new project details.
     * @param userId the ID of the user creating the project.
     * @return the created ProjectDTO with generated project ID.
     * @throws Exception if there is an issue with creation.
     * @throws IllegalArgumentException if the project title is empty.
     */
    public ProjectDTO execute(ProjectDTO projectDTO, int userId) throws Exception {
        if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Project title cannot be empty");
        }

        Project project = new Project(
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                userId
        );

        int projectId = projectRepository.saveProject(project);
        projectDTO.setId(projectId);

        return projectDTO;
    }
}
