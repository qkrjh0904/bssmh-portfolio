-- qkrjh0904-change-log-release-1.1.0.sql-01
alter table portfolio
    add recommend_status varchar(255) default 'NONE' not null;

-- qkrjh0904-change-log-release-1.1.0.sql-02
create table comment_bookmark
(
    comment_bookmark_id bigint auto_increment not null primary key,
    created_date        datetime not null,
    last_modified_date  datetime not null,
    member_id           bigint   not null,
    comment_id          bigint   not null,
    constraint fk_comment_bookmark_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_comment_bookmark_comment_id
        foreign key (comment_id) references comment (comment_id)
);

-- qkrjh0904-change-log-release-1.1.0.sql-03
alter table comment
    add parent_id bigint null;

-- qkrjh0904-change-log-release-1.1.0.sql-04
alter table comment
    add constraint fk_parent_id_comment
        foreign key comment(parent_id) references comment (comment_id);