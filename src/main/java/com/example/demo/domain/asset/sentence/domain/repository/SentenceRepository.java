package com.example.demo.domain.asset.sentence.domain.repository;

import com.example.demo.domain.asset.sentence.domain.Sentence;
import com.example.demo.domain.asset.word.domain.Word;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {

	@Query(value = "SELECT * FROM sentences ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Optional<Sentence> findRandomSentence();
}
