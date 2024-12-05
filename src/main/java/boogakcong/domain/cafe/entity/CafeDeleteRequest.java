package boogakcong.domain.cafe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CafeDeleteRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페 ID")
    @Column(nullable = false)
    private Long cafeId;

    @Comment("요청 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private RequestStatus requestStatus;

    @Comment("삭제 이유")
    @Enumerated(EnumType.STRING)
    private DeleteReason deleteReason;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public enum RequestStatus {
        REQUESTED, ACCEPTED, REJECTED
    }

    public enum DeleteReason {
        CAFE_CLOSED, CAFE_UNMAINTAINABLE, ETC;

        public static DeleteReason fromId(Long reasonId) {
            return switch (Math.toIntExact(reasonId)) {
                case 1 -> CAFE_CLOSED;
                case 2 -> CAFE_UNMAINTAINABLE;
                default -> ETC;
            };
        }
    }
}
