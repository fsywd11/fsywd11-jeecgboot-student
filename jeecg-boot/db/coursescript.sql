create table edu_course
(
    id              varchar(36) not null
        primary key,
    create_by       varchar(50) null comment '创建人',
    create_time     datetime    null comment '创建日期',
    update_by       varchar(50) null comment '更新人',
    update_time     datetime    null comment '更新日期',
    sys_org_code    varchar(64) null comment '所属部门',
    course_no       varchar(32) null comment '课程编号',
    course_name     varchar(32) null comment '课程名称',
    college         varchar(32) null comment '开课学院',
    credit          varchar(32) null comment '学分',
    class_hour      varchar(32) null comment '课时',
    teacher_id      varchar(32) null comment '授课教师ID',
    teacher_name    varchar(32) null comment '授课教师姓名',
    course_time     varchar(32) null comment '上课时间',
    classroom       varchar(32) null comment '上课教室',
    max_student     varchar(32) null comment '最大选课人数',
    current_student varchar(32) null comment '当前选课人数',
    status          varchar(32) null comment '状态'
);


