# tomydays

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
  + ##### 1. dayList(dList) 작성
    
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
  
  + ##### 2. day/schedule/diary 통합 리스트(dayDiaryDtoList)
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
  <br>  
    
+ #### 사진 첨부 및 슬라이드
  + ##### 1. 외부경로 설정
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
  + ##### 2. Diary Create Page 사진 업로드
  > create.html 일부

  ```html

       <!-- 사진 첨부 버튼 -->
       <div onclick="document.getElementById('fileModal').style.display='block'" class="rounded m-1 p-1">
          <img src="/icons/images.svg">사진첨부
       </div>

       <!-- 첨부한 파일 리스트 보여주는 영역 -->
       <!-- 사진 첨부 버큰 누르면 실시간으로 추가한 사진 리스트 보여질 영역 -->
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

  + ##### 3. Diary Detail Page 사진 슬라이드 보여주기
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
  
+ #### Weather 선택
  + ##### Diary Create Page - 일기 작성시 날씨 선택 기능 (중복X, 하나만 고를 수 있게, 선택한 날씨로 저장됨)
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/0bc50f68-ef11-4213-b2c1-ff09a8338767" width="620" height="400" alt="웨더"></div>
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
  
+ #### 이모티콘(Emoji) 추가
  + ##### Diary Create Page - Emoji 이용한 다채로운 글 작성
 
     <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/882ab275-f82d-4aa7-80d9-4281531b575b" width="600" height="400" alt="이모지"></div> 
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
  
+ #### 정렬(sort)
  + ##### 일기 목록 보기 - 미니 캘린더 or 리스트 형식 선택 가능
  <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/0d179c34-0396-4724-84ab-6a086d583e80" width="620" height="400" alt="정렬"></div>
  
  > create.html
  ```html

      <div class="w3-cell-row mb-1">
        <div class="w3-container w3-cell" id="ms"></div>
        <div style="width: 40px;" class="w3-container w3-cell" id="sortBtn"><i id="sorti"></i></div>
      </div>
  
      <div id="mini"></div><!-- 미니 캘린더 형식 -->
      <div id="listDiv"></div><!-- 리스트 형식 -->
  
  ```
  > diary.js
  ```javascript

     let sort = 1;
  
     const mini = document.getElementById("mini");
     const listDiv = document.getElementById("listDiv");
     const sorti = document.getElementById("sorti");

     <!-- 디폴트: 미니 캘린더 형식 -->
     mini.style.display = "block";
     listDiv.style.display = "none";

     sorti.classList.add("far", "fa-list-alt");

     <!-- 정렬(Sort) 아이콘 클릭했을 때 sort Type 바꾸는 함수 -->
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
  <br>
  
+ #### 일정(Schedule) 
  + ##### Calendar - Month 스케쥴 확인
  + ##### Day Modal Front,Back - Day 스케쥴 확인 
  + ##### 긴 내용 - 마우스 오버 효과

   <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/c190b6d8-dd29-4ac2-85c7-cd62eb719f76" width="600" height="400" alt="일정"></div> 
  
  + ##### Enter Key로 입력 가능
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

+ #### D-DAY
  + ##### 1. 원하는 날짜 클릭 -> Day Modal -> D-DAY 설정 클릭
  + ##### 2. 우측 Notice Board 아래 D-DAY 추가 버튼 클릭
  + ##### 원하는 날짜 선택시, D-DAY Count. 다른 날짜 선택도 가능.
  + ##### Notice Board에 Today 기준 D-DAY Count 됨 
   <div style="margin-left: 100px;"><img src="https://github.com/epepssp/Calendar/assets/118948099/72ee9b70-acd3-45cc-afc0-df8dd0b07605" width="600" height="400" alt="디데이"></div>  
<br>

+ #### Month 이름 포인트 컬러 주기
+ #### Calendar 와 Diary 다양한 연결/이동 기능




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

