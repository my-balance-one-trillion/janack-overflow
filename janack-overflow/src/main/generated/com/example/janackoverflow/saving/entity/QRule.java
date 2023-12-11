package com.example.janackoverflow.saving.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = -784366404L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRule rule = new QRule("rule");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInputAccount inputAccount;

    public final NumberPath<Integer> overThreeHour = createNumber("overThreeHour", Integer.class);

    public final NumberPath<Integer> underHour = createNumber("underHour", Integer.class);

    public final NumberPath<Integer> underThirty = createNumber("underThirty", Integer.class);

    public final NumberPath<Integer> underThreeHour = createNumber("underThreeHour", Integer.class);

    public final com.example.janackoverflow.user.entity.QUsers users;

    public QRule(String variable) {
        this(Rule.class, forVariable(variable), INITS);
    }

    public QRule(Path<? extends Rule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRule(PathMetadata metadata, PathInits inits) {
        this(Rule.class, metadata, inits);
    }

    public QRule(Class<? extends Rule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inputAccount = inits.isInitialized("inputAccount") ? new QInputAccount(forProperty("inputAccount"), inits.get("inputAccount")) : null;
        this.users = inits.isInitialized("users") ? new com.example.janackoverflow.user.entity.QUsers(forProperty("users")) : null;
    }

}

