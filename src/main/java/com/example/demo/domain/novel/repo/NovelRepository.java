package com.example.demo.domain.novel.repo;


import com.example.demo.domain.novel.Novel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel,Long> {

}

