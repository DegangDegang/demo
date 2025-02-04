package com.example.demo.domain.asset.keyword.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.asset.keyword.domain.Keyword;
import com.example.demo.domain.asset.keyword.domain.repository.KeywordRepository;
import com.example.demo.domain.asset.keyword.presentation.dto.response.KeywordResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordServiceImpl implements KeywordService {

	private final KeywordRepository keywordRepository;

	@Override
	public List<KeywordResponse> getCurrentWords() {
		List<Keyword> keywords = keywordRepository.findTop3ByOrderByIdDesc();
		return keywords.stream()
			.map((keyword) -> new KeywordResponse(keyword.getKeywordInfo())).collect(Collectors.toList());
	}
}
