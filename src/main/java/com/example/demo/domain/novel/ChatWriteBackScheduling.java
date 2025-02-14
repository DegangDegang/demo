package com.example.demo.domain.novel;


import com.example.demo.domain.novel.repo.NovelRepository;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWriteBackScheduling {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;
    private final ChatJdbcRepository chatJdbcRepository;
    private final UserUtils userUtils;
    private final NovelRepository novelRepository;


    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "5,15,25,35,45,55 * * * * *")
    @Transactional
    public void writeBack() {
        log.info("------Scheduling start------");


        BoundZSetOperations<String, ChatMessageSaveDto> setOperations = chatRedisTemplate.boundZSetOps("NEW_CHAT");
        ScanOptions scanOptions = ScanOptions.scanOptions().build();

        List<ChatMessageSaveDto> chatList = new ArrayList<>();
        try (Cursor<ZSetOperations.TypedTuple<ChatMessageSaveDto>> cursor = setOperations.scan(scanOptions)) {
            while (cursor.hasNext()) {
                ZSetOperations.TypedTuple<ChatMessageSaveDto> chatMessageDto = cursor.next();
                ChatMessageSaveDto chatMessage = chatMessageDto.getValue();
                chatList.add(chatMessage);
            }

            // 데이터를 데이터베이스에 일괄 삽입합니다.
            chatJdbcRepository.batchInsertRoomInventories(chatList);

            // Redis의 데이터를 삭제합니다.
            redisTemplate.delete("NEW_CHAT");

        } catch (Exception e) {
            log.error("Error during scheduling: ", e);
        }

        log.info("-----Scheduling done------");
    }
}