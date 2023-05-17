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

    @GetMapping("/calendar")  // 오늘 날짜 
    public String calendar1(Model model) {
         LocalDate date = null;
     
        //현재 날짜로 출발
        date = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(), 1);
                 // date = LocalDate.of(2021, 7, 1); 
        
        CalendarDto dto =  calendarService.calendarCreate(date);
        List<Integer> dList = dto.fromEntity(dto);  // day 리스트
        
    
        List<DDay> ooList = new ArrayList<>();
        List<Integer> dDayList = new ArrayList<>();
        
       for (DDay dd: dDayService.findByMonth(date.getMonthValue())) { 
               ooList.add(dd);
       }      // d-day 리스트
       
       
       Set<Integer> daysHaveDDay= new HashSet<>(); 
       for (DDay i: ooList) {
           daysHaveDDay.add(i.getDay());
        }
       
       for (Integer d : dList) {
           if (daysHaveDDay.contains(d)) {
                  dDayList.add(dDayService.findByD(d).getDDayId());
                } else{
                  dDayList.add(null);
                }
        }   
            
        log.info("디데이리스트={}마지막",dDayList);        
       
        

        //2 일기 Icon 시작
        List<Integer> diaryList = new ArrayList<>();
        List<Diary> ll = diaryService.findByMonth(date.getMonthValue());
        
        Set<Integer> daysHaveDiary= new HashSet<>(); 
        for (Diary m : diaryService.findByMonth(date.getMonthValue())) {
            daysHaveDiary.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
  
        for (Integer d : dList) {
            if (daysHaveDiary.contains(d)) {
               diaryList.add(diaryService.findByD(d).getDiaryId());
                } else{
                    diaryList.add(0);
                }
            }  // 다이어리 리스트
       
        
        // 스켑쥴
    
        Set<Integer> daysHaveSchedule = new HashSet<>(); 
        
        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
            daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
        
        List<List<Schedule>> daysScheduleList = new ArrayList<>();
        List<Schedule> eachOfDaySchedule = new ArrayList<>();
        

        for (Integer d : dList) {
            if(daysHaveSchedule.contains(d)){
                 eachOfDaySchedule = scheduleService.findByDay(d);
               for (int i = 0; i < eachOfDaySchedule.size(); i++) {
                   if(eachOfDaySchedule.get(i).getMonthValue() != date.getMonthValue()) {
                       eachOfDaySchedule.remove(i);
                   }
               }
                  daysScheduleList.add(eachOfDaySchedule);
            } else {
                  daysScheduleList.add(null);
             }
         }  // 스케쥴 리스트
       
        
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
       
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).dDayId(dDayList.get(i)).build());
        }
        // log.info("합쳐졌나!!!!={}",dayDiaryDtoList);  // 합친 리스트
   
        List<DayDiaryDto>  d1 = new ArrayList<>(); // 한 주 단위로 쪼개서 넘김
        List<DayDiaryDto>  d2 = new ArrayList<>();
        List<DayDiaryDto>  d3 = new ArrayList<>();
        List<DayDiaryDto>  d4 = new ArrayList<>();
        List<DayDiaryDto>  d5 = new ArrayList<>();    

    
         for (int i = 0; i < dayDiaryDtoList.size(); i++) {
             if(i < 7) {
                 d1.add(dayDiaryDtoList.get(i));
             } 
             if( 6 < i && i< 14) {
                 d2.add(dayDiaryDtoList.get(i));
             }
             if( 13 < i && i< 21) {
                 d3.add(dayDiaryDtoList.get(i));
             }
             if( 20 < i && i< 28) {
                 d4.add(dayDiaryDtoList.get(i));
             }
             if( 27 < i && i< 35) {
                 d5.add(dayDiaryDtoList.get(i));
             }
         }   
      
         if(dayDiaryDtoList.size() >= 35) {
             List<DayDiaryDto> d6 = new ArrayList<>();
             for (int n = 35; n < 42; n++) {
                 if( 34 < n && n< dList.size()) {
                     d6.add(dayDiaryDtoList.get(n));
                 }
                 if(dList.size()-1< n && n< 42) {
                     d6.add(null);
                 }
          }
             model.addAttribute("d6", d6);
         }

         model.addAttribute("d1", d1);
         model.addAttribute("d2", d2);
         model.addAttribute("d3", d3);
         model.addAttribute("d4", d4);
         model.addAttribute("d5", d5);
         
         
         model.addAttribute("dto", dto); // CalendarDto
     
         LocalDate now = LocalDate.now();
         int today = now.getDayOfMonth();
       
        
        return "/calendar/main";

    }
    
    @GetMapping("/calendar/front")
    public String frontCalendar(Integer monthValue, Integer year, Model model) {
        LocalDate date = null;
        CalendarDto dto =null;
           
        if(monthValue == 1) {
            date = LocalDate.of(year-1, 12, 1);
        } else {
            date = LocalDate.of(year, monthValue-1, 1);
        }
        
        dto = calendarService.calendarCreate(date);
        List<Integer> dList = dto.fromEntity(dto);
            
//        log.info("dList 31일={}",dList);
//        log.info("dList 31일={}",dList.size());
        
        List<Integer> diaryList = new ArrayList<>();
        List<Diary> ll = diaryService.findByMonth(date.getMonthValue());
        
        Set<Integer> daysHaveDiary= new HashSet<>(); 
        for (Diary m : diaryService.findByMonth(date.getMonthValue())) {
            daysHaveDiary.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
  
        for (Integer d : dList) {
            if (daysHaveDiary.contains(d)) {
               diaryList.add(diaryService.findByD(d).getDiaryId());
                } else{
                    diaryList.add(0);
                }
            }
        
        

        Set<Integer> daysHaveSchedule = new HashSet<>(); 
        
        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
            daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
        
        List<List<Schedule>> daysScheduleList = new ArrayList<>();
        List<Schedule> eachOfDaySchedule = new ArrayList<>();
        

        for (Integer d : dList) {
            if(daysHaveSchedule.contains(d)){
                 eachOfDaySchedule = scheduleService.findByDay(d);
               for (int i = 0; i < eachOfDaySchedule.size(); i++) {
                   if(eachOfDaySchedule.get(i).getMonthValue() != date.getMonthValue()) {
                       eachOfDaySchedule.remove(i);
                   }
               }
                  daysScheduleList.add(eachOfDaySchedule);
            } else {
                  daysScheduleList.add(null);
             }
         }
       
        
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
       
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i)).sList(daysScheduleList.get(i)).build());
        }
        log.info("합쳐졌나!!!!={}",dayDiaryDtoList);  // 합쳐짐!!!!
        
   
        
        List<DayDiaryDto>  d1 = new ArrayList<>();
        List<DayDiaryDto>  d2 = new ArrayList<>();
        List<DayDiaryDto>  d3 = new ArrayList<>();
        List<DayDiaryDto>  d4 = new ArrayList<>();
        List<DayDiaryDto>  d5 = new ArrayList<>();    

//끝      
         for (int i = 0; i < dayDiaryDtoList.size(); i++) {
             if(i < 7) {
                 d1.add(dayDiaryDtoList.get(i));
             } 
             if( 6 < i && i< 14) {
                 d2.add(dayDiaryDtoList.get(i));
             }
             if( 13 < i && i< 21) {
                 d3.add(dayDiaryDtoList.get(i));
             }
             if( 20 < i && i< 28) {
                 d4.add(dayDiaryDtoList.get(i));
             }
             if( 27 < i && i< 35) {
                 d5.add(dayDiaryDtoList.get(i));
             }
         }   
      
         if(dayDiaryDtoList.size() >= 35) {
             List<DayDiaryDto> d6 = new ArrayList<>();
             for (int n = 35; n < 42; n++) {
                 if( 34 < n && n< dList.size()) {
                     d6.add(dayDiaryDtoList.get(n));
                 }
                 if(dList.size()-1< n && n< 42) {
                     d6.add(null);
                 }
          }
             model.addAttribute("d6", d6);
         }

         model.addAttribute("d1", d1);
         model.addAttribute("d2", d2);
         model.addAttribute("d3", d3);
         model.addAttribute("d4", d4);
         model.addAttribute("d5", d5);
         
       model.addAttribute("dto", dto); // CalendarDto
        
       return "/calendar/main";
     }


    
    @GetMapping("/calendar/back")
    public String backCalendar(Integer monthValue, Integer year, Model model) {
        LocalDate date = null;
        CalendarDto dto =null;
           
        if(monthValue == 12) {
            date = LocalDate.of(year+1, 1, 1);
        } else {
            date = LocalDate.of(year, monthValue+1, 1);
        }
        
        dto = calendarService.calendarCreate(date);
        List<Integer> dList = dto.fromEntity(dto);
        
        
        
        
        List<Integer> diaryList = new ArrayList<>();
        List<Diary> ll = diaryService.findByMonth(date.getMonthValue());
        
        Set<Integer> daysHaveDiary= new HashSet<>(); 
        for (Diary m : diaryService.findByMonth(date.getMonthValue())) {
            daysHaveDiary.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
  
        for (Integer d : dList) {
            if (daysHaveDiary.contains(d)) {
               diaryList.add(diaryService.findByD(d).getDiaryId());
                } else{
                    diaryList.add(0);
                }
            }
        
        

        Set<Integer> daysHaveSchedule = new HashSet<>(); 
        
        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
            daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
        }
        
        List<List<Schedule>> daysScheduleList = new ArrayList<>();
        List<Schedule> eachOfDaySchedule = new ArrayList<>();
        

        for (Integer d : dList) {
            if(daysHaveSchedule.contains(d)){
                 eachOfDaySchedule = scheduleService.findByDay(d);
               for (int i = 0; i < eachOfDaySchedule.size(); i++) {
                   if(eachOfDaySchedule.get(i).getMonthValue() != date.getMonthValue()) {
                       eachOfDaySchedule.remove(i);
                   }
               }
                  daysScheduleList.add(eachOfDaySchedule);
            } else {
                  daysScheduleList.add(null);
             }
         }
       
        
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
       
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i)).sList(daysScheduleList.get(i)).build());
        }
        log.info("합쳐졌나!!!!={}",dayDiaryDtoList);  // 합쳐짐!!!!
        
   
        
        List<DayDiaryDto>  d1 = new ArrayList<>();
        List<DayDiaryDto>  d2 = new ArrayList<>();
        List<DayDiaryDto>  d3 = new ArrayList<>();
        List<DayDiaryDto>  d4 = new ArrayList<>();
        List<DayDiaryDto>  d5 = new ArrayList<>();    

//끝      
         for (int i = 0; i < dayDiaryDtoList.size(); i++) {
             if(i < 7) {
                 d1.add(dayDiaryDtoList.get(i));
             } 
             if( 6 < i && i< 14) {
                 d2.add(dayDiaryDtoList.get(i));
             }
             if( 13 < i && i< 21) {
                 d3.add(dayDiaryDtoList.get(i));
             }
             if( 20 < i && i< 28) {
                 d4.add(dayDiaryDtoList.get(i));
             }
             if( 27 < i && i< 35) {
                 d5.add(dayDiaryDtoList.get(i));
             }
         }   
      
         if(dayDiaryDtoList.size() >= 35) {
             List<DayDiaryDto> d6 = new ArrayList<>();
             for (int n = 35; n < 42; n++) {
                 if( 34 < n && n< dList.size()) {
                     d6.add(dayDiaryDtoList.get(n));
                 }
                 if(dList.size()-1< n && n< 42) {
                     d6.add(null);
                 }
          }
             model.addAttribute("d6", d6);
         }

         model.addAttribute("d1", d1);
         model.addAttribute("d2", d2);
         model.addAttribute("d3", d3);
         model.addAttribute("d4", d4);
         model.addAttribute("d5", d5);
         
       model.addAttribute("dto", dto); // CalendarDto
        
       return "/calendar/main";
     }


  

    }
    