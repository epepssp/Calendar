package com.example.diary.domain;


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
@Entity(name = "DIARY")
@SequenceGenerator(name = "DIARY_SEQ_GEN", sequenceName = "DIARY_SEQ", initialValue = 1, allocationSize = 1)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIARY_SEQ_GEN")
    private int diaryId;
    
    @Column(nullable = false)
    private int monthValue;

    @Column(nullable = false)
    private int year;
    
    @Column(nullable = false)
    private int day;
    
    private int weather;
    
    private String title;

    @Column(nullable = false)
    private String content;
    
    // private User user; //로그인 만들면
    
    
    
  
   
}