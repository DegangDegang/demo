package com.example.demo.domain.novel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.novel.domain.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long> {
}
