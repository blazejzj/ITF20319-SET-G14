package org.doProject.core.usecases;

import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;

/**
 * Updates an existing task's details, ensuring valid input and task existence.
 *
 * Ensures:
 * - Task title is not empty.
 * - Task exists in the repository before updating.
 *
 * Throws an exception if validation fails or the task is not found.
 */
public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructs UpdateTaskUseCase with the provided repository.
     *
     * @param taskRepository TaskRepository for task data handling.
     */
    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Updates a task with the provided task ID and details.
     *
     * @param taskId  the ID of the task to update.
     * @param taskDTO TaskDTO containing updated task details.
     * @throws Exception if an error occurs or if the task title is empty or task is not found.
     */
    public void execute(int taskId, TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
            throw new Exception("Task title can't be empty");
        }

        Task existingTask = taskRepository.getTaskById(taskId);
        if (existingTask == null) {
            throw new Exception("Task not found");
        }

        Task updatedTask = new Task(
                taskId,
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getDueDate(),
                taskDTO.getIsFinished(),
                taskDTO.getIsRepeating(),
                taskDTO.getRepeatDays()
        );

        taskRepository.updateTask(updatedTask);
    }
}
