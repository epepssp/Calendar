package com.example.diary.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.diary.domain.Calendar;
import com.example.diary.dto.CalendarDto;
import com.example.diary.repository.CalendarRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    
    private final CalendarRepository calendarRepository;
    
    public CalendarDto calendarCreate(LocalDate date){ //오늘 날짜면 now겠고
        
        CalendarDto dto = CalendarDto.builder().year(date.getYear()).month(date.getMonth())
                .monthValue(date.getMonthValue()).monthString(date.getMonth().toString())
                .day(date.getDayOfMonth()).dayOfWeek(date.getDayOfWeek())
                .lengthOfMonth(date.getMonth().maxLength()).build();
     
        List<Calendar> list = calendarRepository.findAll();
        for (Calendar c : list) {
             if(c.getYear() == date.getYear() && c.getMonthValue() == date.getMonthValue()) {
                  break;
            } else {
                LocalDate day1 = LocalDate.of(date.getYear(),date.getMonth(), 1);
                Calendar cal = Calendar.builder().year(date.getYear()).month(date.getMonth().toString())
                                .monthValue(date.getMonthValue()).firstDayOf(day1.getDayOfWeek().toString())
                                .monthLength(date.getMonth().maxLength()).build();
                
                calendarRepository.save(cal);
            }
         }
          
        
        return dto;
    }
    
  
    
}