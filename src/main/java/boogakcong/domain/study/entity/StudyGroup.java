package boogakcong.domain.study.entity;

import boogakcong.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("스터디 그룹 생성자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("스터디 그룹 이름")
    @Column(nullable = false)
    private String name;

    @Comment("스터디 그룹 설명")
    @Column(nullable = false)
    private String description;

    @Comment("스터디 그룹 주제")
    @Column(nullable = false)
    private String subject;

    @Comment("스터디 그룹 최대 인원")
    @Column(nullable = false)
    private Integer maxMemberCount;

    @Comment("스터디 날짜")
    @Column(nullable = false)
    private LocalDate studyDate;

    @Comment("스터디 시간")
    @Column(nullable = false)
    private LocalTime studyTime;

    @Comment("스터디 생성 날짜")
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
