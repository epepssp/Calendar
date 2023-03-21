package com.example.diary.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "SCHEDULE")
@SequenceGenerator(name = "SCHEDULE_SEQ_GEN", sequenceName = "SCHEDULE_SEQ", initialValue = 1, allocationSize = 1)
public class Schedule {

    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULE_SEQ_GEN")
    private int scheduleId;
    
    private Integer userId;
    
    @Column(nullable = false)
    private String fullDate;
    

    @Column(nullable = false)
    private int year;
    
    @Column(nullable = false)
    private int monthValue;
    
    @Column(nullable = false)
    private int day;
    
    @Column(nullable = false)
    private String content;
    
}
