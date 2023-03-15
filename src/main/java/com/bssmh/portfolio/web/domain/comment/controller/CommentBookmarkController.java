package com.bssmh.portfolio.web.domain.comment.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rq.BookmarkCommentRq;
import com.bssmh.portfolio.web.domain.comment.service.CommentBookmarkService;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 좋아요")
@RestController
@RequiredArgsConstructor
public class CommentBookmarkController {

    private final CommentBookmarkService commentBookmarkService;

    @Operation(summary = "댓글 좋아요", description = "toggle 방식")
    @PutMapping(ApiPath.COMMENT_BOOKMARK)
    public void bookmarkComment(@AuthenticationPrincipal MemberContext memberContext,
                                @Validated @RequestBody BookmarkCommentRq rq) {
        commentBookmarkService.bookmarkComment(memberContext, rq);
    }

}
