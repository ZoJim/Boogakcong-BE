package boogakcong.domain.posting.controller;

import boogakcong.domain.posting.dto.request.CreatePostingRequest;
import boogakcong.domain.posting.dto.response.PostingResponse;
import boogakcong.domain.posting.entity.Posting;
import boogakcong.domain.posting.service.PostingPlatformService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/postings")
@RequiredArgsConstructor
public class PostingPlatformController {
    private final PostingPlatformService postingPlatformService;

    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> createPosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute CreatePostingRequest request, // @ModelAttribute로 변경
            @RequestParam("file") MultipartFile file
    ) {
        PostingResponse post = postingPlatformService.post(userDetails.getUserId(), request, file);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<?> getPostings(@RequestParam("postType") Posting.PostType postType) {
        return ResponseEntity.ok(postingPlatformService.getPostings(postType));
    }

    @GetMapping("/my")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> getMyPostings(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("postType") Posting.PostType postType
    ) {
        return ResponseEntity.ok(postingPlatformService.getMyPostings(userDetails.getUserId(), postType));
    }

    @GetMapping("/{postingId}")
    public ResponseEntity<?> getPosting(@PathVariable Long postingId) {
        return ResponseEntity.ok(postingPlatformService.getPosting(postingId));
    }

    @PatchMapping("/{postingId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> updatePosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "postingId") Long postingId,
            @ModelAttribute CreatePostingRequest request, // @ModelAttribute로 변경
            @RequestParam("file") MultipartFile file
    ) {
        PostingResponse post = postingPlatformService.update(userDetails.getUserId(), postingId, request, file);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postingId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    public ResponseEntity<?> deletePosting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "postingId") Long postingId
    ) {
        postingPlatformService.delete(userDetails.getUserId(), postingId);
        return ResponseEntity.ok().build();
    }

}
