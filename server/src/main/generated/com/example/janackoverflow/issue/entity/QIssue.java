package com.example.janackoverflow.issue.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIssue is a Querydsl query type for Issue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssue extends EntityPathBase<Issue> {

    private static final long serialVersionUID = -1814959796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIssue issue = new QIssue("issue");

    public final com.example.janackoverflow.global.entity.QAuditingFields _super = new com.example.janackoverflow.global.entity.QAuditingFields(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath category = createString("category");

    public final StringPath code = createString("code");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.janackoverflow.saving.entity.QInputAccount inputAccount;

    public final StringPath keyword = createString("keyword");

    public final BooleanPath publicStatus = createBoolean("publicStatus");

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public final com.example.janackoverflow.user.entity.QUsers users;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QIssue(String variable) {
        this(Issue.class, forVariable(variable), INITS);
    }

    public QIssue(Path<? extends Issue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIssue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIssue(PathMetadata metadata, PathInits inits) {
        this(Issue.class, metadata, inits);
    }

    public QIssue(Class<? extends Issue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inputAccount = inits.isInitialized("inputAccount") ? new com.example.janackoverflow.saving.entity.QInputAccount(forProperty("inputAccount"), inits.get("inputAccount")) : null;
        this.users = inits.isInitialized("users") ? new com.example.janackoverflow.user.entity.QUsers(forProperty("users")) : null;
    }

}

