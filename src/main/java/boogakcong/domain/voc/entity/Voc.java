package boogakcong.domain.voc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("제목")
    @Column(nullable = false)
    private String title;

    @Comment("내용")
    @Column(nullable = false)
    private String content;

    @Comment("작성자")
    @Column(nullable = false)
    private Long memberId;

    @Comment("작성일")
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
