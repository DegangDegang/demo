package com.example.demo.domain.paragraph.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.example.demo.domain.paragraph.presentation.dto.request.ParagraphUpdateRequest;
import com.example.demo.domain.paragraph.presentation.dto.response.ParagraphDetailResponse;
import com.example.demo.domain.user.domain.User;

public interface ParagraphService {
	ParagraphDetailResponse startWriteParagraph(Long novelId, User user);

	ParagraphDetailResponse updateParagraphContent(Long paragraphId, ParagraphUpdateRequest paragraphUpdateRequest,
		User user);

	ParagraphDetailResponse endWriteParagraph(Long paragraphId, User user);

	ParagraphDetailResponse readParagraph(Long paragraphId, User user);

	Slice<ParagraphDetailResponse> getParagraphs(Long novelId, Pageable pageable, User user);

	Slice<ParagraphDetailResponse> getParagraphsWriting(Long novelId, Pageable pageable, User user);

	Slice<ParagraphDetailResponse> getParagraphsUserNotAdopted(Long userId, Pageable pageable, User user);

	ParagraphDetailResponse like(Long paragraphId, User user);

	ParagraphDetailResponse unlike(Long paragraphId, User user);
}
