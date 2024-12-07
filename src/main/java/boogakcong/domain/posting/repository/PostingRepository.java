package boogakcong.domain.posting.repository;

import boogakcong.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.JsonPath;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findAllByPostTypeOrderByCreatedAtDesc(Posting.PostType postType);

    List<Posting> findAllByUserIdAndPostTypeOrderByCreatedAtDesc(Long userId, Posting.PostType postType);

    List<Posting> findAllByOrderByCreatedAtDesc();

    Posting findTopByOrderByViewCountDesc();

    List<Posting> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
