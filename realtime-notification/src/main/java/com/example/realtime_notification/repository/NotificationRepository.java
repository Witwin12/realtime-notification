package com.example.realtime_notification.repository;

import com.example.realtime_notification.domain.Notification;
import com.example.realtime_notification.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByTask(Task task);
    
    java.util.List<Notification> findByTargetTag(String targetTag);
}