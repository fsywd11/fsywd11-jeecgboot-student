-- 注意：该页面对应的前台目录为views/newLostAndFound文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


-- 主菜单
INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('176715110521601', NULL, '失物招领表', '/newLostAndFound/lostAndFoundList', 'newLostAndFound/LostAndFoundList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0);

-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521602', '176715110521601', '添加失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521603', '176715110521601', '编辑失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521604', '176715110521601', '删除失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521605', '176715110521601', '批量删除失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521606', '176715110521601', '导出excel_失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('176715110521607', '176715110521601', '导入excel_失物招领表', NULL, NULL, 0, NULL, NULL, 2, 'newLostAndFound:lost_and_found:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-12-31 11:18:25', NULL, NULL, 0, 0, '1', 0);

-- 角色授权（以 admin 角色为例，role_id 可替换）
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521608', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521601', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521609', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521602', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521610', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521603', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521611', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521604', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521612', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521605', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521613', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521606', NULL, '2025-12-31 11:18:25', '127.0.0.1');
INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_date, operate_ip) VALUES ('176715110521614', 'f6817f48af4fb3af11b9e8bf182f618b', '176715110521607', NULL, '2025-12-31 11:18:25', '127.0.0.1');