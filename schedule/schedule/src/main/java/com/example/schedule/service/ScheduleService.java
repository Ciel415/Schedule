package com.example.schedule.service;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.model.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    public ScheduleResponseDto createSchedule(String todo, String writer, String password) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(null, todo, writer, password, now, now);
        repository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return repository.findAll()
                .stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto updateSchedule(Long id, String todo, String writer, String password) {
        Schedule schedule = repository.findById(id);
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        schedule.setTodo(todo);
        schedule.setWriter(writer);
        schedule.setUpdatedAt(LocalDateTime.now());

        repository.update(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id, String password) {
        Schedule schedule = repository.findById(id);
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        repository.delete(id);
    }
}
