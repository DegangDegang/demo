package com.example.demo.domain.paragraph.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.paragraph.domain.vo.ParagraphInfoVo;

import lombok.Getter;

@Getter
public class ParagraphSimpleResponse {

	private final Long paragraphId;

	private final String content;

	private final LocalDateTime createdAt;

	public ParagraphSimpleResponse(ParagraphInfoVo paragraphInfoVo) {
		this.paragraphId = paragraphInfoVo.getParagraphId();
		this.content = paragraphInfoVo.getContent();
		this.createdAt = paragraphInfoVo.getCreatedAt();
	}

}
