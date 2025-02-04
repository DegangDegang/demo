package com.example.demo.domain.asset.keyword.presentation.dto.response;

import com.example.demo.domain.asset.keyword.domain.vo.KeywordInfoVo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordResponse {

	private final Long keywordId;
	private final String word;

	public KeywordResponse(KeywordInfoVo keywordInfoVo) {
		this.keywordId = keywordInfoVo.getKeywordId();
		this.word = keywordInfoVo.getKeyword();
	}

}
