package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe._notification.entity.CafeNotification;
import boogakcong.domain.cafe._notification.service.CafeNotificationService;
import boogakcong.domain.cafe.dto.response.CafeDetailResponse;
import boogakcong.domain.cafe.dto.response.CafeSimpleResponse;
import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.review.dto.response.ReviewResponse;
import boogakcong.domain.review.service.CafeReviewPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafePlatformService {
    private final CafeService cafeService;
    private final CafeNotificationService cafeNotificationService;
    private final CafeReviewPlatformService cafeReviewPlatformService;

    public List<CafeSimpleResponse> getCafeList() {
        return cafeService.getCafeList()
                .stream()
                .map(CafeSimpleResponse::fromEntity)
                .toList();
    }

    public CafeDetailResponse getCafeById(Long cafeId) {
        Cafe cafe = cafeService.getCafeById(cafeId);
        List<ReviewResponse> reviewListByCafeId = cafeReviewPlatformService.getReviewListByCafeId(cafeId);
        if (cafeNotificationService.isNotificationExist(cafeId)) {
            CafeNotification notification = cafeNotificationService.getNotificationByCafeId(cafeId);
            return CafeDetailResponse.builder()
                    .id(cafe.getId())
                    .name(cafe.getName())
                    .phoneNumber(cafe.getPhoneNumber())
                    .roadAddress(cafe.getRoadAddress())
                    .addressDetail(cafe.getAddressDetail())
                    .latitude(cafe.getLatitude())
                    .longitude(cafe.getLongitude())
                    .placeUrl(cafe.getPlaceUrl())
                    .outletCount(cafe.getOutletCount())
                    .maxPeoplePerTable(cafe.getMaxPeoplePerTable())
                    .notice(notification.getContent())
                    .reviewResponse(reviewListByCafeId)
                    .build();
        }
        return CafeDetailResponse.builder()
                .id(cafe.getId())
                .name(cafe.getName())
                .phoneNumber(cafe.getPhoneNumber())
                .roadAddress(cafe.getRoadAddress())
                .addressDetail(cafe.getAddressDetail())
                .latitude(cafe.getLatitude())
                .longitude(cafe.getLongitude())
                .placeUrl(cafe.getPlaceUrl())
                .outletCount(cafe.getOutletCount())
                .reviewResponse(reviewListByCafeId)
                .maxPeoplePerTable(cafe.getMaxPeoplePerTable())
                .build();


    }
}
