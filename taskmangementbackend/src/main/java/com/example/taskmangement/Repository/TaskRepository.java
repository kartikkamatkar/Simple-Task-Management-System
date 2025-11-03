package com.example.taskmangement.Repository;

import com.example.taskmangement.Model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

    // Fetch all tasks for a user, sorted by newest first
    List<TaskModel> findByUserLoginOrderByCreatedAtDesc(String userLogin);

    // Fetch tasks for a user filtered by status (ex: todo, progress, completed), sorted by newest first
    List<TaskModel> findByUserLoginAndStatusOrderByCreatedAtDesc(String userLogin, String status);
}
