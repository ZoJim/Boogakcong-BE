package boogakcong.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessError {
    // MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEMBER_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    MEMBER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),

    // CAFE
    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND, "카페를 찾을 수 없습니다."),
    CAFE_OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "카페 소유자를 찾을 수 없습니다."),
    CAFE_ALREADY_HAS_OWNER(HttpStatus.CONFLICT, "이미 소유자가 있는 카페입니다."),
    CAFE_OWNER_NOT_REQUESTED(HttpStatus.BAD_REQUEST, "카페 소유자가 요청 상태가 아닙니다."),

    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;
}
