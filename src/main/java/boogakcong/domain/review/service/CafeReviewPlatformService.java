package boogakcong.domain.review.service;

import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.cafe.service.CafeService;
import boogakcong.domain.review.dto.request.CreateReviewRequest;
import boogakcong.domain.review.dto.request.UpdateReviewRequest;
import boogakcong.domain.review.dto.response.ReviewResponse;
import boogakcong.domain.review.entity.Review;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeReviewPlatformService {
    private final CafeReviewService cafeReviewService;
    private final CafeService cafeService;

    @Transactional
    public void createReview(CreateReviewRequest request, Long memberId) {
        Cafe cafe = cafeService.getCafeById(request.cafeId());
        // 이미 쓴 리뷰가 있는지 확인하는 로직이 필요합니다.
        if (cafeReviewService.existsReview(memberId, request.cafeId())) {
            throw new BusinessException(BusinessError.REVIEW_ALREADY_EXISTS);
        }

        cafeReviewService.createReview(
                Review.builder()
                        .cafeId(request.cafeId())
                        .content(request.content())
                        .memberId(memberId)
                        .build());
    }

    @Transactional
    public void updateReview(UpdateReviewRequest request, Long userId) {
        Review review = cafeReviewService.getReviewById(request.reviewId());

        if (!review.getMemberId().equals(userId)) {
            throw new BusinessException(BusinessError.REVIEW_UNAUTHORIZED);
        }

        cafeReviewService.updateContent(review, request.content());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = cafeReviewService.getReviewById(reviewId);

        if (!review.getMemberId().equals(userId)) {
            throw new BusinessException(BusinessError.REVIEW_UNAUTHORIZED);
        }

        cafeReviewService.deleteReview(reviewId);
    }

    public List<ReviewResponse> getMyReview(Long userId) {
        List<Review> list = cafeReviewService.getMyReview(userId)
                .stream()
                .toList();

        List<Cafe> cafes = cafeService.getCafeListByIds(
                list.stream()
                        .map(Review::getCafeId)
                        .toList()
        );

        return list.stream()
                .map(review -> {
                    Cafe cafe = cafes.stream()
                            .filter(c -> c.getId().equals(review.getCafeId()))
                            .findFirst()
                            .orElseThrow(() -> new BusinessException(BusinessError.CAFE_NOT_FOUND));

                    return ReviewResponse.fromEntity(review, cafe.getName());
                })
                .toList();
    }

    public List<Review> getAllReview() {
        return cafeReviewService.getAllReview()
                .stream()
                .toList();
    }

    public List<ReviewResponse> getReviewListByCafeId(Long cafeId) {
        List<Review> list = cafeReviewService.getReviewListByCafeId(cafeId)
                .stream()
                .toList();

        Cafe cafe = cafeService.getCafeById(cafeId);

        return list.stream()
                .map(review -> ReviewResponse.fromEntity(review, cafe.getName()))
                .toList();
    }
}
