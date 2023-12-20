package com.example.janackoverflow.community.repository;//package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.community.entity.Comment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janackoverflow.community.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentListRepositoruImpl implements CommentListRespository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Comment> selectCommentList(long issueId, Pageable pageable) {
        List<Comment> comments = jpaQueryFactory.selectFrom(comment)
                .where(comment.issue.id.eq(issueId))
                .orderBy(new OrderSpecifier<>(Order.DESC, comment.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(comments, pageable, getCount(issueId));
    }

    public Long getCount(long issueId) {
        return jpaQueryFactory.select(comment.count())
                .from(comment)
                .where(comment.issue.id.eq(issueId))
                .fetchOne();
    }
}
