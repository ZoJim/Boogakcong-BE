package boogakcong.domain.cafe._notification.dto.request;

public record NotificationRequest(
        Long cafeId,
        String content
) {
}
