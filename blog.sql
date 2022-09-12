create table if not exists blog_article
(
    article_id   varchar(10)      not null,
    author_id    varchar(10)      null,
    title        varchar(255)     null,
    introduction varchar(255)     null,
    release_time datetime         null,
    update_time  datetime         null,
    set_top      bit default b'0' null,
    deleted      bit default b'0' null,
    visits_count int default 0    null,
    like_count   int default 0    null,
    bg_img       varchar(255)     null,
    primary key (article_id),
    constraint blog_article_article_id_uindex
        unique (article_id)
)
    charset = utf8mb4;

create table if not exists blog_content
(
    article_id varchar(10) not null,
    content    longtext    null,
    primary key (article_id),
    constraint blog_content_article_id_uindex
        unique (article_id)
)
    charset = utf8mb4;

create table if not exists blog_error
(
    id          int auto_increment
        primary key,
    e_msg       longtext null,
    stack_trace longtext null,
    timestamp   datetime null,
    constraint blog_error_id_uindex
        unique (id)
);

create table if not exists blog_filing
(
    article_id varchar(10) default '0' null,
    filing_id  int                     null
)
    charset = utf8mb4;

create index blog_filing_article_id_index
    on blog_filing (article_id);

create table if not exists blog_filing_name
(
    id          int auto_increment
        primary key,
    filing_name varchar(20) null,
    constraint blog_filing_name_id_uindex
        unique (id)
)
    charset = utf8mb4;

create table if not exists blog_message
(
    id          int auto_increment
        primary key,
    author_id   varchar(20) null,
    content     longtext    null,
    create_time datetime    null,
    ip          varchar(60) null,
    region      varchar(30) null,
    constraint blog_message_id_uindex
        unique (id)
);

create table if not exists blog_message_author
(
    qq       varchar(15)  not null,
    nickname varchar(255) null,
    avatar   varchar(255) null,
    primary key (qq),
    constraint blog_message_author_qq_uindex
        unique (qq)
);

create table if not exists blog_tag
(
    article_id varchar(10) null,
    tag_id     varchar(10) null
)
    charset = utf8mb4;

create table if not exists blog_tag_name
(
    tag_id   int auto_increment
        primary key,
    tag_name varchar(50) null,
    constraint blog_tag_name_tag_id_uindex
        unique (tag_id)
)
    charset = utf8mb4;

create table if not exists blog_truck
(
    id           int auto_increment
        primary key,
    module       varchar(100) null,
    operation    varchar(100) null,
    method       varchar(200) null,
    ip           varchar(40)  null,
    execute_time varchar(10)  null,
    br_name      varchar(100) null,
    os_name      varchar(100) null,
    time         datetime     null,
    constraint blog_truck_id_uindex
        unique (id)
);

create table if not exists blog_user
(
    id            varchar(10)                     not null,
    username      varchar(36)                     null,
    password      varchar(64)                     null,
    permission    bit          default b'0'       null,
    tel           varchar(11)                     null,
    email         varchar(64)                     null,
    head_portrait varchar(255) default '默认头像' null,
    salt          varchar(10)                     null,
    deleted       bit          default b'0'       null,
    reg_time      datetime                        null,
    last_login    datetime                        null,
    primary key (id),
    constraint blog_user_id_uindex
        unique (id)
)
    charset = utf8mb4;


