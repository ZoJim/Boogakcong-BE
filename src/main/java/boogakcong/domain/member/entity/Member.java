package boogakcong.domain.member.entity;

import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.dto.response.MemberResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
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

    @Comment("탈퇴 일시")
    private LocalDateTime deletedAt;

    public MemberResponse fromEntity() {
        return MemberResponse.builder()
                .id(id)
                .role(role.name())
                .email(email)
                .build();
    }

    public void confirmCafeOwner() {
        this.role = MemberRole.ROLE_CAFE_OWNER;
    }

    public void confirmCommunityManager() {
        this.role = MemberRole.ROLE_COMMUNITY_MANAGER;
    }

    public void cancelCafeOwner() {
        this.role = MemberRole.ROLE_NORMAL_USER;
    }
}
