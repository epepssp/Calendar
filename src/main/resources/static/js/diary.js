/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {
dateDiv();
calendarList();

});

function fileDivOpen(){
    
}


let weather = null; // 초기에는 값이 없는 상태

function svgClick(n) {

  if (weather === null) {  
    weather = n; // 변수 weather에 새로운 값 저장\
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

// 버튼 클릭 이벤트 리스너 등록
const btn1 = document.getElementById("svg1");
const btn2 = document.getElementById("svg2");
const btn3 = document.getElementById("svg3");
const btn4 = document.getElementById("svg4");
const btn5 = document.getElementById("svg5");

btn1.addEventListener("click", function() {
  handleClick(btn1, 1);
});

btn2.addEventListener("click", function() {
  handleClick(btn2, 2);
});

btn3.addEventListener("click", function() {
  handleClick(btn3, 3);
});

btn4.addEventListener("click", function() {
  handleClick(btn4, 4);
});

btn5.addEventListener("click", function() {
  handleClick(btn5, 5);
});

/*
    document.querySelector('#diaryCreateBtn').addEventListener('click', submitForm);
    
    function submitForm(event) {
        event.preventDefault();
        
        const year = document.querySelector('#year').value;
        const monthValue = document.querySelector('#monthValue').value;
        const day = document.querySelector('#day').value;
        
        const weather = document.querySelector('#weather').value;
        
        const title = document.querySelector('#title').value;
        const content = document.querySelector('#diaryContent').value;
        if (title == '' || content == '') {
            alert('제목과 내용은 반드시 입력...');
            return;
        }
        
        const uploads = document.querySelector('#uploads');
        const files = uploadResults.querySelectorAll('img');
        let htmlStr = '';
        files.forEach(x => {
            const imgLink = x.getAttribute('data-src');
            //console.log(imgLink);
            htmlStr += `<input type="hidden" name="fileNames" value="${imgLink}" />`;
        });
        uploads.innerHTML = htmlStr;
        
        document.querySelector('#postForm').submit();
        
    }

*/

const diaryCreateBtn = document.querySelector('#diaryCreateBtn');

// 새 일기 등록
diaryCreateBtn.addEventListener('click', e => {
    alert('d여긴??');

    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    const day = document.querySelector('#day').value;
    
    const weather = document.querySelector('#weather').value;
    
    const title = document.querySelector('#title').value;
    const diaryContent = document.querySelector('#diaryContent').value;
  
    const uploads = document.querySelector('#uploads');
    const files = uploadResults.querySelectorAll('img');
    let htmlStr = '';
     files.forEach(x => {
        const imgLink = x.getAttribute('data-src');
        //console.log(imgLink);
        htmlStr += `<input type="hidden" name="fileNames" value="${imgLink}" />`;
     });
     uploads.innerHTML = htmlStr;
  
   const elements = uploads.querySelectorAll('input[name="fileNames"]');
   const fileNames = Array.from(elements).map(input => input.value);
    
  //  console.log(fileNames);

    const data = {
        year: year,
        monthValue: monthValue,
        day: day,
        title: title,
        diaryContent: diaryContent,
        weather: weather,
        fileNames: fileNames
    }

    axios.post('/add/diary', data)
        .then(response => {
            alert('저장 완료!');
          
            location.reload();
        })
        .catch(err => { console.log(err) });

});

function calendarList(){
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    
    const data ={
        year:year,
        monthValue:monthValue
    }
    
    axios.post('/calendar/mini', data)
        .then(response => {
            
            showMini(response.data);
         
        })
        .catch(err => { console.log(err) });
}

function showMini(data){
   const mini = document.querySelector('#mini');
   
  
   let str1='';
   
    str1 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d1[i].diaryId != 0) {
            str1 +=`<div class="box" diaryId="${data.d1[i].diaryId}" onclick="diaryPop(this.getAttribute(\'diaryId\'));" id="diaryId" style="background-color:#eaeafb;">`
                +'<small>'+ data.d1[i].day+'</small></div>';
        }
         if(data.d1[i].diaryId == 0 && data.d1[i].day == 0) {
            str1 +='<div class="box" style="color: white;"><small>'+ data.d1[i].day+'</small></div>';
        }
         if(data.d1[i].diaryId == 0 && data.d1[i].day != 0) {
            str1 +='<div class="box"><small>'+ data.d1[i].day+'</small></div>';
        }
   } 
    str1 +='</div>';
    
    
     let str2='';
   
    str2 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d2[i].diaryId != 0) {
            str2 +=`<div class="box" diaryId="${data.d2[i].diaryId}" onclick="diaryPop(this.getAttribute(\'diaryId\'));" id="diaryId" style="background-color:#eaeafb;">`
                +'<small>'+ data.d2[i].day+'</small></div>';
        }
         if(data.d2[i].diaryId == 0) {
            str2 +='<div class="box"><small>'+ data.d2[i].day+'</small></div>';
        }
   } 
    str2+='</div>';
    
    
    let str3='';
   str3 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d3[i].diaryId != 0) {
            str3 +=`<div class="box" diaryId="${data.d3[i].diaryId}" onclick="diaryPop(this.getAttribute(\'diaryId\'));" id="diaryId" style="background-color:#eaeafb;">`
                +'<small>'+ data.d3[i].day+'</small></div>';
        }
         if(data.d3[i].diaryId == 0) {
            str3 +='<div class="box"><small>'+ data.d3[i].day+'</small></div>';
        }
   } 
    str3+='</div>';
  
   
    

   let str4='';
   
    str4 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d4[i].diaryId != 0) {
            str4 +=`<div class="box" diaryId="${data.d4[i].diaryId}" onclick="diaryPop(this.getAttribute(\'diaryId\'));" id="diaryId" style="background-color:#eaeafb;">`
                 +'<small>'+ data.d4[i].day+'</small></div>';
        }
         if(data.d4[i].diaryId == 0) {
            str4 +='<div class="box"><small> '+ data.d4[i].day+'</small></div>';
        }
   } 
    str4 +='</div>';
    
     let str5='';
   
    str5 +='<div class="row line" style=" border-bottom: 1px solid gray;">';
    for (let i = 0; i < 7; i++) {
        if(data.d5[i].diaryId != 0) {
           str5 +=`<div class="box" diaryId="${data.d5[i].diaryId}" onclick="diaryPop(this.getAttribute(\'diaryId\'));" id="diaryId" style="background-color:#eaeafb;">`
                +'<small>'+ data.d5[i].day+'</small></div>';
        }
         if(data.d5[i].diaryId == 0 && data.d5[i].day == 0) {
            str5 +='<div class="box" style="color: white;"><small>'+ data.d5[i].day+'</small></div>';
        }
         if(data.d5[i].diaryId == 0 && data.d5[i].day != 0) {
            str5 +='<div class="box"><small>'+ data.d5[i].day+'</small></div>';
        }
   } 
    str5 +='</div>';
      
    mini.innerHTML = str1 + str2 + str3 + str4 + str5;
   }


function dateDiv(){
  
    const day = document.querySelector('#day').value;
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    
const ms = document.querySelector('#ms');
let m = '<div align="right" class="row m-1 fw-bold"><span>'+monthValue+'월의 일기</span></div>';
ms.innerHTML = m;

 const dateDiv = document.querySelector('#dateDiv');
  let str = '<span class="hand1 m-1">'+ year +'</span>년 <span class="hand1 m-1">'+ monthValue+'</span>월 <span class="hand1 m-1">'+ day +'</span>일';
  dateDiv.innerHTML = str;
  }
 
 