package com.example.demo.domain.asset.phrase.domain.vo;

import lombok.Getter;

@Getter
public class PhraseInfoVo {

	private final Long phraseId;
	private final String phrase;

	public PhraseInfoVo(Long phraseId, String phrase) {
		this.phraseId = phraseId;
		this.phrase = phrase;
	}
}
