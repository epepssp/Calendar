/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {

 let str ='';
 
  str +='<a href="#">Prev</a>';
 
  for (let i = 1; i <= totalPages; i++) {
     
      str +='<a href="#">'+i+'</a>';
      
   }
     str +='<a href="#" >Next</a>';

});


   // 페이지 번호 버튼 생성
   const prevButton = document.createElement('a');
prevButton.href = '#';
prevButton.classList.add('page-link');
prevButton.textContent = 'Prev';
pagination.appendChild(prevButton);
   
    for (let i = 1; i <= totalPages; i++) {
      const pageButton = document.createElement('a');
      pageButton.textContent = i;
      pageButton.classList.add('page-link');
      if (i === currentPage) {
        pageButton.disabled = true; // 현재 페이지는 비활성화
      }
      pageButton.addEventListener('click', () => {
        setPage(i);
      });
      pagination.appendChild(pageButton);
    }
    
    // Next 버튼 생성
const nextButton = document.createElement('a');
nextButton.href = '#';
nextButton.classList.add('page-link');
nextButton.textContent = 'Next';
pagination.appendChild(nextButton);