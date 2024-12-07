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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeManageService {
    private final KakaoMapService kakaoMapService;
    private final CafeService cafeService;
    private final CafeOwnerService cafeOwnerService;
    private final CafeNotificationService cafeNotificationService;
    private static final String PUSAN_UNIV_X = "129.081035";
    private static final String PUSAN_UNIV_Y = "35.233519";
    private static final String SEARCH_RADIUS = "1200";

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
                    .timeFromMainGate(kakaoMapService.getWalkingTime("\n" +
                            PUSAN_UNIV_X, PUSAN_UNIV_Y, response.x(), response.y()))
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

    public List<CafeResponse> getCafeCount() {
        // 최근 7일간 등록된 카페 수
        List<Long> newCafeCountPerDay = cafeService.getNewCafeCountPerDay(LocalDateTime.now().minusDays(7));

        // 전체 카페 수
        Long totalCafeCount = cafeService.getTotalCafeCount();

        List<CafeResponse> cafeResponses = new ArrayList<>();
        long cumulativeCafeCount = totalCafeCount;

        // 하루별로 등록된 카페 수와 그날의 전체 카페 수를 계산
        for (Long newCafeCount : newCafeCountPerDay) {
            // 그날 등록된 카페 수만큼 전체 카페 수 누적
            cumulativeCafeCount -= newCafeCount;
            cafeResponses.add(new CafeResponse(newCafeCount, cumulativeCafeCount));
        }

        return cafeResponses;
    }

    public record CafeResponse(
            Long newCafeCount,
            Long totalCafeCount
    ) {
    }
}
