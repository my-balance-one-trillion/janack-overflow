package com.example.janackoverflow.community.repository;

import com.example.janackoverflow.issue.entity.Issue;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janackoverflow.issue.entity.QIssue.issue;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommunityRepositoryImpl implements CommunityRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Issue> findAllByTitle(String title, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(issue.title.contains(title))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getCount(title));
    }

    @Override
    public Page<Issue> findAllByCategory(String title, List<String> category, Pageable pageable) {
        if(!title.isEmpty() && !category.isEmpty()) {
            return findAllByCategoryAndTitle(title, category, pageable);
        }
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(eqPublicStatus(true).and(eqStatus("03")).and(eqCategories(category)).or(eqTitle(title)))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getCategoryCount(title, category));
    }

    @Override
    public Page<Issue> findAllByCategoryName(String category, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(eqCategory(category))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getCategoryCount(category));
    }

    @Override
    public Page<Issue> findAllByCategoryAndTitle(String title, List<String> category, Pageable pageable) {
        List<Issue> Issues = jpaQueryFactory.selectFrom(issue)
                .where(eqPublicStatus(true).and(eqCategories(category)).and(eqTitle(title)))
                .orderBy(new OrderSpecifier<>(Order.DESC, issue.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(Issues, pageable, getTitleAndCategoryCount(title, category));
    }

    private Long getCount(String title) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(issue.title.contains(title))
                .fetchOne();
    }

    private Long getCategoryCount(String title, List<String> category) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(eqPublicStatus(true).and(eqStatus("03")).and(eqCategories(category)).or(eqTitle(title)))
                .fetchOne();
    }

    private Long getCategoryCount(String category) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(eqCategory(category))
                .fetchOne();
    }

    private Long getTitleAndCategoryCount(String title, List<String> category) {
        return jpaQueryFactory.select(issue.count())
                .from(issue)
                .where(eqCategories(category).and(eqTitle(title)))
                .fetchOne();
    }

    private BooleanExpression eqTitle(String searchTitle){
        log.info("eqTitle : " + (searchTitle == null));
        return searchTitle == null || searchTitle.isEmpty() ? null : issue.title.contains(searchTitle);
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

    private BooleanBuilder eqCategory(String category){
        if(category == null){
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(issue.category.eq(category));

        return booleanBuilder;
    }

    private BooleanBuilder eqPublicStatus(boolean status){
        if(!status){
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(issue.publicStatus.eq(status));

        return booleanBuilder;
    }

    private BooleanBuilder eqStatus(String status){
        if(status == null){
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(issue.status.eq(status));

        return booleanBuilder;
    }


}
