package com.example.diary.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "CALENDAR")
@SequenceGenerator(name = "CALENDAR_SEQ_GEN", sequenceName = "CALENDAR_SEQ", initialValue = 1, allocationSize = 1)
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALENDAR_SEQ_GEN")
    private int calendarId;
    
    @Column(nullable = false)
    private int monthValue;
    
    @Column(nullable = false)
    private int monthLength;
    
    private String month;
    
   
    
   private LocalDate localDate;
   
   private LocalDateTime localDateTime;
   
   private String firstDayOf; // 달마다 1일 요일
    
   // private DayOfWeek dayOfWeek;
   
   @Column(nullable = false)
    private int year;
    
 
    private int day;
    
 //  private LocalDate date; 

   
   
    

}