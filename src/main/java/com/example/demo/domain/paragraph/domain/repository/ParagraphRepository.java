package com.example.demo.domain.paragraph.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.paragraph.domain.Paragraph;

import feign.Param;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
	Slice<Paragraph> findAllByNovelId(Long novelId, Pageable pageable);

	@Query("SELECT p FROM Paragraph p WHERE p.novel.id = :novelId AND p.isClosed = false")
	Slice<Paragraph> findParagraphsByNovelId(@Param("novelId") Long novelId, Pageable pageable);

	@Query("SELECT p FROM Paragraph p WHERE p.user.id = :userId AND p.isAdopted = false")
	Slice<Paragraph> findParagraphsByUserId(Long userId, Pageable pageable);
}
