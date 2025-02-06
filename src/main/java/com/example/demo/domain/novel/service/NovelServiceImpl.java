package com.example.demo.domain.novel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.asset.keyword.presentation.dto.response.KeywordResponse;
import com.example.demo.domain.asset.keyword.service.KeywordService;
import com.example.demo.domain.novel.domain.Novel;
import com.example.demo.domain.novel.domain.repository.NovelRepository;
import com.example.demo.global.api.client.ContentClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NovelServiceImpl implements NovelService {

	private final NovelRepository novelRepository;
	private final ContentClient contentClient;
	private final KeywordService keywordService;

	@Override
	@Scheduled(cron = "0 59 3,7,9,11,15,19,23 * * *")
	public void createNovel() {

		String category = "느와르";

		Map<String, Object> request = new HashMap<>();
		request.put("category", category);

		// Map<String, Object> response = contentClient.analyzeKeywords(request);

		// String content = (String)response.get("content");
		String content = "나 상승규인데, 3대 600이고, 여자친구 구하고 있고 그냥 그렇다.";

		Novel novel = Novel.builder()
			.content(content)
			.category(category)
			.build();
		novelRepository.save(novel);
	}
}
