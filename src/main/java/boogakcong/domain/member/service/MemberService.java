package boogakcong.domain.member.service;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.cafe.service.CafeService;
import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.repository.MemberRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;


    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(BusinessError.MEMBER_DUPLICATE_EMAIL);
        }
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void confirmCaffeOwner(Member member) {
        member.confirmCafeOwner();
        memberRepository.save(member);
    }

    @Transactional
    public void cancelCaffeOwner(Long ownerId) {
        Member member = getMemberById(ownerId);
        member.cancelCafeOwner();
        memberRepository.save(member);
    }

    public MyInfoResponse getMyInfo(UserDetailsImpl userDetails) {
        Member member = getMemberById(userDetails.getUserId());
        return MyInfoResponse.from(member);
    }

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberAnalysisResponse getMemberAnalysis() {
        return new MemberAnalysisResponse(
                memberRepository.count(),
                memberRepository.countByRole(MemberRole.ROLE_CAFE_OWNER),
                memberRepository.countByRole(MemberRole.ROLE_COMMUNITY_MANAGER),
                memberRepository.countByRole(MemberRole.ROLE_NORMAL_USER),
                memberRepository.countByRole(MemberRole.ROLE_ADMIN)
        );
    }

    @Transactional
    public void modifyRole(String userId, MemberRole role) {
        Member member = getMemberById(Long.parseLong(userId));
        member.changeRole(role);
        memberRepository.save(member);
    }

    public record MemberAnalysisResponse(
            Long totalMemberCount,
            Long cafeOwnerCount,
            Long communityManagerCount,
            Long normalUserCount,
            Long adminCount
    ) {
    }

    public record MyInfoResponse(
            Long id,
            String email,
            String name,
            MemberRole role
    ) {
        public static MyInfoResponse from(Member member) {
            return new MyInfoResponse(
                    member.getId(),
                    member.getEmail(),
                    member.getName(),
                    member.getRole()
            );
        }
    }
}
