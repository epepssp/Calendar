/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {
   
});

// modalDay 
function dayPop(day) {
    
    // 날짜 클릭하면 modalDay 뜸 
    document.getElementById('id01').style.display = 'block';
    const modalDay = document.querySelector('#modalDay');

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
    const monthValue = document.querySelector('#m').value;
    const day = document.querySelector('#modalDay').value;
    const full = document.querySelector('#fullDate');
    fullDate = year + monthValue + day;
    full.value = fullDate;

}


// Day 일정 리스트 요청
function showScheduleOfDay() {
    
    const fullDate = document.querySelector('#fullDate').value;

    axios
        .get('/show/schedule/day/' + fullDate)
        .then(response => {
            console.log('쇼스케쥴 성공');
            showList(response.data);
        })
        .catch(err => { console.log(err) });

}

// Day 일정 리스트 show 
function showList(data) {

    const sListDiv = document.querySelector('#sListDiv');
    str = '';
    for (let x of data) {

        str += '<div class="effectT"><small>⦁ ' + x.content + '</small></div>';
    }
    sListDiv.innerHTML = str;
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
    
    const data = {
        year : year,
        monthValue : monthValue,
        day: day-1
    }
    
    if(day != 1){
            modalDay.value = day - 1;
            showFullDate();
            showScheduleOfDay();
    }
    
    if(day == 1){
       var month  = monthValue-1;
       
         axios
        .get('/front/day/' + month)
        .then(response => {
            modalDay.value = response.data;
            m.value = monthValue-1;
            showFullDate();
            showScheduleOfDay();
        })
        .catch(err => { console.log(err) });
    } 

}


// modalDay 뒷 날짜 이동(Day +1)
function backDay() {
    const day = document.querySelector('#modalDay').value;

    axios
        .get('/back/day/' + day)
        .then(response => {
            modalDay.value = response.data;
            showFullDate();
            showScheduleOfDay();
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

    const modalDay = document.querySelector('#modalDay').value;
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;
    const todayDiv = document.querySelector('#todayDiv');

    let str = '';  // 모달에 날짜
    str = '<span style="margin-left: 5px;">' + year + '년 ' + monthValue + '월 ' + modalDay + '일<span>부터';
    todayDiv.innerHTML = str;

}

// 날짜 차이 계산 함수
function subtract(event) {
    const untilDate = document.querySelector('#untilDate').value;

    const day = document.querySelector('#modalDay').value;
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;

    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        untilDate: untilDate
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
    str = '<small><span>기준일로부터</span></small>'
        + '<h3> D ' + subtract + '</h3>';

    subDiv.innerHTML = str;
}

// 디데이 추가 함수
const dDayBtn = document.querySelector('#dDayBtn');

dDayBtn.addEventListener('click', e => {
    const untilDate = document.querySelector('#untilDate').value;
    const dDayName = document.querySelector('#dDayName').value;
    const day = document.querySelector('#modalDay').value;
    const year = document.querySelector('#y').value;
    const monthValue = document.querySelector('#m').value;

    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        untilDate: untilDate,
        dDayName: dDayName
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
    document.getElementById('dDayName').value = "";
}





// 날짜 클릭 팝업 안 사이드바 opem
function w3_open() {  
    document.getElementById("mySidebar").style.display = "block";
}

// 날짜 클릭 팝업 안 사이드바 close
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
}


 