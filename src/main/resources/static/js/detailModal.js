/**
 * 
 */  // diary detail modal

window.addEventListener('DOMContentLoaded', () => {
    
});


function diaryPop(diaryId) {
    alert(diaryId);
    
    axios
      .get('/day/diary/', { params: {diaryId:diaryId}})
        .then(response => {
          console.log('tjdrhd');
      })
        .catch(err => { console.log(err) });
};


function showDiary(diary){

  // detail 모달 내용 채우기
  const divDetailModal = document.querySelector('#divDetailModal');

  str ='';

  str +='<div id="diaryDetailModal" class="w3-modal">'
    +'<div class="w3-modal-content" style="width: 480px; height: 580px;">'
    +'<span onclick="document.getElementById(\'diaryDetailModal\').style.display=\'none\'" class="w3-button w3-display-topright">&times;</span><br><br>'
      +'<div class="w3-container m-3">'
        +'<div class="w3-cell-row mb-3" align="left">'
        +'<span class="w3-container w3-cell" style="width: 150px;">' +diary.year+'년 ' +diary.monthValue+'월 '+diary.day+'일</span>'
          
        +`<div class="w3-container w3-cell"><input id="weather" type="hidden" value="${diary.weather}">`;
        
     if(diary.weather ==1) {
       str +='날씨: <img width="22px;" style="margin-left: 7px;" id="svg1" src="/icons/1.svg">';
     } if(diary.weather ==2) {
       str +='날씨: <img width="22px;" style="margin-left: 7px;" id="svg1" src="/icons/2.svg">';
     } if(diary.weather ==3) {
       str +='날씨: <img width="22px;" style="margin-left: 7px;" id="svg1" src="/icons/3.svg">';
     } if(diary.weather ==4) {
       str +='날씨: <img width="22px;" style="margin-left: 7px;" id="svg1" src="/icons/4.svg">';
     } if(diary.weather ==5) {
       str +='날씨: <img width="22px;" style="margin-left: 7px;" id="svg1" src="/icons/5.svg">';
     } if(diary.weather ==0) {
       str +='날씨: X';
     }
     
  str +='</div></div>'
       
        +'<div class="w3-container w3-cell">'
          +'<div class="mt-2 mb-1 border-bottom fw-bold" style="border-bottom-color: 3px solid #DCDCDC; height: 25px;" name ="title" readonly><span>' + diary.title + '</span></div>'
          +'<div class="mt-2" style="border-color:black; width:390px; height: 350px;" name="content" readonly><small>' + diary.content + '</small></div>'
        +'</div>'
    +'</div></div></div>';
    
  divDetailModal.innerHTML = str;   
  
  // detail 모달 띄우기
  document.getElementById("diaryDetailModal").style.display = "block";
    
}    