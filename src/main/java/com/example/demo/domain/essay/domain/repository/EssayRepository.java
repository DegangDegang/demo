package com.example.demo.domain.essay.domain.repository;

import com.example.demo.domain.essay.domain.Essay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EssayRepository extends JpaRepository<Essay,Long> {

    Slice<Essay> findSliceByOrderByLastModifyAtDesc(Pageable pageable);

    @Query("select distinct e from Essay e"+
            " where e.user.id = :userId order by e.lastModifyAt desc")
    List<Essay> findReservedByMe(@Param("userId") Long userId);


}
