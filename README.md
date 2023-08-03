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
+ #### API 사용하지 않고 직접 Calendar 만들기
  + ##### 1. dList 작성
    
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
     
        if(original != 0) { // 1일이 일요일이 아닌 요일인 경우
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
  
  + ##### day의 Schedule list, diaryId
  > DayDiaryDto.java
  
  ```java
    
        public class DayDiaryDto {

          private int day;
          private Integer diaryId;      // 일기
          private List<Schedule> sList; // day에 등록된 일정 리스트
          private int today;            // today면= 1, 아니면 =0 

          // day와 함께 전달할 정보(day 일정 리스트, 다이어리Id)를 묶어 DayDiaryDto 만듦
      }
  ```

  > CalendarController.java 일부
  
  ```java
    
        // 스케쥴(일정) 리스트
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


        // 통합 리스트 - HTML에 통합 된 하나의 리스트로 넘긴다. 
        List<DayDiaryDto> dayDiaryDtoList = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
           dayDiaryDtoList.add(DayDiaryDto.builder().day(dList.get(i)).diaryId(diaryList.get(i))
                        .sList(daysScheduleList.get(i)).today(to.get(i)).dayOfWeek(dayOfWeek.get(i)).build());
       }
  ```
    
    
+ #### 사진 첨부
  + ##### 외부경로 설정
  > application.properties 일부
  ```application.properties
  
      # Own configuration values
      com.example.upload.path=C:\\diary\\uploads 
  
  ```
  > FileUploadController.java 일부
  ```java

      @Value("${com.example.upload.path}")
      private String uploadPath;    // 경로 주입

  ```
  + ##### Diary Create 사진 업로드
  > create.html 일부

  ```html

       <!-- 사진 첨부 버튼 -->
       <div onclick="document.getElementById('fileModal').style.display='block'" class="rounded m-1 p-1">
          <img src="/icons/images.svg">사진첨부
       </div>

       <!-- 첨부한 파일 리스트 보여주는  영역 -->
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
        console.log(fileInput.files);
       
        for (let file of fileInput.files) {
            console.log(file);
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
                //console.log(data);
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
    
   function removeFileFromServer(event) {  // 업로드 선택했던 사진 제거
        event.preventDefault();
        //console.log(event.target);
        //console.log(event.target.closest('.card'));
        
        const btn = event.target;
        const uuid = btn.getAttribute('data-uuid');
        const fname = btn.getAttribute('data-fname');
        const fileName = uuid + '_' + fname;
        //console.log(fileName);
        
        axios.delete('/remove/' + fileName)
            .then(resp => { btn.closest('.card').remove() })
            .catch(err => { console.log(err) })
            .then(function () {});
        
     }
  ```
  
+ #### weather
+ #### Emoji
+ #### 정렬(sort) 기준 선택
+ #### 엔터로 등록 
+ #### Calendar 와 Diary 각종 연결과 이동 기능




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

