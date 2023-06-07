package com.example.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSetDto {
    
    private int year;
    private int monthValue;
    private int page;
}
