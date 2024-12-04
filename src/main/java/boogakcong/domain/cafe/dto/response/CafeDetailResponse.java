package boogakcong.domain.cafe.dto.response;

import lombok.Builder;

@Builder
public record CafeDetailResponse(
        Long id,
        String name,
        String phoneNumber,
        String roadAddress,
        String addressDetail,
        Double latitude,
        Double longitude,
        String placeUrl,
        Integer outletCount,
        Integer maxPeoplePerTable
) {
}
