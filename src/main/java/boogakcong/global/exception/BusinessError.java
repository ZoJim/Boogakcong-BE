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
    CAFE_OWNER_ALREADY(HttpStatus.CONFLICT, "이미 카페 소유자로 등록되어 있습니다."),
    CAFE_ALREADY_HAS_OWNER(HttpStatus.CONFLICT, "이미 소유자가 있는 카페입니다."),
    CAFE_OWNER_NOT_REQUESTED(HttpStatus.BAD_REQUEST, "카페 소유자가 요청 상태가 아닙니다."),
    CAFE_OWNER_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "카페 소유자 요청을 찾을 수 없습니다."),
    CAFE_DELETE_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "카페 삭제 요청을 찾을 수 없습니다."),
    CAFE_DELETE_REQUEST_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 카페 삭제 요청이 존재합니다."),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),

    // REVIEW
    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 리뷰를 작성하였습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    REVIEW_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리뷰 작성자만 수정할 수 있습니다."),

    // POSTING
    POSTING_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POSTING_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "게시글 작성자만 수정할 수 있습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;
}
