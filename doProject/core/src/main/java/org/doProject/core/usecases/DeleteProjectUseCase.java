package org.doProject.core.usecases;

import org.doProject.core.port.ProjectRepository;

public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    public DeleteProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void execute(int projectId) throws Exception {
        projectRepository.deleteProject(projectId);
    }
}
