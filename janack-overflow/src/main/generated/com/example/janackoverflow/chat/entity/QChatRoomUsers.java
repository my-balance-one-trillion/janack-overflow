package com.example.janackoverflow.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomUsers is a Querydsl query type for ChatRoomUsers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomUsers extends EntityPathBase<ChatRoomUsers> {

    private static final long serialVersionUID = -188649513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomUsers chatRoomUsers = new QChatRoomUsers("chatRoomUsers");

    public final QChatRoom chatRoom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.janackoverflow.user.entity.QUsers users;

    public QChatRoomUsers(String variable) {
        this(ChatRoomUsers.class, forVariable(variable), INITS);
    }

    public QChatRoomUsers(Path<? extends ChatRoomUsers> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomUsers(PathMetadata metadata, PathInits inits) {
        this(ChatRoomUsers.class, metadata, inits);
    }

    public QChatRoomUsers(Class<? extends ChatRoomUsers> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.users = inits.isInitialized("users") ? new com.example.janackoverflow.user.entity.QUsers(forProperty("users")) : null;
    }

}

