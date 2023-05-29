package com.example.diary.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class MiniDiaryDto {
    
    private Integer diaryId;  
    
    private int year;
    private int monthValue;
    private int day;
    private int weather;
    
    private String title;
    
    private String uuid;
    private String fileName;
    
    
    
}
