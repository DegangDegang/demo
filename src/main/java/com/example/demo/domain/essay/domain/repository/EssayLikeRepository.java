package com.example.demo.domain.essay.domain.repository;

import com.example.demo.domain.essay.domain.Essay;
import com.example.demo.domain.essay.domain.EssayLike;
import com.example.demo.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EssayLikeRepository extends JpaRepository<EssayLike,Long> {

    boolean existsByEssayAndUser(Essay essay, User user);

    @Modifying
    @Query("DELETE FROM EssayLike el WHERE el.essay = :essay AND el.user = :user")
    void deleteByEssayAndUser(@Param("essay") Essay essay, @Param("user") User user);
    Long countByEssay(Essay essay);

}
