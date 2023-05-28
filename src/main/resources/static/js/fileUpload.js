/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {


});


  // 모달의 업로드 버튼 클릭 이벤트 처리
    document.querySelector('#modalUploadBtn').addEventListener('click', e => {
   
        const formData = new FormData();
        const fileInput = document.querySelector('input[name="files"]');
        console.log(fileInput.files);
        
        /*
        console.log(fileInput.files);
        Array.from(fileInput.files).forEach(f => {
            formData.append('files', f);
        });
        */
       
        for (let file of fileInput.files) {
            console.log(file);
            formData.append('files', file);
        }
          uploadFiles(formData);
    });
    
    function uploadFiles(formData) {
        /*
        axios({
            method: 'post',
            url: '/upload',
            data: formData,
            //headers: { 'Content-Type': 'multipart/form-data' },
            // Request Header의 Content-Type이 자동으로 세팅 되는데?
        })
        */
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
                   
                const htmlStr = `
<div class="card my-2">
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
    
   function removeFileFromServer(event) {
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
    
/*
function clicked() {
      was = true;
}
    
let fileNames = null;    
  
function appendFileNames(names){
      let was = false;  
  
      if(fileNames === null) {
         for (let n of names) {
             fileNames.push('fileNames',n);
         }
      }
      if(fileNames !== null) {
         for (let n of names) {
             fileNames.append('fileNames',n);
        } console.log(fileNames.length);
      } 
      if(was) {
          fileNames = null;
          was =false;
      } 
  */
      
 
  
  


}


  
  