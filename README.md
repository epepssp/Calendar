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
##### 11. [Day Modal](#modal): ◽ 날짜를 클릭하면 뜨는 모달<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ 일정 추가 / 일기 작성 / D-day 설정 기능<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ◽ Front/Back 버튼 클릭하여 날짜 이동
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
  ###### ◽ 디폴트는 현재 날짜 기준이며, 사용자가 선택한(입력한) 특정 날짜가 있다면 해당 날짜로 통합 리스트 생성을 요청한다.
<br>
  
##### <div id="t1">1. 해당 월의 시작 요일을 찾는다.</div>
  ###### ◽ 해당 월의 첫 번째 날이 무슨 요일인지에 따라 달력이 시작되는 위치가 달라지기 때문이다. 

```java
    LocalDate date = LocalDate.of(year,monthValue, 1);   // 입력 받은 year, monthValue의 첫 번째 날을 생성한다
    String dayOfWeekS = date.getDayOfWeek().toString();  // 해당 월의 시작 요일을 찾는다
```
<br>

##### <div id="t2">2. Week enum을 정의하고 입력받은 요일 문자열과 일치하는 인덱스를 반환받는 방식으로 요일 문자열을 숫자로 변환한다.</div>
>  CalendarController
```java

    int front = 0;
    Week w = Week.valueOf(dayOfWeekS);  // 문자열 dayOfWeekS(요일)을 Week 열거형으로 변환

    for(Week e: Week.values()) {   
        if(e.equals(w)) {   
           front = e.ordinal();    // enum에서 해당 요일 문자열과 일치하는 인덱스 값 받아옴 -> 숫자 값으로 변환 완료 
        }                            
     }

```
<br>

##### <div id="t3">3. 해당 월의 시작 요일과 마지막 요일을 고려해, 달력의 앞뒤 공백 칸에는 0을, 날짜가 표시될 칸에는 해당 날짜를 넣은 달력용 Day List(= dLsit)를 생성한다.</div>
> CalendarController
```java

    // dLsit: HTML에 달력 그리기 위한 day리스트
    // 통합 리스트의 뼈대가 됨 
    // dList.length() = front + date.getMonth().maxLength() + back;

    // 달력 마지막 줄: (앞 공백 + 날짜 갯수)를 7로 나눈 나머지 만큼 차있는 상태
    int remainder = (front + date.getMonth().maxLength()) % 7 ;

    // 달력 뒷 공백 = 7 - 나머지
    int back = 7 - remainder;

        List<Integer> dList =new ArrayList<>();
   
        if(front != 0) { 
            for (int i = 0; i < front; i++) {
                 dList.add(0);  // 앞 공백에 0 
              } 
        }
        
       for (int j = 1;j < date.getMonth().maxLength() +1; j++) {
              dList.add(j);    // 날짜에는 날짜 넣고
        }
    
        if(remainder != 0) { 
           for (int i = 0; i < back; i++) {
                dList.add(0);  // 뒷 공백에 0 
              }
        }

```
<br>

##### <div id="t4">4. 통합리스트에 필요한 데이터들을 묶어 DayDiaryDto를 정의한다.</div>

> DayDiaryDto
```java

    @ToString
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class DayDiaryDto {

       private int day;              // 날짜
       private Integer diaryId;      // diaryId
       private List<Schedule> sList; // day에 등록된 일정 리스트 - 리스트안의 리스트 형식
       private int today;            // today면 = 1, 아니면 = 0 

   }
```
<br>


##### <div id="t5">5. 한 날짜에 여러 개의 일정이 있을 수 있으므로, 리스트 안에 리스트로 Schedule List를 생성한다.</div>
 CalendarController
```java

        Set<Integer> daysHaveSchedule = new HashSet<>();  // daysHaveSchedule: (이번달에) 스케쥴이 있는 날짜
        for (Schedule m :scheduleService.findByMonth(date.getMonthValue())) {
                 daysHaveSchedule.add(m.getDay());  // 해당 월에 스케쥴이 있는 날짜들: 하루에 스케쥴 어려개 가능 -> HashSet<>()으로
                                                     // 날짜: 1 2 3 4 5 6 7 8... : 스케쥴이 있는 날짜: 3 4 6 8 ...
        }
        
        List<List<Schedule>> daysScheduleList = new ArrayList<>();   // List안에 List 
        List<Schedule> eachOfDaySchedule = new ArrayList<>();       // 날짜별 스케쥴 리스트
    
        for (Integer d : dList) {  // dList를 for문으로 돌려
            if(daysHaveSchedule.contains(d)){   // 스케쥴이 있는 날짜인 경우
                 eachOfDaySchedule = scheduleService.findByDayOfMonth(date.getMonthValue(),d);   // day 스케쥴 리스트
                 daysScheduleList.add(eachOfDaySchedule);   // 날짜 안에 day 스케쥴 리스트 담기
            }
            else {  // 스케쥴이 없는 날짜인 경우
                  daysScheduleList.add(null);   // null (스케쥴 없다는 의미)
             }
         } 
```
<br>

##### <div id="t6">6. 작성된 일기가 있다면 diaryId를, 없다면 0을 넣어 Diary List를 생성한다.</div>
>  CalendarController
```java

         Set<Integer> daysHaveDiary= new HashSet<>(); 
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

##### <div id="t7">7. 오늘 날짜와 일치하는 날짜에는 1을, 일치하지 않는 날짜에는 0을 넣어 Today List를 생성한다.</div> 
>  CalendarController
```java

        List<Integer> to = new ArrayList<>();
        if(LocalDate.now().getMonthValue() == date.getMonthValue()) {
           for (Integer i : dList) {  // 월, 일 일치: 이게 오늘이잖아
              if(i == LocalDate.now().getDayOfMonth()) {
                  to.add(1);  // Today라고 알려주기 위해 1
              } else {
                  to.add(0);   //Today 아니면 0
              }
            }
         }
        
        if(LocalDate.now().getMonthValue() != date.getMonthValue()) {
            for (Integer i : dList) {
                to.add(0);
            }
        }

```
<br>

##### <div id="t8">8. DayDiaryDto 타입으로 리스트를 생성한다.</div>
>  CalendarController
```java

        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();   // DayDiaryDto: 다이어리 프로그램으로 데이에 담아 전달하려는 모든 정보 들어있는 dto
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).today(to.get(i)).dayOfWeek(dayOfWeek.get(i)).build());
        }

```
<br>

##### <div id="t9">9. HTML에서 달력 날짜가 일주일 단위로 채워지므로, 통합리스트를 7일 단위로 쪼개어 넘긴다.</div>
>  CalendarController
```java

        List<DayDiaryDto>  d1 = new ArrayList<>(); // 한 주 단위로 쪼개서
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
<br><br>



---------------------

---------------------

**💡 API 사용하지 않고, 직접 Calendar 그리기**<br>
   + ##### dList - 달력 테이블에 해당 월의 달력을 그려줄 day 리스트
    
  > CalendarController.java 일부
  
  ```java

        // LocalDate date  = LocalDate.now();
        LocalDate date = LocalDate.of(year,monthValue, 1);
        // 현재 또는 전달 받은 year,monthValue 로 date 구함
        
        String dayOfWeekS = date.getDayOfWeek().toString();
        // 월(month) 1일의 요일 구하고
        
        int original = 0;
        Week w = Week.valueOf(dayOfWeekS);  
        for(Week e: Week.values()) {
            if(e.equals(w)) {
              original=  e.ordinal();
            }
        }
        // Enum 이용해서 요일 차 계산: (HTML에서 달력 그릴 때 몇번째칸부터 1일 시작 할 지)
        // 차이 만큼 앞에 0 넣어 리스트 만들어서 넘기면 달력을 제대로 채울 수 있기 때문
        
        int sub = date.getMonth().maxLength() + original;
        // 뒤에 빈 칸 계산해서 역시 0넣음. HTML 달력 모양 깨지지 않게  
        
        List<Integer> dList =new ArrayList<>(); 
        // dLsit: HTML에 달력 그리기 위한 데이리스트
        // 1일 시작 지점까지 0을 넣고, 1일 시작 지점부터 해당 월의 days를 넣고,
        // 캘린더 양식(7x5, 7x6)에 맞게 뒤에 남는 칸 계산하여 0 넣는다.
     
        if(original != 0) { // 1일이 일요일이 아닌 다른 요일인 경우
            for (int i = 0; i < original-1; i++) { // 1일 시작 지점 까지 0 넣음
               dList.add(0);
              } 
            for (int j = 0;j < date.getMonth().maxLength() +1; j++) { // 1일 시작 지점부터 해당 월의 days 넣음
             dList.add(j);
          }
        }
        
        if(original == 0) { // 1일이 일요일인 경우
            for (int j = 1;j < date.getMonth().maxLength() +1; j++) {  // 해당 월의 days 넣음
                dList.add(j);
             }
        }


         // HTML에 그릴 캘린더 양식(7x5, 7x6)이 깨지지 않도록 뒤에 남는 칸 계산하여 0 넣는다.
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
  
  + ##### dList + Schedule/Diary/Today : 통합 리스트  

  > CalendarController.java 일부
  
  ```java
    
        // Schedule 리스트
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
          
        // Diary 리스트
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

        // Today or Not
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


        // 통합 리스트  
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).today(to.get(i)).dayOfWeek(dayOfWeek.get(i)).build());
       }
  ```
  <br>  
    
 **💡 파일 업로드 (Booque ver2 보다 업그레이드 된)**
  + ##### 여러개 업로드 / 드래그로 선택 업로드 / 첨부파일 추가 후 또 추가 / 이미지 제거 / Thumbnails / 이미지 슬라이드

  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/1f73aeef-6bb6-448c-afac-4a6911527903" width="620" height="400" alt="사진"></div>
    
  > create.html 일부

  ```html

       <!-- 사진 첨부 버튼 -->
       <div onclick="document.getElementById('fileModal').style.display='block'" class="rounded m-1 p-1">
          <img src="/icons/images.svg">사진첨부
       </div>

       <!-- 첨부한 파일 리스트 보여주는 영역 -->
       <!-- 사진 첨부 버튼 누르면 실시간으로 추가된 사진 리스트 보여질 영역 -->
       <!-- 사진 추가 후 사진 첨부 버튼으로 이어서 추가 가능. 삭제(remove)도 가능 -->
       <div class="col">
          <div id="uploadResults" class="container-fluid d-flex" style="flex-wrap: wrap;"></div>
          <div id="uploads"></div> <!-- saveTemporarily 리스트 보여주는 영역 -->
       </div>
  
  ```

  > fileUpload.js
  
  ```javascript

     // 모달의 업로드 버튼 클릭 이벤트 처리
     document.querySelector('#modalUploadBtn').addEventListener('click', e => {
   
        const formData = new FormData();
        const fileInput = document.querySelector('input[name="files"]');
       
        for (let file of fileInput.files) {
            formData.append('files', file);
        }
          uploadFiles(formData);
    });
    
    function uploadFiles(formData) {
      
        axios.post('/upload', formData)
            .then(getUploadedThumbnails)
            .catch(err => { console.log(err) })
            .then(document.getElementById('fileModal').style.display = 'none');
    }
    
    
    function getUploadedThumbnails(response){
            if (response.status == 200) {
            response.data.forEach(x => {
                // 이미지 파일이 아닌 경우, 디폴트 썸네일 이미지를 사용하도록.
                let img = '';
                if (x.image) {
                    img = `<img src="/api/view/${x.link}" data-src="${x.uuid + '_' + x.fileName}" />`;
                } else {
                    img = `<img src="/images/file_128.png" data-src="${x.uuid + '_' + x.fileName}" />`;
                }

       // 업로드 선택한 파일들 innerHTML로 diary create 페이지에 보여줌   
       const htmlStr = `<div class="card my-2">
                          <div class="card-header d-flex justify-content-center">
                             ${x.fileName}
                             <button class="btnDelete btn-close" aria-label="Close"
                              data-uuid="${x.uuid}" data-fname="${x.fileName}"></button>
                          </div>
                          <div class="card-body">
                             ${img}
                          </div>
                        </div>`;
                
          uploadResults.innerHTML += htmlStr;
      });   


      // 업로드 목록 사진 각각 btnDelete 버튼 달아주기 
      document.querySelectorAll('.btnDelete').forEach(btn => {
             btn.addEventListener('click', removeFileFromServer);
      });
          
           const uploads = document.querySelector('#uploads');
           const files = uploadResults.querySelectorAll('img');
       
           let str = '';
           files.forEach(x => {
              const imgLink = x.getAttribute('data-src');
              str += `<input type="hidden" name="fileNames" value="${imgLink}" />`;
            });

           uploads.innerHTML = str;
       }

       // 선택한 사진 제거
       function removeFileFromServer(event) { 
          event.preventDefault();
      
          const btn = event.target;
          const uuid = btn.getAttribute('data-uuid');
          const fname = btn.getAttribute('data-fname');
          const fileName = uuid + '_' + fname;
        
        axios.delete('/remove/' + fileName)
            .then(resp => { btn.closest('.card').remove() })
            .catch(err => { console.log(err) })
            .then(function () {});
       }
   ``` 
  
   > detail.html 일부

   ```detail.html
  
     <div class="w-50 container"> <!-- image slide -->
       <div id="carouselExampleDark" class="carousel carousel-dark slide">
       
          <div class="carousel-inner">
             <div class="carousel-item active">
                <img id="img" th:src="${ '/api/view/'+firstImg.uuid+'_'+firstImg.fileName }" class="mx-auto d-block w-30" style="width:220px; height: 270px; object-fit: cover;"> 
             </div> 
             <div class="carousel-item" th:each=" imgList : ${ imgList } ">
                <img id="img" th:src="${ '/api/view/'+imgList.uuid+'_'+imgList.fileName }" class="mx-auto d-block w-30" style="width:220px; height: 270px; object-fit: cover;"> 
             </div>
          </div>
          
         <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
         </button>
         <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
         </button>
         
       </div>
    </div>
  
  ```

  <br> 
  
  **💡 Weather 선택** 
   + ##### 하나만 선택 가능(중복X), 취소 후 재선택 가능
     
   <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/7141445f-4786-4c59-9112-7c72d5d327d9" width="620" height="400" alt="웨더"></div>
   
   > diary.js
  
  ```javascript

       let weather = null; // 초기에는 값이 없는 상태

       function svgClick(n) {

         if (weather === null) {  
             weather = n; // 변수 weather에 새로운 값 저장
             updateWeather(n);
             console.log("변수 값이 저장. weather =", weather);
         } else if (weather === n) {
             weather = null; // 저장된 값을 취소하기 위해 변수를 null로 설정
             updateWeather(null);
             console.log("변수 값이 취소. weather =", weather);
         } else {
             isClickable = false; // 클릭 비활성화  
             console.log("변화 없음. weather =", weather);
         }
      }

      function handleClick(btn, n) {
          let isClickable = true; // 클릭 가능한 상황인지 True, False
  
          if (weather !== null && weather !== n) {
              isClickable = false; // 클릭 비활성화
          }
          if (isClickable) { // 클릭 가능한 경우에만 동작 수행
              svgClick(n); 
              btn.classList.toggle("invert");
          }
       }

       function updateWeather(n){
           const weather = document.querySelector('#weather');
           weather.value = n;
       }
  ```
  <br>
  
 **💡 Emoji**
  + ##### Emoji(이모티콘) 이용한 다채로운 글 작성

  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/25d61a59-863d-470f-8ec3-1a091dd92692" width="600" height="400" alt="이모지"></div>
  
  > create.html
  ```html

     <div class="w3-sidebar w3-bar-block mt-3" align="center" style="display:none; position: absolute;" id="mySidebar"><!--이모티콘 Sidebar -->
        <div id="emoji-list" class="card mt-4" style="width: 250px; height: 250px; border: 1px solid red;"><!-- 사이드바 컨텐츠 -->
           <!-- 추가할 이모지 data-emoji 형식으로 -->
           <div class="w3-cell-row mt-2" style="width: 100%;">
               <div class="w3-container w3-cell emoji p-1" data-emoji="😁"><i class="fa-regular fa-face-smile-beam m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😉"><i class="fa-regular fa-face-smile-wink m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😊"><i class="fa-regular fa-face-laugh m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😄"><i class="fa-regular fa-face-laugh-beam m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😆"><i class="fa-regular fa-face-laugh-squint m-1" style="font-size: 20px;"></i></div>
           </div>
           <div class="w3-cell-row mt-1" style="width: 100%;">
               <div class="w3-container w3-cell emoji p-1" data-emoji="😘"><i class="fa-regular fa-face-kiss-wink-heart m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😍"><i class="fa-regular fa-face-grin-hearts m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😜"><i class="fa-regular fa-face-grin-tongue-squint m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="😅"><i class="fa-regular fa-face-grin-beam-sweat m-1" style="font-size: 20px;"></i></div>
               <div class="w3-container w3-cell emoji p-1" data-emoji="🙄"><i class="fa-regular fa-face-rolling-eyes m-1" style="font-size: 20px;"></i></div>
            </div>
         </div><!-- 사이드바 컨텐츠 끝 -->
      </div><!--이모티콘 Sidebar 끝 -->
  
  ```
  > diary.js
  ```javascript
  
     //이모지
     const emojiList = document.querySelectorAll('.emoji');
     const textarea = document.getElementById('diaryContent');

     //이모지 리스트 각각의 이모지에 이벤트 리스너 설정
     emojiList.forEach(emoji => {
         emoji.addEventListener('click', () => {
         const selectedEmoji = emoji.getAttribute('data-emoji');
         textarea.value += selectedEmoji;
        });
     });
      
     function w3_open() {
          document.getElementById("mySidebar").style.display = "block";
     }

      function w3_close() {
          document.getElementById("mySidebar").style.display = "none";
     }
  
  ```
   
  <br>
  
 **💡 Sort (정렬)**
  + ##### 미니 캘린더 or 리스트 두 가지 방식으로 일기 목록 정렬 방식 선택 가능
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/044fc5b3-964a-4d44-85cd-3c4a44279f3f" width="620" height="400" alt="정렬"></div>

  > diary.js
  ```javascript

     let sort = 1;    
  
     const mini = document.getElementById("mini");
     const listDiv = document.getElementById("listDiv");
     const sorti = document.getElementById("sorti");

     <!-- 정렬 디폴트 값 - 미니 캘린더형 -->
     mini.style.display = "block";
     listDiv.style.display = "none";

     sorti.classList.add("far", "fa-list-alt");

     <!-- 정렬(Sort) 아이콘(버튼) 클릭했을 때 sort Type 바뀌는 함수 -->
     function sortTypeChange() {
         if (sort === 1) {
             mini.style.display = "none";
             listDiv.style.display = "block";
             sorti.classList.remove("far", "fa-list-alt");
             sorti.innerHTML = '<i class="material-icons" style="font-size:19px;">grid_on</i>';
             sort = 2; // 1일 경우 2로 변경
         } else {
             mini.style.display = "block";
             listDiv.style.display = "none";
             sorti.innerHTML = "";
             sorti.classList.add("far", "fa-list-alt");
             sort = 1; // 2일 경우 1로 변경
         }
     }

     // 버튼 클릭 이벤트 리스너 등록
     const sortBtn = document.getElementById("sortBtn");
     sortBtn.addEventListener("click", sortTypeChange);
  
  ```
  > diary.js

  ```javascript

     // 미니캘린더 형식
     function showMini(data){   
         const mini = document.querySelector('#mini'); 

         let str2='';  // 캘린더 한 줄 시작 
         str2 +='<div class="row line">';
         for (let i = 0; i < 7; i++) {
             if(data.d2[i].diaryId != 0) {    // 일기有 (Background-color有) - 클릭시 Diary Detail Page 이동 (작성한 일기 볼 수 있음)
                 str2 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d2[i].diaryId }">`
                      +'<small>'+ data.d2[i].day+'</small></a></div>';
             }
             if(data.d2[i].diaryId == 0) {   // 일기無 - 클릭시 해당 날짜 Diary Create Page 이동 (일기 작성 가능)
                 str2 += `<div class="box" day="${data.d2[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                      +'<small>'+ data.d2[i].day+'</small></div>';
             }
         } 
         str2+='</div>';  // 캘린더 한 줄 끝
                          // 캘린더 줄 수 만큼 반복 작성
                 
         mini.innerHTML = str1 + str2 + str3 + str4 + str5 + str6;
       }

       // 리스트 형식
       function showMiniList(data){
           let str='';
    
           str +='<div style="border-top: 1px solid gray; margin-left: 10px; width: 266px; border-bottom: 1px solid gray;"><div style="border-top: 1px solid #DCDCDC;" >';
    
           for(let x of data){
              str +='<div style="border-bottom: 1px solid #DCDCDC;" class="w3-cell-row p-1">'
                  +'<div class="w3-container w3-cell"><div style="color:#A9A9A9; font-size: 9px;">'+x.year+'.'+x.monthValue+'.'+x.day+' 일기</div>'
                    // 제목 클릭시 Diary Detail 이동
                  +`<a style="text-decoration: none;" href="/diary/detail?diaryId=${ x.diaryId }">`
                  +'<span style="color:#FF6347;">⦁</span> '+x.title+'</a></div>'
                  +'<div class="w3-container w3-cell" style="width:38px;">'
                                                                                                     // 첨부 파일 리스트 인덱스 = 0 의 uuid, fileName                         // 첨부 이미지 파일 갯수         
                  +'<div class="image-container"><img class="rounded" width="35px;" height="42px;" src="/api/view/'+x.uuid + '_' + x.fileName +'" /><span class="caption">'+x.totalAttachments+'</span></div></div></div>';
           }
    
           str +='</div></div>';
    
           listDiv.innerHTML=str;
    
      }
  ```
  <br>
  
**💡 일정(Schedule)** 
  

  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/7762a146-fb85-4f2c-a35a-c2b7930433d7" width="600" height="400" alt="일정추가2"></div> 

  + ##### 마우스 hover-Effect - 전체 내용 확인 가능
  + ##### Calendar: Monthly 스케쥴 확인

  + ##### Front, Back 버튼 이동
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

  + ##### 원하는 날짜 선택 이동
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/7e285afc-afad-4e7f-8ab7-ca464d4538c6" width="600" height="440" alt="날짜선택이동2"></div>
  
  + ##### Day Modal - Daily 스케쥴 확인 / 일정 추가, 일기 작성, D-DAY 설정 가능
  > main.html
  ```html

     <!-- day 클릭 모달 -->
     <div id="id01" class="w3-modal">
        <div class="w3-modal-content" style="width: 280px; height: 400px;">
    
            <!-- day 클릭 모달 사이드바 메뉴 -->
            <div class="w3-sidebar w3-center w3-bar-block" style="background-color:#F5F5F5; display:none; width:110px; height:150px;" id="mySidebar">
                <button class="w3-bar-item w3-button" onclick="w3_close()"><span style="margin-right: 50px;"></span>&times;</button>
                <span onclick="showDDayInput()" class="w3-bar-item w3-button w3-small">D-DAY ♥</span>
                <span onclick="showInput()" class="w3-bar-item w3-button w3-small"><span onclick="w3_close()">일정 추가</span></span>
                <span onclick="dateInfo()" class="w3-bar-item w3-button w3-small">내 일기장</span>
            </div>
            
            <!-- day 클릭 모달 사이드바 열기 -->
            <button class="w3-button w3-white" style="margin-left:190px; display: inline-block;" onclick="w3_open()"><span style="color:#C0C0C0;">&#9776;</span></button><br>  
            
            <div><small>&nbsp;&nbsp;오늘의 일정</small></div>
            <div class="border rounded mt-1 mb-1" style="padding-left:15px; width:230px; height: 150px; border: 1px solid gray;" id="sListDiv"></div> <!-- 일정 리스트 -->

            <span id="inputDiv"></span><!-- 추가할 일정 입력창 -->
            <span id="modifyInputDiv"></span><!-- 일정 수정하기 위한 입력창 -->
               
            <!-- 날짜 Front/Back 이동 버튼 -->
            <div class="w3-container mt-2" style="display: inline-block;">
            <input type="hidden" id="last">
               <button class="w3-button" style="margin-left:50px;" onclick="frontDay();"><img src="/icons/l.svg" width="12" height="12"></button>
               <button class="w3-button" onclick="backDay();"><img src="/icons/r.svg" width="12" height="12"></button>
            </div>
        </div>
     </div> <!-- 모달 끌 -->
     
  ```

  + ##### 일정 추가 - Enter Key 입력
  > calendar.js
  ```javascript

     // 일정 추가 인풋
     function showInput() {
         const inputDiv = document.querySelector('#inputDiv');
  
         // 사이드바에서 일정 추가 클릭시 Input창 뜸
         let str = '<input style="border:none;" autofocus="autofocus" id="addP" type="text" /><span id=addBtn style="margin-left: 10px;"><small>추가</small></span>';
         inputDiv.innerHTML = str;

         const addBtn = document.querySelector('#addBtn');  
         addBtn.addEventListener('click', send);  // 추가 버튼으로 입력

         var addScheduleInput = document.getElementById('addP');
         addScheduleInput.addEventListener('keyup', function enterSend(event) {  // 엔터로 입력
             let addScheduleInputValue = addScheduleInput.value;

             if (event.keyCode === null) { // 입력된 내용이 없으면
                   event.preventDefault();
             }
             if ((event.keyCode === 13) && ($.trim(addScheduleInputValue) != '')) { // 입력된 내용이 있으면
                   send(); // 보냄
             }
         });
     };
  
  ```
  <br>
  
 **💡 D-DAY**
  + ##### D-DAY 설정 - Day Modal or 우측 하단 Notice Board 아래 -> D-DAY 추가
  + ##### Notice Board - Today 기준 D-DAY Count 리스트
  + ##### 날짜 선택 -> 바로 D-DAY Count 계산
  
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/tomydays/assets/118948099/981f4051-9938-4bcc-9b53-ab7b4afcf78a" width="600" height="400" alt="디데이22"></div>  
  <br>

  > DDayRestController.java
 
  ```java

      @PostMapping("/dday/subtract")
      public ResponseEntity<Integer> dDaySubtract(@RequestBody DDay entity){

          LocalDate utDate = entity.getUntilDate(); // D-DAY 날짜
          LocalDate frDate = LocalDate.of(entity.getYear(), entity.getMonthValue(),entity.getDay());  // 오늘

          // subtract: D-DAY Count 값
          long daysSubtract = ChronoUnit.DAYS.between(utDate,frDate);
          int subtract = (int) daysSubtract;       
        
          return ResponseEntity.ok(subtract);
      }
   ```


