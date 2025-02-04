package com.example.demo.domain.essay.domain.repository;

import com.example.demo.domain.essay.domain.Essay;
import com.example.demo.domain.essay.domain.EssayLike;
import com.example.demo.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EssayLikeRepository extends JpaRepository<EssayLike,Long> {

    boolean existsByEssayAndUser(Essay essay, User user);
    void deleteByEssayAndUser(Essay essay, User user);
    Long countByEssay(Essay essay);

}
