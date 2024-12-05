package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe._notification.dto.request.NotificationRequest;
import boogakcong.domain.cafe._notification.entity.CafeNotification;
import boogakcong.domain.cafe._notification.service.CafeNotificationService;
import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.domain.cafe.dto.request.UpdateCafeRequest;
import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.global.security.UserDetailsImpl;
import boogakcong.global.util.KakaoMapResponse;
import boogakcong.global.util.KakaoMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeManageService {
    private final KakaoMapService kakaoMapService;
    private final CafeService cafeService;
    private final CafeOwnerService cafeOwnerService;
    private final CafeNotificationService cafeNotificationService;
    private static final String PUSAN_UNIV_X = "129.0864402";
    private static final String PUSAN_UNIV_Y = "35.2314337";
    private static final String SEARCH_RADIUS = "1000";

    public void registerCafeByKakao() {
        // 1. 카카오 API로부터 카페 목록 가져오기
        List<KakaoMapResponse> kakaoMapResponses = kakaoMapService.searchByCategory(PUSAN_UNIV_X, PUSAN_UNIV_Y, SEARCH_RADIUS);

        // 2. 중복되지 않는 카페 정보 필터링
        List<KakaoMapResponse> nonExistingCafes = cafeService.filterNonExistingCafes(kakaoMapResponses);

        // 3. 새로운 카페 저장
        nonExistingCafes.forEach(response -> {
            Cafe cafe = Cafe.builder()
                    .name(response.place_name())
                    .roadAddress(response.road_address_name())
                    .latitude(Double.parseDouble(response.y()))
                    .longitude(Double.parseDouble(response.x()))
                    .phoneNumber(response.phone())
                    .addressDetail(response.address_name())
                    .placeUrl(response.place_url())
                    .build();
            cafeService.save(cafe);
        });
    }

    public void updateCafe(Long userId, UpdateCafeRequest request) {
        CafeOwner cafeOwner = cafeOwnerService.findByOwnerId(userId);
        Cafe cafe = cafeService.getCafeById(cafeOwner.getCafeId());
        cafe.update(request);
        cafeService.save(cafe);
        cafeNotificationService.updateNotification(new NotificationRequest(cafe.getId(), request.notice()));
    }
}
