package com.example.demo.domain.asset.sentence.service;

import com.example.demo.domain.asset.keyword.domain.Keyword;
import com.example.demo.domain.asset.keyword.domain.repository.KeywordRepository;
import com.example.demo.domain.asset.phrase.domain.Phrase;
import com.example.demo.domain.asset.phrase.domain.repository.PhraseRepository;
import com.example.demo.domain.asset.sentence.domain.Sentence;
import com.example.demo.domain.asset.sentence.domain.repository.SentenceRepository;
import com.example.demo.domain.asset.sentence.exception.SentenceNotFoundException;
import com.example.demo.domain.asset.word.domain.Word;
import com.example.demo.domain.asset.word.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;
	private final PhraseRepository phraseRepository;

	@Override
	@Scheduled(cron = "59 59 23 * * MON,THU")
	public void createRandomWords() {
		Sentence sentence = sentenceRepository.findRandomSentence()
				.orElseThrow(() -> SentenceNotFoundException.EXCEPTION);

		Phrase phrase = new Phrase(sentence.getSentence());
		phraseRepository.save(phrase);
	}

}
