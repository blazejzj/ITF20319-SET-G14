package org.doProject.core.usecases;

import org.doProject.core.port.TaskRepository;

/**
 * Use case for deleting a task by its ID.
 */
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor for DeleteTaskUseCase.
     *
     * @param taskRepository the repository interface for task operations.
     */
    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Executes the use case to delete a task.
     *
     * @param taskId the ID of the task to delete.
     * @throws Exception if an error occurs during deletion.
     */
    public void execute(int taskId) throws Exception {
        taskRepository.deleteTask(taskId);
    }
}