package com.example.demo.domain.asset.keyword.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.asset.keyword.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

	List<Keyword> findTop3ByOrderByIdDesc();

}
