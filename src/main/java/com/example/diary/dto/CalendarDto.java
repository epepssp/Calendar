package com.example.diary.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.example.diary.domain.Week;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data

public class CalendarDto {

//   LocalDate date = LocalDate.of(2023, 5, 1);
//    LocalDate now = LocalDate.now();  //현재 날짜로 출발
//    int day = now.getDayOfMonth();
//    Month month = now.getMonth();  // MARCH
//    int year = now.getYear();
//    int monthV =   now.getMonthValue(); // 3
//    DayOfWeek dow = now.getDayOfWeek(); // MONDAY
//    String dows = now.getDayOfWeek().toString();
//    int mLength= now.getMonth().maxLength();  // 한달 며칠까지 있는지
    
    
//    Calendar calendar = Calendar.getInstance();
//     calendar.get(Calendar.YEAR);
//     calendar.get(Calendar.MONTH);
    
   private Integer id; 
   private  LocalDate date; 
   
   private  int day;
   private  int monthValue;
   private  int year;
   private  int lengthOfMonth;
   
   
   
   private  Month month;
   private  DayOfWeek dayOfWeek; // 요일
     
   private  String monthString;
   private  int original;
   
   
   public  List<Integer> fromEntity(CalendarDto dto) {
       List<Integer> dList =new ArrayList<>();
       
       String dayOfWeekS = dto.getDayOfWeek().toString(); // 요일
       
       Week w = Week.valueOf(dayOfWeekS);
       for(Week e: Week.values()) {
           if(e.equals(w)) {
             original=  e.ordinal();
           }
       }
       
       lengthOfMonth = dto.getLengthOfMonth();
       
       if(original-1 >0) {
           for (int i = 0; i < original-1; i++) {
              dList.add(0);
             }
         }
              
     for (int j = 0;j <  lengthOfMonth; j++) {
            dList.add(j);
           }
     
       int sub =  lengthOfMonth + original -1;
           
       if(35-sub >0) {
          for (int i = 0; i <35- sub; i++) {
              dList.add(0);
               }
           }
       
      
       return dList;
   }
   
  
    
    
    
}