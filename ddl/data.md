```sql
insert into user (name) values('admin');
insert into account(user_id,account) values (1,'admin');

INSERT INTO `menus` VALUES (1, NULL, '主页', 'home', '/home', 0, 'HomeIcon', '2020-05-08 16:54:38', '2020-05-08 16:54:38');
INSERT INTO `menus` VALUES (2, NULL, '客户管理', 'khgl', NULL, 1, 'UsersIcon', '2020-05-08 16:55:48', '2020-05-08 16:55:48');
INSERT INTO `menus` VALUES (3, 2, '平台客户管理', 'platformCustomer', '/platformCustomer', 2, NULL, '2020-05-08 16:56:33', '2020-05-08 16:56:33');
INSERT INTO `menus` VALUES (4, 2, '潜在客户操作', 'lurkingCustomer', '/lurkingCustomer', 3, NULL, '2020-05-08 16:57:04', '2020-05-08 16:57:04');
INSERT INTO `menus` VALUES (5, NULL, '权限管理', 'qxgl', NULL, 4, 'SettingsIcon', '2020-05-08 16:57:40', '2020-05-08 16:57:40');
INSERT INTO `menus` VALUES (6, 5, '权限管理', 'permissionsManagement', '/permissionsManagement', 5, NULL, '2020-05-08 16:58:08', '2020-05-08 16:58:08');
INSERT INTO `menus` VALUES (7, NULL, '班级维护', 'bjwh', NULL, 6, 'SmileIcon', '2020-05-08 16:58:57', '2020-05-08 16:58:57');
INSERT INTO `menus` VALUES (8, 7, '班级管理', 'classManagement', '/classManagement', 10, NULL, '2020-05-08 16:59:33', '2020-05-08 16:59:33');
INSERT INTO `menus` VALUES (9, 7, '课表管理', 'curriculumManagement', '/curriculumManagement', 20, NULL, '2020-05-08 17:00:04', '2020-05-08 17:00:04');
INSERT INTO `menus` VALUES (10, NULL, '问卷调查', 'wjdc', NULL, 30, 'PaperclipIcon', '2020-05-08 17:00:51', '2020-05-08 17:00:51');
INSERT INTO `menus` VALUES (11, 10, '问卷调查', 'questionnaireList', '/questionnaireList', 40, NULL, '2020-05-08 17:01:27', '2020-05-08 17:01:27');
INSERT INTO `menus` VALUES (12, NULL, '系统维护', 'xtwh', NULL, 50, 'SmileIcon', '2020-05-08 17:02:02', '2020-05-08 17:02:02');
INSERT INTO `menus` VALUES (13, 12, '学员库管理', 'studentManagement', '/studentManagement', 60, NULL, '2020-05-08 17:02:46', '2020-05-08 17:02:46');
INSERT INTO `menus` VALUES (14, 12, '师资管理', 'teacherManagement', '/teacherManagement', 70, NULL, '2020-05-08 17:03:05', '2020-05-08 17:03:05');
INSERT INTO `menus` VALUES (15, 12, '点位管理', 'pointManagement', '/pointManagement', 80, NULL, '2020-05-08 17:03:26', '2020-05-08 17:03:26');
INSERT INTO `menus` VALUES (16, 12, '课题管理', 'subjectManagement', '/subjectManagement', 90, NULL, '2020-05-08 17:03:43', '2020-05-08 17:03:43');



insert into token (account,user_id,token,expire) values ('admin',1,'supertoken',1456785412);
insert into token (account,user_id,token,expire) values ('admins',999999,'supertoken',1456785412);

insert into site
(name,types, contactor_name, contactor_phone,
 topic_id)
 values
        ('点位1',4,'lufy','186',3),
        ('点位2',4,'lisa','186',5);

insert into type
(name, category)
values
('aa',1),
('qwe',0),
('uhhu',1);
```