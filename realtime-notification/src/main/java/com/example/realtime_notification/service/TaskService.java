package com.example.realtime_notification.service;

import com.example.realtime_notification.domain.Task;
import com.example.realtime_notification.domain.User;
import com.example.realtime_notification.repository.TaskRepository;
import com.example.realtime_notification.repository.TaskLogRepository;
import com.example.realtime_notification.domain.TaskLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskLogRepository taskLogRepository;

    // 1. สร้าง Task
    @Transactional
    public Task createTask(Task task, User creator) {
        task.setCreator(creator);
        Task savedTask = taskRepository.save(task);

        // บันทึก Log เมื่อมีการสร้าง
        saveLog(savedTask, "สร้างงานใหม่โดย: " + creator.getUsername());
        
        return savedTask;
    }

    // 2. แก้ไข Task (เช็คสิทธิ์คนสร้าง)
@Transactional
public Task updateTask(Long taskId, Task taskDetails, User user) {
    Task existingTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("ไม่พบ Task ID: " + taskId));

    if (!existingTask.getCreator().getId().equals(user.getId())) {
        throw new RuntimeException("คุณไม่มีสิทธิ์จัดการงานนี้!");
    }

    if (taskDetails.getTitle() != null) existingTask.setTitle(taskDetails.getTitle());
    if (taskDetails.getDetail() != null) existingTask.setDetail(taskDetails.getDetail());
    if (taskDetails.getDeadline() != null) existingTask.setDeadline(taskDetails.getDeadline());

    saveLog(existingTask, "แก้ไขข้อมูลงานโดย: " + user.getUsername());

    return existingTask; 
}

    // 3. ลบ Task (เช็คสิทธิ์คนสร้าง)
    @Transactional
    public void deleteTask(Long taskId, User user) {
        Task existingTask = getTaskAndCheckOwnership(taskId, user);
        
        taskRepository.delete(existingTask);

    }

    // --- Helper Methods (Logic ภายใน) ---

    private Task getTaskAndCheckOwnership(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("ไม่พบ Task ID: " + taskId));

        // Logic สำคัญ: ถ้าคนที่จะแก้/ลบ ไม่ใช่คนสร้าง (Creator) ให้ Error ทันที
        if (!task.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("คุณไม่มีสิทธิ์จัดการงานนี้");
        }
        
        return task;
    }

    private void saveLog(Task task, String content) {
        TaskLog log = TaskLog.builder()
                .task(task)
                .logContent(content)
                .build();
        taskLogRepository.save(log);
    }
}