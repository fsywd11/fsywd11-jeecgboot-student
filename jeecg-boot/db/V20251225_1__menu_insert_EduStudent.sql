-- 注意：该页面对应的前台目录为views/edustudent文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('176662691917301', NULL, '学生信息表', '/edustudent/eduStudentList', 'edustudent/EduStudentList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917302', '176662691917301', '添加学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917303', '176662691917301', '编辑学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917304', '176662691917301', '删除学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917305', '176662691917301', '批量删除学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917306', '176662691917301', '导出excel_学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662691917307', '176662691917301', '导入excel_学生信息表', NULL, NULL, 0, NULL, NULL, 2, 'edustudent:edu_student:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 09:41:59', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917408', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917301', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917409', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917302', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917410', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917303', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917411', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917304', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917412', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917305', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917413', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917306', NULL, '2025-12-25 09:41:59', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662691917414', 'f6817f48af4fb3af11b9e8bf182f618b', '176662691917307', NULL, '2025-12-25 09:41:59', '127.0.0.1');