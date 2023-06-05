package com.example.diary.web;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.plaf.metal.MetalIconFactory.TreeLeafIcon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.DDay;
import com.example.diary.domain.Diary;
import com.example.diary.domain.Lists;
import com.example.diary.domain.Month;
import com.example.diary.domain.Schedule;
import com.example.diary.domain.Week;
import com.example.diary.dto.CalendarDto;
import com.example.diary.dto.DayDiaryDto;
import com.example.diary.dto.DayDto;
import com.example.diary.dto.MiniDiaryDto;
import com.example.diary.dto.ScheduleAddDto;
import com.example.diary.dto.YearAndMonthDto;
import com.example.diary.repository.DiaryAttachmentRepository;
import com.example.diary.service.CalendarService;
import com.example.diary.service.DDayService;
import com.example.diary.service.DiaryService;
import com.example.diary.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CalendarRestController {
    
    private final CalendarService calendarService;
    private final ScheduleService scheduleService;
    private final DiaryService diaryService;
    private final DDayService dDayService;
    private final DiaryAttachmentRepository diaryAttachmentRepository;
    
    @PostMapping("/calendar/miniList")
    public ResponseEntity<List<MiniDiaryDto>> miniListVersion(@RequestBody YearAndMonthDto dto){
        log.info("카ㅔ렌더 미니미니리스트령식??={} :{}", dto.getYear(), dto.getMonthValue());
        
        List<Diary> dd = new ArrayList<>();
        for (Diary d: diaryService.findByMonth(dto.getMonthValue())) {
            if(d.getYear() == dto.getYear()) {
                dd.add(d);
            }
        }
        
        List<MiniDiaryDto> miniList = new ArrayList<>();

        for (Diary s : dd) {
            MiniDiaryDto miniDto = MiniDiaryDto
                                     .builder()
                                     .diaryId(s.getDiaryId())
                                     .year(s.getYear())
                                     .monthValue(s.getMonthValue())
                                     .day(s.getDay())
                                     .weather(s.getWeather())
                                     .title(s.getTitle())
                                     .uuid(diaryAttachmentRepository.findByDiaryDiaryId(s.getDiaryId()).get(0).getUuid())
                                     .fileName(diaryAttachmentRepository.findByDiaryDiaryId(s.getDiaryId()).get(0).getFileName())
                                     .build();
            miniList.add(miniDto);
        }
       
        
        return ResponseEntity.ok(miniList);
    }
    
    @PostMapping("/calendar/mini")
    public ResponseEntity<Lists> miniVersion(@RequestBody YearAndMonthDto dto){
        log.info("카ㅔ렌더 미니미니={} :{}", dto.getYear(), dto.getMonthValue());
        
        List<List<DayDiaryDto>> integratedList = integrateInfo(dto.getYear(), dto.getMonthValue());
        
       // log.info("미니 통합 카렌더 리스트?={}", integratedList.get(3));
        List<DayDiaryDto>  d1 = integratedList.get(0); 
        List<DayDiaryDto>  d2 = integratedList.get(1);
        List<DayDiaryDto>  d3 = integratedList.get(2);
        List<DayDiaryDto>  d4 = integratedList.get(3);
        List<DayDiaryDto>  d5 = integratedList.get(4);
        List<DayDiaryDto>  d6 = null;
        
        if(!integratedList.get(5).isEmpty()) {
            d6 = integratedList.get(5);
        }
         
        Lists lists = new Lists(d1, d2, d3,d4, d5, d6);
        
       // log.info("리스폰스ㅔㅇㄴ터팉통합?={}", lists.toString());
        
     
        
        return ResponseEntity.ok(lists);
    }
    
    
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
    
  
    
    @GetMapping("/day/detail/{day}")
    public ResponseEntity<String> dayInfo(@PathVariable int day){
        log.info("클릭한데이={}",day);
        
        return ResponseEntity.ok("0");
    }
    

    
    @PostMapping("/add/schedule")
    public ResponseEntity<Integer> add(@RequestBody ScheduleAddDto dto) {
        log.info("새 일정 추가(데이터={})", dto);
        
            
       Integer scheduleId = scheduleService.create(dto);
      
        return ResponseEntity.ok(scheduleId);
    }
  
     
     
     @PostMapping("/scheduleDay")
     public ResponseEntity<List<Schedule>> scheduleDay(@RequestBody DayDto dto) {
         log.info("dto??????????????????(데이터={}:{})", dto.getMonthValue(), dto.getDay());
         
         List<Schedule> sList= new ArrayList<>();
         for (Schedule s : scheduleService.findByMonth(dto.getMonthValue())) {
             if(s.getDay() == dto.getDay()) {
               sList.add(s);
             }
        }
       
         return ResponseEntity.ok(sList);
     }
     
     @GetMapping("/lastDay/{monthValue}")
     public ResponseEntity<Integer> lastDay(@PathVariable int monthValue){
         log.info("몇월의 라스트대이={}",monthValue);
         
         LocalDate date = LocalDate.of(2023, monthValue, 1);
       
         return ResponseEntity.ok(date.getMonth().maxLength());
     }

 
@GetMapping("/front/day/{monthValue}")
public ResponseEntity<Integer> showFrontDay(@PathVariable int monthValue){
    log.info("frontttt데이(데이={})", monthValue);
   
    
    LocalDate date = LocalDate.of(2023, monthValue, 1);
    int lastDay = date.getMonth().maxLength();
   

     
//    List<Schedule> sList = new ArrayList<>();
//    
//    Integer date =  Integer.parseInt(fullDate.substring(fullDate.length()-1, fullDate.length())) -1;
//     
//    if(date == 0) { 
//        Integer year = Integer.parseInt(fullDate.substring(0, 4));
//        Integer m = 0;
//        Integer da = 0;
//        
//        if(fullDate.length() == 6) {
//          m= Integer.parseInt(fullDate.substring(4, 5)) -1;
//        } else {
//          m= Integer.parseInt(fullDate.substring(4, 6)) -1;
//        }
//            LocalDate d = LocalDate.of(year, m, 1);
//            da= d.getMonth().maxLength();
//          //  fullDate = year.toString()+m.toString()+da.toString();
//            sList = scheduleService.findByDate(year.toString()+m.toString()+da.toString());
//    } 
//    
//    if(date != 0) {
//        date = Integer.parseInt(fullDate)-1;
//        sList = scheduleService.findByDate(date.toString());
//   }
//    
    return ResponseEntity.ok(lastDay);
}

//@GetMapping("/change/backgrondCalendar/{monthValue}")
//public ResponseEntity<Lists> changeCalendar(@PathVariable int monthValue){
//    log.info("백 카렌다 체인지 몬스?(먼스={})", monthValue);
//    
//    List<List<DayDiaryDto>> integratedList = integrateInfo(2023, monthValue);
//    
//   Lists lists = null;
//    if(integratedList.get(5).isEmpty()) {
//      lists = new Lists(integratedList.get(0),integratedList.get(1),integratedList.get(2),integratedList.get(3),integratedList.get(4),null);
//        log.info("백 카렌다 체인지 리스트스1111?(리스트??={})", lists);
//        
//    }
//    if(!integratedList.get(5).isEmpty()) {
//  lists = new Lists(integratedList.get(0),integratedList.get(1),integratedList.get(2),integratedList.get(3),integratedList.get(4),integratedList.get(5));
//        log.info("백 카렌다 체인지 리스트스2222?(리스트??={})", lists);
//    }
//   
//    return ResponseEntity.ok(lists);
//   }

     
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
public ResponseEntity<Integer> showBackDay(@PathVariable int day){
    log.info("frontttt데이인트(데이={})", day);
    
    Integer date = day+1;
   // Integer date =  Integer.parseInt(fullDate) +1;
    
   // List<Schedule> sList = scheduleService.findByDate(date.toString());
    
    return ResponseEntity.ok(date);
}

@GetMapping("/back/day/month/{monthValue}")
public ResponseEntity<Integer> showBackDayM(@PathVariable int monthValue){
    log.info("f?????????????????/={})", monthValue);
    
    Integer m = monthValue +1;
   // Integer date =  Integer.parseInt(fullDate) +1;
    
   // List<Schedule> sList = scheduleService.findByDate(date.toString());
    
    return ResponseEntity.ok(m);
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
    // dLsit: HTML에 달력 그리기 위한 데이리스트
    if(original != 0) {  
        for (int i = 0; i < original-1; i++) {
           dList.add(0);
          } 
        for (int j = 0;j < date.getMonth().maxLength() +1; j++) {
         dList.add(j);
      }
    }
    
    if(original == 0) {
        for (int j = 1;j < date.getMonth().maxLength() +1; j++) {
            dList.add(j);
         }
    }
         
    
    if(35 >= sub) { // 5주까지
       for (int i = 0; i <35- sub; i++) {
           dList.add(0);
          }
    }
    
    if(35 < sub) { // 6주까지 있는 경우
        for (int i = 0; i <42- sub; i++) {
            dList.add(0);
           }
     }
    
    

//    // 스케쥴 리스트
//    Set<Integer> daysHaveSchedule = new HashSet<>(); 
//    for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
//        daysHaveSchedule.add(m.getDay()); // 스케쥴이 있는 날짜들
//    }
//    
//    List<List<Schedule>> daysScheduleList = new ArrayList<>();
//    List<Schedule> eachOfDaySchedule = new ArrayList<>();
//    
//    for (Integer d : dList) {
//        if(daysHaveSchedule.contains(d)){
//             eachOfDaySchedule = scheduleService.findByDay(d);
//           for (int i = 0; i < eachOfDaySchedule.size(); i++) {
//               if(eachOfDaySchedule.get(i).getMonthValue() != date.getMonthValue()) {
//                   eachOfDaySchedule.remove(i);
//               }
//           }
//              daysScheduleList.add(eachOfDaySchedule);
//        } else {
//              daysScheduleList.add(null);
//         }
//     } 
    
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
    
//    // 디데이 리스트
//    Set<Integer> daysHaveDDay= new HashSet<>(); 
//    for (DDay i: dDayService.findByMonth(date.getMonthValue())) {
//        daysHaveDDay.add(i.getDay());
//     }
//    
//    List<Integer> dDayList = new ArrayList<>();
//    for (Integer d : dList) {
//        if (daysHaveDDay.contains(d)) {
//               dDayList.add(dDayService.findByD(d).getDDayId());
//             } else{
//               dDayList.add(null);
//             }
//     }   
//   
   
    // 다 합친 하나의 리스트 + 앞뒤로 
    List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
    for (int i = 0; i < dList.size(); i++) {
       dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i)).build());
    }
   
    
    List<DayDiaryDto>  d1 = new ArrayList<>(); // 한 주 단위로 쪼개서
    List<DayDiaryDto>  d2 = new ArrayList<>();
    List<DayDiaryDto>  d3 = new ArrayList<>();
    List<DayDiaryDto>  d4 = new ArrayList<>();
    List<DayDiaryDto>  d5 = new ArrayList<>(); 
    List<DayDiaryDto> d6 = new ArrayList<>();
    
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
        
        if(34 < i && i< 42) {
            d6.add(dayDiaryDtoList.get(i));
        }
        integratedList.add(d6);
        
    }   
 
   
    return integratedList;
}

}
