package boogakcong.domain.cafe.dto.response;

import boogakcong.domain.cafe.entity.Cafe;
import lombok.Builder;

@Builder
public record CafeSimpleResponse(
        Long id,
        String name,
        String address,
        String phoneNumber,
        Integer timeFromMainGate,
        String placeUrl

        // FIXME: 카페 사진 필요할지도..?
) {
    public static CafeSimpleResponse fromEntity(Cafe cafe) {
        return CafeSimpleResponse.builder()
                .id(cafe.getId())
                .name(cafe.getName())
                .address(cafe.getRoadAddress() + " " + cafe.getAddressDetail())
                .phoneNumber(cafe.getPhoneNumber())
                .timeFromMainGate(cafe.getTimeFromMainGate())
                .placeUrl(cafe.getPlaceUrl())
                .build();
    }
}
