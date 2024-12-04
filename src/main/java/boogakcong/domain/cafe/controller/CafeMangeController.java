package boogakcong.domain.cafe.controller;

import boogakcong.domain.cafe.service.CafeManageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Comment("카페를 관리하는 컨트롤러")
@RequestMapping("/api/cafes")
@RestController
@RequiredArgsConstructor
public class CafeMangeController {
    private final CafeManageService cafeManageService;


    // 카카오 API를 이용하여 카페를 등록하는 메소드
    @PostMapping("/kakao")
    public ResponseEntity<?> registerCafeByKakao() {
        cafeManageService.registerCafeByKakao();
        return ResponseEntity.ok().build();
    }

}
