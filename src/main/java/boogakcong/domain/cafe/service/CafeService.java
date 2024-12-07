package boogakcong.domain.cafe.service;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.cafe.repository.CafeRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import boogakcong.global.util.KakaoMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    public Cafe getCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BusinessException(BusinessError.CAFE_NOT_FOUND));
    }

    public List<Cafe> getCafeList() {
        return cafeRepository.findAll();
    }

    public List<Cafe> getCafeList(Pageable pageable) {
        return cafeRepository.findAll(pageable).getContent();
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

    public void requestDeleteCafe(Long cafeId) {
        Cafe cafe = getCafeById(cafeId);
    }

    public void deleteCafe(Long cafeId) {
        cafeRepository.deleteById(cafeId);
    }

    public List<Cafe> getCafeListByIds(List<Long> list) {
        return cafeRepository.findAllById(list);
    }

    public List<Long> getNewCafeCountPerDay(LocalDateTime startDate) {
        LocalDateTime endDate = startDate.plusDays(7);  // endDate는 startDate의 다음 날로 설정
        return cafeRepository.countNewCafesPerDay(startDate, endDate);
    }

    public Long getTotalCafeCount() {
        return cafeRepository.count();
    }
}
