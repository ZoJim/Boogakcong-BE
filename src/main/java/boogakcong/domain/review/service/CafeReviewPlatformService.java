package boogakcong.domain.review.service;

import boogakcong.domain.review.dto.request.CreateReviewRequest;
import boogakcong.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeReviewPlatformService {
    private final CafeReviewService cafeReviewService;

    @Transactional
    public void createReview(CreateReviewRequest request, Long memberId) {
        cafeReviewService.createReview(
                Review.builder()
                        .cafeId(request.cafeId())
                        .content(request.content())
                        .memberId(memberId)
                        .build());
    }
}
