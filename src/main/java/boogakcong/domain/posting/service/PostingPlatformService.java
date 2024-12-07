package boogakcong.domain.posting.service;

import boogakcong.domain.posting.dto.request.CreatePostingRequest;
import boogakcong.domain.posting.dto.response.PostingResponse;
import boogakcong.domain.posting.entity.Posting;
import boogakcong.domain.review.service.CafeReviewService;
import boogakcong.global.aws.S3Service;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingPlatformService {
    private final PostingService postingService;
    private final CafeReviewService cafeReviewService;
    private final S3Service s3Service;

    @Transactional
    public PostingResponse post(Long userId, CreatePostingRequest request, MultipartFile file) {
        String fileUrl = s3Service.uploadFile(file);
        Posting post = postingService.post(
                Posting.builder()
                        .userId(userId)
                        .viewCount(0L)
                        .title(request.title())
                        .content(request.content())
                        .imageUrl(fileUrl)
                        .postType(request.postType())
                        .build()
        );

        return PostingResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .postType(post.getPostType())
                .build();
    }

    public List<PostingResponse> getPostings() {
        // posttype에 따라서 최슨 순서로 가져오기
        List<PostingResponse> postings = postingService.getPostings()
                .stream()
                .map(post -> PostingResponse.builder()
                        .id(post.getId())
                        .userId(post.getUserId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .postType(post.getPostType())
                        .createdAt(post.getCreatedAt())
                        .build()
                )
                .toList();

        return postings;
    }

    public List<PostingResponse> getMyPostings(Long userId) {
        // userId와 posttype에 따라서 최신 순서로 가져오기
        List<PostingResponse> postings = postingService.getMyPostings(userId)
                .stream()
                .map(post -> PostingResponse.builder()
                        .id(post.getId())
                        .userId(post.getUserId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .postType(post.getPostType())
                        .createdAt(post.getCreatedAt())
                        .build()
                )
                .toList();

        return postings;
    }

    public PostingResponse getPosting(Long postingId) {
        Posting post = postingService.getPosting(postingId);

        return PostingResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .postType(post.getPostType())
                .createdAt(post.getCreatedAt())
                .build();
    }

    @Transactional
    public PostingResponse update(Long userId, Long postingId, CreatePostingRequest request, MultipartFile file) {
        if (!postingService.getPosting(postingId).getUserId().equals(userId)) {
            throw new BusinessException(BusinessError.POSTING_NOT_AUTHORIZED);
        }

        Posting post = postingService.update(
                Posting.builder()
                        .id(postingId)
                        .userId(userId)
                        .title(request.title())
                        .content(request.content())
                        .postType(request.postType())
                        .viewCount(postingService.getPosting(postingId).getViewCount())
                        .createdAt(postingService.getPosting(postingId).getCreatedAt())
                        .imageUrl(postingService.getPosting(postingId).getImageUrl())
                        .build()
        );

        if (file != null) {
            String fileUrl = s3Service.uploadFile(file);

            post = postingService.update(
                    Posting.builder()
                            .id(postingId)
                            .userId(userId)
                            .title(request.title())
                            .content(request.content())
                            .postType(request.postType())
                            .imageUrl(fileUrl)
                            .createdAt(postingService.getPosting(postingId).getCreatedAt())
                            .build()
            );


        }

        Posting newPost = postingService.update(post);

        return PostingResponse.builder()
                .id(newPost.getId())
                .userId(newPost.getUserId())
                .title(newPost.getTitle())
                .content(newPost.getContent())
                .imageUrl(newPost.getImageUrl())
                .postType(newPost.getPostType())
                .createdAt(newPost.getCreatedAt())
                .build();
    }

    @Transactional
    public void delete(Long userId, Long postingId) {
        if (!postingService.getPosting(postingId).getUserId().equals(userId)) {
            throw new BusinessException(BusinessError.POSTING_NOT_AUTHORIZED);
        }

        postingService.delete(postingId);
    }

    public PostingResponse getPopularPostings() {
        Posting popularPosting = postingService.getPopularPosting();
        return PostingResponse.builder()
                .id(popularPosting.getId())
                .userId(popularPosting.getUserId())
                .title(popularPosting.getTitle())
                .content(popularPosting.getContent())
                .imageUrl(popularPosting.getImageUrl())
                .postType(popularPosting.getPostType())
                .createdAt(popularPosting.getCreatedAt())
                .build();
    }

    public List<PostAnalysis> getPostingAnalysis() {
        // 7일간의 새로운 포스트 수와 리뷰 수
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        // getNewReviewsPerDay와 getNewPostsPerDay 메서드는 7일간의 데이터를 반환한다고 가정
        List<Long> newReviewsPerDay = cafeReviewService.getNewReviewsPerDay(startDate, endDate);
        List<Long> newPostsPerDay = postingService.getNewPostsPerDay(startDate, endDate);

        List<PostAnalysis> postAnalyses = new ArrayList<>();

        // 반복문에서 각 리스트의 크기를 확인한 후, 최대 7일만큼 데이터 처리
        for (int i = 0; i < 7; i++) {
            long newPostCount = (i < newPostsPerDay.size()) ? newPostsPerDay.get(i) : 0; // 데이터가 없으면 0으로 처리
            long newReviewCount = (i < newReviewsPerDay.size()) ? newReviewsPerDay.get(i) : 0; // 데이터가 없으면 0으로 처리

            postAnalyses.add(new PostAnalysis(newPostCount, newReviewCount));
        }

        return postAnalyses;
    }

    public record PostAnalysis(
            Long newPostCount,
            Long newReviewCount
    ) {

    }
}
