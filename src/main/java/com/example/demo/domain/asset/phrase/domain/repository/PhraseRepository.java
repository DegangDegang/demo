package com.example.demo.domain.asset.phrase.domain.repository;

import com.example.demo.domain.asset.phrase.domain.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {

	Optional<Phrase> findTop1ByOrderByIdDesc();

}
