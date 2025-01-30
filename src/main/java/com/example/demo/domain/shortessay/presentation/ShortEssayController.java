package com.example.demo.domain.shortessay.presentation;

import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayCommentRequest;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.shortessay.service.ShortEssayService;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/short-essay")
public class ShortEssayController {

    private final ShortEssayService shortEssayService;
    private final UserUtils userUtils;

    @PostMapping
    public ShortEssayDetailResponse writeShortEssay(@Valid @RequestBody WriteShortEssayRequest writeShortEssayRequest) {
        User user = userUtils.getUserFromSecurityContext();
        return shortEssayService.write(writeShortEssayRequest, user);
    }

    @GetMapping("/{shortEssayId}")
    public ShortEssayDetailResponse readShortEssay(@PathVariable Long shortEssayId) {
        return shortEssayService.read(shortEssayId);
    }

    @DeleteMapping("/{shortEssayId}")
    public void deleteShortEssay(@PathVariable Long shortEssayId) {
        User user = userUtils.getUserFromSecurityContext();
        shortEssayService.delete(shortEssayId, user);
    }

    @GetMapping
    public List<ShortEssayDetailResponse> getVideos(
        @RequestParam(required = false) Long currentId, // 마지막으로 가져온 영상 ID
        @RequestParam(defaultValue = "10") int size, // 한 번에 가져올 개수
        @RequestParam(defaultValue = "prev") String direction // "prev" (이전 데이터) or "next" (새로운 데이터)
    ) {
        return shortEssayService.getShortEssays(currentId, size, direction);
    }

    @PostMapping("/{shortEssayId}/like")
    public ShortEssayDetailResponse likeShortEssay(@PathVariable Long shortEssayId) {
        User user = userUtils.getUserFromSecurityContext();
        return shortEssayService.like(shortEssayId, user);
    }

    @DeleteMapping("/{shortEssayId}/like")
    public ShortEssayDetailResponse unlikeShortEssay(@PathVariable Long shortEssayId) {
        User user = userUtils.getUserFromSecurityContext();
        return shortEssayService.unlike(shortEssayId, user);
    }

    @PostMapping("/{shortEssayId}/comment")
    public ShortEssayDetailResponse commentShortEssay(@PathVariable Long shortEssayId, @RequestBody
        WriteShortEssayCommentRequest commentRequest) {
        User user = userUtils.getUserFromSecurityContext();
        return shortEssayService.comment(shortEssayId, commentRequest, user);
    }

    @DeleteMapping("/{shortEssayId}/comment/{commentId}")
    public ShortEssayDetailResponse deleteComment(@PathVariable Long shortEssayId, @PathVariable Long commentId) {
        User user = userUtils.getUserFromSecurityContext();
        return shortEssayService.deleteComment(shortEssayId, commentId, user);
    }


}
