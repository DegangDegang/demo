package com.example.demo.domain.novel.presentation.dto.response;

import java.util.List;

import com.example.demo.domain.paragraph.presentation.dto.response.ParagraphDetailResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelDetailResponse {

	private Long novelId;

	private String content;

	private List<ParagraphDetailResponse> paragraphs;

	private boolean isEnd;
}
