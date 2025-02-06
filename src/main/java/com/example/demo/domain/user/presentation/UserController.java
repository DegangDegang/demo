package com.example.demo.domain.user.presentation;

import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.demo.domain.user.presentation.dto.response.UserDetailResponse;
import com.example.demo.domain.user.presentation.dto.response.UserProfileResponse;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.utils.user.UserUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	private final UserUtils userUtils;

	@GetMapping("/{userId}")
	public UserDetailResponse getUser(@PathVariable Long userId) {
		User user = userUtils.getUserFromSecurityContext();
		return userService.findById(userId, user);
	}

	@GetMapping("/my")
	public UserDetailResponse getMyUser() {
		User user = userUtils.getUserFromSecurityContext();
		return userService.findById(user.getId(), user);
	}

	@GetMapping
	public Slice<UserProfileResponse> getUsers(
		@PageableDefault Pageable pageable,
		@RequestParam(value = "nickname", required = false) String nickname ) {

		User user = userUtils.getUserFromSecurityContext();
		return userService.findAll(nickname, pageable, user);
	}

	@PatchMapping
	public UserDetailResponse updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
		User user = userUtils.getUserFromSecurityContext();
		return userService.update(updateUserRequest, user);
	}

	@GetMapping("/{userId}/follower")
	public Slice<UserProfileResponse> getFollowers(@PathVariable Long userId, @PageableDefault Pageable pageable) {
		return userService.getFollowers(pageable, userId);
	}

	@GetMapping("/{userId}/following")
	public Slice<UserProfileResponse> getFollowing(@PathVariable Long userId, @PageableDefault Pageable pageable) {
		return userService.getFollowings(pageable, userId);
	}

	@PostMapping("/follow/{toId}")
	public void follow(@PathVariable(name = "toId") Long toId) {
		User user = userUtils.getUserFromSecurityContext();
		userService.follow(toId, user);
	}


	@DeleteMapping("/follow/{toId}")
	public void unfollow(@PathVariable(name = "toId") Long toId) {
		User user = userUtils.getUserFromSecurityContext();
		userService.unfollow(toId, user);
	}

}
