package boogakcong.domain.cafe._image.entity;

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
public class CafeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페 ID")
    @Column(nullable = false)
    private Long cafeId;

    @Comment("이미지 URL")
    @Column(nullable = false)
    private String url;

    @Comment("업로더 ID")
    @Column(nullable = false)
    private Long uploaderId;
}
