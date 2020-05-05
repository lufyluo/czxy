```sql
insert into user (name) values('admin');
insert into account(user_id,account) values (1,'admin');

insert into token (account,user_id,token,expire) values ('admin',1,'supertoken',1456785412);
insert into token (account,user_id,token,expire) values ('admins',999999,'supertoken',1456785412);
```