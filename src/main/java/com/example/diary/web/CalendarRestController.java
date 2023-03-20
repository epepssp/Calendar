package com.example.diary.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.Week;
import com.example.diary.dto.CalendarDto;
import com.example.diary.service.CalendarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalendarRestController {
    
    private final CalendarService calendarService;
    
    @GetMapping("/day/detail/{day}")
    public ResponseEntity<String> dayInfo(@PathVariable int day){
        log.info("클릭한데이={}",day);
        
        return ResponseEntity.ok("0");
    }
    
    
    @GetMapping("/front/calendar/{monthValue}")
    public ResponseEntity<List<Integer>> frontPage(@PathVariable int monthValue){
        log.info("페리징프론트={}",monthValue);
        
        LocalDate date = LocalDate.of(2023, monthValue-1, 1); 
        CalendarDto dto = calendarService.calendarCreate(date);
        
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
      
        if(original-1 >0) {
             for (int i = 0; i < original-1; i++) {
                dList.add(0);
               }
           }
                
       for (int j = 0;j <  length; j++) {
              dList.add(j);
             }
       
         int sub =  length + original -1;
             
         if(35-sub >0) {
            for (int i = 0; i <35- sub; i++) {
                dList.add(0);
                 }
             }
             
            
        return ResponseEntity.ok(dList);
        
    }
    
//  @PostMapping("/request/calendar")
//  public ResponseEntity<List<Integer>> sendCalendarInfo(@RequestBody Integer month){
//      log.info("캘린더리쿼스트 도착={}",month);
//      
//      LocalDate date = LocalDate.of(2023, month, 1);
//     int mLength= date.getMonth().maxLength();
//     String dows= date.getDayOfWeek().toString();
//  
//      int ori =0;
//      Week w = Week.valueOf(dows);
//      for(Week e:Week.values()) {
//          if(e.equals(w)) {
//            ori=  e.ordinal();
//          }
//      }
//      
//      List<Integer> dList = new ArrayList<>();
//      V
//      if(ori-1 >0) {
//          for (int i = 0; i < ori-1; i++) {
//             dList.add(0);
//         }
//      }
//      
//      for (int j = 0;j < mLength; j++) {
//          dList.add(j);
//      }
//         int mori = mLength +ori -1;
//      if(35-mori >0) {
//          
//          for (int i = 0; i <35-mori; i++) {
//              dList.add(0);
//          }
//       }
//      
//      
//      return ResponseEntity.ok(dList);
//  
//  }
    
//    @PostMapping("/request/calendar")
//    public ResponseEntity<List<CalendarDto>> sendCalendarInfo(){
//        
//        LocalDate n = LocalDate.now();
//        String s = n.toString().substring(5, 6);
//        String d = n.toString().substring(8, 9);
//
//        String month= null;
//        String today= null;
//     
//      
//        
//  
//        if(s.equals("0")) {
//             month= n.toString().substring(6,7);
//        } else {
//             month= n.toString().substring(5,7);
//             
//        }
//    
//       int mon = Integer.parseInt(month);
//         LocalDate date = LocalDate.of(2023, mon, 1);
//       DayOfWeek dayOfWeek = null;
//        dayOfWeek = date.getDayOfWeek();
//    //    log.info("욜ㄹㄹㄹ=00000 dayOfWeek{}", dayOfWeek);
//        
//      //  int dow =  Integer.parseInt(dayOfWeek.toString());
//        
//        String dda = dayOfWeek.toString();
//        
//        Week w = Week.valueOf(dda);
//         log.info("디디에이w={}", w);
//           int or= 0;
//        for (Week ww : Week.values() ) {
//            if(ww == w) {
//                   or =  w.ordinal();
//               log.info("욜ㄹㄹㄹ=00000 ororororor{}", or);
//            }
//        }
//        
//        
//    
//         
//        List<Integer> ddd = new ArrayList<>();
//  
//        for (int i = 0; i < or-1; i++) {
//                ddd.add(0);
//        
//        }  log.info("욜ㄹㄹㄹ=00000 까지만{}", ddd);
//      
////        Week w = Week.values().equals(dayOfWeek);
////          log.info("욜ㄹㄹㄹ디날={}", w.ordinal());
////                
//                
//                
//        if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
//          
//            for (int i = 0; i < 31; i++) {
//                ddd.add(i);
//            }
//        }
//        else if (month.equals("2")) {
//          
//            
//            for (int i = 0; i < 29; i++) {
//                ddd.add( i);
//            }
//        }  
//        else  {
//           
//            for (int i = 0; i < 32; i++) {
//                ddd.add(i);
//            }
//        }
//        
//        log.info("dddd출력={}",ddd);
//        
//       List<CalendarDto> daysList = calendarService.read(ddd);
//       log.info("d데이즈리스트!!={}",daysList);
//    
//      
//        if(d.equals("0")) {
//             today= n.toString().substring(9);
//        } else {
//             today= n.toString().substring(8);
//        }
//        return ResponseEntity.ok(daysList);
//    }

}