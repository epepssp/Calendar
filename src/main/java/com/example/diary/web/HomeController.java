package com.example.diary.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {  
        
        LocalDate date = LocalDate.now();
        log.info("나나나우={}",date);
         return "home";
                
    }
    
    @GetMapping("/layout")
    public String  layout() {  
        
        LocalDate date = LocalDate.now();
        log.info("나나나우={}",date);
   
         return "layout";
       
    }
    
    @GetMapping("/test")
    public String  testPage() {  
        
     
         return "test";
       
    }
  
    
}