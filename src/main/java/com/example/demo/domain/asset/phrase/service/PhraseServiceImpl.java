package com.example.demo.domain.asset.phrase.service;

import com.example.demo.domain.asset.phrase.domain.Phrase;
import com.example.demo.domain.asset.phrase.domain.repository.PhraseRepository;
import com.example.demo.domain.asset.phrase.exception.PhraseNotFoundException;
import com.example.demo.domain.asset.phrase.presentation.dto.response.PhraseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PhraseServiceImpl implements PhraseService {

	private final PhraseRepository phraseRepository;

	@Override
	public PhraseResponse getCurrentSentence() {
		Phrase phrase = phraseRepository.findTop1ByOrderByIdDesc()
				.orElseThrow(() -> PhraseNotFoundException.EXCEPTION);
		return new PhraseResponse(phrase.getPhraseInfo());
	}
}
