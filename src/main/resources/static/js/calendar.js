/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {
   now();
 //  lastDay();
});



function now(){
     const today = new Date();
 
     const tYear= today.getFullYear()
     const tMonth= today.getMonth() + 1;
     const tDay= today.getDate();
     
     const hDiv = document.querySelector('#hDiv');
     str ='';
     
     str +='<span style="font-size:13px; margin-left:10px;"><span style="color:#ff8080;">t </span><span style="color:#FFFF00;">o </span><span style="color:#d2ff4d;">d </span>'
          +'<span style="color:#4dffff;">a </span><span style="color:#cca1f7;">y</span></span>  '
          +'<small class="ale" style="font-size:15px; color:white"><span id="tYear">"'+tYear+'</span>.<span id="tMonth">'+tMonth+'</span>.<span id="tDay">'+tDay+'"</span></small>';
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
        if(x.subtract < 0) {
           str +='<div style="color: #FF6347;"><small>D '+x.subtract+'</small></span> <span><small>'+x.name+'</small></div>';
        } 
        if(x.subtract > 0) {
           str +='<div style="color: #4169E1;"><small><img width="11px;" src="/icons/2hh.svg">+'+x.subtract+'</small></span> <span><small>'+x.name+'</small></div>';
        }
        
     } 
     dDayDiv.innerHTML = str;
    
}


// modalDay 
function dayPop(day) {
   
    // 날짜 클릭하면 modalDay 뜸 
    document.getElementById('id01').style.display = 'block';
    
    const monthValue = document.querySelector('#monthValue').value;
    const mon = document.querySelector('#m2');
    const d = document.querySelector('#day');
    
    mon.value = monthValue;
    d.value =day;
    
    showFullDate();
    showScheduleOfDay();  // Day의 일정 리스트
   
};


function showFullDate() {
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#m2').value;
    const day = document.querySelector('#day').value;
    const full = document.querySelector('#fullDate');
    fullDate = year + monthValue + day;
    full.value = fullDate;

}


// Day 일정 리스트 요청
function showScheduleOfDay() {
    
lastDay();
    
    const fullDate = document.querySelector('#fullDate').value;
    const monthValue = document.querySelector('#m2').value;
    const day = document.querySelector('#day').value;
    
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
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    const day = document.querySelector('#day').value;
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
    
    const d = document.querySelector('#day').value;
    const monthValue = document.querySelector('#m2').value;
    const year = document.querySelector('#year').value;
   
    if(d != 1){
            day.value = d - 1;
            showFullDate();
            showScheduleOfDay();
    }
    
    if(d == 1){
       var month  = monthValue-1;
 
         axios
        .get('/front/day/' + month)
        .then(response => {
            m2.value = monthValue-1;
            day.value = response.data;
           showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    } 

}


function lastDay(){
    const monthValue = document.querySelector('#m2').value;
    const l = document.querySelector('#last');
    
    axios
        .get('/lastDay/' + monthValue)
        .then(response => {
            last.value = response.data;
        })
        .catch(err => { console.log(err) });
}


// modalDay 뒷 날짜 이동(Day +1)
function backDay() {
    const d = document.querySelector('#day').value;
    const monthValue = document.querySelector('#m2').value;  // 모달의 month id =m2
    const year = document.querySelector('#year').value;
    const l = document.querySelector('#last').value;
    
     console.log(monthValue);
    console.log(d);
    
    
    axios
        .get('/lastDay/' + monthValue)
        .then(response => {
            last.value = response.data;
        })
        .catch(err => { console.log(err) });
    
   
    if(d != l){
         axios
        .get('/back/day/' + d)
        .then(response => {
            day.value = response.data;
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    }
    
    if(d == l) {
          axios
        .get('/back/day/month/' + monthValue)
        .then(response => {
             day.value =1; 
             m2.value = response.data;
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    
       }
  
}



// diaryModal 
function showDiaryFoam() {  // 1보류
    // 사이드바에서 내 일기장 클릭하면 
    document.getElementById("diaryModal").style.display = "block"; // diaryModal 뜨고
    document.getElementById("id01").style.display = "none";  // modalDay close

    const day = document.querySelector('#day').value;
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;

    const diaryDayDiv = document.querySelector('#diaryDayDiv');

    let str = '';
    str += '<span>' + year + '년 ' + monthValue + '월 ' + day + '일<span>';
    +'';
    diaryDayDiv.innerHTML = str;
}


function dateInfo(){   // 내 일기장 클릭시 diary create로 이동
    const day = document.querySelector('#day').value;
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    
    axios.get('/diary/create', {
        params: {
             year: year,
             monthValue: monthValue,
             day:day
         }
        })
  .then(response => {
     window.location.href = '/diary/create?year=' + encodeURIComponent(year) + '&monthValue=' + encodeURIComponent(monthValue) + '&day=' + encodeURIComponent(day);
  })
  .catch(error => {
    console.log(err);
  });
    
    
}


const diaryBtn = document.querySelector('#diaryBtn');

// 새 일기 등록
diaryBtn.addEventListener('click', e => {

    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    const title = document.querySelector('#title').value;
    const diaryContent = document.querySelector('#diaryContent').value;
    const day = document.querySelector('#day').value;
    const weather = document.querySelector('#weather').value;

    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        title: title,
        diaryContent: diaryContent,
        weather: weather
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


 