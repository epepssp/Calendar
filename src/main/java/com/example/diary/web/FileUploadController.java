package com.example.diary.web;

import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.diary.dto.FileUploadDto;
import com.example.diary.dto.FileUploadResultDto;



import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@RestController
public class FileUploadController {

    @Value("${com.example.upload.path}")
    private String uploadPath;
    
    @PostMapping("/upload")
    public ResponseEntity<List<FileUploadResultDto>> upload(FileUploadDto dto) {
        log.info("uploadFile디티오 잘 옴?(dto={})", dto);
        
        List<MultipartFile> files = dto.getFiles();
        if (files == null) {
            return ResponseEntity.noContent().build();
        }
        
        List<FileUploadResultDto> list = new ArrayList<>();
        files.forEach(mutipartFile -> {
//            log.info(mutipartFile.getOriginalFilename());
//            log.info(mutipartFile.getContentType());
//            log.info("size = {}", mutipartFile.getSize());
             FileUploadResultDto result = saveFile(mutipartFile);
             
             log.info("result???={}",result);
             list.add(result);
        });
        
        
        log.info("파럽리티디오list.add(result)={}",list);
        return ResponseEntity.ok(list);
    }
    
    
    private FileUploadResultDto saveFile(MultipartFile file) {
        FileUploadResultDto result = null;
        
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        boolean image = false;
        String target = uuid + "_" + originalName;
        log.info("타겟??={}", target);
  
  
//        Path path = Paths.get(uploadPath, target);
        File dest = new File(uploadPath, target);
      
            try {
                file.transferTo(dest); 
                
                if (file.getContentType().startsWith("image")) {
                    image = true;
                    String thumbnailTarget = "s_" + target;
                    File thumbnailDest = new File(uploadPath, thumbnailTarget);
                    Thumbnailator.createThumbnail(dest, thumbnailDest, 200, 200);
                }
                
                result = FileUploadResultDto.builder()
                    .uuid(uuid)
                    .fileName(originalName)
                    .image(image)
                    .build();
            } catch (IllegalStateException | IOException e) {
              
                e.printStackTrace();
            }
      
       return result;
    }
    
    @GetMapping("/api/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        log.info("viewFile(fileName={})", fileName);
        
        File file = new File(uploadPath, fileName);
        
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            log.error("{} : {}", e.getCause(), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);
        
        Resource resource = new FileSystemResource(file);
        
        return ResponseEntity.ok().headers(headers).body(resource);
    }
    
    @DeleteMapping("/remove/{fileName}")
    public ResponseEntity<Map<String, Boolean>> removeFile(@PathVariable String fileName) {
        log.info("removeFile(fileName={})", fileName);
        
        Map<String, Boolean> result = new HashMap<>();
        
        File file = new File(uploadPath, fileName);
        log.info("file={}", file);
        
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            log.error("{} : {}", e.getCause(), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        
        boolean removed = file.delete();
        log.info("removed={}", removed);
        
//        if (contentType.startsWith("image")) {
//            File thumbnailFile = new File(uploadPath, "s_" + fileName);
//            log.info("thumbnailFile={}", thumbnailFile);
//            
//            removed = thumbnailFile.delete();
//            log.info("removed={}", removed);
//        }
        
        result.put("result", removed);
        
        return ResponseEntity.ok(result);
    }
    
}
