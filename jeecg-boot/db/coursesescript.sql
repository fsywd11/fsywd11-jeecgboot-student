create table edu_course_selection
(
    id           varchar(36) not null
        primary key,
    create_by    varchar(50) null comment '创建人',
    create_time  datetime    null comment '创建日期',
    update_by    varchar(50) null comment '更新人',
    update_time  datetime    null comment '更新日期',
    sys_org_code varchar(64) null comment '所属部门',
    student_id   varchar(32) null comment '学生ID',
    student_no   varchar(32) null comment '学号',
    course_id    varchar(32) null comment '课程ID',
    course_no    varchar(32) null comment '课程编号',
    course_name  varchar(32) null comment '课程名称',
    status       varchar(32) null comment '状态'
);


