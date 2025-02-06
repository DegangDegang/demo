package com.example.demo.domain.asset.word.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.asset.keyword.domain.Keyword;
import com.example.demo.domain.asset.keyword.domain.repository.KeywordRepository;
import com.example.demo.domain.asset.word.domain.Word;
import com.example.demo.domain.asset.word.domain.repository.WordRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService{

	private final WordRepository wordRepository;
	private final KeywordRepository keywordRepository;

	@Override
	@Scheduled(cron = "0 58 3,7,11,15,19,23 * * *")
	public void createRandomWords() {
		List<Word> randomWords = wordRepository.findRandomWords(3);
		for (Word randomWord : randomWords) {
			keywordRepository.save(new Keyword(randomWord.getWord()));
		}
	}
}
