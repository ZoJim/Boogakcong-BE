package boogakcong.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private final RestClient.Builder restClient;

    @Value("${kakao.map.api.key}")
    private String kakaoMapApiKey;

    private static final String KAKAO_MAP_CATEGORY_API_PATH = "/v2/local/search/category.json";
    private static final String KAKAO_CATEGORY_GROUP_CODE_CAFE = "CE7";

    public List<KakaoMapResponse> searchByCategory(String x, String y, String radius) {
        List<KakaoMapResponse> allResults = new ArrayList<>();
        int page = 1; // 시작 페이지
        int size = 15; // 페이지당 데이터 개수
        int pageableCount = Integer.MAX_VALUE; // 처음에는 최대값으로 설정

        while (page <= pageableCount) {
            // API 요청
            int finalPage = page;
            Map response = restClient
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("dapi.kakao.com")
                            .path(KAKAO_MAP_CATEGORY_API_PATH)
                            .queryParam("category_group_code", KAKAO_CATEGORY_GROUP_CODE_CAFE)
                            .queryParam("x", x)
                            .queryParam("y", y)
                            .queryParam("radius", radius)
                            .queryParam("size", size) // 페이지당 데이터 개수 설정
                            .queryParam("page", finalPage) // 현재 페이지 설정
                            .build()
                    )
                    .header("Authorization", "KakaoAK " + kakaoMapApiKey)
                    .retrieve()
                    .body(Map.class);

            // 응답 데이터에서 documents 가져오기
            List<Map<String, Object>> documents = (List<Map<String, Object>>) response.get("documents");
            for (Map<String, Object> document : documents) {
                allResults.add(convertToKakaoMapResponse(document));
            }

            // 응답 데이터에서 meta 정보 가져오기
            Map<String, Object> meta = (Map<String, Object>) response.get("meta");
            pageableCount = (int) meta.get("pageable_count"); // 요청 가능한 전체 페이지 수 갱신

            System.out.printf("Fetched page %d/%d%n", page, pageableCount);

            if (meta.get("is_end") != null && (boolean) meta.get("is_end")) {
                break; // 마지막 페이지인 경우 중단
            }

            page++; // 다음 페이지로 이동
        }

        return allResults;
    }

    // Map 데이터를 KakaoMapResponse로 변환
    private KakaoMapResponse convertToKakaoMapResponse(Map<String, Object> map) {
        return new KakaoMapResponse(
                (String) map.get("address_name"),
                (String) map.get("category_group_code"),
                (String) map.get("category_group_name"),
                (String) map.get("category_name"),
                (String) map.get("distance"),
                (String) map.get("id"),
                (String) map.get("phone"),
                (String) map.get("place_name"),
                (String) map.get("place_url"),
                (String) map.get("road_address_name"),
                (String) map.get("x"),
                (String) map.get("y")
        );
    }

    public int getWalkingTime(String fromX, String fromY, String toX, String toY) {
        String url = "https://apis-navi.kakaomobility.com/v1/directions"; // 카카오 길찾기 API의 엔드포인트

        // API 요청
        Map response = restClient
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("apis-navi.kakaomobility.com")
                        .path("/v1/directions")
                        .queryParam("origin", fromX + "," + fromY) // 출발 좌표
                        .queryParam("destination", toX + "," + toY) // 도착 좌표
                        .queryParam("waypoints", "") // 중간 경로 (필요 없으면 빈 값)
                        .queryParam("vehicle_type", "1") // 도보 경로 (1: 도보, 2: 자동차)
                        .queryParam("priority", "distance") // 최단 거리 기준
                        .build()
                )
                .header("Authorization", "KakaoAK " + kakaoMapApiKey) // API 키 추가
                .retrieve()
                .body(Map.class); // 응답을 Map 형태로 변환

        // 응답 처리
        List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
        if (routes != null && !routes.isEmpty()) {
            // 첫 번째 경로의 duration 값을 가져옴 (초 단위)
            Map<String, Object> firstRoute = routes.get(0);
            List<Map<String, Object>> sections = (List<Map<String, Object>>) firstRoute.get("sections");
            if (sections != null && !sections.isEmpty()) {
                int duration = (int) sections.get(0).get("duration"); // 초 단위 시간
                return duration / 60; // 분 단위로 변환
            }
        }

        return -1; // 실패 시 -1 반환
    }
}
