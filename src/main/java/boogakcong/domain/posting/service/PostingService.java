package boogakcong.domain.posting.service;

import boogakcong.domain.posting.entity.Posting;
import boogakcong.domain.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public List<Posting> getPostings(Posting.PostType postType) {
        return postingRepository.findAllByPostTypeOrderByCreatedAtDesc(postType);
    }

    public List<Posting> getMyPostings(Long userId, Posting.PostType postType) {
        return postingRepository.findAllByUserIdAndPostTypeOrderByCreatedAtDesc(userId, postType);
    }

    public Posting getPosting(Long postingId) {
        return postingRepository.findById(postingId).orElseThrow(
                () -> new BusinessException(BusinessError.POSTING_NOT_FOUND)
        );
    }
}
