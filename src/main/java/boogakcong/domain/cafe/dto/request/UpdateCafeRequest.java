package boogakcong.domain.cafe.dto.request;

public record UpdateCafeRequest(
        String notice,
        boolean isWifi,
        int outletCount,
        int maxPeoplePerTable
) {

}
