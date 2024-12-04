package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.cafe.repository.CafeRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import boogakcong.global.util.KakaoMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    public Cafe getCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BusinessException(BusinessError.CAFE_NOT_FOUND));
    }

    // 도로명 주소와 이름을 기준으로 중복되지 않은 카페 필터링
    public List<KakaoMapResponse> filterNonExistingCafes(List<KakaoMapResponse> responses) {
        List<String> roadAddresses = responses.stream()
                .map(KakaoMapResponse::road_address_name)
                .toList();

        List<String> names = responses.stream()
                .map(KakaoMapResponse::place_name)
                .toList();

        // 기존 데이터 조회
        List<Cafe> existingCafes = cafeRepository.findAllByRoadAddressAndName(roadAddresses, names);


        // 이미 존재하는 "도로명|카페이름" 조합 생성
        List<String> existingKeys = existingCafes.stream()
                .map(cafe -> cafe.getRoadAddress() + "|" + cafe.getName())
                .toList();

        // 새로운 카페 정보 중 중복되지 않은 것만 필터링
        return responses.stream()
                .filter(response -> {
                    String key = response.road_address_name() + "|" + response.place_name();
                    return !existingKeys.contains(key);
                })
                .toList();
    }

    public void save(Cafe cafe) {
        cafeRepository.save(cafe);
    }
}
