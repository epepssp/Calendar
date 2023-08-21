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
    private Integer diaryId;      // 일기
    private List<Schedule> sList; // day에 등록된 일정 리스트
    private Integer dDayId;
    private int today;   // today면= 1, 아니면 =0 
    private String dayOfWeek; // 0:일요일 ~ 6툐요일
    
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