package com.example.diary.dto;

import java.util.List;

import com.example.diary.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DayDto {
    private  int day;
    private  int monthValue;
}
