```sql
insert into user (name) values('admin');
insert into account(user_id,account) values (1,'admin');

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