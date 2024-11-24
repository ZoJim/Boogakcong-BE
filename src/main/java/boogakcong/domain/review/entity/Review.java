package boogakcong.domain.review.entity;

import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.member.entity.Member;
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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페")
    @JoinColumn(name = "cafe_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe cafe;

    @Comment("작성자")
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("리뷰 내용")
    @Column(nullable = false)
    private String content;
}
