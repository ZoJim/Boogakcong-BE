package boogakcong.domain.review.dto.response;

import boogakcong.domain.review.entity.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewResponse(
        Long id,
        Long cafeId,
        String cafeName,
        String content,
        LocalDateTime createdAt
) {
    public static ReviewResponse fromEntity(Review review, String cafeName) {
        return new ReviewResponse(
                review.getId(),
                review.getCafeId(),
                cafeName,
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
