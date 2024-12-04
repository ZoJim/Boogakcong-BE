package boogakcong.domain.cafe.controller;

import boogakcong.domain.cafe.dto.response.CafeDetailResponse;
import boogakcong.domain.cafe.dto.response.CafeSimpleResponse;
import boogakcong.domain.cafe.service.CafePlatformService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Comment("사용자 웹에서 카페를 관리하는 컨트롤러")
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
public class CafePlatformController {
    private final CafePlatformService cafePlatformService;

    @Comment("카페 목록을 조회하는 메소드")
    @GetMapping
    public ResponseEntity<List<CafeSimpleResponse>> getCafeList(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(cafePlatformService.getCafeList(pageable));
    }


    @Comment("카페 단건 조회")
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailResponse> getCafe(@PathVariable Long cafeId) {
        return ResponseEntity.ok(cafePlatformService.getCafeById(cafeId));
    }
}
