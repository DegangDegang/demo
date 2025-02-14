package com.example.demo.global.stomp;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.global.property.JwtProperties;
import com.example.demo.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.example.demo.global.utils.user.UserUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final UserUtils userUtils;
    private final JwtTokenProvider jwtTokenProvider;
    //public static final String SIMP_DESTINATION = "roomId";
    private final JwtProperties jwtProperties;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("accessor : {}", accessor);

        if (StompCommand.CONNECT == accessor.getCommand()) {

            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());

            String accessToken = jwtTokenProvider.resolveTokenWeb(rawHeader);

            jwtTokenProvider.validateToken(accessToken);

            Long userId = jwtTokenProvider.getUserId(accessToken);

            log.info("==========연결 완료=========={}","test");
            log.info("userid = {}",userId);

        }
        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

//            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());
//
//            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);
//
//            jwtTokenProvider.validateToken(token);

            //String roomId = (String)message.getHeaders().get(SIMP_DESTINATION);

            log.info("==========구독 완=========={}","test");
            //log.info("roomId = {}",roomId);


        } else if (StompCommand.SEND == accessor.getCommand()) {

            log.info("==========send를 타기는 하는건가==========");


        } else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {

            log.info("========== 구독 해제 완 =========={}","test");

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {

            log.info("========== 연결 끊기 완 =========={}","test");

        }

        return message;
    }
}