package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.domain.repository.PostRepository;
import com.example.demo.domain.post.presentation.dto.request.UpdatePostRequest;
import com.example.demo.domain.post.presentation.dto.request.WritePostRequest;
import com.example.demo.domain.post.presentation.dto.response.PostDetailResponse;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.global.exception.post.PostNotFoundException;
import com.example.demo.global.exception.post.PostPermissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceImplTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;


    User testUser, testUser2;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .nickname("닉네임1")
                .email("test@naver.com")
                .build();

        testUser2 = User.builder()
                .nickname("닉네임2")
                .email("test@naver.com")
                .build();
        userRepository.save(testUser);
        userRepository.save(testUser2);
    }

    @Test
    @DisplayName("게시글 목록 반환 성공")
    void getAllPost() {

        // given: 2개의 게시글이 저장되어 있을 때
        Post post1 = Post.builder()
                .title("제목1")
                .content("내용1")
                .user(testUser)
                .build();

        Post post2 = Post.builder()
                .title("제목2")
                .content("내용2")
                .user(testUser)
                .build();
        postRepository.save(post1);
        postRepository.save(post2);

        // when & then
        assertThat(postService.getAllPosts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 작성")
    void writePost() {

        // given 게시글 작성 요청이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");

        // when 게시글 작성을 하면
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // then
        assertThat(postDetailResponse.getTitle()).isEqualTo(writePostRequest.getTitle());
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void getPostById_success() {

        // given 게시글이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // when 게시글을 조회하면
        PostDetailResponse postById = postService.getPostById(postDetailResponse.getId());

        // then 게시글이 성공적으로 반환된다.
        assertThat(postDetailResponse.getId()).isEqualTo(postById.getId());
    }

    @Test
    @DisplayName("게시글 조회 실패")
    void getPostById_fail() {

        Assertions.assertThrows(PostNotFoundException.class, () -> postService.getPostById(123L));
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost_success() {

        // given 게시글이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // when 게시글을 수정하면
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("수정1");
        updatePostRequest.setContent("수정2");
        PostDetailResponse updatedPost = postService.updatePost(updatePostRequest, postDetailResponse.getId(), testUser);

        // then 성공적으로 수정된다.
        assertThat(updatedPost.getTitle()).isEqualTo(updatePostRequest.getTitle());
    }

    @Test
    @DisplayName("게시글 수정 실패")
    void updatePost_fail() {
        // given 게시글이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // when & then 다른 유저가 게시글을 수정하면 예외가 발생한다.
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("수정1");
        updatePostRequest.setContent("수정2");
        Assertions.assertThrows(PostPermissionException.class, () -> postService.updatePost(updatePostRequest, postDetailResponse.getId(), testUser2));
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost_success() {
        // given 게시글이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // when 게시글을 삭제하면
        postService.deletePost(postDetailResponse.getId(), testUser);

        // then 게시글이 삭제된다.
        Assertions.assertThrows(PostNotFoundException.class, () -> postService.getPostById(postDetailResponse.getId()));
    }

    @Test
    @DisplayName("게시글 삭제 실패")
    void deletePost_fail() {

        // given 게시글이 있을 때
        WritePostRequest writePostRequest = new WritePostRequest();
        writePostRequest.setTitle("제목");
        writePostRequest.setContent("내용");
        PostDetailResponse postDetailResponse = postService.writePost(writePostRequest, testUser);

        // when & then 권한이 없는 유저가 게시글을 삭제하면 예외가 발생한다.
        Assertions.assertThrows(PostPermissionException.class, () -> postService.deletePost(postDetailResponse.getId(), testUser2));
    }

}