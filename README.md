# 🗓 tomydays
<br>

<div align="center"><img src="https://github.com/epepssp/tomydays/assets/118948099/96fddcfe-a834-454f-8e87-9936b5266f90" width="100%" alt="tomydays"></div>
<br>

## 개요
❣ 수료 후 틈틈히 구현한 개인 프로젝트<br>
❣ 2023.3.8 ~ 2023.6.15<br>
<br>

## 프로젝트 소개
✔ 일상에 가까운 것을 개발해 보자는 취지로 선택한 캘린더 기반 개인용 다이어리 프로그램<br>
✔ 달력 구현, 일정 추가, D-DAY 설정, 일기

<br>

## 사용 기술 및 개발환경
- Java </br>
- SQL </br>
- Spring Boot </br>
- HTML/CSS/Java Script
<br>  

## 목차
### 💎 통합 리스트 생성
##### 0. 사용자로부터 [연, 월을 입력받는다.](#t0)
##### 1. [해당 월의 시작 요일](#t1)을 찾는다.
##### 2. Week enum을 정의하고 입력받은 요일 문자열과 일치하는 인덱스를 반환받는 방식으로 [요일 문자열을 숫자로 변환](#t2)한다.
##### 3. 해당 월의 시작 요일과 마지막 요일을 고려해, 달력의 앞뒤 공백 칸에는 0을, 날짜가 표시될 칸에는 해당 날짜를 넣은 [달력용 Day List(= dList)를 생성](#t3)한다.
##### 4. 통합리스트에 필요한 데이터들을 묶어 [DayDiaryDto를 정의](#t4)한다.
##### 5. 한 날짜에 여러 개의 일정이 있을 수 있으므로, 리스트 안에 리스트로 [Schedule List를 생성](#t5)한다.
##### 6. 작성된 일기가 있다면 diaryId를, 없다면 0을 넣어 [Diary List를 생성](#t6)한다. 
##### 7. 오늘 날짜와 일치하는 날짜에는 1을, 일치하지 않는 날짜에는 0을 넣어 [Today List를 생성](#t7)한다. 
##### 8. [DayDiaryDto 타입 리스트(= 통합 리스트)를 생성](#t8)한다. 
##### 9. HTML에서 달력 날짜가 일주일 단위로 채워지므로, [통합 리스트를 7일 단위로 쪼개어 넘긴다.](#t9)
<br>

### 💎 달력 뷰 구현
##### 10. 달력 날짜가 채워지는 순서? [1주일 단위로 반복문 작성한다.](#v1)
##### 11. [Day Modal](#modal): ◽ [날짜를 클릭하면 뜨는 모달](#m1)<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [일정 추가 / 일기 작성 / D-day 설정 기능](#m2)<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [Front/Back 버튼 클릭하여 날짜 이동](#m3)
##### 12. [Diary Icon](#icon): 작성된 일기가 있는 날짜에 표시되며, Diary Icon을 클릭하면 해당 일기의 detail page로 이동한다.
##### 13. [Today](#v2): Font Color & Blink Effect
##### 14. [Day's Schedule List](#v3): Mouse-hover Effect
##### 15. 달력 이동:  &nbsp;&nbsp;◽ [Front/Back 버튼 클릭하여 이동](#btn)<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [date Input창에서 원하는 날짜 선택하여 이동](#input)
##### 16. Notice Board: 오늘 날짜 기준 D-day 리스트를 보여주는 영역
<br>

### 💎 일정 (Schedule)
##### 17. 일정 추가: [Day Modal](#modal) 사이드바 "일정 추가" 클릭 > [모달 하단에 입력창(Input창) 생성됨](#show) > 내용 입력 후, 버튼 클릭 또는 [엔터키로 입력](#show)
<br>

### 💎 일기 (Diary)
##### 18. 일기 작성: &nbsp;◽ [Day Modal](#modal) 사이드바 "내 일기장" 클릭 > onclick="dateInfo()" 호출 > create.html로 이동<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [미니 캘린더](#mini)에서 작성된 일기가 없는 날짜(흰색) 클릭 > onclick="createDiary(this.getAttribute(day))" 호출 > create.html로 이동
##### 19. 작성 완료: &nbsp;◽ 해당 날짜에 [Diary Icon](#icon) 추가 됨 > [Diary Icon](#icon) 클릭 시, 작성된 일기로 이동<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [미니 캘린더](#mini)에 보라색으로 표시 됨 > 보라색 날짜 클릭 시, 작성된 일기로 이동
##### 20. 정렬 (Sort): &nbsp;◽ [미니 캘린더](#mini) 형식<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [리스트](#list) 형식
##### 21. [오늘의 날씨 (Weather)](#weather)
##### 22. [이미지 첨부](#image)
##### 23. [이모지 (Emoji)](#emoji) 
<br>

### 💎 디데이 (D-day)
##### 24. 디데이 설정:&nbsp; ◽ [Day Modal](#modal) 사이드바 "D-DAY ♥" 클릭 > D-day Modal 뜸<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ Notice Board 하단 "D-Day추가" 버튼 클릭 > D-day Modal 뜸 
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; > Modal에서 원하는 날짜 선택 > 오늘 날짜 기준 D-day 값 계산하여 보여줌 > D-day 설정하거나 or 다른 날짜 재선택 가능
##### 25. Notice Board: 오늘 날짜 기준D-day 리스트 보여줌


<br><br>

## 구현 기능 소개
### <div id="num1">💡 통합 리스트 생성</div>

##### <div id="t0">0. 사용자로부터 연, 월을 입력받는다.</div>
###### &nbsp;◽&nbsp; 디폴트는 현재 날짜 기준이며, 사용자가 선택한(입력한) 특정 날짜가 있다면 해당 날짜로 통합 리스트 생성을 요청한다.
<br>
  
##### <div id="t1">1. 해당 월의 시작 요일을 찾는다.</div>
###### &nbsp;◽&nbsp; 해당 월의 첫 번째 날이 무슨 요일인지에 따라 달력이 시작되는 위치가 달라지기 때문이다. 

```java
    LocalDate date = LocalDate.of(year,monthValue, 1);   // 입력 받은 year, monthValue의 첫 번째 날을 생성한다
    String dayOfWeekS = date.getDayOfWeek().toString();  // 해당 월의 시작 요일을 찾는다
```
<br>

##### <div id="t2">2. Week enum을 정의하고 입력받은 요일 문자열과 일치하는 인덱스를 반환받는 방식으로 요일 문자열을 숫자로 변환한다.</div>
###### &nbsp;◽&nbsp; 요일 문자열을 숫자로 변환하면 요일을 문자열로 입력받아도 직관적으로 날짜를 배치할 수 있게 된다.
###### &nbsp;◽&nbsp; 변환된 인덱스 값 만큼 달력 첫 줄에 빈 셀을 추가하고 날짜를 삽입한다.

```java

    int index = 0;
    Week w = Week.valueOf(dayOfWeekS);  // 요일 문자열을 Week enum의 열거형으로 변환

    for(Week e: Week.values()) {       
        if(e.equals(w)) {          
           index = e.ordinal();        // 요일 문자열과 일치하는 인덱스 값을 받아온다
        }                            
     }

```
<br>

##### <div id="t3">3. 해당 월의 시작과 마지막 요일을 반영해, 빈 셀에는 0, 날짜 셀에는 해당 날짜를 넣은 달력용 Day List(= dList)를 생성한다.</div>
###### &nbsp;◽&nbsp; dList는 통합 리스트의 뼈대가 된다.
```java

     // dLsit: HTML에 달력 그리기 위한 day리스트

     // dList의 총 길이는 7의 배수여야 한다
     // 달력 마지막 줄은 ((index + date.getMonth().maxLength())를 7로 나눈 나머지(remainder) 만큼 날짜 차 있을 것
     int remainder = (index + date.getMonth().maxLength()) % 7 ;

     // 7에서 날짜가 차 있는 칸을 빼면 나머지가 빈 셀이겠지
     int back = 7 - remainder;

     // dList.length() = index + date.getMonth().maxLength() + back;
     // index: 달력 첫 줄에 빈 셀이 몇 개인지
     // back: 달력 마지막 줄에 빈 셀이 몇 개인지

 
     List<Integer> dList =new ArrayList<>();
   
        if(front != 0) { 
            for (int i = 0; i < index; i++) {
                 dList.add(0);  // 달력 첫 줄의 빈 셀에 0 넣는다. 
              } 
        }
        
       for (int j = 1;j < date.getMonth().maxLength() +1; j++) {
              dList.add(j);    // 날짜에는 날짜 넣고
        }
    
        if(remainder != 0) { 
           for (int i = 0; i < back; i++) {
                dList.add(0);  // 달력 마지막 줄의 빈 셀에도 0 넣는다. 
              }
        }

        // dList 완성!

```
<br>

##### <div id="t4">4. 통합 리스트에 포함시킬 데이터 4개를 묶어 DayDiaryDto를 정의한다.</div>

```java
    public class DayDiaryDto {
        private int day;             
        private Integer diaryId;      
        private List<Schedule> sList;  // 리스트안의 리스트 형식  
        private int today;             // 4 today면 = 1, 아니면 = 0 넣은 리스트
   }
```
<br>

##### <div id="t5">5. 한 날짜에 여러 개의 일정이 등록돼 있을 수 있으므로, 리스트 안에 리스트 형식으로 Schedule List를 생성한다.</div>
###### &nbsp;◽&nbsp; 등록된 스케줄이 있으면 날짜에 해당 날짜의 스케줄 리스트를, 등록된 스케쥴이 없으면 null을 담는다.
```java

        Set<Integer> daysHaveSchedule = new HashSet<>();  // daysHaveSchedule: 해당 월의 스케쥴이 있는 날짜들

        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {   // 입력받은 연, 월에 해당하는 스케줄 리스트에서
                 daysHaveSchedule.add(m.getDay());   // 해당 월의 스케쥴이 있는 날짜들을 찾는다. 
                                                     // 하루에 스케쥴 어려개 가능하기 때문에 -> 즉, 날짜 중복 가능하기 때문에 -> HashSet<>()으로
        }



        List<List<Schedule>> daysScheduleList = new ArrayList<>();   // List안에 List 
        List<Schedule> eachOfDaySchedule = new ArrayList<>();        // 날짜별 스케쥴 리스트
    
        for (Integer d : dList) {  // dList를 for문으로 돌려
            if(daysHaveSchedule.contains(d)){   // 등록된 스케쥴이 있는 날짜인 경우
                 eachOfDaySchedule = scheduleService.findByDayOfMonth(date.getMonthValue(),d);   // 해당 날짜의 스케쥴 리스트 만들어서
                 daysScheduleList.add(eachOfDaySchedule);   // 날짜에 담는다
            }
            else {                              // 등록된 스케쥴이 없는 날짜인 경우
                  daysScheduleList.add(null);   // null                         }
       } 
```
<br>

##### <div id="t6">6. 작성된 일기가 있다면 diaryId를, 없다면 0을 넣어 Diary List를 생성한다.</div>

```java

         List<Integer> daysHaveDiary= new ArrayList<>(); 
         for (Diary m : diaryService.findByMonth(date.getMonthValue())) {
               daysHaveDiary.add(m.getDay()); // 등록된 일기가 있는 날짜들
         }
        
         List<Integer> diaryList = new ArrayList<>();
         for (Integer d : dList) {
            if (daysHaveDiary.contains(d)) {  // 등록된 일기가 있는 날짜인 경우
                diaryList.add(diaryService.findByMD(date.getMonthValue(),d).getDiaryId());  // 그 날의 diaryId
            } else{  // 등록된 일기 없으면
                diaryList.add(0);  // 0
            }
         }

```
<br>

##### <div id="t7">7. Today와 일치하는 날짜에는 1을, 일치하지 않는 날짜에는 0을 담아 Today List를 생성한다.</div> 

```java

        List<Integer> todayList = new ArrayList<>();
        if(LocalDate.now().getMonthValue() == date.getMonthValue()) {
           for (Integer i : dList) {  // 월, 일 일치: 이게 오늘이잖아
              if(i == LocalDate.now().getDayOfMonth()) {
                  todayList.add(1);  // Today라고 알려주기 위해 1
              } else {
                  todayList.add(0);   //Today 아니면 0
              }
            }
         }
        
        if(LocalDate.now().getMonthValue() != date.getMonthValue()) {
            for (Integer i : dList) {
                todayList.add(0);
            }
        }

```
<br>

##### <div id="t8">8. DayDiaryDto 타입 통합 리스트를 생성한다.</div>
###### &nbsp;◽&nbsp; 앞서 생성한 dList, daysScheduleList, diaryList, todayList를 하나의 리스트에 담는다. 

```java
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();   // DayDiaryDto: 다이어리 프로그램으로 데이에 담아 전달하려는 모든 정보 들어있는 dto
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).today(to.get(i)).dayOfWeek(dayOfWeek.get(i)).build());
        }
```
<br>

##### <div id="t9">9. HTML에서 달력의 날짜는 일주일 단위로 채워지기 때문에, 통합리스트를 7일 단위로 쪼개어 넘긴다.</div>
###### &nbsp;◽&nbsp; 일주일 단위로 반복문 작성하기 수월하도록 전체 리스트를 일주일씩 자른 후, 각각의 리스트를 리스트 안의 리스트 형태로 담아 전달한다.

```java

        List<DayDiaryDto>  d1 = new ArrayList<>(); // 한 주 단위로 쪼갠 리스트
        List<DayDiaryDto>  d2 = new ArrayList<>();
        List<DayDiaryDto>  d3 = new ArrayList<>();
        List<DayDiaryDto>  d4 = new ArrayList<>();
        List<DayDiaryDto>  d5 = new ArrayList<>(); 
        List<DayDiaryDto>  d6 = new ArrayList<>();
        
        List<List<DayDiaryDto>> integratedList = new ArrayList<>();  // 리스트 안에 리스트로 담는다.
        
        for (int i = 0; i < dayDiaryDtoList.size(); i++) {
            if(i < 7) {   d1.add(dayDiaryDtoList.get(i));   } 
               integratedList.add(d1);
            if( 6 < i && i< 14) {    d2.add(dayDiaryDtoList.get(i));    }
                integratedList.add(d2);
            if( 13 < i && i< 21) {    d3.add(dayDiaryDtoList.get(i));   }
                integratedList.add(d3);
            if( 20 < i && i< 28) {    d4.add(dayDiaryDtoList.get(i))    }
                integratedList.add(d4);
            if( 27 < i && i< 35) {    d5.add(dayDiaryDtoList.get(i));   }
                integratedList.add(d5);
            if(34 < i && i< 42) {    d6.add(dayDiaryDtoList.get(i));    }
                integratedList.add(d6);
         }  

          return integratedList;  // 리스트안에 리스트로 1주 단위로 쪼개 담은 통합 리스트를 최종 리턴
    }
```
<br><br><br>

### <div id="num2">💡 달력 뷰</div>
##### <div id="v1">10. 달력의 날짜는 아래와 같은 순서로 채워진다. 따라서 일주일 단위 반복문을, 달력의 줄 수 만큼 작성한다.</div>
###### &nbsp;◽&nbsp; 일주일 단위 반복문에는 [Day Modal](#modal) / [Diary Icon](#icon) / [Today](#v2) / [Day Schedule List](#v3)가 포함되어 있다. 
<img width="350" alt="방" src="https://github.com/user-attachments/assets/de75e0ef-9d7b-4418-82d5-e7dcea5ba78e"><br>

> main.html
```html

   <div class="w3-cell-row"> <!-- 1줄(7일)시작 -->  <!-- 달력 줄 갯수만큼 반복하여 뷰 구현 -->
     <div class="w3-container w3-cell text pt-1" th:each="d : ${d1}">

        <!-- day(날짜) -->
        <div class="row" th:if="${ d.today == 1 }"> <!-- 투데이인 경우 -->

           
           <div class="col-2" th:day="${d.day}" onclick="dayPop(this.getAttribute('day'));" id="d" th:text="${ d.day }"></div>
           
           <!-- 2. diary Icon: 작성된 일기 있으면 다이어리 아이콘 표시하고, 클릭 시 다이어리 detail 페이지로 이동 -->
           <div class="col-2" th:if="${d.diaryId} != 0">
              <a th:href="@{ /diary/detail?diaryId={diaryId} (diaryId = ${ d.diaryId })}"><img class="icon" src="/icons/heart.svg"></a>
           </div>
               
           <!-- 3. Today 표시 -->
           <div class="col-7" align="right" id="divToday"></div>
        </div>

        <div th:if="${ d.today == 0 }"> <!-- 투데이가 아닌 경우 -->
           <span th:if="${d.day} != 0" th:day="${d.day}" onclick="dayPop(this.getAttribute('day'));" id="d" th:text="${ d.day }"></span>
           <span th:if="${d.diaryId} != 0" th:diaryId="${d.diaryId}" onclick="diaryPop(this.getAttribute('diaryId'));" id="diaryId"><img src="/heart"></span>
        </div>
        
        
        <!-- 4. day의 스케줄 리스트 보여줄 영역-->
        <div class="m-2"> 
           <div th:if="${d.sList}" class="effectT" th:each="s : ${d.sList}"><small>⦁ <span th:text="${ s.content }"></span></small></div>
        </div>

      </div>
   </div><!-- 1줄(7일) 끝-->      

```
<br>

##### <div id="modal">11. Day Modal: 달력을 월(月) 단위가 아니라 일(日) 단위로 보여주는 모달</div>
<div align="center"><img src="https://github.com/user-attachments/assets/2f9dc8d0-5ac9-4c43-b639-c70fdbc7676c" width="680" alt="데이모달"></div><br>

###### <div id="m1">&nbsp;◽&nbsp; 달력에서 날짜 클릭하면 Day Modal 뜸</div>

> main.html
```html
     <!-- 날짜 클릭시 해당 날짜 받아와서 Day Modal 띄움 -->
     <div class="col-2" th:day="${d.day}" onclick="dayPop(this.getAttribute('day'));" id="d" th:text="${ d.day }"></div>
````

> calendar.js
```javaScript

    function dayPop(day) {
       document.getElementById('id01').style.display = 'block';   // day 클릭 모달 open
       showScheduleOfDay();  // Day의 일정 리스트
    };

    function showScheduleOfDay() { // Day 일정 리스트 요청
       const fullDate = document.querySelector('#fullDate').value;
       const monthValue = document.querySelector('#m2').value;
       const day = document.querySelector('#day').value;
    
       const data ={  monthValue:monthValue, day:day  };
       axios.post('/scheduleDay', data)
            .then(response => { 
                showList(response.data) // 받아온 Day의 일정 리스트를 show
            }).catch(err => { console.log(err) });
     }

     function showList(data) { // Day 모달에 일정 리스트 show 
        const sListDiv = document.querySelector('#sListDiv');
        str = '';

        for (let x of data) {
            str += `<div class="effectT" scheduleId="${x.scheduleId}" onclick="modifySchedule(this.getAttribute('scheduleId'))">`
                +'<small>⦁ ' + x.content + '</small><span style="color:white;">'+x.scheduleId+'</span></div>';
        }
        sListDiv.innerHTML = str;
     }

```
<br>

###### <div id="m2">&nbsp;◽&nbsp; Day Modal 사이드바에서 일정 추가 / 일기 작성 / D-day 설정 가능</div> 
> main.html
```html

        <!-- Day Modal 사이드바 -->
        <div class="w3-sidebar w3-center w3-bar-block" style="display:none;” id="mySidebar">
            <button class="w3-bar-item w3-button" onclick="w3_close()"><span style="margin-right: 50px;"></span>&times;</button>
                <!-- D-day 설정 -->
                <span onclick="showDDayInput()" class="w3-bar-item w3-button w3-small">D-DAY ♥</span>
                <!-- 일정 추가 -->
                <span onclick="showInput()" class="w3-bar-item w3-button w3-small"><span onclick="w3_close()">일정 추가</span></span>
                <!-- 일기 작성 -->
                <span onclick="dateInfo()" class="w3-bar-item w3-button w3-small">내 일기장</span>
        </div>         
```
<br>

###### <div id="m3">&nbsp;◽&nbsp; Front/Back 버튼 클릭 > Day Modal의 날짜 이동</div>

> main.html
```html
         <!-- day modal 하단: Front/Back 날짜 이동 버튼 -->
         <div class="w3-container mt-2" style="display: inline-block;"><input type="hidden" id="last">
            <button class="w3-button" onclick="frontDay();"><img src="/icons/l.svg" width="12" height="12"></button>
            <button class="w3-button" onclick="backDay();"><img src="/icons/r.svg" width="12" height="12"></button>
         </div>
```

> calendar.js
```javaScript

   function frontDay() {   // modalDay 앞 날짜로 이동(Day -1)
      const d = document.querySelector('#day').value;
      const monthValue = document.querySelector('#m2').value;
      const year = document.querySelector('#year').value;
   
      if(d != 1){     // day가 1이 아니면    
          day.value = d - 1;
          showFullDate();
          showScheduleOfDay();
      }
    
      if(d == 1){    // day가 1이면 -> 이전 달 말일로 넘어가야
           var month  = monthValue-1;
 
           axios.get('/front/day/' + month)
                .then(response => {
                     m2.value = monthValue-1;
                     day.value = response.data;
                     showFullDate();
                     showScheduleOfDay();
                }).catch(err => { console.log(err) });
      } 
   }


   function backDay() {  // modalDay 뒷 날짜로 이동(Day +1)
     
      if(d != la){    // day가 말일이 아니면
          axios.get('/back/day/' + d)
               .then(response => {
                   day.value = response.data;
                   showFullDate();
                   showScheduleOfDay();
              }).catch(err => { console.log(err) });
      }
    
      if(d == la) {   // day가 말일이면 -> 다음 달 1일로 넘어가야
          axios.get('/back/day/month/' + monthValue)
               .then(response => {
                     day.value =1; 
                     m2.value = response.data;
                     showFullDate();
                     showScheduleOfDay();
               }).catch(err => { console.log(err) });
       }
    }

```
<br>

##### <div id="icon">12. Diary Icon: 작성된 일기가 있는 날짜에 표시되는 아이콘</div>
<div style="margin-left: 100px;"><img width="850" alt="다이어리아이콘" src="https://github.com/user-attachments/assets/aecd9e32-3d10-4bb9-926c-eda5afecbc74"></div>

##### <div id="v2">13. Today: Font Color & Blink Effect - <img width="60" alt="TODAY" src="https://github.com/user-attachments/assets/f83a69f6-566f-48b3-88e9-01dfbf90ea51"> 표시</div>
##### <div id="v3">14. Day Schedule List: Mouse-hover Effect - Background Color & Text overflow: ellipsis</div>
<div align="center"><img src="https://github.com/user-attachments/assets/59be6174-2df4-4ad5-9ad6-7fdfbabd341d" width="680" alt="마우스오버"></div><br><br>
  

##### 15. 달력 이동: &nbsp;&nbsp;◽ [Front/Back 버튼 클릭하여 이동](#btn)<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ [date Input창에서 원하는 날짜 선택하여 이동](#input)


<div  align="center"><img src="https://github.com/epepssp/tomydays/assets/118948099/7e285afc-afad-4e7f-8ab7-ca464d4538c6" width="680" alt="날짜선택이동2"></div>

###### <div id="btn"><small>Front/Back 버튼 클릭하여 이동</small><div>

> main.html
```html


     <!-- 달력 Front 이동 버튼 -->
     <span th:if="${ dto.monthValue != 1}"> <!-- 1월이 아닐 때: 현재 monthValue에서 -1 -->
        <button th:onclick="|location.href='@{ /calendar/front?monthValue={monthValue}&year={year}
                      (monthValue =${ dto.monthValue -1 }, year = ${ dto.year})}'|" >
        <i class="material-icons">chevron_left</i></button>
     </span>
     <span th:if="${ dto.monthValue == 1}"> <-- 1월일 때: 작년 12월로 이동해야 함 -->
        <button th:onclick="|location.href='@{ /calendar/front?monthValue={monthValue}&year={year}
                      (monthValue =${ dto.monthValue +11 }, year = ${ dto.year -1})}'|" >
        <i class="material-icons">chevron_left</i></button>
     </span>
   
     <!-- 달력 Back 이동 버튼 -->
     <span th:if="${ dto.monthValue != 12}"> <!-- 12월이 아닐 때: 현재 monthValue에서 +1 -->
        <button th:onclick="|location.href='@{ /calendar/back?monthValue={monthValue}&year={year}
                       (monthValue =${ dto.monthValue +1 }, year = ${ dto.year})}'|">
        <i class="material-icons">chevron_right</i></button>
     </span>
     <span th:if="${ dto.monthValue == 12}"> <-- 12월일 때: 내년 1월로 이동해야 함 -->
        <button th:onclick="|location.href='@{ /calendar/back?monthValue={monthValue}&year={year}
                       (monthValue =${ dto.monthValue -11 }, year = ${ dto.year +1})}'|">
        <i class="material-icons">chevron_right</i></button>
     </span>


```
<br>

###### <div id="input"><small>date Input창에서 원하는 날짜 선택하여 이동</small></div>

> main.html
```html


      <div id="selectDiv" class="w3-cell-row" align="right" style="height: 30px; display: none;">
         <!-- date input -->
         <input class="form-control" type="date" style="display: inline-block;" id="select-d">
         <small><span class="rounded border p-1 mt-3" onclick="selectSubmit();">선택</span></small>
      </div>


      <script>
         function selectSubmit(){
             var selectd = document.querySelector('#select-d').value;  // date input에서 선택한 날짜
             var dates = selectd.split("-");  
             const year = dates[0];
             const month = dates[1];
        
             var f = month.substring(0,1);  
             var monthValue;
             if (f === "0") {  // 월: 한자릿수면 앞애 0 떼야 
                 monthValue = month.substring(1, 2);
             } else {
                 monthValue = month;
             }
        
             location.href = `/calendar/selected?monthValue=${monthValue}&year=${year}`;
         }
       </script>
```
<br>

##### 16. Notice Board: 우측 하단에 표시되며 오늘 날짜 기준으로 D-day 리스트를 보여준다.
<div align="center"><img width="680" alt="노티스보드" src="https://github.com/user-attachments/assets/4966a39a-8d75-499f-9650-14d2cede8df1"></div>
<br><br>



---------------------

