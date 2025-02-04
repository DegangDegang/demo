package com.example.demo.domain.asset.keyword.domain.vo;

import lombok.Getter;

@Getter
public class KeywordInfoVo {

	private final Long keywordId;
	private final String keyword;

	public KeywordInfoVo(Long keywordId, String keyword) {
		this.keywordId = keywordId;
		this.keyword = keyword;
	}
}
