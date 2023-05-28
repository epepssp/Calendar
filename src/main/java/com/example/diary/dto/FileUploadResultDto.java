package com.example.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileUploadResultDto {

    
    private String uuid;
    private String fileName;
    private boolean image;
  
    
    public String getLink() {
        if (image) {
            return "s_" + uuid + "_" + fileName;            
        } else {
            return uuid + "_" + fileName;
        }
    }
    
}
