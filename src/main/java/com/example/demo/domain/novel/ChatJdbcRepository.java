package com.example.demo.domain.novel;

import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchInsertRoomInventories(List<ChatMessageSaveDto> chatList) {
        if (chatList == null || chatList.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO paragraph_comment (user_id, novel_id, message, created_at) "
                + "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ChatMessageSaveDto chat = chatList.get(i);
                ps.setLong(1, chat.getUserId()); // message
                ps.setLong(2, Long.parseLong(chat.getRoomId()));   // user_id
                ps.setString(3, chat.getMessage()); // user_name
                ps.setString(4, chat.getCreatedAt()); // created_at
            }

            @Override
            public int getBatchSize() {
                return chatList.size();
            }
        });
    }
}