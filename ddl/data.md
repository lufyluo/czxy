```sql
insert into user (name) values('admin');
insert into account,user_id (account) values ('admin',1);

insert into token (account,user_id,token,expire) values ('admin',1,'supertoken',34567854121)
```