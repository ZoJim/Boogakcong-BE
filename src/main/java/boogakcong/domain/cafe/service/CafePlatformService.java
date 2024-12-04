package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe.dto.response.CafeDetailResponse;
import boogakcong.domain.cafe.dto.response.CafeSimpleResponse;
import boogakcong.domain.cafe.entity.Cafe;
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

    public List<CafeSimpleResponse> getCafeList(Pageable pageable) {
        return cafeService.getCafeList(pageable)
                .stream()
                .map(CafeSimpleResponse::fromEntity)
                .toList();
    }

    public CafeDetailResponse getCafeById(Long cafeId) {
        Cafe cafe = cafeService.getCafeById(cafeId);
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
                .build();
    }
}
