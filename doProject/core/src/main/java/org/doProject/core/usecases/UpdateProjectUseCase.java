package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

public class UpdateProjectUseCase {

    private final ProjectRepository projectRepository;

    public UpdateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
