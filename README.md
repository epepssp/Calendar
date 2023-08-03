# Calendar

## 개요
> #### 수료 후 틈틈히 개발&구현한 개인 프로젝트
> #### 개발기간: 2023년 3월 8일 ~ 2023년 8월 4일<br>

## 프로젝트 소개
> #### Calendar를 기본 바탕으로 한 개인 다이어리
> #### 일정 관리, D-DAY 설정, 일기 작성 
> #### API 사용하지 않고 수작업으로(창의적인 나만의 방법으로) Calendar 구현
> #### 

## 사용 기술 및 개발환경
+ Java
+ Spring Boot
+ HTML
+ CSS
+ JavaScript

## 주요기능 소개
+ Calendar 보기
+ 일정 추가
+ D-DAY 설정
+ 일기장

## 구현 기능
### 1. Calendar
+ #### Calendar 생성

  > CalendarController.java 일부
  ```java
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
    
    
```
