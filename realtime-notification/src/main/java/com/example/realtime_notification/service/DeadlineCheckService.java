package com.example.realtime_notification.service;

import com.example.realtime_notification.domain.Task;
import com.example.realtime_notification.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeadlineCheckService {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void checkDeadlines() {
        LocalDate today = LocalDate.now();

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            // เช็คเงื่อนไข: วันที่ตรงกับวันนี้ และ ยังไม่ได้แจ้งเตือน (notified == false หรือ null)
            if (task.getDeadline().equals(today) && !Boolean.TRUE.equals(task.getNotified())) {
                
                String message = "แจ้งเตือน: งาน '" + task.getTitle() + "' ถึงกำหนดส่งวันนี้แล้ว!";
                
                // ส่งข้อมูลผ่าน WebSocket
                messagingTemplate.convertAndSend("/topic/notifications", message);
                
                // อัปเดตสถานะเป็นแจ้งเตือนแล้ว
                task.setNotified(true);
                taskRepository.save(task);
                
                System.out.println("DEBUG: ส่งแจ้งเตือนงาน ID " + task.getId());
            }
        }
    }
}