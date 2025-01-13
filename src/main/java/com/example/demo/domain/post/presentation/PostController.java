package com.example.demo.domain.post.presentation;

import com.example.demo.domain.post.presentation.dto.request.UpdatePostRequest;
import com.example.demo.domain.post.presentation.dto.request.WritePostRequest;
import com.example.demo.domain.post.presentation.dto.response.PostDetailResponse;
import com.example.demo.domain.post.presentation.dto.response.PostSummaryResponse;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {

    private final PostService postService;
    private final UserUtils userUtils;

    @GetMapping("/{postId}")
    public PostDetailResponse getPost(@PathVariable Long postId) {
        System.out.println("postId: " + postId);
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDetailResponse writePost(@RequestBody WritePostRequest writePostRequest) {
        User user = userUtils.getUserFromSecurityContext();
        return postService.writePost(writePostRequest, user);
    }

    @PatchMapping("/{postId}")
    public PostDetailResponse updatePost(@RequestBody UpdatePostRequest updatePostRequest, @PathVariable Long postId) {
        User user = userUtils.getUserFromSecurityContext();
        return postService.updatePost(updatePostRequest, postId, user);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        User user = userUtils.getUserFromSecurityContext();
        postService.deletePost(postId, user);
    }

    @GetMapping
    public List<PostSummaryResponse> getAllPosts() {
        return postService.getAllPosts();
    }


}
