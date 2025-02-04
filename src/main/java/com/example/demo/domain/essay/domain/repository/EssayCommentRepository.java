package com.example.demo.domain.essay.domain.repository;

import com.example.demo.domain.essay.domain.EssayComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EssayCommentRepository extends JpaRepository<EssayComment, Long> {

    Slice<EssayComment> findSliceByOrderByCreatedAtDesc(Pageable pageable);
}
