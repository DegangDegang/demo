package com.example.demo.domain.novel.presentation;

import com.example.demo.domain.novel.presentation.dto.ChatResponse;
import com.example.demo.domain.novel.service.ChatRedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatDataController {

    private final ChatRedisCacheService cacheService;


}