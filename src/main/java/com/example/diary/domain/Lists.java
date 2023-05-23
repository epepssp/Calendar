package com.example.diary.domain;

import java.util.List;

import com.example.diary.dto.DayDiaryDto;

import lombok.ToString;

@ToString
public class Lists {

    private  List<DayDiaryDto>  d1;
    private  List<DayDiaryDto>  d2;
    private  List<DayDiaryDto>  d3;
    private  List<DayDiaryDto>  d4;
    private  List<DayDiaryDto>  d5;
    private  List<DayDiaryDto>  d6;
    
    public Lists(List<DayDiaryDto> d1,List<DayDiaryDto> d2,List<DayDiaryDto> d3,
            List<DayDiaryDto> d4,List<DayDiaryDto> d5,List<DayDiaryDto> d6) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.d5 = d5;
        
        if(d6 == null) {
            
        }
 if(d6 != null) {
     this.d6 = d6;
        }
       
    }
    
}
