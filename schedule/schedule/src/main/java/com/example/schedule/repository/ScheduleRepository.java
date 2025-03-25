package com.example.schedule.repository;

import com.example.schedule.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ScheduleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ✅ RowMapper 미리 선언해서 재사용
    private final RowMapper<Schedule> rowMapper = (rs, rowNum) -> new Schedule(
            rs.getLong("id"),
            rs.getString("todo"),
            rs.getString("writer"),
            rs.getString("password"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
    );

    public void save(Schedule schedule) {
        String sql = "INSERT INTO schedules (todo, writer, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                schedule.getTodo(),
                schedule.getWriter(),
                schedule.getPassword(),
                Timestamp.valueOf(schedule.getCreatedAt()),
                Timestamp.valueOf(schedule.getUpdatedAt()));
    }

    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedules ORDER BY updated_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // ✅ 여기만 수정
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void update(Schedule schedule) {
        String sql = "UPDATE schedules SET todo = ?, writer = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTodo(),
                schedule.getWriter(),
                schedule.getUpdatedAt(),
                schedule.getId()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
