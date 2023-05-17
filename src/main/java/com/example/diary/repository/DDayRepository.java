package com.example.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.domain.DDay;

public interface DDayRepository extends JpaRepository<DDay, Integer>{

    List<DDay> findByMonthValue(int monthValue);

    DDay findByDay(int day);

}
