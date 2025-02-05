package com.example.demo.domain.asset.phrase.presentation;

import com.example.demo.domain.asset.phrase.presentation.dto.response.PhraseResponse;
import com.example.demo.domain.asset.phrase.service.PhraseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phrases")
@RequiredArgsConstructor
public class PhraseController {

	private final PhraseService phraseService;

	@GetMapping
	private PhraseResponse getCurrentWords() {
		return phraseService.getCurrentSentence();
	}

}
