package com.example.diary.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.diary.domain.Week;
import com.example.diary.dto.CalendarDto;
import com.example.diary.service.CalendarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CalendarController {
    
    private final CalendarService calendarService;
    

    @GetMapping("/calendar")  // 오늘 날짜 
    public String calendar1(Model model) {
         LocalDate date = null;
     
           //현재 날짜로 출발
            date = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(), 1);
    
           // date = LocalDate.of(2021, 7, 1); 
        
        
        CalendarDto dto =  calendarService.calendarCreate(date); 
        
        int original =0;
        String dayOfWeekS = dto.getDayOfWeek().toString(); // 요일
        
        Week w = Week.valueOf(dayOfWeekS);
        for(Week e: Week.values()) {
            if(e.equals(w)) {
              original=  e.ordinal();
            }
        }
    
           
        List<Integer> dList =new ArrayList<>();
        int length = dto.getLengthOfMonth();  
        log.info("monthLengh먼스렝={}",length);
      
        if(original-1 >0) {
             for (int i = 0; i < original-1; i++) {
                dList.add(0);
               }
           }
                
       for (int j = 0; j <  length+1; j++) {
              dList.add(j);
             }
       
         int sub =  length + original;
             
         if(35-sub >0) {
            for (int i = 0; i <35- sub; i++) {
                dList.add(0);
                 }
             }
             
         log.info("dList 31일={}",dList);

         List<Integer> w1 = new ArrayList<>();
         List<Integer> w2 = new ArrayList<>();
         List<Integer> w3 = new ArrayList<>();
         List<Integer> w4 = new ArrayList<>();
         List<Integer> w5 = new ArrayList<>();
        

         
         for (int i = 0; i < dList.size(); i++) {
             if(i < 7) {
                 w1.add(dList.get(i));
             } 
             if( 6 < i && i< 14) {
                 w2.add(dList.get(i));
             }
             if( 13 < i && i< 21) {
                 w3.add(dList.get(i));
             }
             if( 20 < i && i< 28) {
                 w4.add(dList.get(i));
             }
             if( 27 < i && i< 35) {
                 w5.add(dList.get(i));
             }
         }   
      
         if(dList.size() >= 35) {
             List<Integer> w6 = new ArrayList<>();
             for (int n = 35; n < 42; n++) {
                 if( 34 < n && n< dList.size()) {
                     w6.add(dList.get(n));
                 }
                 if(dList.size()-1< n && n< 42) {
                     w6.add(0);
                 }
          }
             model.addAttribute("w6", w6);
         }
      
         model.addAttribute("w1", w1);
         model.addAttribute("w2", w2);
         model.addAttribute("w3", w3);
         model.addAttribute("w4", w4);
         model.addAttribute("w5", w5);
         model.addAttribute("dto", dto); // CalendarDto
     
         LocalDate now = LocalDate.now();
         int today = now.getDayOfMonth();
         log.info("ㅌㅌㅌ투데이!!={}", today);
        
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
        
       List<Integer> w1 = new ArrayList<>();
       List<Integer> w2 = new ArrayList<>();
       List<Integer> w3 = new ArrayList<>();
       List<Integer> w4 = new ArrayList<>();
       List<Integer> w5 = new ArrayList<>();
      
   
       for (int i = 0; i < dList.size(); i++) {
           if(i < 7) {
               w1.add(dList.get(i));
           } 
           if( 6 < i && i< 14) {
               w2.add(dList.get(i));
           }
           if( 13 < i && i< 21) {
               w3.add(dList.get(i));
           }
           if( 20 < i && i< 28) {
               w4.add(dList.get(i));
           }
           if( 27 < i && i< 35) {
               w5.add(dList.get(i));
           }
       }   
    
       if(dList.size() >= 35) {
           List<Integer> w6 = new ArrayList<>();
           for (int n = 35; n < 42; n++) {
               if( 34 < n && n< dList.size()) {
                   w6.add(dList.get(n));
               }
               if(dList.size()-1< n && n< 42) {
                   w6.add(0);
               }
        }
           model.addAttribute("w6", w6);
       }
       
       model.addAttribute("w1", w1);
       model.addAttribute("w2", w2);
       model.addAttribute("w3", w3);
       model.addAttribute("w4", w4);
       model.addAttribute("w5", w5);
       model.addAttribute("dto", dto);
        
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
            
       List<Integer> w1 = new ArrayList<>();
       List<Integer> w2 = new ArrayList<>();
       List<Integer> w3 = new ArrayList<>();
       List<Integer> w4 = new ArrayList<>();
       List<Integer> w5 = new ArrayList<>();
      
     
//       
//      int n= 0; 
//      if (dList.size()-35 >= 0) {
//           n = 35;  
//           List<Integer> w6 = new ArrayList<>();
//           for (int i = 0; i < dList.size()-36; i++) {
//             w6.add(i);
//        }
//           model.addAttribute("w6", w6);
//           
//      } else {
//          n = dList.size();
//      }
      
   
       for (int i = 0; i <dList.size(); i++) {
  
           if(i < 7) {
               w1.add(dList.get(i));
           } 
           if( 6 < i && i< 14) {
               w2.add(dList.get(i));
           }
           if( 13 < i && i< 21) {
               w3.add(dList.get(i));
           }
           if( 20 < i && i< 28) {
               w4.add(dList.get(i));
           }
           if( 27 < i && i< 35) {
               w5.add(dList.get(i));
           }
       }   
    
       if(dList.size() >= 35) {
           List<Integer> w6 = new ArrayList<>();
           for (int n = 35; n < 42; n++) {
               if( 34 < n && n< dList.size()) {
                   w6.add(dList.get(n));
               }
               if(dList.size()-1< n && n< 42) {
                   w6.add(0);
               }
        }
           model.addAttribute("w6", w6);
       }
    
       model.addAttribute("w1", w1);
       model.addAttribute("w2", w2);
       model.addAttribute("w3", w3);
       model.addAttribute("w4", w4);
       model.addAttribute("w5", w5);
       model.addAttribute("dto", dto);
        
       return "/calendar/main";
     }


  

    }
    