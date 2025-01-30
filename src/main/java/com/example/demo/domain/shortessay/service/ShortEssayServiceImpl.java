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
import com.example.demo.domain.shortessay.exception.UnauthorizedShortEssayDeletionException;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayCommentRequest;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            .build();

        shortEssayRepository.save(shortEssay);
        return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
    }

    @Transactional(readOnly = true)
    @Override
    public ShortEssayDetailResponse read(Long shortEssayId) {
        ShortEssay shortEssay = findById(shortEssayId);
        return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
    }

    @Override
    public void delete(Long shortEssayId, User user) {
        ShortEssay shortEssay = findById(shortEssayId);

        if (!user.getId().equals(shortEssay.getUser().getId())) {
            throw UnauthorizedShortEssayDeletionException.EXCEPTION;
        }
        shortEssayRepository.delete(shortEssay);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShortEssayDetailResponse> getShortEssays(Long currentShortEssayId, int size,
        String direction) {

        boolean isNext = "next".equalsIgnoreCase(direction);

        Pageable pageable = isNext ?
            PageRequest.of(0, size, Sort.by(Sort.Order.desc("id"))) :
            PageRequest.of(0, size, Sort.by(Sort.Order.asc("id")));

        Page<ShortEssay> shortEssays;
        if (currentShortEssayId == null) {
            shortEssays = shortEssayRepository.findAll(pageable);
        } else {
            if (isNext) {
                shortEssays = shortEssayRepository.findByIdLessThan(currentShortEssayId, pageable);
            } else {
                shortEssays = shortEssayRepository.findByIdGreaterThan(currentShortEssayId,
                    pageable);
            }
        }

        if (shortEssays.isEmpty()) {
            if (isNext) {
                shortEssays = shortEssayRepository.findAll(
                    PageRequest.of(0, size, Sort.by(Sort.Order.desc("id"))));
            } else {
                shortEssays = shortEssayRepository.findAll(
                    PageRequest.of(0, size, Sort.by(Sort.Order.asc("id"))));
            }
        }

        // 응답 객체로 변환
        return shortEssays.stream()
            .map(shortEssay -> new ShortEssayDetailResponse(shortEssay.getShortEssayInfo()))
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

        ShortEssay shortEssay = findById(shortEssayId);
        return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
    }

    @Override
    public ShortEssayDetailResponse comment(Long shortEssayId, WriteShortEssayCommentRequest commentRequest, User user) {

        ShortEssay shortEssay = findById(shortEssayId);
        ShortEssayComment shortEssayComment = ShortEssayComment.builder()
            .shortEssay(shortEssay)
            .user(user)
            .content(commentRequest.getContent())
            .build();

        shortEssayCommentRepository.save(shortEssayComment);
        return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
    }

    @Override
    public ShortEssayDetailResponse deleteComment(Long shortEssayId, Long commentId, User user) {

        ShortEssayComment shortEssayComment = shortEssayCommentRepository.findById(commentId)
            .orElseThrow();

        shortEssayCommentRepository.delete(shortEssayComment);

        ShortEssay shortEssay = findById(shortEssayId);
        return new ShortEssayDetailResponse(shortEssay.getShortEssayInfo());
    }



    private ShortEssay findById(Long id) {
        return shortEssayRepository.findById(id)
            .orElseThrow(() -> ShortEssayNotFoundException.EXCEPTION);
    }

}
