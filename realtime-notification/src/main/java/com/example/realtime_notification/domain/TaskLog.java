package com.example.realtime_notification.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_logs")
public class TaskLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "log_content", columnDefinition = "TEXT")
    private String logContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public TaskLog() {}

    // Getters and Setters
    public Long getId() { return id; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }

    public String getLogContent() { return logContent; }
    public void setLogContent(String logContent) { this.logContent = logContent; }
}