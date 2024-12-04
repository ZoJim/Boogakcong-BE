package boogakcong.domain.cafe._owner.controller;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes/owners")
public class CafeOwnerController {
    private final CafeOwnerService cafeOwnerService;

    @Comment("카페 소유자 신청")
    @Secured({"ROLE_USER"})
    @PostMapping("/request")
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

    @Comment("카페 소유자 신청 조회")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @GetMapping("/requests")
    public ResponseEntity<List<CafeOwner>> getCafeOwnerRequests() {
        return ResponseEntity.ok(cafeOwnerService.getCafeOwnerRequests());
    }

    @Comment("카페 소유자 승인/반려")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @PostMapping("/accept")
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
