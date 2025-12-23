package com.example.realtime_notification.repository;

import com.example.realtime_notification.domain.TaskLog;
import com.example.realtime_notification.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {

    Optional<TaskLog> findByTask(Task task);
}