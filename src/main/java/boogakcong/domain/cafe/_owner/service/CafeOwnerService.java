package boogakcong.domain.cafe._owner.service;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.repository.CafeOwnerRepository;
import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.cafe.service.CafeService;
import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.service.MemberService;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeOwnerService {
    private final CafeOwnerRepository cafeOwnerRepository;
    private final MemberService memberService;
    private final CafeService cafeService;

    public void requestCafeOwner(Long userId, Long cafeId) {
        // 회원이 유효한가?
        Member member = memberService.getMemberById(userId);

        // 카페가 유효한가?
        Cafe cafe = cafeService.getCafeById(cafeId);

        // 카페에 소유자가 이미 존재하는가?
        if (cafeOwnerRepository.existsByCafeId(cafeId)) {
            throw new BusinessException(BusinessError.CAFE_ALREADY_HAS_OWNER);
        }

        // 카페 소유자로 등록
        cafeOwnerRepository.save(
                CafeOwner
                        .builder()
                        .ownerId(member.getId())
                        .cafeId(cafe.getId())
                        .allocationStatus(CafeOwner.AllocationStatus.REQUESTED)
                        .build()
        );
    }
}
