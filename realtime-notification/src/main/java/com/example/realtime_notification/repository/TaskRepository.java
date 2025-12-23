package com.example.realtime_notification.repository;

import com.example.realtime_notification.domain.Task;
import com.example.realtime_notification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
        List<Task> findByCreator(User creator);
}