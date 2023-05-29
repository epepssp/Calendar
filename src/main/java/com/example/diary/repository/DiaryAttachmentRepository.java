package com.example.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diary.domain.DiaryAttachment;

public interface DiaryAttachmentRepository extends JpaRepository<DiaryAttachment, String> {

    List<DiaryAttachment> findByDiaryDiaryId(Integer diaryId);

}
