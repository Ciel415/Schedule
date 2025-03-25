package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(service.createSchedule(
                request.getTodo(),
                request.getWriter(),
                request.getPassword()
        ));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAllSchedules());
    }

    @Data
    public static class ScheduleRequest {
        private String todo;
        private String writer;
        private String password;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody ScheduleRequest request
    ) {
        try {
            ScheduleResponseDto updated = service.updateSchedule(
                    id,
                    request.getTodo(),
                    request.getWriter(),
                    request.getPassword()
            );
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordBody
    ) {
        String password = passwordBody.get("password");
        try {
            service.deleteSchedule(id, password);
            return ResponseEntity.ok("삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
