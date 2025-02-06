package com.example.demo.domain.paragraph.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.paragraph.domain.ParagraphLike;

public interface ParagraphLikeRepository extends JpaRepository<ParagraphLike, Long> {
	 Optional<ParagraphLike> findByUserIdAndParagraphId(Long id, Long id1);
}
