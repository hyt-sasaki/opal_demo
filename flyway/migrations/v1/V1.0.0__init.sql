CREATE TABLE messages
(
    id         bigint       not null primary key,
    body       varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp
);

CREATE TABLE data
(
    id         bigint    not null primary key,
    flag       boolean   not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
);
