package boogakcong.domain.cafe.controller;

import boogakcong.domain.cafe.dto.request.UpdateCafeRequest;
import boogakcong.domain.cafe.service.CafeManageService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Comment("카페를 관리하는 컨트롤러")
@RequestMapping("/api/cafes")
@RestController
@RequiredArgsConstructor
public class CafeMangeController {
    private final CafeManageService cafeManageService;


    @Comment("카카오 API를 이용하여 카페를 등록하는 API")
    @PostMapping("/kakao")
    public ResponseEntity<?> registerCafeByKakao() {
        cafeManageService.registerCafeByKakao();
        return ResponseEntity.ok().build();
    }


    @Comment("카페 정보 수정 API")
    @PostMapping("/owners/update")
    @Secured("ROLE_CAFE_OWNER")
    public ResponseEntity<?> updateCafe(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateCafeRequest request
    ) {
        cafeManageService.updateCafe(userDetails.getUserId(), request);
        return ResponseEntity.ok().build();
    }
}
