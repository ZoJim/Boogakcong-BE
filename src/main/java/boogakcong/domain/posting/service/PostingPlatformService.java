package boogakcong.domain.posting.service;

import boogakcong.domain.posting.dto.request.CreatePostingRequest;
import boogakcong.domain.posting.dto.response.PostingResponse;
import boogakcong.domain.posting.entity.Posting;
import boogakcong.global.aws.S3Service;
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

    public List<PostingResponse> getPostings(Posting.PostType postType) {
        // posttype에 따라서 최슨 순서로 가져오기
        List<PostingResponse> postings = postingService.getPostings(postType)
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

    public List<PostingResponse> getMyPostings(Long userId, Posting.PostType postType) {
        // userId와 posttype에 따라서 최신 순서로 가져오기
        List<PostingResponse> postings = postingService.getMyPostings(userId, postType)
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
}