package com.example.demo.domain.user.domain.repository;


import com.example.demo.domain.user.domain.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import feign.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthIdAndOauthProvider(String oauthId, String oauthProvider);

	@Query("SELECT u FROM User u WHERE (u.nickname LIKE %:nickname% OR :nickname IS NULL) AND u.id != :currentUserId")
	Slice<User> findAllByNicknameContainingExcludeSelf(@Param("nickname") String nickname, @Param("currentUserId") Long currentUserId, Pageable pageable);

	Optional<User> findByNickname(String nickname);

	@Query("SELECT u FROM User u WHERE u.id != :currentUserId")
	Slice<User> findAllByIdNot(@Param("currentUserId") Long currentUserId, Pageable pageable);

}
