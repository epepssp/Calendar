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
            
        // dList = HTML에 달력을 그리기 위한 day 리스트  -> 캘린더 그리기 완성
        // dList + 스케쥴 리스트 + 일기 리스트 = List<DayDiaryDto> 타입의 통합 리스트 생성 -> HTML에 그릴 수 있게 넘겨줌
  
    }
    }
```
