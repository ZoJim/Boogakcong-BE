package boogakcong.domain.posting.service;

import boogakcong.domain.posting.dto.request.CreatePostingRequest;
import boogakcong.domain.posting.dto.response.PostingResponse;
import boogakcong.domain.posting.entity.Posting;
import boogakcong.global.aws.S3Service;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingPlatformService {
    private final PostingService postingService;
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
}
