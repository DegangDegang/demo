package com.example.demo.domain.user.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.user.domain.Follow;

import feign.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByToUserId(Long toUserId);

	@Query("SELECT f FROM Follow f JOIN FETCH f.toUser WHERE f.fromUser.id = :userId")
	Slice<Follow> findByFromUserIdWithUser(@Param("userId") Long userId, Pageable pageable);

	@Query("SELECT f FROM Follow f JOIN FETCH f.fromUser WHERE f.toUser.id = :userId")
	Slice<Follow> findByToUserIdWithUser(@Param("userId") Long userId, Pageable pageable);

	boolean existsByFromUserIdAndToUserId(Long userId, Long findUserId);
}
