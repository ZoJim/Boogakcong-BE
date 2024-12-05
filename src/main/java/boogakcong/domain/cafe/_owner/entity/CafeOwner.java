package boogakcong.domain.cafe._owner.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE cafe_owner SET deleted_at = NOW() WHERE id = ?")
public class CafeOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private Long cafeId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    // 할당 상태 : 승인, 반려, 요청
    @Setter
    @Enumerated(EnumType.STRING)
    private AllocationStatus allocationStatus;

    public enum AllocationStatus {
        APPROVED, REJECTED, REQUESTED
    }

}
