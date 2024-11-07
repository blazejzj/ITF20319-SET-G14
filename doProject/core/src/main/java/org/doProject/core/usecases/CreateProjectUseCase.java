package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;


/**
 * CreateProjectUseCase is responsible for creating a new project in the repository.
 * It validates that the project title is not empty and associates the new project
 * with a specified user ID.
 *
 * If validation fails, an IllegalArgumentException is thrown.
 * Upon successful creation, this use case returns the created project as a ProjectDTO.
 */
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructor for CreateProjectUseCase.
     *
     * @param projectRepository an instance of ProjectRepository to handle project data operations.
     */
    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    /**
     * Executes the creation of a new project for a specified user.
     *
     * @param projectDTO a ProjectDTO containing details for the new project.
     * @param userId the ID of the user creating the project.
     * @return the created ProjectDTO with a generated project ID.
     * @throws Exception if there is an issue with creating the project.
     * @throws IllegalArgumentException if the project title is empty.
     */
    public ProjectDTO execute(ProjectDTO projectDTO, int userId) throws Exception{
        if (projectDTO.getTitle() == null || projectDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cant be empty");
        }

        Project project = new Project(
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                userId
        );

        // assign (genereate) id via database
        int projectId = projectRepository.saveProject(project);
        projectDTO.setId(projectId);

        return projectDTO;
    }
}
