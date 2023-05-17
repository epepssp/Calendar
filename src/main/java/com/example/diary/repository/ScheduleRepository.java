package com.example.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.domain.Schedule;
import com.example.diary.dto.ScheduleAddDto;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByFullDate(String fullDate);

    List<Schedule> findByDay(int day);

    Schedule findByScheduleId(int scheduleId);

    List<Schedule> findByMonthValue(int monthValue);

   

}
