package com.example.diary.web;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HomeRestController {
    
    @GetMapping("/now")
    public ResponseEntity<Integer> nowDate(){
        
        LocalDate now = LocalDate.now();
    
        return ResponseEntity.ok(1);
    }

}
