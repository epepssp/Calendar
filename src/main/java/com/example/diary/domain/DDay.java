package com.example.diary.domain;

import java.time.LocalDate;
import java.util.Date;

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
@Entity(name = "DDAY")
@SequenceGenerator(name = "DDAY_SEQ_GEN", sequenceName = "DDAY_SEQ", initialValue = 1, allocationSize = 1)
public class DDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DDAY_SEQ_GEN")
    private int dDayId;

    private LocalDate fromDate; // ~부터: 기준점
    
    @Column(nullable = false)
    private LocalDate untilDate; // ~ 까지: D -DAY
    
    private int subtract;   // 기준일과 D-Day 차이. 표시할때
    
    private int monthValue;
    private int year;
    private int day;
    
 
    private String dDayName;
    
    
//    public DDay fromEntity(DDay dDay) {
//       year = Integer.parseInt(dDay.getFromDate().toString().substring(0, 4));
//       monthValue = Integer.parseInt(dDay.getFromDate().toString().substring(5,7));
//       day = Integer.parseInt(dDay.getFromDate().toString().substring(8);
//       
//       if(Integer.parseInt(dDay.getFromDate().toString().substring(3, 4)) == 0) {
//           monthValue = Integer.parseInt(dDay.getFromDate().toString().substring(4, 5));
//       } else {
//           monthValue = Integer.parseInt(dDay.getFromDate().toString().substring(3, 5));
//       }
//       
//       if(Integer.parseInt(dDay.getFromDate().toString().substring(6, 7)) == 0) {
//           day = Integer.parseInt(dDay.getFromDate().toString().substring(7,8));
//       } else {
//           day = Integer.parseInt(dDay.getFromDate().toString().substring(6,8));
//       }
//      
//
//        return DDay.builder().year(year).monthValue(monthValue).day(day).build();
//    }
}
