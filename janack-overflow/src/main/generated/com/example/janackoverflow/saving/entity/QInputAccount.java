package com.example.janackoverflow.saving.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInputAccount is a Querydsl query type for InputAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputAccount extends EntityPathBase<InputAccount> {

    private static final long serialVersionUID = 985563331L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInputAccount inputAccount = new QInputAccount("inputAccount");

    public final com.example.janackoverflow.global.entity.QAuditingFields _super = new com.example.janackoverflow.global.entity.QAuditingFields(this);

    public final NumberPath<Integer> acntAmount = createNumber("acntAmount", Integer.class);

    public final StringPath acntName = createString("acntName");

    public final StringPath acntNum = createString("acntNum");

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> goalAmount = createNumber("goalAmount", Integer.class);

    public final StringPath goalName = createString("goalName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public final com.example.janackoverflow.user.entity.QUsers users;

    public QInputAccount(String variable) {
        this(InputAccount.class, forVariable(variable), INITS);
    }

    public QInputAccount(Path<? extends InputAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInputAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInputAccount(PathMetadata metadata, PathInits inits) {
        this(InputAccount.class, metadata, inits);
    }

    public QInputAccount(Class<? extends InputAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.example.janackoverflow.user.entity.QUsers(forProperty("users")) : null;
    }

}

