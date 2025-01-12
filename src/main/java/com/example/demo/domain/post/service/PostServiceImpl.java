package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.domain.repository.PostRepository;
import com.example.demo.domain.post.presentation.dto.request.UpdatePostRequest;
import com.example.demo.domain.post.presentation.dto.request.WritePostRequest;
import com.example.demo.domain.post.presentation.dto.response.PostDetailResponse;
import com.example.demo.domain.post.presentation.dto.response.PostSummaryResponse;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.exception.post.PostNotFoundException;
import com.example.demo.global.exception.post.PostPermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> PostNotFoundException.EXCEPTION);
        return new PostDetailResponse(post);
    }

    @Override
    public PostDetailResponse writePost(WritePostRequest writePostRequest, User user) {
        Post post = Post.builder()
                .title(writePostRequest.getTitle())
                .content(writePostRequest.getContent())
                .user(user)
                .build();
        Post savedPost = postRepository.save(post);
        return new PostDetailResponse(savedPost);
    }

    @Override
    public PostDetailResponse updatePost(UpdatePostRequest updatePostRequest, Long postId, User user) {
        Post post = validatePostOwnership(postId, user);
        post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
        return new PostDetailResponse(post);
    }

    @Override
    public void deletePost(Long postId, User user) {
        Post post = validatePostOwnership(postId, user);
        postRepository.delete(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getAllPosts() {
        List<Post> all = postRepository.findAll();
        List<PostSummaryResponse> responses = new ArrayList<>();
        for (Post post : all) {
            responses.add(new PostSummaryResponse(post));
        }
        return responses;
    }

    private Post validatePostOwnership(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
        if (!post.getUser().equals(user)) {
            throw PostPermissionException.EXCEPTION;
        }
        return post;
    }

}
