package com.example.diary.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DiaryController {
    
    @GetMapping("/diary/main")
    public String changeMode(Model model,int monthValue) {
        log.info(" 다이어리 모드로={}", monthValue);
        
        model.addAttribute("year", 2023);
        model.addAttribute("monthValue", monthValue);
        
        
       return "/diary/main";
    }

}
