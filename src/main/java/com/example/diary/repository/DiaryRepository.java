package com.example.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Integer>{

    List<Diary> findByMonthValueOrderByDayDesc(int monthValue);

    Diary findByDay(Integer day);

    Diary findByDiaryId(Integer diaryId);

}
