package boogakcong.domain.cafe.controller;

import boogakcong.domain.cafe.service.CafeDeleteRequestService;
import boogakcong.domain.cafe.service.CafeManageService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Comment("카페를 관리하는 컨트롤러")
@RequestMapping("/api/cafes")
@RestController
@RequiredArgsConstructor
public class CafeAdminController {
    private final CafeManageService cafeManageService;
    private final CafeDeleteRequestService cafeDeleteRequestService;


    @Comment("카카오 API를 이용하여 카페를 등록하는 API")
    @PostMapping("/kakao")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> registerCafeByKakao() {
        cafeManageService.registerCafeByKakao();
        return ResponseEntity.ok().build();
    }

    @Comment("카페 삭제 승인/반려")
    @PostMapping("/owners/delete")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> acceptCafeDelete(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "requestId") Long requestId,
            @RequestParam(name = "accept") boolean accept
    ) {
        cafeDeleteRequestService.acceptCafeDelete(requestId, accept);
        return ResponseEntity.ok().build();
    }

    @Comment("카페 삭제 요청 조회")
    @GetMapping("/owners/delete")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> getCafeDeleteRequests(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(cafeDeleteRequestService.getCafeDeleteRequests());
    }


    @Comment("카페 개수 조회")
    @GetMapping("/analysis")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> getCafeCount() {
        return ResponseEntity.ok(cafeManageService.getCafeCount());
    }
}
