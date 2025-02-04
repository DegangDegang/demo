package com.example.demo.domain.user.service;

import com.example.demo.domain.user.domain.Follow;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.FollowRepository;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.demo.domain.user.presentation.dto.response.UserDetailResponse;
import com.example.demo.domain.user.presentation.dto.response.UserProfileResponse;
import com.example.demo.global.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	@Override
	public UserDetailResponse findById(Long findUserId, User user) {

		User findUser = userRepository.findById(findUserId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
		UserDetailResponse userDetailResponse = new UserDetailResponse(findUser.getUserInfo());
		userDetailResponse.checkOwner(findUserId.equals(user.getId()));
		userDetailResponse.checkFollowed(followRepository.existsByFromUserIdAndToUserId(user.getId(), findUserId));
		return userDetailResponse;
	}

	@Override
	public Slice<UserProfileResponse> findAll(String nickname, Pageable pageable, User currentUser) {

		Slice<User> all;

		User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);

		if (nickname == null || nickname.isEmpty()) {
			all = userRepository.findAllByIdNot(user.getId(), pageable);
		} else {
			all = userRepository.findAllByNicknameContainingExcludeSelf(nickname, user.getId(),pageable);
		}
		return all.map(u -> new UserProfileResponse(u.getUserInfo()));
	}

	@Override
	public UserDetailResponse update(UpdateUserRequest updateUserRequest, User user) {

		if (!user.getNickname().equals(updateUserRequest.getNickname())) {
			validateDuplicateNickname(updateUserRequest.getNickname());
		}
		user.update(updateUserRequest.getNickname(), updateUserRequest.getProfileImgUrl(), updateUserRequest.getBiography());

		User updatedUser = userRepository.save(user);
		UserDetailResponse userDetailResponse = new UserDetailResponse(updatedUser.getUserInfo());
		userDetailResponse.checkOwner(updatedUser.getId().equals(user.getId()));
		userDetailResponse.checkFollowed(false);
		return userDetailResponse;
	}

	@Override
	public Slice<UserProfileResponse> getFollowers(Pageable pageable, Long userId) {
		userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
		Slice<Follow> followers = followRepository.findByToUserIdWithUser(userId, pageable);
		return followers.map(follow -> new UserProfileResponse(follow.getFromUser().getUserInfo()));
	}

	@Override
	public Slice<UserProfileResponse> getFollowings(Pageable pageable, Long userId) {
		userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
		Slice<Follow> followings = followRepository.findByFromUserIdWithUser(userId, pageable);
		return followings.map(follow -> new UserProfileResponse(follow.getToUser().getUserInfo()));
	}

	@Override
	public void follow(Long toId, User user) {
		User toUser = userRepository.findById(toId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
		Follow follow = new Follow(user, toUser);
		followRepository.save(follow);
	}

	@Override
	public void unfollow(Long toId, User user) {

		Follow follow = followRepository.findByToUserId(toId).orElseThrow(() -> UserNotFoundException.EXCEPTION);

		if (!follow.getFromUser().getId().equals(user.getId())) {
			throw UserNotFoundException.EXCEPTION;
		}
		followRepository.delete(follow);
	}

	private void validateDuplicateNickname(String nickname) {
		userRepository.findByNickname(nickname)
			.ifPresent(user -> { throw UserNotFoundException.EXCEPTION; });
	}
}
