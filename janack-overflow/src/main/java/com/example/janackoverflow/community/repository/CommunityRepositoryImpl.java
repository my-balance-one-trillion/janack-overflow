package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.issue.entity.Issue;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janackoverflow.issue.entity.QIssue.issue;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Issue> findAllByKeyword(String keyword, String title, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(issue.keyword.contains(keyword), issue.title.contains(title))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getCount(keyword));
    }

    @Override
    public Page<Issue> findAllByCategoryOrTitile(String title, List<String> category, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(eqCategories(category).or(eqTitle(title)))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getTitleCategoryCount(title, category));
    }

    @Override
    public Page<Issue> findAllByCategory(List<String> category, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(eqCategories(category))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getCategoryCount(category));
    }

    private Long getCount(String keyword) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(issue.keyword.contains(keyword))
                .fetchOne();
    }

    private Long getTitleCategoryCount(String title, List<String> category) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(eqCategories(category).or(eqTitle(title)))
                .fetchOne();
    }

    private Long getCategoryCount(List<String> category) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(eqCategories(category))
                .fetchOne();
    }

    private BooleanExpression eqTitle(String searchTitle){
        return searchTitle == null ? null : issue.title.contains(searchTitle);
    }

    /**
     * 카테고리 복수 선택
     * @param categories
     * @return
     */
    private BooleanBuilder eqCategories(List<String> categories){
        if(categories == null){
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String category : categories){
            booleanBuilder.or(issue.category.contains(category));
        }

        return booleanBuilder;
    }


}
