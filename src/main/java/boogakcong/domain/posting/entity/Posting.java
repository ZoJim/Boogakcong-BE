package boogakcong.domain.posting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@SQLDelete(sql = "UPDATE posting SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;
    private String title;
    private String content;
    private Long userId;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public enum PostType {
        RECRUITMENT, REVIEW
    }
}
