package org.doProject.core.usecases;

import org.doProject.core.domain.Project;
import org.doProject.core.dto.ProjectDTO;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.ProjectRepository;

import java.util.ArrayList;

/**
 * Retrieves all projects associated with a specific user.
 *
 * Returns an empty list if no projects exist for the user.
 */
public class GetProjectsByUserUseCase {

    private final ProjectRepository projectRepository;
    private final GetTasksByProjectUseCase getTasksByProjectUseCase;

    /**
     * Constructs GetProjectsByUserUseCase with the given repository and task use case.
     *
     * @param projectRepository ProjectRepository for data handling.
     * @param getTasksByProjectUseCase Use case to retrieve tasks by project ID.
     */
    public GetProjectsByUserUseCase(ProjectRepository projectRepository, GetTasksByProjectUseCase getTasksByProjectUseCase) {
        this.projectRepository = projectRepository;
        this.getTasksByProjectUseCase = getTasksByProjectUseCase;
    }

    /**
     * Fetches projects for a specific user ID, including tasks for each project.
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return a list of ProjectDTOs representing the user's projects.
     * @throws Exception if there is an issue with retrieval.
     */
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

            ArrayList<TaskDTO> taskDTOs = getTasksByProjectUseCase.execute(project.getId());
            projectDTO.setTaskDTOs(taskDTOs);
            projectDTOs.add(projectDTO);
        }
        return projectDTOs;
    }
}
