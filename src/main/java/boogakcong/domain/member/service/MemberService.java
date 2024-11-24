package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberCreateRequest;
import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Comment("순수 Entity를 다루는 서비스")
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }

    public Long createMember(MemberCreateRequest request) {
        memberRepository.findByEmail(request.email()).ifPresent(member -> {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        });

        return memberRepository.save(request.toEntity()).getId();
    }
}
