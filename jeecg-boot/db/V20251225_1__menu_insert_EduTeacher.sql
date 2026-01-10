-- 注意：该页面对应的前台目录为views/eduteacher文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('176662846987701', NULL, '教师信息表', '/eduteacher/eduTeacherList', 'eduteacher/EduTeacherList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987802', '176662846987701', '添加教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987803', '176662846987701', '编辑教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987804', '176662846987701', '删除教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987805', '176662846987701', '批量删除教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987806', '176662846987701', '导出excel_教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176662846987807', '176662846987701', '导入excel_教师信息表', NULL, NULL, 0, NULL, NULL, 2, 'eduteacher:edu_teacher:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-25 10:07:49', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987908', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987701', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987909', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987802', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987910', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987803', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987911', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987804', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987912', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987805', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987913', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987806', NULL, '2025-12-25 10:07:49', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176662846987914', 'f6817f48af4fb3af11b9e8bf182f618b', '176662846987807', NULL, '2025-12-25 10:07:49', '127.0.0.1');