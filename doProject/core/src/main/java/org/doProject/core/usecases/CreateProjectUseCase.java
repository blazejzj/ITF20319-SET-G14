package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
