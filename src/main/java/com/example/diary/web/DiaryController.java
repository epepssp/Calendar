package com.example.diary.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.diary.domain.DiaryAttachment;
import com.example.diary.repository.DiaryAttachmentRepository;
import com.example.diary.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DiaryController {
    
    private final DiaryService diaryService;
    private final DiaryAttachmentRepository diaryAttachmentRepository;
    
    @GetMapping("/diary/main")
    public String changeMode(Model model, int monthValue) {
        log.info(" 다이어리 모드로={}");
        
//        model.addAttribute("year", 2023);
//        model.addAttribute("monthValue", monthValue);
           
       return "/diary/main";
    }
    
    
    @GetMapping("/diary/create")
    public void createPage(Model model ,Integer year, Integer monthValue, Integer day) {
        log.info(" 다이어리 크리에이트로???={} : {} : {}", year, monthValue, day);
        
        model.addAttribute("year", year);
        model.addAttribute("monthValue",monthValue);
        model.addAttribute("day", day);
   }
    
    
    @GetMapping("/diary/detail")
    public void detail(Model model,Integer diaryId) {
        log.info(" 다이어리 디태일 아이디 잘 왓냐?={}", diaryId);
        
        List<DiaryAttachment> all = diaryAttachmentRepository.findByDiaryDiaryId(diaryId);
        
        
        log.info("allll={}",all);
        log.info("First!!!={}",all.get(0));
        
        List<DiaryAttachment> left = new ArrayList<>();
        
        for (int i = 1; i < all.size(); i++) {
            left.add(all.get(i));
        }
        log.info("나머지 이미지리스트={}",left);
        
        model.addAttribute("diary", diaryService.read(diaryId));
        model.addAttribute("firstImg", all.get(0));
        model.addAttribute("imgList", left);
    }
    
    @GetMapping("/diary/list")
    public String listPage(Model model) {
        log.info(" 다이어리 모드로={}");

       return "/diary/list";
    }

}
