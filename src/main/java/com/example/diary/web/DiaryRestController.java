package com.example.diary.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.Diary;
import com.example.diary.dto.DiaryCreateDto;
import com.example.diary.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryRestController {

    private final DiaryService diaryService;
    
    @PostMapping("/add/diary")
    public ResponseEntity<Integer> addNewDiary(@RequestBody DiaryCreateDto dto){
        log.info("다이어리 디티어ㅗ 잘 왔냐?={}", dto);
        
       Integer diaryId = diaryService.create(dto);
        
        return ResponseEntity.ok(diaryId);
    }
    
    @GetMapping("/monthDairy/{diaryId}")
    public ResponseEntity<Integer> showIcons(@PathVariable Integer diaryId){
        
        return ResponseEntity.ok(diaryId);
    }
    
    @GetMapping("/day/diary/{diaryId}")
    public ResponseEntity<Diary> detailDiary(@PathVariable Integer diaryId){
        log.info("다이어flIDIDIDI 잘 왔냐?={}", diaryId);
        
        Diary diary = diaryService.read(diaryId);
        
        return ResponseEntity.ok(diary);
       
    }
    
    @GetMapping("/select/weather/{i}")
    public  ResponseEntity<Integer> selectW(@PathVariable int i){
        log.info("설렉 웨더!!!?={}", i);
        
        return ResponseEntity.ok(1);
    }
     
}
