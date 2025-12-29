package com.example.realtime_notification.service;

import com.example.realtime_notification.domain.Task;
import java.time.LocalDate;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.realtime_notification.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeadlineCheckService {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 100)
    public void checkDeadlines(){
        LocalDate today = LocalDate.now();

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            // ถ้าวันนี้คือกำหนดส่ง (Deadline)
            if (task.getDeadline().equals(today)) {
                String message = "แจ้งเตือน: งาน '" + task.getTitle() + "' ถึงกำหนดส่งวันนี้แล้ว!";
                
                // ส่งข้อมูลผ่าน WebSocket ไปยังท่อ /topic/notifications
                // ใครที่เปิดหน้าเว็บทิ้งไว้และ Subscribe ท่อนี้อยู่ จะได้รับข้อความทันที
                messagingTemplate.convertAndSend("/topic/notifications", message);
                
                System.out.println("DEBUG: ส่งแจ้งเตือนงาน ID " + task.getId());
            }
        }
    }
}
