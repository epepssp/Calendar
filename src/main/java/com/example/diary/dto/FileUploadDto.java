package com.example.diary.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadDto {

    private List<MultipartFile> files;
    
}
