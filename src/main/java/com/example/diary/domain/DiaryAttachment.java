package com.example.diary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "diary")
@Entity
@Table(name = "DIARY_ATTACHMENTS")
public class DiaryAttachment implements Comparable<DiaryAttachment> {
    
    @Id
    private String uuid;
    
    private String fileName;
    
    private int ordinal;
    
    @ManyToOne
    private Diary diary;

    // @OneToMany 처리에서 순서에 맞게 정렬하기 위해서.
    @Override
    public int compareTo(DiaryAttachment other) {
        return this.ordinal - other.ordinal;
    }
    
    // Post 엔터티를 삭제할 때 PostAttachment 객체의 참조도 변경하기 위해서.
    public DiaryAttachment updateDiary(Diary diary) {
        this.diary = diary;
        
        return this;
    }

}
