-- 注意：该页面对应的前台目录为views/test文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2024080610401100330', NULL, 'TikTok达人列表', '/creator/list', 'creator/list', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100331', '2024080610401100330', '新增TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100332', '2024080610401100330', '编辑TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100333', '2024080610401100330', '删除TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100334', '2024080610401100330', '批量删除TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100335', '2024080610401100330', '导出excel_TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024080610401100336', '2024080610401100330', '导入excel_TikTok达人列表', NULL, NULL, 0, NULL, NULL, 2, 'tiktok:creator:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-08-06 10:40:33', NULL, NULL, 0, 0, '1', 0);