package com.example.demo.domain.shortessay.service;

import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayCommentRequest;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayCommentInfo;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayCommentResponse;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDeleteCommentNotification;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDeleteLikeNotification;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayLikeInfo;
import com.example.demo.domain.user.domain.User;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ShortEssayService {

    ShortEssayDetailResponse write(WriteShortEssayRequest writeShortEssayRequest, User user);

    ShortEssayDetailResponse read(Long shortEssayId, User user);

    void delete(Long shortEssayId, User user);

    List<ShortEssayDetailResponse> getShortEssays(Long currentShortEssayId, int size, String direction, User user);

    ShortEssayLikeInfo like(Long shortEssayId, User user);

    ShortEssayDeleteLikeNotification unlike(Long shortEssayId, User user);

    ShortEssayDeleteCommentNotification deleteComment(Long shortEssayId, Long commentId, User user);

    ShortEssayCommentInfo comment(Long shortEssayId, WriteShortEssayCommentRequest commentRequest, User user);

    Slice<ShortEssayCommentResponse> getComments(Long shortEssayId, Pageable pageable, User user);

    Slice<ShortEssayDetailResponse> getUserShortEssays(Long userId, Pageable pageable);
}
