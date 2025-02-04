package com.example.demo.domain.asset.keyword.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.asset.keyword.presentation.dto.response.KeywordResponse;
import com.example.demo.domain.asset.keyword.service.KeywordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/keywords")
@RequiredArgsConstructor
public class KeywordController {

	private final KeywordService keywordService;

	@GetMapping
	private List<KeywordResponse> getCurrentWords() {
		return keywordService.getCurrentWords();
	}


}
