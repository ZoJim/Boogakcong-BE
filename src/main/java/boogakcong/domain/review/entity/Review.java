package boogakcong.domain.review.entity;

import boogakcong.domain.cafe.entity.Cafe;
import boogakcong.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE review SET deleted_at = current_timestamp WHERE id = ?")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페")
    @Column(nullable = false)
    private Long cafeId;

    @Comment("작성자")
    @Column(nullable = false)
    private Long memberId;

    @Comment("리뷰 내용")
    @Column(nullable = false)
    private String content;

    @CurrentTimestamp
    @Comment("작성일")
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
}
