package boogakcong.domain.study._member.entity;

import boogakcong.domain.study.entity.StudyGroup;
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
public class StudyGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("스터디 그룹 멤버")
    @Column(nullable = false)
    private Long studyGroupId;

    @Comment("멤버 아이디")
    @Column(nullable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "studyGroupId", insertable = false, updatable = false)
    private StudyGroup studyGroup;
}
