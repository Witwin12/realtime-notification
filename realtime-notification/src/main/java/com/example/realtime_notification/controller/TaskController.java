package com.example.realtime_notification.controller;

import com.example.realtime_notification.domain.Task;
import com.example.realtime_notification.domain.User;
import com.example.realtime_notification.service.TaskService;
import com.example.realtime_notification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService; 

    // สร้างงาน: POST /api/tasks?userId=1
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(taskService.createTask(task, user));
    }

    // แก้ไขงาน: PUT /api/tasks/1?userId=1
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long taskId,
            @RequestBody Task taskDetails,
            @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDetails, user));
    }

    // ลบงาน: DELETE /api/tasks/1?userId=1
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        taskService.deleteTask(taskId, user);
        return ResponseEntity.ok("ลบงานเรียบร้อยแล้ว");
    }
}