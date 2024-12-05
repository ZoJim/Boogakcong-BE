package boogakcong.domain.posting.dto.response;

import boogakcong.domain.posting.entity.Posting;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostingResponse(
        Long id,
        String title,
        String content,
        Long userId,
        Posting.PostType postType,
        String imageUrl,
        LocalDateTime createdAt

) {
}
