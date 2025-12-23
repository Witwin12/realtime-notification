package com.example.realtime_notification.repository;

import com.example.realtime_notification.domain.TaskLog;
import com.example.realtime_notification.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
    // ค้นหา Log ของ Task นั้นๆ
    Optional<TaskLog> findByTask(Task task);
}