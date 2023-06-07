package com.example.diary.service;

import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.diary.domain.Diary;
import com.example.diary.dto.DiaryCreateDto;
import com.example.diary.dto.MiniDiaryDto;
import com.example.diary.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    
    public Integer create(DiaryCreateDto dto) {
      
       Diary d = diaryRepository.save(dto.toEntity());
        
        return d.getDiaryId();
        
    }

    public List<Diary> findByMonth(int monthValue) {
        List<Diary> diaryList  =diaryRepository.findByMonthValueOrderByDayDesc(monthValue);
        
        return diaryList;
    }

    public List<Diary> readAll() {
        List<Diary> diaryList  =diaryRepository.findAll();
        return  diaryList;
    }

    public Diary findByD(Integer d) {
       Diary di = diaryRepository.findByDay(d);
        return di;
    }

    public Diary read(Integer diaryId) {
         Diary d =  diaryRepository.findByDiaryId(diaryId);
         
        return d;
    }

    public Diary findByMD(Integer monthValue, Integer d) {
        
    
        List<Diary> diaryList  =diaryRepository.findByMonthValueOrderByDayDesc(monthValue);
        Diary diary = null;
        for (Diary s : diaryList) {
            if(s.getDay() == d) {
                 diary = s;
                
            }
        } return diary;
       
    }

    public Page<Diary> getPagedDiaryList(Integer year,Integer monthValue, Pageable pageable) {
        
        Page<Diary> pageList = diaryRepository.findByYearAndMonthValueOrderByDay(year,monthValue,pageable);
        
//        Page<MiniDiaryDto> mm = new 
//        for (Diary diary : pageList.getContent()) {
//            log.info("페이징리스트????={}",diary.toString());
//        }
//        
//        log.info("현재 페이지: {}/{}", pageList.getNumber() + 1, pageList.getTotalPages());
//        log.info("페이지 다이어리 리스트?={}", pageList.toString());
        return pageList;
    }

}
