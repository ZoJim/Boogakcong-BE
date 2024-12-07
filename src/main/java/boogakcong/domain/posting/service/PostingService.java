package boogakcong.domain.posting.service;

import boogakcong.domain.posting.entity.Posting;
import boogakcong.domain.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLSelect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@SQLSelect(sql = "UPDATE posting SET view_count = view_count + 1 WHERE id = ?")
public class PostingService {
    private final PostingRepository postingRepository;

    @Transactional
    public Posting post(Posting post) {
        return postingRepository.save(post);
    }

    @Transactional
    public void delete(Long postingId) {
        postingRepository.deleteById(postingId);
    }

    @Transactional
    public Posting update(Posting post) {
        return postingRepository.save(post);
    }

    public List<Posting> getPostings() {
        return postingRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Posting> getMyPostings(Long userId) {
        return postingRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public Posting getPosting(Long postingId) {
        return postingRepository.findById(postingId).orElseThrow(
                () -> new BusinessException(BusinessError.POSTING_NOT_FOUND)
        );
    }

    public Posting getPopularPosting() {
        return postingRepository.findTopByOrderByViewCountDesc();
    }

    public List<Long> getNewPostsPerDay(LocalDateTime startDate, LocalDateTime endDate) {
        return postingRepository.findNewPostsPerDay(startDate, endDate);
    }
}
