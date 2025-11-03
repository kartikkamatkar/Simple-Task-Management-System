package com.example.taskmangement.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class TaskModel {

    // Primary key for the task table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Short task title, required
    @Column(nullable = false)
    private String title;

    // Detailed task description (optional)
    private String description;

    // Task priority (high, medium, low), required
    @Column(nullable = false)
    private String priority;

    // Due date stored as string format (should ideally be LocalDate, but acceptable)
    @Column(nullable = false)
    private String dueDate;

    // Category (example: work, personal, etc.)
    private String category;

    // Current task status (todo, progress, completed)
    @Column(nullable = false)
    private String status = "todo";

    // Username to whom the task belongs (foreign reference but stored as text)
    @Column(nullable = false)
    private String userLogin;

    // Timestamp when the task was created
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Default constructor required by JPA
    public TaskModel() {}

    // Constructor used when manually creating a task
    public TaskModel(String title, String description, String priority, String dueDate, String category, String userLogin) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.category = category;
        this.userLogin = userLogin;
    }

    // Getters and Setters for all fields

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserLogin() { return userLogin; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Helper method: returns true only if the task is completed
    public boolean isCompleted() {
        return "completed".equals(status);
    }
}
