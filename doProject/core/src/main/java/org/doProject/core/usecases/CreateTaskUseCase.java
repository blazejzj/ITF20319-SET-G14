package org.doProject.core.usecases;
import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;

/**
 * Use case for creating a new task within a project.
 */
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor for CreateTaskUseCase.
     *
     * @param taskRepository the repository interface for task operations.
     */
    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Executes the use case to create a new task.
     *
     * @param taskDTO   the data transfer object containing task details.
     * @param projectId the ID of the project to which the task belongs.
     * @return a TaskDTO containing the created task's details.
     * @throws Exception if an error occurs during task creation.
     */
    public TaskDTO execute(TaskDTO taskDTO, int projectId) throws Exception {

        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
            throw new Exception("Task title can't be empty");
        }

        Task task = new Task(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getDueDate(),
                taskDTO.getIsFinished(),
                taskDTO.getIsRepeating(),
                taskDTO.getRepeatDays()
        );

        int taskId = taskRepository.saveTask(task, projectId);
        taskDTO.setTaskID(taskId);
        return taskDTO;
    }
}
