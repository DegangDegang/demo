package com.example.demo.domain.asset.phrase.presentation.dto.response;

import com.example.demo.domain.asset.phrase.domain.vo.PhraseInfoVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhraseResponse {

	private final Long phraseId;
	private final String phrase;

	public PhraseResponse(PhraseInfoVo phraseInfoVo) {
		this.phraseId = phraseInfoVo.getPhraseId();
		this.phrase = phraseInfoVo.getPhrase();
	}

}
