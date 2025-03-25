package com.example.schedule.dto;

import com.example.schedule.model.Schedule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.writer = schedule.getWriter();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
