create table lost_and_found
(
    id              varchar(36) not null
        primary key,
    create_by       varchar(50) null comment '创建人',
    create_time     datetime    null comment '创建日期',
    update_by       varchar(50) null comment '更新人',
    update_time     datetime    null comment '更新日期',
    sys_org_code    varchar(64) null comment '所属部门',
    user_id         varchar(32) null comment '发布人ID',
    user_name       varchar(32) null comment '发布人姓名',
    type            varchar(32) null comment '类型',
    title           varchar(32) null comment '标题',
    content         varchar(32) null comment '详细描述',
    item_category   varchar(32) null comment '物品分类',
    location        varchar(32) null comment '丢失/拾取地点',
    status          varchar(32) null comment '状态',
    claim_user_id   varchar(32) null comment '认领人ID',
    claim_user_name varchar(32) null comment '认领人姓名',
    image           varchar(32) null comment '图片'
);


