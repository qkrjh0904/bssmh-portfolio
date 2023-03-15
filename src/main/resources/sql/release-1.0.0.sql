create table attach_file
(
    attach_file_id     bigint auto_increment
        primary key,
    created_date       datetime     not null,
    last_modified_date datetime     not null,
    file_name          varchar(255) not null,
    file_path          varchar(255) not null,
    file_size          bigint       not null,
    file_uid           varchar(255) not null,
    constraint file_uid
        unique (file_uid)
)DEFAULT CHARACTER SET UTF8MB4;

create index idx_file_uid
    on attach_file (file_uid);

create table member
(
    member_id          bigint auto_increment
        primary key,
    created_date       datetime                     not null,
    last_modified_date datetime                     not null,
    description        text                         null,
    email              varchar(255)                 not null,
    member_role_type   varchar(255)                 null,
    name               varchar(255)                 null,
    nick_name          varchar(255)                 null,
    phone              varchar(255)                 null,
    profile_image_url  text                         null,
    registration_id    varchar(255)                 not null,
    job                varchar(255)                 null,
    member_type        varchar(255) default 'EMPTY' not null,
    belong             varchar(255)                 null,
    admission_year     int                          null,
    constraint uk_email_registration_id_on_member
        unique (email, registration_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table follow
(
    follow_id          bigint auto_increment
        primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    from_member_id     bigint   not null,
    to_member_id       bigint   not null,
    constraint uk_from_member_id_to_member_id_on_follow
        unique (from_member_id, to_member_id),
    constraint fk_follow_from_member_id
        foreign key (from_member_id) references member (member_id),
    constraint fk_follow_to_member_id
        foreign key (to_member_id) references member (member_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table member_agreement
(
    member_agreement_id bigint auto_increment
        primary key,
    created_date        datetime not null,
    member_id           bigint   not null,
    constraint fk_member_agreement_member_id
        foreign key (member_id) references member (member_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table member_class_info
(
    member_class_info_id bigint auto_increment
        primary key,
    created_date         datetime not null,
    last_modified_date   datetime not null,
    school_grade         int      null,
    school_class         int      null,
    school_number        int      null,
    member_id            bigint   not null,
    constraint fk_member_class_info_member_id
        foreign key (member_id) references member (member_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table member_login_log
(
    member_login_log_id bigint auto_increment
        primary key,
    created_date        datetime     not null,
    email               varchar(255) null,
    name                varchar(255) null,
    member_id           bigint       not null,
    constraint fk_member_login_log_member_id
        foreign key (member_id) references member (member_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table member_sign_up_log
(
    member_sign_up_id bigint auto_increment
        primary key,
    created_date      datetime     not null,
    email             varchar(255) null,
    name              varchar(255) null,
    member_id         bigint       not null,
    constraint fk_member_sign_up_log_member_id
        foreign key (member_id) references member (member_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table portfolio
(
    portfolio_id             bigint auto_increment
        primary key,
    created_date             datetime          not null,
    last_modified_date       datetime          not null,
    description              text              null,
    git_url                  text              null,
    portfolio_scope          varchar(255)      not null,
    portfolio_type           varchar(255)      not null,
    portfolio_url            text              null,
    title                    varchar(255)      not null,
    views                    bigint            not null,
    member_id                bigint            not null,
    thumbnail_attach_file_id bigint            not null,
    video_attach_file_id     bigint            null,
    sequence                 int default 99999 not null,
    school_grade             int               null,
    constraint fk_portfolio_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_portfolio_thumbnail_attach_file_id
        foreign key (thumbnail_attach_file_id) references attach_file (attach_file_id),
    constraint fk_portfolio_video_attach_file_id
        foreign key (video_attach_file_id) references attach_file (attach_file_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table portfolio_bookmark
(
    bookmark_id        bigint auto_increment
        primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    member_id          bigint   not null,
    portfolio_id       bigint   not null,
    constraint fk_portfolio_bookmark_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_bookmark_portfolio_id
        foreign key (portfolio_id) references portfolio (portfolio_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table comment_bookmark
(
    bookmark_id        bigint auto_increment
        primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    member_id          bigint   not null,
    comment_id       bigint   not null,
    constraint fk_comment_bookmark_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_bookmark_comment_id
        foreign key (comment_id) references comment (comment_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table comment
(
    comment_id         bigint auto_increment
        primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    content            text     not null,
    member_id          bigint   not null,
    portfolio_id       bigint   not null,
    parent_id          bigint,
    constraint fk_comment_parent_id
        foreign key (parent_id) references comment (comment_id),
    constraint fk_comment_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_comment_portfolio_id
        foreign key (portfolio_id) references portfolio (portfolio_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table contributor
(
    contributor_id     bigint auto_increment
        primary key,
    created_date       datetime not null,
    last_modified_date datetime not null,
    member_id          bigint   not null,
    portfolio_id       bigint   not null,
    constraint fk_contributor_member_id
        foreign key (member_id) references member (member_id),
    constraint fk_contributor_portfolio_id
        foreign key (portfolio_id) references portfolio (portfolio_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table portfolio_skill
(
    portfolio_skill_id bigint auto_increment
        primary key,
    created_date       datetime     not null,
    last_modified_date datetime     not null,
    skill_id           bigint       null,
    skill_name         varchar(255) not null,
    portfolio_id       bigint       not null,
    constraint fk_portfolio_skill_portfolio_id
        foreign key (portfolio_id) references portfolio (portfolio_id)
)DEFAULT CHARACTER SET UTF8MB4;

create table skill
(
    skill_id     bigint auto_increment
        primary key,
    created_date datetime     not null,
    name         varchar(255) not null,
    constraint name
        unique (name)
)DEFAULT CHARACTER SET UTF8MB4;

