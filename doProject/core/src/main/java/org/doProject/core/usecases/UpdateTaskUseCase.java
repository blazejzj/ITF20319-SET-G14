package org.doProject.core.usecases;

import org.doProject.core.domain.Task;
import org.doProject.core.dto.TaskDTO;
import org.doProject.core.port.TaskRepository;

/**
 * Use case for updating an existing task.
 */
public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor for UpdateTaskUseCase.
     *
     * @param taskRepository the repository interface for task operations.
     */
    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Executes the use case to update a task.
     *
     * @param taskId  the ID of the task to update.
     * @param taskDTO the data transfer object containing updated task details.
     * @throws Exception if an error occurs during update.
     */
    public void execute(int taskId, TaskDTO taskDTO) throws Exception {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
            throw new Exception("Task title cantt be empty");
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