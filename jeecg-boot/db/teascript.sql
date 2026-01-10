create table edu_teacher
(
    id              varchar(36) not null
        primary key,
    create_by       varchar(50) null comment '创建人',
    create_time     datetime    null comment '创建日期',
    update_by       varchar(50) null comment '更新人',
    update_time     datetime    null comment '更新日期',
    sys_org_code    varchar(64) null comment '所属部门',
    user_id         varchar(32) null comment '关联用户ID',
    teacher_no      varchar(32) null comment '工号',
    college         varchar(32) null comment '所属学院',
    department      varchar(32) null comment '教研室',
    title           varchar(32) null comment '职称',
    teacher_id_card varchar(32) null comment '身份证号'
);


