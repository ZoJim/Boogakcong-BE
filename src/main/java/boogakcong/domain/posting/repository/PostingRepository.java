package boogakcong.domain.posting.repository;

import boogakcong.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.JsonPath;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findAllByPostTypeOrderByCreatedAtDesc(Posting.PostType postType);

    List<Posting> findAllByUserIdAndPostTypeOrderByCreatedAtDesc(Long userId, Posting.PostType postType);

    List<Posting> findAllByOrderByCreatedAtDesc();

    Posting findTopByOrderByViewCountDesc();

    List<Posting> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COUNT(p) FROM Posting p WHERE p.createdAt >= :startDate AND p.createdAt < :endDate GROUP BY FUNCTION('DATE', p.createdAt)")
    List<Long> findNewPostsPerDay(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
