package com.example.diary.dto;

import java.util.List;

import com.example.diary.domain.Diary;
import com.example.diary.domain.Schedule;
import com.example.diary.service.DiaryService;
import com.example.diary.service.ScheduleService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DayDiaryDto {

    private int day;
    private Integer diaryId;
    private List<Schedule> sList;
    private Integer dDayId;
    
  //  private String dDayName;
    
//    private final DiaryService diaryService;
//    private final ScheduleService scheduleService;
    
//    public List<Schedule> fromMonthOfDayS(int monthValue, int day){
//       for (Schedule s : scheduleService.findByDay(day)) {
//            if(s.getMonthValue() == monthValue){
//                sList.add(s);
//            }
//        }
//       return sList;
//    }
//    
  
    
}