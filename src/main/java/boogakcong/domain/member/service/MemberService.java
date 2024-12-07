package boogakcong.domain.member.service;

import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.repository.MemberRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
