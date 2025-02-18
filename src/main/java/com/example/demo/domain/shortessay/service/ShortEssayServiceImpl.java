package com.example.demo.domain.shortessay.service;

import com.example.demo.domain.shortessay.domain.ShortEssay;
import com.example.demo.domain.shortessay.domain.ShortEssayComment;
import com.example.demo.domain.shortessay.domain.ShortEssayLike;
import com.example.demo.domain.shortessay.domain.repository.ShortEssayCommentRepository;
import com.example.demo.domain.shortessay.domain.repository.ShortEssayLikeRepository;
import com.example.demo.domain.shortessay.domain.repository.ShortEssayRepository;
import com.example.demo.domain.shortessay.exception.AlreadyLikedException;
import com.example.demo.domain.shortessay.exception.ShortEssayLikeNotFoundException;
import com.example.demo.domain.shortessay.exception.ShortEssayNotFoundException;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayCommentRequest;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayCommentResponse;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ShortEssayServiceImpl implements ShortEssayService {

	private final ShortEssayRepository shortEssayRepository;
	private final ShortEssayLikeRepository shortEssayLikeRepository;
	private final ShortEssayCommentRepository shortEssayCommentRepository;

	@Override
	public ShortEssayDetailResponse write(WriteShortEssayRequest writeShortEssayRequest,
		User user) {

		ShortEssay shortEssay = ShortEssay.builder()
			.content(writeShortEssayRequest.getContent())
			.imgUrl(writeShortEssayRequest.getImgUrl())
			.user(user)
			.keywords(getKeywordsOrDefault(writeShortEssayRequest.getKeywords()))
			.build();

		ShortEssay savedShortEssay = shortEssayRepository.save(shortEssay);
		ShortEssayDetailResponse shortEssayDetailResponse = new ShortEssayDetailResponse(
			savedShortEssay.getShortEssayInfo());
		shortEssayDetailResponse.checkIsOwner(user.getId().equals(shortEssay.getUser().getId()));
		shortEssayDetailResponse.checkIsLiked(false);
		return shortEssayDetailResponse;

	}

	@Transactional(readOnly = true)
	@Override
	public ShortEssayDetailResponse read(Long shortEssayId, User user) {

		ShortEssay shortEssay = findById(shortEssayId);

		ShortEssayDetailResponse shortEssayDetailResponse = new ShortEssayDetailResponse(
			shortEssay.getShortEssayInfo());
		shortEssayDetailResponse.checkIsOwner(user.getId().equals(shortEssay.getUser().getId()));
		shortEssayDetailResponse.checkIsLiked(false);
		return shortEssayDetailResponse;
	}

	@Override
	public void delete(Long shortEssayId, User user) {
		ShortEssay shortEssay = findById(shortEssayId);
		shortEssay.validUserIsHost(user.getId());
		shortEssayRepository.delete(shortEssay);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ShortEssayDetailResponse> getShortEssays(Long currentShortEssayId, int size,
		String direction, User user) {

		boolean isNext = "next".equalsIgnoreCase(direction);

		Pageable pageable = isNext ?
			PageRequest.of(0, size, Sort.by(Sort.Order.desc("id"))) :
			PageRequest.of(0, size, Sort.by(Sort.Order.asc("id")));

		Page<ShortEssay> pages;

		if (currentShortEssayId == null) {
			pages = shortEssayRepository.findAll(pageable);
		} else {
			if (isNext) {
				pages = shortEssayRepository.findByIdLessThan(currentShortEssayId, pageable);
			} else {
				pages = shortEssayRepository.findByIdGreaterThan(currentShortEssayId, pageable);
			}
		}
		List<ShortEssay> shortEssays = new ArrayList<>(pages.getContent());

		if (shortEssays.size() < size) {
			Page<ShortEssay> additionalShortEssays;
			if (isNext) {
				additionalShortEssays = shortEssayRepository.findAll(
					PageRequest.of(0, size - shortEssays.size(), Sort.by(Sort.Order.desc("id"))));
			} else {
				additionalShortEssays = shortEssayRepository.findAll(
					PageRequest.of(0, size - shortEssays.size(), Sort.by(Sort.Order.asc("id"))));
			}
			shortEssays.addAll(additionalShortEssays.getContent());
		}

		// 데이터 반환
		return shortEssays.stream()
			.map(shortEssay -> {
				ShortEssayDetailResponse response = new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
				response.checkIsOwner(user.getId().equals(shortEssay.getUser().getId()));
				response.checkIsLiked(false);
				return response;
			})
			.collect(Collectors.toList());
	}



	@Override
	public ShortEssayDetailResponse like(Long shortEssayId, User user) {

		shortEssayLikeRepository.findByUserIdAndShortEssayId(user.getId(), shortEssayId)
			.ifPresent(like -> {
				throw AlreadyLikedException.EXCEPTION;
			});

		ShortEssay shortEssay = findById(shortEssayId);
		ShortEssayLike shortEssayLike = ShortEssayLike.builder()
			.user(user)
			.shortEssay(shortEssay)
			.build();

		shortEssayLikeRepository.save(shortEssayLike);
		return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
	}

	@Override
	public ShortEssayDetailResponse unlike(Long shortEssayId, User user) {

		ShortEssayLike shortEssayLike = shortEssayLikeRepository.findByUserIdAndShortEssayId(
			user.getId(), shortEssayId).orElseThrow(() -> ShortEssayLikeNotFoundException.EXCEPTION);

		shortEssayLikeRepository.delete(shortEssayLike);
		shortEssayLikeRepository.flush();

		ShortEssay shortEssay = findById(shortEssayId);

		ShortEssayDetailResponse shortEssayDetailResponse = new ShortEssayDetailResponse(
			shortEssay.getShortEssayInfo());
		shortEssayDetailResponse.checkIsOwner(user.getId().equals(shortEssay.getUser().getId()));
		shortEssayDetailResponse.checkIsLiked(false);

		return shortEssayDetailResponse;
	}

	@Override
	public void comment(Long shortEssayId, WriteShortEssayCommentRequest commentRequest,
		User user) {

		ShortEssay shortEssay = findById(shortEssayId);
		ShortEssayComment shortEssayComment = ShortEssayComment.builder()
			.shortEssay(shortEssay)
			.user(user)
			.content(commentRequest.getContent())
			.build();
		shortEssayCommentRepository.save(shortEssayComment);
	}

	@Override
	public void deleteComment(Long shortEssayId, Long commentId, User user) {

		ShortEssayComment shortEssayComment = shortEssayCommentRepository.findById(commentId)
			.orElseThrow();

		shortEssayCommentRepository.delete(shortEssayComment);
	}

	@Override
	public Slice<ShortEssayCommentResponse> getComments(Long shortEssayId, Pageable pageable, User user) {
		Slice<ShortEssayComment> comments = shortEssayCommentRepository.findAllByShortEssayId(shortEssayId,
			pageable);

		return comments.map(comment -> {
			ShortEssayCommentResponse response = new ShortEssayCommentResponse(
				comment.getShortEssayCommentInfo());
			response.checkOwner(user.getId().equals(comment.getUser().getId()));
			return response;
		});
	}

	@Override
	public Slice<ShortEssayDetailResponse> getUserShortEssays(Long userId, Pageable pageable) {
		Slice<ShortEssay> shortEssays = shortEssayRepository.findAllByUserId(userId, pageable);
		return shortEssays.map(shortEssay -> new ShortEssayDetailResponse(shortEssay.getShortEssayInfo()));
	}

	private List<String> getKeywordsOrDefault(List<String> keywords) {
		return keywords == null ? new ArrayList<>() : keywords;
	}

	private ShortEssay findById(Long id) {
		return shortEssayRepository.findById(id)
			.orElseThrow(() -> ShortEssayNotFoundException.EXCEPTION);
	}

}
