package boogakcong.domain.cafe._owner.controller;

import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes/owners")
public class CafeOwnerController {
    private final CafeOwnerService cafeOwnerService;

    @Comment("카페 소유자 신청")
    @Secured({"ROLE_USER"})
    @RequestMapping("/request")
    public ResponseEntity<?> requestCafeOwner(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long cafeId
    ) {
        cafeOwnerService.requestCafeOwner(
                userDetails.getUserId(),
                cafeId
        );
        return ResponseEntity.ok().build();
    }

    @Comment("카페 소유자 승인/반려")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @RequestMapping("/accept")
    public ResponseEntity<?> acceptCafeOwner(
            @RequestParam Long userId,
            @RequestParam Long cafeId,
            @RequestParam Boolean accept
    ) {
        cafeOwnerService.acceptCafeOwner(
                userId,
                cafeId,
                accept
        );
        return ResponseEntity.ok().build();
    }
}
