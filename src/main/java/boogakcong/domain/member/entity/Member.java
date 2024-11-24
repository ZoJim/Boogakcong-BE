package boogakcong.domain.member.entity;

import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.dto.response.MemberResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Comment("이메일")
    @Column(nullable = false)
    private String email;

    @Comment("패스워드")
    @Column(nullable = false)
    private String password;

    @Comment("이름")
    @Column(nullable = false)
    private String name;

    @Comment("전화번호")
    @Column(nullable = false)
    private String phoneNumber;

    public MemberResponse fromEntity() {
        return MemberResponse.builder()
                .id(id)
                .role(role.name())
                .email(email)
                .build();
    }
}
