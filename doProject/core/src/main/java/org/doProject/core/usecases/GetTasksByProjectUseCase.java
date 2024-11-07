package org.doProject.core.usecases;


import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;

import java.util.ArrayList;

/**
 * Use case for retrieving all tasks associated with a specific project.
 */
public class GetTasksByProjectUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor for GetTasksByProjectUseCase.
     *
     * @param taskRepository the repository interface for task operations.
     */
    public GetTasksByProjectUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Executes the use case to retrieve tasks by project ID.
     *
     * @param projectId the ID of the project whose tasks are to be retrieved.
     * @return a list of TaskDTOs representing the tasks.
     * @throws Exception if an error occurs during retrieval.
     */
    public ArrayList<TaskDTO> execute(int projectId) throws Exception {
        ArrayList<Task> tasks = taskRepository.loadTasks(projectId);
        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getIsFinished(),
                    task.getIsRepeating(),
                    task.getRepeatDays()
            );
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
