create table attach_file
(
    attach_file_id     bigint       not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    file_name          varchar(255) not null,
    file_path          varchar(255) not null,
    file_size          bigint       not null,
    file_uid           varchar(255) not null unique,
    primary key (attach_file_id)
);

create table member
(
    member_id          bigint       not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    description        text,
    email              varchar(255) not null unique,
    member_role_type   varchar(255),
    name               varchar(255),
    nick_name          varchar(255),
    phone              varchar(255),
    profile_image_url  text,
    registration_id    varchar(255),
    job                varchar(255),
    primary key (member_id)
);

create table portfolio
(
    portfolio_id             bigint       not null auto_increment,
    created_date             datetime,
    last_modified_date       datetime,
    description              text,
    git_url                  text,
    portfolio_scope          varchar(255) not null,
    portfolio_type           varchar(255) not null,
    portfolio_url            text,
    title                    varchar(255) not null,
    views                    bigint       not null,
    member_id                bigint,
    thumbnail_attach_file_id bigint,
    video_attach_file_id     bigint,
    primary key (portfolio_id),
    constraint fk_portfolio_member_id
        foreign key (member_id)
            references member (member_id),
    constraint fk_portfolio_thumbnail_attach_file_id
        foreign key (thumbnail_attach_file_id)
            references attach_file (attach_file_id),
    constraint fk_portfolio_video_attach_file_id
        foreign key (video_attach_file_id)
            references attach_file (attach_file_id)

);

create table bookmark
(
    bookmark_id        bigint not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    member_id          bigint,
    portfolio_id       bigint,
    primary key (bookmark_id),
    constraint fk_bookmark_member_id
        foreign key (member_id)
            references member (member_id),
    constraint fk_bookmark_portfolio_id
        foreign key (portfolio_id)
            references portfolio (portfolio_id)
);

create table comment
(
    comment_id         bigint not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    content            text,
    member_id          bigint,
    portfolio_id       bigint,
    primary key (comment_id),
    constraint fk_comment_member_id
        foreign key (member_id)
            references member (member_id),
    constraint fk_comment_portfolio_id
        foreign key (portfolio_id)
            references portfolio (portfolio_id)
);

create table contributor
(
    contributor_id     bigint not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    member_member_id   bigint,
    portfolio_id       bigint,
    primary key (contributor_id),
    constraint fk_contributor_member_member_id
        foreign key (member_member_id)
            references member (member_id),
    constraint fk_contributor_portfolio_id
        foreign key (portfolio_id)
            references portfolio (portfolio_id)
);



create table member_login_log
(
    member_login_log_id bigint not null auto_increment,
    created_date        datetime,
    email               varchar(255),
    name                varchar(255),
    member_id           bigint,
    primary key (member_login_log_id),
    constraint fk_member_login_log_member_id
        foreign key (member_id)
            references member (member_id)
);

create table member_sign_up_log
(
    member_sign_up_id bigint not null auto_increment,
    created_date      datetime,
    email             varchar(255),
    name              varchar(255),
    member_id         bigint,
    primary key (member_sign_up_id),
    constraint fk_member_sign_up_log_member_id
        foreign key (member_id)
            references member (member_id)
);



create table portfolio_skill
(
    portfolio_skill_id bigint       not null auto_increment,
    created_date       datetime,
    last_modified_date datetime,
    skill_id           bigint,
    skill_name         varchar(255) not null,
    portfolio_id       bigint,
    primary key (portfolio_skill_id),
    constraint fk_portfolio_skill_portfolio_id
        foreign key (portfolio_id)
            references portfolio (portfolio_id)
);

create table skill
(
    skill_id     bigint       not null auto_increment,
    created_date datetime,
    name         varchar(255) not null,
    primary key (skill_id)
);

create table follow
(
    follow_id      bigint not null auto_increment,
    from_member_id bigint,
    to_member_id   bigint,
    primary key (follow_id),
    constraint uk_from_member_id_to_member_id_on_follow unique (from_member_id, to_member_id),
    constraint fk_follow_from_member_id
        foreign key (from_member_id)
            references member (member_id),
    constraint fk_follow_to_member_id
        foreign key (to_member_id)
            references member (member_id)

);