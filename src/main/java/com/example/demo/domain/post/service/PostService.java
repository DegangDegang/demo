package com.example.demo.domain.post.service;

import com.example.demo.domain.post.presentation.dto.request.UpdatePostRequest;
import com.example.demo.domain.post.presentation.dto.request.WritePostRequest;
import com.example.demo.domain.post.presentation.dto.response.PostDetailResponse;
import com.example.demo.domain.post.presentation.dto.response.PostSummaryResponse;
import com.example.demo.domain.user.domain.User;

import java.util.List;

public interface PostService {
    PostDetailResponse getPostById(Long postId);

    PostDetailResponse writePost(WritePostRequest writePostRequest, User user);

    PostDetailResponse updatePost(UpdatePostRequest updatePostRequest, Long postId, User user);

    void deletePost(Long postId, User user);

    List<PostSummaryResponse> getAllPosts();
}
