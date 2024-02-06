package com.example.diary.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.example.diary.domain.Diary;
import com.example.diary.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiaryCreateDto {
    
    private int monthValue;
    private int year;
    private int day;
    private int weather;
    private String title;
    private String diaryContent;
    
   private List<String> fileNames;
    
    public Diary toEntity() {
        Diary diary = Diary.builder()
                .title(title).content(diaryContent).year(year).monthValue(monthValue).day(day)
                .weather(weather).build();
        
        if (fileNames != null) {
            fileNames.forEach(fileName -> {
//                String[] names = fileName.split("_");
                String uuid = fileName.split("_")[0];
                // 실제 파일 이름에 "_"가 포함된 경우가 있기 때문에 
                // names[1]이 실제 파일 이름의 전체가 아니라 일부만일 수도 있다!
                String fName = fileName.substring(uuid.length() + 1);
                diary.addAttachment(uuid, fName);
            });
        }
        
        return diary;
    }
}
