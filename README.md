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

## 주요 구현 기능



## 구현 기능
### 1. Calendar
+ #### API 사용하지 않고 직접 Calendar 구현하기
    > CalendarController.java 일부
    ```java

        LocalDate date = LocalDate.of(year,monthValue, 1);  // 달력을 그릴 연도, 달
        
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
    ```

+ #### Calendar 이동
  + ##### Front, Back 버튼
  ```html

   <span th:if="${ dto.monthValue != 1}"><!-- 달력 프론트 이동 버튼 -->
     <button class="w3-button mb-3" th:onclick="|location.href='@{ /calendar/front?monthValue={monthValue}&year={year}
           (monthValue =${ dto.monthValue -1 }, year = ${ dto.year})}'|" >
       <i class="material-icons" style="font-size:30px; color: gray;">chevron_left</i>
     </button>
   </span>
   <span th:if="${ dto.monthValue == 1}">
     <button class="w3-button mb-3" th:onclick="|location.href='@{ /calendar/front?monthValue={monthValue}&year={year}
           (monthValue =${ dto.monthValue +11 }, year = ${ dto.year -1})}'|" >
        <i class="material-icons" style="font-size:30px; color: gray;">chevron_left</i>
     </button>
   </span>

   <span th:if="${ dto.monthValue != 12}"><!-- 달력 백 이동 버튼 -->
      <button class="w3-button mb-3" th:onclick="|location.href='@{ /calendar/back?monthValue={monthValue}&year={year}
            (monthValue =${ dto.monthValue +1 }, year = ${ dto.year})}'|">
        <i class="material-icons" style="font-size:30px; color: gray;">chevron_right</i>
      </button>
   </span>
   <span th:if="${ dto.monthValue == 12}">
      <button class="w3-button mb-3" th:onclick="|location.href='@{ /calendar/back?monthValue={monthValue}&year={year}
            (monthValue =${ dto.monthValue -11 }, year = ${ dto.year +1})}'|">
        <i class="material-icons" style="font-size:30px; color: gray;">chevron_right</i>
      </button>
   </span>
  
  ```

  + ##### 원하는 날짜 선택
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/ed2ede74-521d-4625-a041-ce75ad407f35" width="600" height="440" alt="날짜선택이동"></div>


+ #### Today
  + ##### Color, Blink Effect
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/fc6646a9-ee1e-4f7e-9466-6d975ac72c65" width="580" height="400" alt="투데이"></div>



+ #### Day 모달
  + ##### Calendar에서 특정 날짜를 클릭하면 모달창에 해당 날짜의 정보들을 볼 수 있다.
  + ##### 스케츌 추가/디데이 설정/일기 작성
  + ##### Day 이동: Front, Back 버튼

