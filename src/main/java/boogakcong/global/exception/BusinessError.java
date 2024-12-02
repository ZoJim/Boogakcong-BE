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

    ;

    private final HttpStatus httpStatus;

    private final String message;
}
