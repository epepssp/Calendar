package com.example.diary.web;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.Month;
import com.example.diary.domain.Schedule;
import com.example.diary.domain.Week;
import com.example.diary.dto.CalendarDto;
import com.example.diary.dto.ScheduleAddDto;
import com.example.diary.service.CalendarService;
import com.example.diary.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalendarRestController {
    
    private final CalendarService calendarService;
    private final ScheduleService scheduleService;
    
    
    @GetMapping("/monthSchedule/{monthValue}")
    public ResponseEntity<Integer> nomthSchedule(@PathVariable int monthValue) {
        
      
//        List<Schedule> monthList = new ArrayList<>();
//        monthList = scheduleService.findByMonth(monthValue);
//        
//        Set<Integer> daysHaveSchedule = new HashSet<>(); 
//        
//        for (Schedule m : monthList) {
//            daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
//        }
        
        
        return ResponseEntity.ok(monthValue);
    }
    
    @GetMapping("/day/{day}")
    public ResponseEntity<Integer> getReply(@PathVariable Integer day) {
        log.info("getReply(클릭한 날짜={})", day);
        
      
        return ResponseEntity.ok(day);
    }
    
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
    
    @PostMapping("/add/schedule")
    public ResponseEntity<Integer> add(@RequestBody ScheduleAddDto dto) {
        log.info("새 일정 추가(데이터={})", dto);
        
            
       Integer scheduleId = scheduleService.create(dto);
      
        return ResponseEntity.ok(scheduleId);
    }
    
     @GetMapping("/show/schedule/day/{fullDate}")
     public ResponseEntity<List<Schedule>> showAll(@PathVariable String fullDate){
     log.info("풀데이트(풀={})", fullDate);
     
    List<Schedule> sList = scheduleService.findByDate(fullDate);
    log.info("스케쥴리스트={}", sList); 
    
     return ResponseEntity.ok(sList);
     }
    
//     @GetMapping("/show/schedule/all/{day}")
//     public ResponseEntity<List<Schedule>> showDayAll(@PathVariable int day){
//     log.info("데이별스케쥴(풀={})", day);
//     
//    List<Schedule> list = scheduleService.findByDay(day);
//    log.info("데이스케쥬리스우={}", list); 
//    
//     return ResponseEntity.ok(list);
//     }
//     
//     
//     @GetMapping("/show/schedule/month")
//     public ResponseEntity<List<Schedule>> showAllMonth(int year, int monthValue){
//         log.info("스케쥴먼트(풀={}:{})", year, monthValue);
//          List<Schedule> alls = scheduleService.readAll();
//          List<Schedule> monthS = new ArrayList<>();
//          
//        for (Schedule s : alls) {
//            if(s.getYear() == year && s.getMonthValue() == monthValue) {
//              monthS.add(s);
//            }
//        }
//        
//        List<Integer> days = new ArrayList<>();
//       days.add(monthS.get(0).getDay());
//       
//       for (int i = 1; i < monthS.size(); i++) {
//           if(monthS.get(i).getDay() !=monthS.get(0).getDay()) {
//               days.add(monthS.get(i).getDay());
//           }
//       }
//        
////       for (int i = 1; i < monthS.size(); i++) {
////         List<Integer> ee = days.stream().filter(x -> x == monthS.get(i).getDay()).collect(Collectors.toList());
////           
////         
////    }
//       log.info("DDDAYs리슽(풀={})", days);
//        
//         return ResponseEntity.ok(monthS);
//     }
//  
//     @GetMapping("/front/day")
//   public ResponseEntity<Integer> showFrontDay(int day,String fullDate){
//       log.info("frontttt데이(데이={}:{})", day, fullDate);
//       
//      
//       if(day == 1) {
//           Integer year = Integer.parseInt(fullDate.substring(0, 4));
//           Integer m =Integer.parseInt(fullDate.substring(4, fullDate.length()-1));
//    
//          LocalDate d = LocalDate.of(year, m-1, 1); 
//          day =d.getMonth().maxLength();
//      } else {
//          day = day-1;
//      }
//        
//    log.info("frontttt데이잘 담ㄷ겻나(데이={})", day);
//     return ResponseEntity.ok(day);
//   }
     
     
     


     
@GetMapping("/front/day/{fullDate}")
public ResponseEntity<List<Schedule>> showFrontDay(@PathVariable String fullDate){
    log.info("frontttt데이(데이={})", fullDate);
    List<Schedule> sList = new ArrayList<>();
    
    Integer date =  Integer.parseInt(fullDate.substring(fullDate.length()-1, fullDate.length())) -1;
     
    if(date == 0) { 
        Integer year = Integer.parseInt(fullDate.substring(0, 4));
        Integer m = 0;
        Integer da = 0;
        
        if(fullDate.length() == 6) {
          m= Integer.parseInt(fullDate.substring(4, 5)) -1;
        } else {
          m= Integer.parseInt(fullDate.substring(4, 6)) -1;
        }
            LocalDate d = LocalDate.of(year, m, 1);
            da= d.getMonth().maxLength();
          //  fullDate = year.toString()+m.toString()+da.toString();
            sList = scheduleService.findByDate(year.toString()+m.toString()+da.toString());
    } 
    
    if(date != 0) {
        date = Integer.parseInt(fullDate)-1;
        sList = scheduleService.findByDate(date.toString());
   }
    
    return ResponseEntity.ok(sList);
}
     
//@GetMapping("/back/day/{fullDate}")
//public ResponseEntity<List<Schedule>> showBcakDay(@PathVariable String fullDate){
//    log.info("frontttt데이(데이={})", fullDate);
//    
//    Integer date =  Integer.parseInt(fullDate) +1;
//    
//    List<Schedule> sList = scheduleService.findByDate(date.toString());
//    
//    return ResponseEntity.ok(sList);
//}

@GetMapping("/back/day/{day}")
public ResponseEntity<Integer> showBcakDay(@PathVariable int day){
    log.info("frontttt데이인트(데이={})", day);
    
    Integer date = day+1;
   // Integer date =  Integer.parseInt(fullDate) +1;
    
   // List<Schedule> sList = scheduleService.findByDate(date.toString());
    
    return ResponseEntity.ok(date);
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