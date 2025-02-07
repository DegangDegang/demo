package com.example.demo.domain.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.demo.domain.user.presentation.dto.response.FollowDeleteNotification;
import com.example.demo.domain.user.presentation.dto.response.FollowNotifyInfo;
import com.example.demo.domain.user.presentation.dto.response.UserDetailResponse;
import com.example.demo.domain.user.presentation.dto.response.UserProfileResponse;

public interface UserService {

	UserDetailResponse findById(Long userId, User user);

	Slice<UserProfileResponse> findAll(String nickname, Pageable pageable, User user);

	UserDetailResponse update(UpdateUserRequest updateUserRequest, User user);

	Slice<UserProfileResponse> getFollowers(Pageable pageable, Long userId);

	Slice<UserProfileResponse> getFollowings(Pageable pageable, Long userId);

	FollowNotifyInfo follow(Long toId, User user);

	FollowDeleteNotification unfollow(Long toId, User user);
}
