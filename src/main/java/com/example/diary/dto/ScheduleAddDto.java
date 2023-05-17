package com.example.diary.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScheduleAddDto {

 
    private int year;
    
   
    private int monthValue;
  
    private int day;
    
    private String fullDate;

    private String content;
    
}
