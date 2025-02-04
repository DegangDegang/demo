package com.example.demo.domain.asset.word.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.asset.word.domain.Word;

import feign.Param;

public interface WordRepository extends JpaRepository<Word, Long> {

	@Query(value = "SELECT * FROM words ORDER BY RAND() LIMIT :count", nativeQuery = true)
	List<Word> findRandomWords(@Param("count") int count);
}
