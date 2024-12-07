package boogakcong.domain.review.repository;

import boogakcong.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByMemberIdAndCafeId(Long memberId, Long cafeId);

    List<Review> findByMemberId(Long userId);

    List<Review> findByCafeId(Long cafeId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.createdAt >= :startDate AND r.createdAt < :endDate GROUP BY FUNCTION('DATE', r.createdAt)")
    List<Long> countNewReviewsPerDay(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
