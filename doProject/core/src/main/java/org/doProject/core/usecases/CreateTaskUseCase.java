package org.doProject.core.usecases;
import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;

/**
 * Creates a new task within a specified project.
 *
 * Ensures:
 * - Task title is not empty.
 *
 * Throws an exception if validation fails or if an error occurs during task creation.
 */
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructs CreateTaskUseCase with the provided repository.
     *
     * @param taskRepository TaskRepository for task data handling.
     */
    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task within a project.
     *
     * @param taskDTO   TaskDTO containing the task's details.
     * @param projectId the ID of the project to which the task belongs.
     * @return a TaskDTO with details of the created task.
     * @throws Exception if an error occurs or if the task title is empty.
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
