package org.doProject.core.usecases;

import org.doProject.core.port.TaskRepository;

/**
 * Handles deletion of a task by its ID.
 *
 * Throws an exception if an error occurs during deletion.
 */
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructs DeleteTaskUseCase with the provided repository.
     *
     * @param taskRepository TaskRepository for task data handling.
     */
    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete.
     * @throws Exception if an error occurs during deletion.
     */
    public void execute(int taskId) throws Exception {
        taskRepository.deleteTask(taskId);
    }
}
