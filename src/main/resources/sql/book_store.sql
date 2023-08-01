CREATE TABLE `book_store`
(
    id int(11) auto_increment primary key,
    name  varchar(255) DEFAULT '' comment '图书名称',
    count int(11) unsigned default 0 comment '库存数量',
    is_delete tinyint(1) default 0 not null comment '1 为已删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
insert into book (name, count)
values
       ('书籍1', 10),
       ('书籍2', 10),
       ('书籍3', 10),
       ('书籍4', 10),
       ('书籍5', 10),
       ('书籍6', 10),
       ('书籍7', 10),
       ('书籍8', 10),
       ('书籍9', 10),
       ('书籍10', 10),
       ('书籍11', 10)
;