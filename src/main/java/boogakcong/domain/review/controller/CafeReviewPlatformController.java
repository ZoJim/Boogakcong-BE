package boogakcong.domain.review.controller;

import boogakcong.domain.review.dto.request.CreateReviewRequest;
import boogakcong.domain.review.dto.request.UpdateReviewRequest;
import boogakcong.domain.review.dto.response.ReviewResponse;
import boogakcong.domain.review.entity.Review;
import boogakcong.domain.review.service.CafeReviewPlatformService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes/review")
public class CafeReviewPlatformController {
    private final CafeReviewPlatformService cafeReviewPlatformService;

    @PostMapping
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> createReview(
            @Validated @RequestBody CreateReviewRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cafeReviewPlatformService.createReview(
                request,
                userDetails.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> updateReview(
            @Validated @RequestBody UpdateReviewRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cafeReviewPlatformService.updateReview(
                request,
                userDetails.getUserId()

        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteReview(
            @PathVariable(name = "reviewId") Long reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cafeReviewPlatformService.deleteReview(
                reviewId,
                userDetails.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<List<ReviewResponse>> getMyReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ReviewResponse> myReview = cafeReviewPlatformService.getMyReview(userDetails.getUserId());
        return ResponseEntity.ok(myReview);
    }

    @GetMapping()
    @Secured({"ROLE_COMMUNITY_MANAGER", "ROLE_ADMIN"})
    public ResponseEntity<?> getAllReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Review> allReview = cafeReviewPlatformService.getAllReview();
        return ResponseEntity.ok(allReview);
    }

}
