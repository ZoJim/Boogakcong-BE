package boogakcong.domain.review.service;

import boogakcong.domain.review.dto.request.CreateReviewRequest;
import boogakcong.domain.review.entity.Review;
import boogakcong.domain.review.repository.ReviewRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(Review review) {
        reviewRepository.save(review);
    }


    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public boolean existsReview(Long memberId, Long cafeId) {
        return reviewRepository.existsByMemberIdAndCafeId(memberId, cafeId);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(BusinessError.REVIEW_NOT_FOUND));

    }

    @Transactional
    public void updateContent(Review review, String content) {
        review.setContent(content);
        reviewRepository.save(review);
    }
}
