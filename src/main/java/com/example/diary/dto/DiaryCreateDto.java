package com.example.diary.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.example.diary.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiaryCreateDto {
    

    
    private int monthValue;
   private int year;
    
   
    private int day;
    
    private String wheater;
    
    private String title;


    private String diaryContent;
    

}
