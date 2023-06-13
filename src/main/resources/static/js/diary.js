/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {

calendarList();

 
});  // 윈도우 


let sort = 1;

  const mini = document.getElementById("mini");
  const listDiv = document.getElementById("listDiv");
 // const sortBtnImg = document.getElementById("sortBtnImg");
   const sorti = document.getElementById("sorti");

  mini.style.display = "block";
  listDiv.style.display = "none";

  sorti.classList.add("far", "fa-list-alt");

function sortTypeChange() {
   if (sort === 1) {
      mini.style.display = "none";
      listDiv.style.display = "block";
      sorti.classList.remove("far", "fa-list-alt");
      sorti.innerHTML = '<i class="material-icons" style="font-size:19px;">grid_on</i>';
    //  sortBtnImg.src = "/icons/table.svg";
      sort = 2; // 1일 경우 2로 변경
   } else {
      mini.style.display = "block";
      listDiv.style.display = "none";
      sorti.innerHTML = "";
      sorti.classList.add("far", "fa-list-alt");
  //    sortBtnImg.src = "/icons/list.svg";
      sort = 1; // 2일 경우 1로 변경
   }

}

 // 버튼 클릭 이벤트 리스너 등록
const sortBtn = document.getElementById("sortBtn");
sortBtn.addEventListener("click", sortTypeChange);



  //이모티콘
    const emojiList = document.querySelectorAll('.emoji');
    const textarea = document.getElementById('diaryContent');
    
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


const diaryCreateBtn = document.querySelector('#diaryCreateBtn');

// 새 일기 등록
diaryCreateBtn.addEventListener('click', e => {

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


// diaryList + paging
window.onload = function() {
    
  const divPaging = document.getElementById('divPaging');
  const pagination = document.getElementById('pagination');
  
  const year = document.querySelector('#year').value;
  const monthValue = document.querySelector('#monthValue').value;

  
function setPage() {
    alert('불럿니!!');
    
     const data ={
         year:year,
         monthValue:monthValue
    }
      
    axios.post('/calendar/setPage', data)
      .then(response => {
        const pageList = response.data;
     
    //    document.getElementById('currentPage').textContent = pageList.pageable.pageNumber + 1;
        renderItems(pageList);
        renderPagination(pageList);
      })
      .catch(err => {
        console.log(err);
      });
  }
  
  setPage();
  
 

// 페이징 버튼  
function renderPagination(pageList){
    console.log('dlfjgrp ehlsd');
    console.log(pageList.page);
    
  //  pagination.innerHTML = '';

    // 현재 페이지 번호와 전체 페이지 수 가져오기
    const currentPage = pageList.pageable.pageNumber + 1;
    const totalPages = pageList.totalPages;
    
    console.log('현재페이지');
    console.log(currentPage);
     console.log(totalPages);
 
 
const maxPagesToShow = 3; // 최대로 출력할 페이지 수
const startPage = Math.max(currentPage - Math.floor(maxPagesToShow / 2), 1);
const endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);
    
 let str ='';
 
  str +='<a href="#">Prev</a>';
  for (let i = startPage; i <= endPage; i++) {
     str +='<a href="#" onclick="setPageNumber(event, ' + (i - 1) + ');">'+i+'</a>';
  }
  str +='<a href="#" >Next</a>';

    pagination.innerHTML = str;
    

   
} // renderPagination 끝




function renderItems(pageList) {
  divPaging.innerHTML = '';
  console.log(pageList);
    console.log(pageList.content);
  
   pageList.content.forEach(item => {
    const listItem = document.createElement('div');
    listItem.textContent = item.title;
    divPaging.appendChild(listItem);
  });
}


function diaryList(){
    const year = document.querySelector('#year').value;
    const monthValue = document.querySelector('#monthValue').value;
    
     const data ={
         year:year,
         monthValue:monthValue
    }
    
      axios.post('/calendar/miniList', data)
        .then(response => {
            showMiniList(response.data);
        //    pagingList(response.data);
            })
        .catch(err => { console.log(err) });
}

  diaryList();
  
};


/*
 // 현재 페이지 번호
  var tempPage = 1;

function renderPagination() {
    var paginationDiv = document.getElementById('pagination');
    paginationDiv.innerHTML = '현재 페이지: ' + tempPage;
  }

  // 초기 페이지 번호 표시
  renderPagination();
  
  

  // 다음 페이지로 이동
  const nextBtn = document.querySelector('#nextBtn');
  document.getElementById('nextBtn').addEventListener('click', nextPage);
  function nextPage() {
    // Ajax 요청을 통해 다음 페이지 데이터 가져오기
    // 필요한 처리 및 렌더링 작업 수행
    alert('넥스트!!!!!');
  }
*/  
  




function showMiniList(data){

    const listDiv = document.querySelector('#listDiv');
    let str='';
    
    str +='<div style="border-top: 1px solid gray; margin-left: 10px; width: 266px; border-bottom: 1px solid gray;"><div style="border-top: 1px solid #DCDCDC;" >';
    
    for(let x of data){
        str +='<div style="border-bottom: 1px solid #DCDCDC;" class="w3-cell-row p-1">'
             +'<div class="w3-container w3-cell"><div style="color:#A9A9A9; font-size: 9px;">'+x.year+'.'+x.monthValue+'.'+x.day+' 일기</div>'
             +`<a style="text-decoration: none;" href="/diary/detail?diaryId=${ x.diaryId }">`
             +'<span style="color:#FF6347;">⦁</span> '+x.title+'</a></div>'
             +'<div class="w3-container w3-cell" style="width:38px;">'
             +'<div class="image-container"><img class="rounded" width="35px;" height="42px;" src="/api/view/'+x.uuid + '_' + x.fileName +'" /><span class="caption">'+x.totalAttachments+'</span></div></div></div>';
    }
    
    str +='</div></div>';
    
    listDiv.innerHTML=str;
    
}

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
    str1 +='<div class="row line" style="border-top: 1px solid gray;">';
    for (let i = 0; i < 7; i++) {
        if(data.d1[i].diaryId != 0) {
            str1 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d1[i].diaryId }">`
                +'<small>'+ data.d1[i].day+'</small></a></div>';
        }
         if(data.d1[i].diaryId == 0 && data.d1[i].day == 0) {
            str1 +='<div class="box" style="color: white;"><small>'+ data.d1[i].day+'</small></div>';
        }
         if(data.d1[i].diaryId == 0 && data.d1[i].day != 0) {
             str1 += `<div class="box" day="${data.d1[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                 +'<small>'+ data.d1[i].day+'</small></div>';
        }
    } 
    str1 +='</div>';
    
    
    let str2='';
    str2 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d2[i].diaryId != 0) {
            str2 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d2[i].diaryId }">`
                +'<small>'+ data.d2[i].day+'</small></a></div>';
        }
         if(data.d2[i].diaryId == 0) {
            str2 += `<div class="box" day="${data.d2[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                 +'<small>'+ data.d2[i].day+'</small></div>';
        }
    } 
    str2+='</div>';
    
    
    let str3='';
    str3 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d3[i].diaryId != 0) {
            str3 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d3[i].diaryId }">`
                +'<small>'+ data.d3[i].day+'</small></a></div>';
        }
         if(data.d3[i].diaryId == 0) {
            str3 += `<div class="box" day="${data.d3[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                 +'<small>'+ data.d3[i].day+'</small></div>';
        }
    } 
    str3+='</div>';
  
   
    let str4='';
    str4 +='<div class="row line">';
    for (let i = 0; i < 7; i++) {
        if(data.d4[i].diaryId != 0) {
            str4 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d4[i].diaryId }">`
                 +'<small>'+ data.d4[i].day+'</small></a></div>';
        }
         if(data.d4[i].diaryId == 0) {
             str4 += `<div class="box" day="${data.d4[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                 +'<small>'+ data.d4[i].day+'</small></div>';
        }
    } 
    str4 +='</div>';
   
    
    let str5='';
    if(data.d6){
         str5 +='<div class="row line">';
    }
    if(!data.d6){
         str5 +='<div class="row line" style="border-bottom: 1px solid gray;">';
    } 
         
 
    for (let i = 0; i < 7; i++) {
        if(data.d5[i].diaryId != 0) {
           str5 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d5[i].diaryId }">`
                +'<small>'+ data.d5[i].day+'</small></a></div>';
        }
         if(data.d5[i].diaryId == 0 && data.d5[i].day == 0) {
            str5 +='<div class="box" style="color: white;"><small>'+ data.d5[i].day+'</small></div>';
        }
         if(data.d5[i].diaryId == 0 && data.d5[i].day != 0) {
             str5 += `<div class="box" day="${data.d5[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                 +'<small>'+ data.d5[i].day+'</small></div>';
        }
    } 
    str5 +='</div>';
    
    
    let str6 ='';
    if(data.d6){
       str6 +='<div class="row line" style="border-bottom: 1px solid gray;">';
       for (let i = 0; i < 7; i++) {
           if(data.d6[i].diaryId != 0) {
               str6 +=`<div class="box" style="background-color:#eaeafb;"><a style="text-decoration: none;" href="/diary/detail?diaryId=${data.d6[i].diaryId }">`
                    +'<small>'+ data.d6[i].day+'</small></a></div>';
           }
           if(data.d6[i].diaryId == 0 && data.d6[i].day == 0) {
               str6 +='<div class="box" style="color: white;"><small>'+ data.d6[i].day+'</small></div>';
           }
           if(data.d6[i].diaryId == 0 && data.d6[i].day != 0) {
               str6 += `<div class="box" day="${data.d6[i].day}" onclick="createDiary(this.getAttribute(\'day\'));">`
                    +'<small>'+ data.d6[i].day+'</small></div>';
           }
       } 
       str6 +='</div>';
    }
      
    mini.innerHTML = str1 + str2 + str3 + str4 + str5 + str6;
   }


 function createDiary(day){
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
 