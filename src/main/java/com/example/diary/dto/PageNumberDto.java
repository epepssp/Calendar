package com.example.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter

@AllArgsConstructor
@NoArgsConstructor

public class PageNumberDto {

   private int pageNumber;
    
   
   
    public int getPageNumber() {
        return pageNumber;
    }
    
//    public void setPageNumber(int pageNumber) {
//        this.pageNumber = pageNumber;
//    }

}
