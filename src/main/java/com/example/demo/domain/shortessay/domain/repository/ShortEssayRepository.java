package com.example.demo.domain.shortessay.domain.repository;

import com.example.demo.domain.shortessay.domain.ShortEssay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ShortEssayRepository extends CrudRepository<ShortEssay, Long> {

    Page<ShortEssay> findByIdGreaterThan(Long cursor, Pageable pageable);

    Page<ShortEssay> findByIdLessThan(Long cursor, Pageable pageable);

    Page<ShortEssay> findAll(Pageable pageable);
}
