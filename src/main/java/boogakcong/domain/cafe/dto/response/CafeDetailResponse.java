package boogakcong.domain.cafe.dto.response;

import boogakcong.domain.review.dto.response.ReviewResponse;
import lombok.Builder;

import java.util.List;

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
        Integer maxPeoplePerTable,
        String notice,
        List<ReviewResponse> reviewResponse
) {
}
