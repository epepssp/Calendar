package com.example.diary.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.diary.domain.DDay;
import com.example.diary.domain.Diary;
import com.example.diary.domain.Schedule;
import com.example.diary.domain.Week;
import com.example.diary.dto.CalendarDto;

import com.example.diary.dto.DayDiaryDto;
import com.example.diary.service.CalendarService;
import com.example.diary.service.DDayService;
import com.example.diary.service.DiaryService;
import com.example.diary.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CalendarController {
    
    private final CalendarService calendarService;
    private final ScheduleService scheduleService;
    private final DiaryService diaryService;
    private final DDayService dDayService;

    @GetMapping("/calendar") // 오늘 날짜
    public String calendar1(Model model) {
        
        // 현재 날짜로 출발
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);

        List<List<DayDiaryDto>> integratedList = integrateInfo(date.getYear(), date.getMonthValue());

        model.addAttribute("d1", integratedList.get(0));
        model.addAttribute("d2", integratedList.get(1));
        model.addAttribute("d3", integratedList.get(2));
        model.addAttribute("d4", integratedList.get(3));
        model.addAttribute("d5", integratedList.get(4));

        if (!integratedList.get(5).isEmpty()) {
            model.addAttribute("d6", integratedList.get(5));
        }

   
        
        CalendarDto dto = calendarService.calendarCreate(date);
        model.addAttribute("dto", dto); // CalendarDto
        
        int last = date.getMonth().maxLength();
        model.addAttribute("last", last);

        LocalDate now = LocalDate.now();
        int nowMonth = now.getMonthValue();
        int nowDay = now.getDayOfMonth();
        
        model.addAttribute("nowMonth", nowMonth);
        model.addAttribute("nowDay", nowDay);

        return "/calendar/main";

    }
    
    
//    public List<DDay> todayDDayLeft(){
//        
//        List<DDay> list =dDayService.readAll();
//       log.info("d-daaayㄹ시틑?={}",list);
//        
//        
//        
//        return list;
//    }
    
    
    public List<List<DayDiaryDto>> integrateInfo(int year, int monthValue) { 
        
        LocalDate date = LocalDate.of(year,monthValue, 1);
        
        String dayOfWeekS = date.getDayOfWeek().toString(); // 월 1일의 요일
        
        // Enum 이용해서 요일 차 계산: (HTML에서 달력 그릴때 몇번째칸부터 1일시작할지)
        // 차이만큼 0 널고 리스트 만들어서 넘기면 제대로 그릴 수 있음
        int original = 0;
        Week w = Week.valueOf(dayOfWeekS);  
        for(Week e: Week.values()) {
            if(e.equals(w)) {
              original=  e.ordinal();
            }
        }
        
        // 뒤에 빈 칸은 몇칸인지 계산해서 역시 0넣음. HTML 달력 모양 깨지지 않게  
        int sub = date.getMonth().maxLength() + original;
        
        
        List<Integer> dList =new ArrayList<>(); //  
        if(original-1 >0) {  // dLsit: HTML에 달력 그리기 위한 데이리스트
            for (int i = 0; i < original-1; i++) {
               dList.add(0);
              }
        }
        for (int j = 0;j < date.getMonth().maxLength() +1; j++) {
             dList.add(j);
        }
        if(35-sub >0) {
           for (int i = 0; i <35- sub; i++) {
               dList.add(0);
              }
        }
        
        

        // 스케쥴 리스트
        Set<Integer> daysHaveSchedule = new HashSet<>(); 
        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
            daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
        
        List<List<Schedule>> daysScheduleList = new ArrayList<>();
        List<Schedule> eachOfDaySchedule = new ArrayList<>();
    
        
        for (Integer d : dList) {
            if(daysHaveSchedule.contains(d)){
                 eachOfDaySchedule = scheduleService.findByDayOfMonth(date.getMonthValue(),d);
                 daysScheduleList.add(eachOfDaySchedule);
            }
            else {
                  daysScheduleList.add(null);
             }
         } 
        

        
        // 일기(다이어리) 리스트
        Set<Integer> daysHaveDiary= new HashSet<>(); 
        for (Diary m : diaryService.findByMonth(date.getMonthValue())) {
            daysHaveDiary.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
        
         List<Integer> diaryList = new ArrayList<>();
        for (Integer d : dList) {
            if (daysHaveDiary.contains(d)) {
               diaryList.add(diaryService.findByMD(date.getMonthValue(),d).getDiaryId());
                } else{
                    diaryList.add(0);
                }
         } 
        
        // 디데이 리스트
//        Set<Integer> daysHaveDDay= new HashSet<>(); 
//        for (DDay i: dDayService.findByMonth(date.getMonthValue())) {
//            daysHaveDDay.add(i.getDay());
//         }
//        
//        List<Integer> dDayList = new ArrayList<>();
//        for (Integer d : dList) {
//            if (daysHaveDDay.contains(d)) {
//                   dDayList.add(dDayService.findByD(d).getDDayId());
//                 } else{
//                   dDayList.add(null);
//                 }
//         }   
       
        
        // Today
        List<Integer> to = new ArrayList<>();
        if(LocalDate.now().getMonthValue() == date.getMonthValue()) {
           for (Integer i : dList) {
              if(i == LocalDate.now().getDayOfMonth()) {
                  to.add(1);
              } else {
                  to.add(0);
              }
            }
        }
        
        if(LocalDate.now().getMonthValue() != date.getMonthValue()) {
            for (Integer i : dList) {
                to.add(0);
            }
        }
        
        // dayOfWeek(요일)
        List<String> dayOfWeek = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
            if(i == 0 || i % 7 == 0 ) {
                dayOfWeek.add("SUNDAY");
            } if(i == 1 || i / 7 == 1 ) {
                dayOfWeek.add("MONDAY");
            } if(i == 2 || i / 7 == 2 ) {
                dayOfWeek.add("TUESDAY");
            } if(i == 3 || i / 7 == 3 ) {
                dayOfWeek.add("WEDNESDAY");
            } if(i == 4 || i / 7 == 4 ) {
                dayOfWeek.add("THURSDAY");
            } if(i == 5 || i / 7 == 5 ) {
                dayOfWeek.add("FRIDAY");
            } if(i == 6 || i / 7 == 6 ) {
                dayOfWeek.add("SATURDAY");
            } 
            
        }
      
       
        // 다 합친 하나의 리스트 + 앞뒤로 
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).today(to.get(i)).dayOfWeek(dayOfWeek.get(i)).build());
        }
       
        
        List<DayDiaryDto>  d1 = new ArrayList<>(); // 한 주 단위로 쪼개서
        List<DayDiaryDto>  d2 = new ArrayList<>();
        List<DayDiaryDto>  d3 = new ArrayList<>();
        List<DayDiaryDto>  d4 = new ArrayList<>();
        List<DayDiaryDto>  d5 = new ArrayList<>(); 
        
        List<List<DayDiaryDto>> integratedList = new ArrayList<>();
        for (int i = 0; i < dayDiaryDtoList.size(); i++) {
            if(i < 7) {
                d1.add(dayDiaryDtoList.get(i));
            } 
            integratedList.add(d1);
            
            if( 6 < i && i< 14) {
                d2.add(dayDiaryDtoList.get(i));
            }
            integratedList.add(d2);
            
            if( 13 < i && i< 21) {
                d3.add(dayDiaryDtoList.get(i));
            }
            integratedList.add(d3);
            
            if( 20 < i && i< 28) {
                d4.add(dayDiaryDtoList.get(i));
            }
            integratedList.add(d4);
            
            if( 27 < i && i< 35) {
                d5.add(dayDiaryDtoList.get(i));
            }
            integratedList.add(d5);
        }   
     
        if (dayDiaryDtoList.size() >= 35) {
            List<DayDiaryDto> d6 = new ArrayList<>();
            for (int n = 35; n < 42; n++) {
                if( 34 < n && n< dList.size()) {
                    d6.add(dayDiaryDtoList.get(n));
                }  
                integratedList.add(d1);
            }
       }
       
        return integratedList;
    }
    
    
    
    
    @GetMapping({ "/calendar/front", "/calendar/back" })
    public String frontCalendar(Integer year, Integer monthValue, Model model) {
        
        log.info("백 트론츠 잘 받았나영?={} : {}", year, monthValue);
        List<List<DayDiaryDto>> integratedList = integrateInfo(year, monthValue);
        
        model.addAttribute("d1", integratedList.get(0));
        model.addAttribute("d2", integratedList.get(1));
        model.addAttribute("d3", integratedList.get(2));
        model.addAttribute("d4", integratedList.get(3));
        model.addAttribute("d5", integratedList.get(4));

        if (!integratedList.get(5).isEmpty()) {
            model.addAttribute("d6", integratedList.get(5));
        }

        LocalDate date = LocalDate.of(year, monthValue, 1);
        CalendarDto dto = calendarService.calendarCreate(date);
        model.addAttribute("dto", dto);
        
        return "/calendar/main";
     }
    
    
  
}
    