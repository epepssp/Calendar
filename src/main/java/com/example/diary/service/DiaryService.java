package com.example.diary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.diary.domain.Diary;
import com.example.diary.dto.DiaryCreateDto;
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

}
