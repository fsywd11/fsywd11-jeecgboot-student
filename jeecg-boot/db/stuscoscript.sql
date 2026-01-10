create table edu_student_score
(
    id                  varchar(36)   not null
        primary key,
    create_by           varchar(50)   null comment '创建人',
    create_time         datetime      null comment '创建日期',
    update_by           varchar(50)   null comment '更新人',
    update_time         datetime      null comment '更新日期',
    sys_org_code        varchar(64)   null comment '所属部门',
    select_id           varchar(32)   null comment '选课id',
    student_id          varchar(32)   null comment '学生id',
    course_id           varchar(32)   null comment '课程id',
    term_code           varchar(32)   null comment '学期编码',
    teacher_id          varchar(32)   null comment '教师id',
    usual_score         double(10, 0) null comment '平时成绩',
    mid_score           double(10, 0) null comment '期中成绩',
    final_score         double(10, 0) null comment '期末成绩',
    total_score         double(10, 0) null comment '综合成绩',
    grade_point         double(10, 0) null comment '绩点',
    score_status        varchar(32)   null comment '成绩状态',
    remark              varchar(32)   null comment '备注',
    mid_score_persent   double(10, 0) null comment '期中成绩占比',
    usual_score_persent double(10, 0) null comment '平时成绩占比',
    final_score_persent double(10, 0) null comment '期末成绩占比'
);


