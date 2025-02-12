package com.example.demo.domain.novel.presentation.dto;

import com.example.demo.domain.essay.presentation.dto.response.HostInfoDto;
import lombok.Getter;
import java.util.List;

@Getter
public class ChatResponse {

    private Long myParticipationId;

    private Long reservationId;

    private Integer passengerNum;

    private Integer currentNum;

    private HostInfoDto hostInfo;

    private Boolean iHost;

    private List<ChatHistoryDto> chatHistoryDtoList;

    public ChatResponse(Long ParticipationId, ReservationBaseInfoVo reservationBaseInfoVo, UserInfoVO userInfoVO, boolean iHost, List<ChatHistoryDto> ChatHistoryDtoList) {

        myParticipationId = ParticipationId;
        reservationId = reservationBaseInfoVo.getReservationId();
        passengerNum = reservationBaseInfoVo.getPassengerNum();
        currentNum = reservationBaseInfoVo.getCurrentNum();
        hostInfo = new HostInfoDto(userInfoVO);
        this.iHost = iHost;
        chatHistoryDtoList = ChatHistoryDtoList;

    }


}
