package com.example.demo.domain.shortessay.service;

import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayCommentRequest;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;
import java.util.List;

public interface ShortEssayService {

    ShortEssayDetailResponse write(WriteShortEssayRequest writeShortEssayRequest, User user);

    ShortEssayDetailResponse read(Long shortEssayId);

    void delete(Long shortEssayId, User user);

    List<ShortEssayDetailResponse> getShortEssays(Long currentShortEssayId, int size, String direction);

    ShortEssayDetailResponse like(Long shortEssayId, User user);

    ShortEssayDetailResponse unlike(Long shortEssayId, User user);

    ShortEssayDetailResponse deleteComment(Long shortEssayId, Long commentId, User user);

    ShortEssayDetailResponse comment(Long shortEssayId, WriteShortEssayCommentRequest commentRequest, User user);
}
