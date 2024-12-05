package boogakcong.domain.cafe._notification.entity;

import boogakcong.domain.cafe.entity.Cafe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CafeNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페")
    private Long cafeId;

    @Comment("알림 내용")
    @Column(nullable = false)
    private String content;

    @Comment("알림 등록 날짜")
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
