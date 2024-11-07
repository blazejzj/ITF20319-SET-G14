package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.port.ProjectRepository;

import java.util.ArrayList;

public class GetProjectByUserUseCase {

    private final ProjectRepository projectRepository;

    public GetProjectByUserUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
