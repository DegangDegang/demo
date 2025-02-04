package com.example.demo.domain.asset.keyword.service;

import java.util.List;

import com.example.demo.domain.asset.keyword.presentation.dto.response.KeywordResponse;

public interface KeywordService {
	List<KeywordResponse> getCurrentWords();
}
