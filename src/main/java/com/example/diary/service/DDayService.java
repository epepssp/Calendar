package com.example.diary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.diary.domain.DDay;
import com.example.diary.domain.Diary;
import com.example.diary.repository.DDayRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DDayService {
    
    private final DDayRepository dDayRepository;
    
    public Integer create(DDay dday) {
       
         DDay d = dDayRepository.save(dday);
        
        return d.getDDayId();
    }

    public List<DDay> readAll() {
         List<DDay> dDayList = dDayRepository.findByOrderBySubtractDesc();
        return dDayList;
    }

    public List<DDay> findByMonth(int monthValue) {
        List<DDay> dDayList = dDayRepository.findByMonthValue(monthValue);
        return  dDayList;
    }

    public DDay findByD(int day) {
        DDay d = dDayRepository.findByDay(day);
        return d;
    }

  

}
