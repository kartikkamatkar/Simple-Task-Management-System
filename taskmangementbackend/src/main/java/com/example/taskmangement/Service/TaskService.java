package com.example.taskmangement.Service;

import com.example.taskmangement.Model.TaskModel;
import com.example.taskmangement.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    // Repository to handle database operations for tasks
    @Autowired
    private TaskRepository taskRepository;

    // Fetch all tasks for a specific user ordered by newest first
    public List<TaskModel> getTasksByUser(String userLogin) {
        return taskRepository.findByUserLoginOrderByCreatedAtDesc(userLogin);
    }

    // Save a new task or update an existing one
    public TaskModel saveTask(TaskModel task) {
        return taskRepository.save(task);
    }

    // Update status of a specific task
    public void updateTaskStatus(Integer taskId, String status) {

        // Find task by ID
        Optional<TaskModel> taskOpt = taskRepository.findById(taskId);

        // If task exists then update status
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    // Delete a task using its ID
    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
