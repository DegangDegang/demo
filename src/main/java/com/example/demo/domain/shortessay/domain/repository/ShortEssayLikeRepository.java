package com.example.demo.domain.shortessay.domain.repository;

import com.example.demo.domain.shortessay.domain.ShortEssayLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortEssayLikeRepository extends JpaRepository<ShortEssayLike, Long> {

    Optional<ShortEssayLike> findByUserIdAndShortEssayId(Long userId, Long shortEssayId);
}
