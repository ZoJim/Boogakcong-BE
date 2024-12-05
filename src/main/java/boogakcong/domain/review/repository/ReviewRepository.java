package boogakcong.domain.review.repository;

import boogakcong.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByMemberIdAndCafeId(Long memberId, Long cafeId);
}
