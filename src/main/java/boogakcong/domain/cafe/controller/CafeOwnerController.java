package boogakcong.domain.cafe.controller;

import boogakcong.domain.cafe.dto.request.UpdateCafeRequest;
import boogakcong.domain.cafe.service.CafeDeleteRequestService;
import boogakcong.domain.cafe.service.CafeManageService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Comment("카페 소유자가 카페를 관리하는 컨트롤러")
@Secured("ROLE_CAFE_OWNER")
@RequestMapping("/api/cafes/owners")
@RequiredArgsConstructor
public class CafeOwnerController {
    private final CafeDeleteRequestService cafeDeleteRequestService;
    private final CafeManageService cafeManageService;

    @Comment("카페 삭제 요청")
    @Secured({"ROLE_CAFE_OWNER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_NORMAL_USER"})
    @DeleteMapping("/request")
    public ResponseEntity<?> deleteCafeRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "reasonId") Long reasonId
    ) {
        cafeDeleteRequestService.requestCafeDelete(userDetails.getUserId(), reasonId);
        return ResponseEntity.ok().build();
    }

    @Comment("카페 정보 수정 API")
    @PostMapping("/update")
    @Secured({"ROLE_CAFE_OWNER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> updateCafe(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateCafeRequest request
    ) {
        cafeManageService.updateCafe(userDetails.getUserId(), request);
        return ResponseEntity.ok().build();
    }

}
