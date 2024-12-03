package boogakcong.domain.member.service;

import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.repository.MemberRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Comment("순수 Entity를 다루는 서비스")
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
    }

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

    public void updateMember(Member member) {
        memberRepository.save(member);
    }
}
