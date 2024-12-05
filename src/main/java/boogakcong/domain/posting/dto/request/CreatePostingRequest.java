package boogakcong.domain.posting.dto.request;

import boogakcong.domain.posting.entity.Posting;

public record CreatePostingRequest(
        String title,
        String content,
        Posting.PostType postType

) {
}
