package boogakcong.global.util;

import java.util.List;

public record KakaoDirectionResponse(
        List<Route> routes
) {
    public record Route(
            List<Section> sections
    ) {
    }

    public record Section(
            int duration // Duration in seconds
    ) {
    }
}
