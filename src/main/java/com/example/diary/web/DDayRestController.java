package com.example.diary.web;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.DDay;
import com.example.diary.service.DDayService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController 
@RequiredArgsConstructor
public class DDayRestController {
    
    private final DDayService dDayService;

    @PostMapping("/dday/subtract")
    public ResponseEntity<Integer> dDaySubtract(@RequestBody DDay entity){
        log.info("잘받았냐 언틸 프롬날짜={}", entity);
        
        LocalDate utDate = entity.getUntilDate();
        LocalDate frDate = LocalDate.of(entity.getYear(), entity.getMonthValue(),entity.getDay());
        
        long daysSubtract = ChronoUnit.DAYS.between(utDate,frDate);
        int subtract = (int) daysSubtract;       
        log.info("daysSub={}>>>>정수로바꾼",subtract);
        
        return ResponseEntity.ok(subtract);
    }
    
    @PostMapping("/dday/add")
    public ResponseEntity<Integer> addNewDDay(@RequestBody DDay entity){
        log.info("새ㅜㄷ데디프롬날짜={}", entity);
        
        LocalDate utDate = entity.getUntilDate();
        LocalDate frDate = LocalDate.of(entity.getYear(), entity.getMonthValue(),entity.getDay());
    
        int year= Integer.parseInt((utDate.toString().substring(0, 4)));
        int monthValue= Integer.parseInt((utDate.toString().substring(5, 7)));
        int day =Integer.parseInt((utDate.toString().substring(8)));
        
        long daysSubtract = ChronoUnit.DAYS.between(utDate,frDate);
        int subtract = (int) daysSubtract;       
     
        DDay dday = DDay.builder().untilDate(utDate).fromDate(frDate).subtract(subtract).dDayName(entity.getDDayName()).year(year).monthValue(monthValue).day(day).build();
        Integer dDayId = dDayService.create(dday);
        
        return ResponseEntity.ok(subtract);
    }
}

