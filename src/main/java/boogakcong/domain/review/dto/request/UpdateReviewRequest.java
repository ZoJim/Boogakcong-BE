package boogakcong.domain.review.dto.request;

public record UpdateReviewRequest(
        Long reviewId,
        String content
) {
}
