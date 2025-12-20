package com.example.realtime_notification.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private Notification notification;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private TaskLog taskLog;

    public Task() {}

    // ===== Getter / Setter =====

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }

    @Transient
    public boolean isExpired() {
        return deadline != null && deadline.isBefore(LocalDate.now());
    }
}
