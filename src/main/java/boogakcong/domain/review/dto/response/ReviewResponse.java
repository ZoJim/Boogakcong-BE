package boogakcong.domain.review.dto.response;

import boogakcong.domain.review.entity.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewResponse(
        Long id,
        Long cafeId,
        String content,
        LocalDateTime createdAt
) {
    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getCafeId(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
