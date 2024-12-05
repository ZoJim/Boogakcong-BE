package boogakcong.domain.review.dto.request;

import jakarta.validation.constraints.Size;

public record CreateReviewRequest(
        Long cafeId,
        @Size(min = 1, max = 100)
        String content
) {
}
