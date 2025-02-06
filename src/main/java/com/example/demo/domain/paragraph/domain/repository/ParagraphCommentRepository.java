package com.example.demo.domain.paragraph.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.paragraph.domain.ParagraphComment;

public interface ParagraphCommentRepository extends JpaRepository<ParagraphComment, Long> {
}
