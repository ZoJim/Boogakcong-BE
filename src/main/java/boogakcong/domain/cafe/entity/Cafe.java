package boogakcong.domain.cafe.entity;

import boogakcong.domain.cafe.dto.request.UpdateCafeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE cafe SET deleted_at = now() WHERE id = ?")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카페명")
    @Column(nullable = false)
    private String name;

    @Comment("전화번호")
    @Column(nullable = false)
    private String phoneNumber;

    @Comment("도로명 주소")
    @Column(nullable = false)
    private String roadAddress;

    @Comment("상세주소")
    @Column(nullable = false)
    private String addressDetail;

    @Comment("위도")
    @Column(nullable = false)
    private Double latitude;

    @Comment("경도")
    @Column(nullable = false)
    private Double longitude;

    @Comment("카페 소개 URL")
    @Column(nullable = false)
    private String placeUrl;

    @Comment("콘센트 수")
    private Integer outletCount;

    @Comment("테이블 당 최대 인원")
    private Integer maxPeoplePerTable;

    @Comment("총 테이블 수")
    private Integer totalTableCount;

    @Comment("와이파이 여부")
    private Boolean isWifi;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    public void update(UpdateCafeRequest request) {
        this.outletCount = request.outletCount();
        this.maxPeoplePerTable = request.maxPeoplePerTable();
        this.isWifi = request.isWifi();
    }
}
