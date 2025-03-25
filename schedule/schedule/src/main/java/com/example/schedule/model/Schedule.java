package com.example.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String todo;
    private String writer;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
