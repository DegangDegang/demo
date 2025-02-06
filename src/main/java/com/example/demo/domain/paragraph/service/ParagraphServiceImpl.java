package com.example.demo.domain.paragraph.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.novel.domain.Novel;
import com.example.demo.domain.novel.domain.repository.NovelRepository;
import com.example.demo.domain.paragraph.domain.Paragraph;
import com.example.demo.domain.paragraph.domain.ParagraphLike;
import com.example.demo.domain.paragraph.domain.repository.ParagraphLikeRepository;
import com.example.demo.domain.paragraph.domain.repository.ParagraphRepository;
import com.example.demo.domain.paragraph.presentation.dto.request.ParagraphUpdateRequest;
import com.example.demo.domain.paragraph.presentation.dto.response.ParagraphDetailResponse;
import com.example.demo.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ParagraphServiceImpl implements ParagraphService {

	private final ParagraphRepository paragraphRepository;
	private final NovelRepository novelRepository;
	private final ParagraphLikeRepository paragraphLikeRepository;

	@Override
	public ParagraphDetailResponse startWriteParagraph(Long novelId, User user) {
		Novel novel = novelRepository.findById(novelId).orElseThrow();
		Paragraph paragraph = Paragraph.builder()
			.user(user)
			.novel(novel)
			.content("").build();
		Paragraph savedParagraph = paragraphRepository.save(paragraph);
		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(paragraph.getParagraphInfo());

		paragraphDetailResponse.checkIsOwner(savedParagraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

	@Override
	public ParagraphDetailResponse updateParagraphContent(Long paragraphId,
		ParagraphUpdateRequest paragraphUpdateRequest, User user) {

		Paragraph paragraph = paragraphRepository.findById(paragraphId).orElseThrow(RuntimeException::new);

		paragraph.updateContent(paragraphUpdateRequest.getContent());
		Paragraph updatedParagraph = paragraphRepository.save(paragraph);
		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(
			updatedParagraph.getParagraphInfo());
		paragraphDetailResponse.checkIsOwner(updatedParagraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

	@Override
	public ParagraphDetailResponse endWriteParagraph(Long paragraphId, User user) {
		Paragraph paragraph = paragraphRepository.findById(paragraphId).orElseThrow(RuntimeException::new);
		paragraph.endWrite();
		Paragraph updatedParagraph = paragraphRepository.save(paragraph);

		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(
			updatedParagraph.getParagraphInfo());
		paragraphDetailResponse.checkIsOwner(updatedParagraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

	@Override
	public ParagraphDetailResponse readParagraph(Long paragraphId, User user) {
		Paragraph paragraph = paragraphRepository.findById(paragraphId).orElseThrow(RuntimeException::new);

		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(
			paragraph.getParagraphInfo());
		paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

	@Override
	public Slice<ParagraphDetailResponse> getParagraphs(Long novelId, Pageable pageable, User user) {
		Slice<Paragraph> paragraphs = paragraphRepository.findAllByNovelId(novelId, pageable);
		return paragraphs.map((paragraph -> {
			ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(paragraph.getParagraphInfo());
			paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
			paragraphDetailResponse.checkIsLiked(false);
			return paragraphDetailResponse;
		}));
	}

	@Override
	public Slice<ParagraphDetailResponse> getParagraphsWriting(Long novelId, Pageable pageable, User user) {
		Slice<Paragraph> paragraphs = paragraphRepository.findParagraphsByNovelId(novelId, pageable);
		return paragraphs.map((paragraph -> {
			ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(paragraph.getParagraphInfo());
			paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
			paragraphDetailResponse.checkIsLiked(false);
			return paragraphDetailResponse;
		}));
	}

	@Override
	public Slice<ParagraphDetailResponse> getParagraphsUserNotAdopted(Long userId, Pageable pageable, User user) {
		Slice<Paragraph> paragraphs = paragraphRepository.findParagraphsByUserId(userId, pageable);
		return paragraphs.map((paragraph -> {
			ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(paragraph.getParagraphInfo());
			paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
			paragraphDetailResponse.checkIsLiked(false);
			return paragraphDetailResponse;
		}));
	}

	@Override
	public ParagraphDetailResponse like(Long paragraphId, User user) {
		Paragraph paragraph = paragraphRepository.findById(paragraphId).orElseThrow(RuntimeException::new);

		paragraphLikeRepository.findByUserIdAndParagraphId(user.getId(), paragraph.getId()).ifPresent(like -> {
			throw new RuntimeException();
		});
		ParagraphLike paragraphLike = ParagraphLike.builder()
			.user(user)
			.paragraph(paragraph)
			.build();
		paragraphLikeRepository.save(paragraphLike);
		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(
			paragraph.getParagraphInfo()
		);
		paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

	@Override
	public ParagraphDetailResponse unlike(Long paragraphId, User user) {
		ParagraphLike paragraphLike = paragraphLikeRepository.findByUserIdAndParagraphId(user.getId(), paragraphId)
			.orElseThrow(RuntimeException::new);
		paragraphLikeRepository.delete(paragraphLike);
		Paragraph paragraph = paragraphRepository.findById(paragraphId).orElseThrow(RuntimeException::new);
		ParagraphDetailResponse paragraphDetailResponse = new ParagraphDetailResponse(paragraph.getParagraphInfo());
		paragraphDetailResponse.checkIsOwner(paragraph.getId().equals(user.getId()));
		paragraphDetailResponse.checkIsLiked(false);
		return paragraphDetailResponse;
	}

}
