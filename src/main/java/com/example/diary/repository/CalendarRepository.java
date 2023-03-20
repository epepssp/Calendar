package com.example.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer>{

}
