package org.doProject.core.usecases;

import org.doProject.core.port.ProjectRepository;

/**
 * Handles deletion of a project by its ID, ensuring project existence.
 *
 * Throws an exception if deletion fails or the project does not exist.
 */
public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructs DeleteProjectUseCase with the given repository.
     *
     * @param projectRepository ProjectRepository for data handling.
     */
    public DeleteProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId the ID of the project to delete.
     * @throws Exception if there is an issue deleting the project or it does not exist.
     */
    public void execute(int projectId) throws Exception {
        projectRepository.deleteProject(projectId);
    }
}

