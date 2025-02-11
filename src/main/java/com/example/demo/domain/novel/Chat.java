package com.example.demo.domain.novel;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "chats")
public class Chat implements Serializable {

    private static final long serialVersionUID = 5090380600159441769L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "chat_id")
    private Long id;

    private String message;

    private Long userId;

    private String userName;

    private String createdAt;

}