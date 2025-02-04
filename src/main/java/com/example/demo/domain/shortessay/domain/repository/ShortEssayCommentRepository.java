package com.example.demo.domain.shortessay.domain.repository;

import com.example.demo.domain.shortessay.domain.ShortEssayComment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortEssayCommentRepository extends JpaRepository<ShortEssayComment, Long> {
	Slice<ShortEssayComment> findAllByShortEssayId(Long shortEssayId, Pageable pageable);

}
