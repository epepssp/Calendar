/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {
   now();
   lastDay();
});


function now(){
     const today = new Date();
 
     const tYear= today.getFullYear();
     const tMonth= today.getMonth() + 1;
     const tDay= today.getDate();
     const hDiv = document.querySelector('#hDiv');
     str ='';
     
     str +='<div style="font-size: 11px; font-weight: bold; background-color:#FFE4E1; margin-left:100px;">'
          +'<span id="tYear">'+tYear+'</span>년 <span id="tMonth">'+tMonth+'</span>월 <span id="tDay">'+tDay+'</span>일 / '
          +'<span style="color: #FF6347;">TODAY</span></div>';
     hDiv.innerHTML = str;
     
     axios
        .get('/allDDays')
        .then(response => {
           console.log(response.data);
           showLeft(response.data);
        })
        .catch(err => { console.log(err) });
     
}



function showLeft(data){
    const dDayDiv = document.querySelector('#dDayDiv');
    str='';
    
    for(let x of data) {
        str +='<div style="margin-left: 70px;" align="center">';
        if(x.subtract < 0) {
           str +='<span class="fw-bold" style="color: #FF6347;"><small>♡'+x.subtract+'</small></span> <span><small>'+x.name+'</small></span></div>';
        } 
        if(x.subtract > 0) {
           str +='<span class="fw-bold"  style="color: #4169E1;"><small>♡+'+x.subtract+'</small></span> <span><small>'+x.name+'</small></span></div>';
        }
        
     } str +='<br>';
     dDayDiv.innerHTML = str;
    
}


// modalDay 
function dayPop(day) {
 
    
    // 날짜 클릭하면 modalDay 뜸 
    document.getElementById('id01').style.display = 'block';
    const modalDay = document.querySelector('#modalDay');
    
     const monthValue = document.querySelector('#m').value;
    const mon = document.querySelector('#m2');
    
    mon.value = monthValue;
    
    axios
        .get('/day/' + day)
        .then(response => {
            modalDay.value = response.data;
            showFullDate();
            showScheduleOfDay();  // Day의 일정 리스트
        })
        .catch(err => { console.log(err) });
};


function showFullDate() {
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m2').value;
    const day = document.querySelector('#modalDay').value;
    const full = document.querySelector('#fullDate');
    fullDate = year + monthValue + day;
    full.value = fullDate;

}


// Day 일정 리스트 요청
function showScheduleOfDay() {
    
    const fullDate = document.querySelector('#fullDate').value;
    const monthValue = document.querySelector('#m2').value;
    const day = document.querySelector('#modalDay').value;
    
   const data ={
       monthValue:monthValue,
            day:day
   }

    axios
        .post('/scheduleDay', data)
        .then(response => {
            console.log('쇼스케쥴 성공');
            showList(response.data)
          
        })
        .catch(err => { console.log(err) });

}

// Day 일정 리스트 show 
function showList(data) {

    const sListDiv = document.querySelector('#sListDiv');
    str = '';
    for (let x of data) {
        str += `<div class="effectT" scheduleId="${x.scheduleId}" onclick="modifySchedule(this.getAttribute('scheduleId'))">`
            +'<small>⦁ ' + x.content + '</small><span style="color:white;">'+x.scheduleId+'</span></div>';
    }
    sListDiv.innerHTML = str;
}

// 일정 수정...(하기싫닼ㅋㅋ)
function modifySchedule(scheduleId){
    alert(scheduleId);
    const modifyInputDiv = document.querySelector('#modifyInputDiv');
    
}


// 일정 추가 인풋
function showInput() {
    
    const inputDiv = document.querySelector('#inputDiv');
    // 사이드바에서 일정 추가 클릭시 Input창 뜸
    let str = '<input style="border:none;" autofocus="autofocus" id="addP" type="text" /><span id=addBtn style="margin-left: 10px;"><small>추가</small></span>';

    inputDiv.innerHTML = str;

    const addBtn = document.querySelector('#addBtn');
    console.log(addBtn);

    addBtn.addEventListener('click', send);  // 추가 버튼으로 입력

    var addScheduleInput = document.getElementById('addP');
    addScheduleInput.addEventListener('keyup', function enterSend(event) {  // 엔터로 입력

        let addScheduleInputValue = addScheduleInput.value;

        if (event.keyCode === null) {
            event.preventDefault();
        }

        if ((event.keyCode === 13) && ($.trim(addScheduleInputValue) != '')) {
            send();
        }
    });
};

// 일정 추가  함수
function send() {

    const content = document.querySelector('#addP').value;
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;
    const day = document.querySelector('#modalDay').value;
    const full = document.querySelector('#fullDate').value;


    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        content: content,
        fullDate: fullDate
    }

    console.log(content);
    console.log(data);

    axios.post('/add/schedule', data)
        .then(response => {
            console.log('성공')
            str = '';
            inputDiv.innerHTML = str;
            showScheduleOfDay();
            location.reload();
        })
        .catch(err => { console.log(err) });
}

// modalDay 앞 날짜 이동(Day -1)
function frontDay() {
    
    const day = document.querySelector('#modalDay').value;
    const fullDate = document.querySelector('#fullDate').value;
    const monthValue = document.querySelector('#m').value;
    const year = document.querySelector('#y').value;
   
    if(day != 1){
            modalDay.value = day - 1;
            showFullDate();
            showScheduleOfDay();
    }
    
    if(day == 1){
       var month  = monthValue-1;
       
       changeBackgrondCalendar(month);
       
         axios
        .get('/front/day/' + month)
        .then(response => {
            
            m2.value = month;
            modalDay.value = response.data;
         
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    } 

}


function changeBackgrondCalendar(monthValue){
     const year = document.querySelector('#y').value;
    
     
   
}

// modalDay 뒷 날짜 이동(Day +1)
function backDay() {
     const day = document.querySelector('#modalDay').value;
    const fullDate = document.querySelector('#fullDate').value;
    const monthValue = document.querySelector('#m').value;
    const year = document.querySelector('#y').value;

    
     const last = document.querySelector('#last').value;
    
      if(day != last){
         axios
        .get('/back/day/' + day)
        .then(response => {
            modalDay.value = response.data;
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    }
    
    if(day == last) {
          axios
        .get('/back/day/month/' + monthValue)
        .then(response => {
             modalDay.value =1; 
             m.value = response.data;
             m2.value = response.data;
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    
       }
  
}

function lastDay(){
    const monthValue = document.querySelector('#m').value;
     const last= document.querySelector('#last');
     
        axios
        .get('/lastDay/' + monthValue)
        .then(response => {
            console.log(response.data);
            last.value = response.data;
        })
        .catch(err => { console.log(err) });
    
}


// diaryModal 
function showDiaryFoam() {
    // 사이드바에서 내 일기장 클릭하면 
    document.getElementById("diaryModal").style.display = "block"; // diaryModal 뜨고
    document.getElementById("id01").style.display = "none";  // modalDay close

    const modalDay = document.querySelector('#modalDay').value;
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;
    const dow = document.querySelector('#dow').value;
    const diaryDayDiv = document.querySelector('#diaryDayDiv');

    let str = '';
    str += '<span>' + year + '년 ' + monthValue + '월 ' + modalDay + '일<span>';
    +'';
    diaryDayDiv.innerHTML = str;
}


const diaryBtn = document.querySelector('#diaryBtn');

// 새 일기 등록
diaryBtn.addEventListener('click', e => {

    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;
    const title = document.querySelector('#title').value;
    const diaryContent = document.querySelector('#diaryContent').value;
    const day = document.querySelector('#modalDay').value;

    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        title: title,
        diaryContent: diaryContent
    }

    axios.post('/add/diary', data)
        .then(response => {
            document.getElementById("diaryModal").style.display = "none";
            alert('저장 완료!');
            updateDiaryIcon(response.data);
            location.reload();
        })
        .catch(err => { console.log(err) });

});


// 일기 작성된 날짜에는 다이어리 아이콘 추가 됨
function updateDiaryIcon(diaryId) {

    axios
        .get('/monthDiary/' + diaryId)
        .then(response => {
            console.log('다이어리먼스성공');
        })
        .catch(err => { console.log(err) });
}


function diaryPop(diaryId) {

    axios
        .get('/day/diary/' + diaryId)
        .then(response => {
            showDiary(response.data);

        })
        .catch(err => { console.log(err) });
};

// diaryDetailModal 
function showDiary(diary) {
    
    // 일기 아이콘 클릭하면 diaryDetailModal 뜸 - 글 볼 수 있음
    document.getElementById("diaryDetailModal").style.display = "block";

    let str = '<span>' + diary.year + '년 ' + diary.monthValue + '월 ' + diary.day + '일<span>';
    detailDayDiv.innerHTML = str;
    
    let str1 = '<h4 class="mt-5 border-bottom" style="border-bottom-color: 1px solid #DCDCDC; height: 20px;" name ="title" readonly>' + diary.title + '</h4>'
        + '<div class="mt-2" style="border-color:white; height: 350px;" name="content" readonly>' + diary.content + '</div>'
        + '<div class="w3-cell-row border-bottom" style="border-bottom-color: 1px solid #DCDCDC;"></div>';

    detailDiv.innerHTML = str1;

}



// dDayModal 디데이
function showDDayInput() {
    
    // dayModal 사이드바에서 D-DAY추가 클릭하면 dDayModal 뜸 
    document.getElementById("dDayModal").style.display = "block"; 

    const today = new Date();
    const tYear= today.getFullYear();
    const tMonth= today.getMonth() + 1;
    const tDay= today.getDate();
    
    const todayDiv = document.querySelector('#todayDiv');

    let str = '';  // 모달에 날짜
    str = '<span style="margin-left: 5px;">' + tYear + '년 ' + tMonth + '월 ' + tDay + '일<span>, 오늘로부터';
    todayDiv.innerHTML = str;

}

// 날짜 차이 계산 함수
function subtract(event) {
    const untilDate = document.querySelector('#untilDate').value;
   
    const today = new Date(); 
    const year= today.getFullYear();
    const monthValue = today.getMonth() + 1;
    const day= today.getDate();

    const data ={
        untilDate:untilDate,
        year:year,
        monthValue:monthValue,
        day:day
        
    }

    axios
        .post('/dday/subtract', data)
        .then(response => {
            showSubtract(response.data);
        })
        .catch(err => { console.log(err) });

}

// 날짜 차이 계산해서 모달에 show
function showSubtract(subtract) {
    const subDiv = document.querySelector('#subDiv');
    str = '';
  
      if(subtract < 0){
         str ='<small><span>기준일로부터</span></small><h3> D ' + subtract + '</h3>';
      }
      if(subtract > 0){
         str ='<small><span>기준일로부터</span></small><h3> D +' + subtract + '</h3>';
      }

    subDiv.innerHTML = str;
}

// 디데이 추가 함수
const dDayBtn = document.querySelector('#dDayBtn');

dDayBtn.addEventListener('click', e => {
    const untilDate = document.querySelector('#untilDate').value;
    const name = document.querySelector('#name').value;
    
    console.log(name);


    const data = {
        untilDate: untilDate,
        name: name
    }
    console.log('여기왔냐')
    console.log(data);

    axios
        .post('/dday/add', data)
        .then(response => {
            modalClose();
            location.reload();
            alert('D-DAY 등록되었습니다!');
        })

        .catch(err => { console.log(err) });
})

function modalClose() {
    document.getElementById("dDayModal").style.display = "none";
    document.getElementById('id01').style.display = "none";
 //   document.getElementById('dDayName').value = "";
}





// 날짜 클릭 팝업 안 사이드바 opem
function w3_open() {  
    document.getElementById("mySidebar").style.display = "block";
}

// 날짜 클릭 팝업 안 사이드바 close
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
}


 