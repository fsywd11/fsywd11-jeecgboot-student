-- 注意：该页面对应的前台目录为views/EduStudentScore文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('176693105893501', NULL, '学生成绩表', '/EduStudentScore/eduStudentScoreList', 'EduStudentScore/EduStudentScoreList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893502', '176693105893501', '添加学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893503', '176693105893501', '编辑学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893504', '176693105893501', '删除学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893505', '176693105893501', '批量删除学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893506', '176693105893501', '导出excel_学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176693105893507', '176693105893501', '导入excel_学生成绩表', NULL, NULL, 0, NULL, NULL, 2, 'EduStudentScore:edu_student_score:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-28 22:10:58', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893508', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893501', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893509', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893502', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893510', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893503', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893511', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893504', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893512', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893505', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893513', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893506', NULL, '2025-12-28 22:10:58', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176693105893514', 'f6817f48af4fb3af11b9e8bf182f618b', '176693105893507', NULL, '2025-12-28 22:10:58', '127.0.0.1');