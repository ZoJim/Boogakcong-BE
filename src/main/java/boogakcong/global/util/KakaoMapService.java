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
        int page = 1;
        int size = 15;
        int totalCount = Integer.MAX_VALUE;


        while ((page - 1) * size < totalCount) {
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

            System.out.println(page);
            System.out.println(meta.get("total_count"));
            totalCount = (int) meta.get("total_count");
            boolean isEnd = (boolean) meta.get("is_end");

            if (isEnd) {
                break;
            }

            page++; // 다음 페이지 요청
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
}
