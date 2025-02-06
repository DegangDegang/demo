package com.example.demo.domain.novel.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.novel.service.NovelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/novels")
public class NovelController {

	private final NovelService novelService;

	// @GetMapping("/{novelId}")





}
