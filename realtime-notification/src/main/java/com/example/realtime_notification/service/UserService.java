package com.example.realtime_notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.realtime_notification.domain.User;
import com.example.realtime_notification.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบ User ID: " + id));
    }
}
