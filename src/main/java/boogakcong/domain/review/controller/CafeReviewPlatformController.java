package boogakcong.domain.review.controller;

import boogakcong.domain.review.dto.request.CreateReviewRequest;
import boogakcong.domain.review.service.CafeReviewPlatformService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes/review")
public class CafeReviewPlatformController {
    private final CafeReviewPlatformService cafeReviewPlatformService;

    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void createReview(
            @Validated @RequestBody CreateReviewRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cafeReviewPlatformService.createReview(
                request,
                userDetails.getUserId()
        );
    }

}
