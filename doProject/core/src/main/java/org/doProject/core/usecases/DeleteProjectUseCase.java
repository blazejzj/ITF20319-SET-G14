package org.doProject.core.usecases;

import org.doProject.core.port.ProjectRepository;

/**
 * DeleteProjectUseCase is responsible for deleting a project from the repository
 * based on the provided project ID.
 *
 * This use case validates that the project exists before attempting deletion.
 * It is intended to be used in cases where a user wishes to remove an existing project.
 *
 * If the project ID is invalid or an issue occurs during deletion, an exception may be thrown.
 */
public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    /**
     * Constructor for DeleteProjectUseCase.
     *
     * @param projectRepository an instance of ProjectRepository to handle project data operations.
     */
    public DeleteProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    /**
     * Executes the delete operation for a project by its ID.
     *
     * @param projectId the ID of the project to delete.
     * @throws Exception if there is an issue deleting the project or if the project does not exist.
     */
    public void execute(int projectId) throws Exception {
        projectRepository.deleteProject(projectId);
    }
}
