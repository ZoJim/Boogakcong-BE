package boogakcong.domain.cafe.entity;

import boogakcong.domain.cafe.OperatingPattern;
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
    private String address;

    @Comment("상세주소")
    @Column(nullable = false)
    private String addressDetail;

    @Comment("위도")
    @Column(nullable = false)
    private Double latitude;

    @Comment("경도")
    @Column(nullable = false)
    private Double longitude;

    @Comment("카페 소개")
    @Column(nullable = false)
    private String description;

    @Comment("시작 시간")
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Comment("종료 시간")
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Comment("운영 날짜")
    @Column(nullable = false)
    private OperatingPattern operatingPattern;

    @Comment("콘센트 수")
    @Column(nullable = false)
    private Integer outletCount;

    @Comment("테이블 당 최대 인원")
    @Column(nullable = false)
    private Integer maxPeoplePerTable;

    @Comment("총 테이블 수")
    @Column(nullable = false)
    private Integer totalTableCount;

    @Comment("와이파이 여부")
    @Column(nullable = false)
    private Boolean isWifi;
}
