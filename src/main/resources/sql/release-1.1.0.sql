-- qkrjh0904-change-log-release-1.1.0.sql-01
create table comment_bookmark
(
    bookmark_id        bigint auto_increment not null primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    member_id          bigint   not null,
    comment_id         bigint   not null,
    constraint fk_comment_bookmark_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_comment_bookmark_comment_id
        foreign key (comment_id) references comment (comment_id)
);

-- qkrjh0904-change-log-release-1.1.0.sql-02
alter table comment
    add parent bigint null;

-- qkrjh0904-change-log-release-1.1.0.sql-03
alter table comment
    add constraint fk_parent_id_comment
        foreign key (parent_id) references comment (comment_id);