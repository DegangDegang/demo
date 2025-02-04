package com.example.demo.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    NOTIFICATION_FCM_TOKEN_INVALID(400, "FCM Token이 유효하지 않습니다."),
    NOT_NULL_TOKEN(400, "토큰 값이 NULL일 수 없습니다"),
    MISMATCH_USER_OAUTH_ID(400, "유저의  OAuth ID값이 토큰 ID 값과 일치하지 않습니다"),
    ESSAY_NOT_HOST(400, "essay의 주인이 아닙니다"),
    ESSAY_COMMENT_NOT_HOST(400, "essay 댓글의 주인이 아닙니다"),
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),

    /* 403 UNAUTHORIZED : 인증되지 않은 사용자 */
    REGISTER_EXPIRED_TOKEN(HttpStatus.FORBIDDEN.value(),"refreshToken expired."),
    POST_PERMISSION(403, "게시글에 대한 수정, 삭제 권한이 없습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND(404, "게시글을 찾을 수 없습니다."),
    ESSAY_NOT_FOUND(404, "장편을 찾을 수 없습니다"),
    ESSAY_LIKE_NOT_FOUND(404, "장편 좋아요를 찾을 수 없습니다"),
    ESSAY_LIKE_ALREADY_EXISTS(404, "이미 좋아요를 누른 상태 입니다"),
    ESSAY_COMMENT_NOT_FOUND(404, "장편 댓글을 찾을 수 없습니다"),

    BAD_FILE_EXTENSION(404,  "FILE extension error"),
    FILE_EMPTY(404,  "FILE empty"),
    FILE_UPLOAD_FAIL(404,  "FILE upload fail"),
    FILE_OVER_SIZE(404,  "FILE 크기가 10mb를 초과 하였습니다"),
    PROFILE_IMAGE_NOT_FOUND(404,  "프로필 이미지를 찾을 수 없습니다"),
    BAD_PROFILE_PATH(404,  "profile path error"),
    URL_INPUT_ERROR(404,"url 입력 오류입니다"),
    METHOD_NOT_ALLOWED(405,"http 메소드가 잘못되었습니다."),

    /* 500 */
    INTERNAL_SERVER_ERROR(500,"서버 에러"),
    NO_SUCH_PUBLIC_KEY(500, " kid 값이 일치하는 데이터가 없습니다."),
    NO_SUCH_RSA_ALGORITHM(500, "RSA 암호화 알고리즘을 지원하지 않는 환경입니다."),
    SHORT_ESSAY_NOT_FOUND(404, "단필을 찾을 수 없습니다."),
    UNAUTHORIZED_SHORT_ESSAY(403, "작성자가 아니므로 단필을 삭제할 수 없습니다."),
    ALREADY_LIKED(400,"이미 단필 좋아요를 눌렀습니다." ),
    SHORT_ESSAY_LIKE_NOT_FOUND(404, "단필 좋아요를 누르지 않았습니다.");

    private final int status;
    private final String reason;
}