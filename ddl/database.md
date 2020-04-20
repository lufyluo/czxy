```sql
create table account
(
  id           int auto_increment
    primary key,
  account      varchar(100)                       not null,
  password     varchar(100)                       not null,
  user_id      int                                null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null,
  constraint account_account_uindex
    unique (account)
)
  comment '账号';

create table address
(
  id           int auto_increment
    primary key,
  name         varchar(100)                       null,
  parent_id    int                                null,
  category     smallint default 0                 null comment '0:省，1：市，2：县',
  code         varchar(16)                        null comment '地区邮编',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '地址';

create table class
(
  id           int auto_increment
    primary key,
  name         varchar(100)                       null,
  teacher_id   int                                null comment '班主任用户id',
  address_id   int                                null,
  org          int                                null comment '所属单位',
  begin_time   datetime                           not null,
  end_time     datetime                           not null,
  arrange_id   int                                null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '班级';

create table course
(
  id           int auto_increment
    primary key,
  name         varchar(100)                       null comment '课程名称',
  description  varchar(100)                       null comment '课程描述',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '课程';

create table course_arrange
(
  id           int auto_increment
    primary key,
  course_id    int                                null,
  content_id   int                                null comment '课程id，可能是点位id，也可能是课题id',
  begin_time   int                                null,
  end_time     int                                null,
  category     smallint default 0                 null comment '课程类型-0：普通，1：点位，2：其它',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '课程表详情';

create table discuss
(
  id           int auto_increment
    primary key,
  user_id      int                                not null,
  class_id     int                                not null,
  arrange_id   int                                not null,
  content      varchar(255)                       not null,
  parent_id    int                                null comment '回复留言id',
  category     smallint default 0                 null comment '留言类型-0：留言，1：回复，2其它',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '课后留言';

create table menus
(
  id           int auto_increment
    primary key,
  parent_id    int                                null,
  name         varchar(20)                        not null,
  code         varchar(60)                        not null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '菜单';

create table op_log
(
  id           int auto_increment
    primary key,
  category     smallint default 0                 null comment '操作类型-0：登录，1：访问，2：其它',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime                           null
)
  comment '操作日志';

create table org
(
  id           int auto_increment
    primary key,
  name         int                                not null,
  address_id   int                                null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null,
  constraint org_name_uindex
    unique (name)
)
  comment '组织单位';

create table plan
(
  id           int auto_increment
    primary key,
  arrang_id    int                                null,
  files        varchar(20)                        null comment '文件（file_id,file_id）',
  name         varchar(100)                       null comment '方案名称',
  types        varchar(30)                        null comment '方案类型（type_id,type_id）',
  topics       varchar(30)                        null comment '方案主题（topic_id,topic_id）',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
);

create table questionitem
(
  id       int auto_increment
    primary key,
  naire_id int          not null,
  title    varchar(100) not null,
  score    int          null,
  `index`  int          not null comment '试题在问卷中的唯一序号'
)
  comment '问卷问题';

create table questionnaire
(
  id           int auto_increment
    primary key,
  name         varchar(100)                       not null,
  class_id     int                                null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '问卷';

create table questionrecieve
(
  id              int auto_increment
    primary key,
  reciever_id     varchar(100)                       null,
  questionitem_id int                                not null,
  score           int                                null,
  content         int                                null comment '主观作答',
  updated_time    datetime default CURRENT_TIMESTAMP null,
  created_time    datetime default CURRENT_TIMESTAMP null
)
  comment '问卷作答';

create table site
(
  id              int auto_increment
    primary key,
  name            varchar(100)                       not null,
  types           varchar(20)                        null comment '点位类型（type_id,type_id）',
  contactor_name  varchar(50)                        null,
  contactor_phone varchar(22)                        null comment '联系人电话',
  address_id      int                                null,
  topics          varchar(40)                        null comment '点位主题（topic_id,topic_id）',
  updated_time    datetime default CURRENT_TIMESTAMP null,
  created_time    datetime default CURRENT_TIMESTAMP null
)
  comment '点位';

create table student
(
  id           int auto_increment
    primary key,
  sign_flag    smallint default 0                 not null comment '是否签到-0：未签到，1：已签到',
  class_id     int                                null,
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '学员';

create table subject
(
  id           int auto_increment
    primary key,
  name         varchar(100)                       not null,
  content      varchar(150)                       null comment '课题简述',
  types        varchar(20)                        null comment '课题类型（type_id,type_id）',
  files        varchar(20)                        null comment '文件（file_id,fileid....）',
  teacher_id   int                                null comment '教师的用户id ',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '课题';

create table teacher
(
  id           int auto_increment
    primary key,
  score        int                                null,
  comment      varchar(255)                       null comment '评语',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
);

create table type
(
  id           int auto_increment
    primary key,
  name         varchar(50)                        not null,
  description  varchar(100)                       null comment '类型描述',
  category     smallint default 0                 null comment '0:类型，1：主题',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null,
  constraint type_name_uindex
    unique (name)
)
  comment '类型';

create table user
(
  id           int auto_increment
    primary key,
  name         varchar(10)                        not null comment '姓名',
  idcard       varchar(20)                        null comment '身份证号码',
  phone        varchar(24)                        null,
  gender       smallint default 0                 null comment '性别',
  position     varchar(255)                       null comment '岗位',
  age          int                                null,
  birthday     date                               null,
  category     smallint                           null comment '用户类型-0：普通用户，1：学员，2：讲师，3：班主任，4：公职人员，5：其它',
  wechat_id    varchar(100)                       null comment '微信用户id',
  updated_time datetime default CURRENT_TIMESTAMP null,
  created_time datetime default CURRENT_TIMESTAMP null
)
  comment '用户信息';

create table user_menu
(
    id           int auto_increment
        primary key,
    user_id int  not null ,
    menu_id int not null ,
    updated_time datetime default CURRENT_TIMESTAMP null,
    created_time datetime default CURRENT_TIMESTAMP null
);


create table token
(
	id serial not null,
	account varchar(60),
	token varchar(180),
	expire integer,
	updated_time timestamp default CURRENT_TIMESTAMP,
	created_time timestamp default CURRENT_TIMESTAMP
);

alter table account alter column password set default 'e10adc3949ba59abbe56e057f20f883e';

```