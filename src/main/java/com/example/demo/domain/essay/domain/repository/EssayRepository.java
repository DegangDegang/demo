package com.example.demo.domain.essay.domain.repository;

import com.example.demo.domain.essay.domain.Essay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EssayRepository extends JpaRepository<Essay,Long> {

    Slice<Essay> findByIsDraftFalseOrderByLastModifyAtDesc(Pageable pageable);

    //Slice<Essay> findByTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseAndIsDraftFalse(String word, Pageable pageable);

    @Query("SELECT e FROM Essay e " +
            "JOIN FETCH e.user u " +  // FETCH JOIN 사용
            "WHERE (LOWER(e.title) LIKE LOWER(CONCAT('%', :word, '%')) " +
            "OR LOWER(u.nickname) LIKE LOWER(CONCAT('%', :word, '%'))) " +
            "AND e.isDraft = false")
    Slice<Essay> searchEssays(@Param("word") String word, Pageable pageable);

    @Query("select e from Essay e" +
            " where e.user.id = :userId and e.isDraft = false order by e.lastModifyAt desc")
    Slice<Essay> findAllMyEssay(@Param("userId") Long userId, Pageable pageable);

    @Query("select e from Essay e" +
            " where e.user.id = :userId and e.isDraft = true")
    Optional<Essay> findDraftByUser(@Param("userId") Long userId);

}
