package com.example.diary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.diary.domain.Schedule;
import com.example.diary.dto.ScheduleAddDto;
import com.example.diary.repository.ScheduleRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RequiredArgsConstructor
@Service
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    
    public Integer create(ScheduleAddDto dto) {
     
       Schedule entity = Schedule.builder()
               .fullDate(dto.getFullDate())
               .year(dto.getYear()).monthValue(dto.getMonthValue()).day(dto.getDay()).content(dto.getContent()).build(); 
       Schedule s = scheduleRepository.save(entity);
       return s.getScheduleId();
    }

    public List<Schedule> findByDate(String fullDate) {
        
        List<Schedule> s = scheduleRepository.findByFullDate(fullDate);
        return s;
    }

    public List<Schedule> readAll() {
        
        List<Schedule> s = scheduleRepository.findAll();
        return s;
    }

    public List<Schedule> findByDay(int day) {
        List<Schedule> s = scheduleRepository.findByDay(day);
        return s;
    }

    public Schedule read(int scheduleId) {
          Schedule schedule = scheduleRepository.findByScheduleId(scheduleId);
        return schedule;
    }

    public List<Schedule> findByMonth(int monthValue) {
        List<Schedule> s = scheduleRepository.findByMonthValue(monthValue);
        
        return s;
    }

//    public Schedule readById(int scheduleId) {
//        Schedule s = scheduleRepository.findById(scheduleId).get();
//        
//        return s;
//    }

    

}
