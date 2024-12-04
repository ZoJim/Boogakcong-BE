package boogakcong.domain.cafe._owner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CafeOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private Long cafeId;

    // 할당 상태 : 승인, 반려, 요청
    private AllocationStatus allocationStatus;

    public enum AllocationStatus {
        APPROVED, REJECTED, REQUESTED
    }

}
