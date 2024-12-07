package boogakcong.domain.posting.controller;

import boogakcong.domain.posting.dto.request.CreatePostingRequest;
import boogakcong.domain.posting.dto.response.PostingResponse;
import boogakcong.domain.posting.service.PostingPlatformService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/postings")
@RequiredArgsConstructor
public class PostingPlatformController {
    private final PostingPlatformService postingPlatformService;

    @PostMapping
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> createPosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute CreatePostingRequest request, // @ModelAttribute로 변경
            @RequestParam("file") MultipartFile file
    ) {
        PostingResponse post = postingPlatformService.post(userDetails.getUserId(), request, file);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<PostingResponse>> getPostings() {
        return ResponseEntity.ok(postingPlatformService.getPostings());
    }


    @GetMapping("/top")
    public ResponseEntity<PostingResponse> getPopularPostings() {
        return ResponseEntity.ok(postingPlatformService.getPopularPostings());
    }


    @GetMapping("/my")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> getMyPostings(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(postingPlatformService.getMyPostings(userDetails.getUserId()));
    }

    @GetMapping("/{postingId}")
    public ResponseEntity<?> getPosting(@PathVariable Long postingId) {
        return ResponseEntity.ok(postingPlatformService.getPosting(postingId));
    }

    @PatchMapping("/{postingId}")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> updatePosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "postingId") Long postingId,
            @ModelAttribute CreatePostingRequest request, // @ModelAttribute로 변경
            @RequestParam(value = "file", required = false) MultipartFile file // required=false로 변경
    ) {
        PostingResponse post = postingPlatformService.update(userDetails.getUserId(), postingId, request, file);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postingId}")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> deletePosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "postingId") Long postingId
    ) {
        postingPlatformService.delete(userDetails.getUserId(), postingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analysis")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<?> getPostingAnalysis(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(postingPlatformService.getPostingAnalysis());
    }

}
