package com.example.diary.domain;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.HashSet;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "attachments")

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
    
    @OneToMany(mappedBy = "diary", // PostAttachment의 post 필드.
            cascade = CascadeType.ALL, // 상위 엔터티의 모든 상태 변경이 하위 엔터티에도 적용.
            fetch = FetchType.LAZY,
            orphanRemoval = true // 하위 엔터티의 참조가 더이상 없는 상태가 되면 삭제되도록.
            )
    @Builder.Default
    private Set<DiaryAttachment> attachments = new HashSet<>();
    
    // Post 객체에서 PostAttachment 객체들을 관리(추가, 삭제)하도록 하기 위해서.
    public void addAttachment(String uuid, String fileName) {
        DiaryAttachment attachment = DiaryAttachment.builder()
                .uuid(uuid)
                .fileName(fileName)
                .ordinal(attachments.size())
                .diary(this)
                .build();
        attachments.add(attachment);
    }
    
    public void clearAttachments() {
        // MAYBE: 참조를 끊기 위해서.
        attachments.forEach(attachment -> attachment.updateDiary(null));
        attachments.clear(); // Removes all of the elements from this set.
    }
    
    public Diary update(String title, String content) {
        this.title = title;
        this.content = content;
        
        return this;
    }
  
   
}