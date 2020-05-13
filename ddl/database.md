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
alter table user
	add org_id int null comment '工作单位';

alter table user
	add nation varchar(10) null comment '民族';
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


alter table org modify name varchar(50) not null;

alter table user
	add native varchar(255) null comment '籍贯';
alter table user change native nativeplace varchar(255) null comment '籍贯';

alter table class
	add topics varchar(40) null comment '班级主题id';


alter table course
	add type smallint default 0 null comment '0-党政综合；1-社会管理；2-农业农村；3-城建规划；4-经济与产业；5-能力素质提升；20-其他' after description;
alter table site
	add drive_time varchar(50) null after topics;
create table problem_track
(
    id           int auto_increment
        primary key,
    feedback     varchar(255)                       null comment '反馈',
    metion_time  datetime default CURRENT_TIMESTAMP null comment '提出时间',
    advise       varchar(255)                       null comment '整改意见',
    status       smallint default 0                 not null comment '是否解决',
    remark       varchar(255)                       null comment '备注',
    updated_time datetime default CURRENT_TIMESTAMP not null,
    created_time datetime default CURRENT_TIMESTAMP null,
    source       smallint default 0                 null comment '0-教师；1-点位'
)
    comment '问题追踪';

create table fee_detail
(
	id int auto_increment,
	type smallint not null comment '费用类型',
	detail varchar(100) null,
	pay int null,
	audit int null,
	total_pay int null comment '总支出',
	subtotal int null comment '小计',
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint fee_detail_pk
		primary key (id)
)
comment '问题明细';

alter table fee_detail
	add class_id int null;

create table stock
(
	id int auto_increment,
	type varchar(20) null comment '总类',
	name varchar(50) null,
	unit int not null comment '单位',
	spec varchar(20) null comment '规格',
	spend int null comment '消耗',
	op smallint null comment '0-入库操作；1-出库操作',
	origin_id int null comment '隶属哪个入库记录，以计算总量',
	class_id int null,
	user_id int null,
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint stock_pk
		primary key (id)
)
comment '库存';
alter table stock change name goods_id int null;

create table goods
(
	id int auto_increment,
	name varchar(20) not null,
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint goods_pk
		primary key (id)
)
comment '物品';

-- alter table problem_track
-- 	add source_id int null after source_type;

alter table problem_track change source source_type smallint default 0 null comment '0-教师；1-点位' after remark;

alter table student
	add user_id int not null after class_id;
alter table student
	add type smallint default 0 null comment '0-学员；1-班委干部；8-带班领导' after user_id;
alter table class change org org_id int null comment '所属单位';

alter table course
	add teacher_id int not null comment '课题讲师' after type;

create table class_master
(
	id int auto_increment,
	user_id int not null,
	type smallint default 0 not null comment '0-班主任；1-副班主任',
	updated_time datetime default now() null,
	created_time datetime default now() null,
constraint class_master_pk
		primary key (id)
)
comment '班级班主任和副班主任';
alter table class drop column teacher_id;

alter table fee_detail modify audit int null comment '审计';

alter table course_arrange
    add `index` int null comment '第几天的课程安排' after category;

alter table class_master
	add class_id int null after type;

alter table class
	add recommend_org_id int null comment '推荐单位' after arrange_id;

alter table class
	add duration int null comment '周期（天）';

alter table class
	add composition_id int null comment '班级成分' after recommend_org_id;

create table composition
(
	id int auto_increment,
	name varchar(30) not null,
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint composition_pk
		primary key (id)
)
comment '班级成分类别';


alter table fee_detail modify type varchar(30) not null comment '费用类型名称';

alter table course_arrange change course_id description varchar(100) null comment '课表描述';

alter table course_arrange change description arrange_id int not null comment '课表描述';

create table arrange
(
    id           int auto_increment
        primary key,
    name         varchar(50)                        not null,
    description  varchar(100)                       null,
    updated_time datetime default CURRENT_TIMESTAMP null,
    created_time datetime default CURRENT_TIMESTAMP null
)
    comment '课程';

alter table course_arrange comment '课程课题关系表详情';
create table class_arrange
(
	id int auto_increment,
	class_id int not null,
	arrange_id int not null,
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint class_arrange_pk
		primary key (id)
)
comment '班级课表关系';

alter table teacher
	add user_id int not null after comment;


alter table site add addr varchar(255) null after drive_time;

alter table course_arrange modify begin_time bigint null;

alter table course_arrange modify end_time bigint null;

alter table course_arrange change `index` offset int null comment '第几天的课程安排,日期偏移量';

alter table class
	add appellation int null comment '培训对象，比如 中组部，书记、干部。。。' after composition_id;

alter table class modify topics varchar(40) null comment '班级主题id 对应type表 category为1' after appellation;

alter table class modify duration int null comment '周期（天）' after topics;

alter table class modify appellation varchar(50) null comment '培训对象，比如 中组部，书记、干部。。。';

alter table address
	add path varchar(255) null after code;

alter table teacher
	add `system` smallint null comment '0-党校系统，1-领导干部系统，2-高校系统' after user_id;

alter table teacher
	add skill varchar(20) null comment '职称' after `system`;

alter table teacher
	add star varchar(8) null comment '星级' after skill;

alter table teacher
	add area varchar(10) null comment '中央级别、四川省级别、成都市级别....' after star;

alter table user
	add education varchar(10) null comment '学历';

alter table class drop column address_id;

alter table class
	add province_id int null comment '对应addressid，省' after duration;

alter table class
	add city_id int null comment '对应addressid，市' after province_id;

alter table class
	add county int null comment '对应addressid，县、区...' after city_id;

alter table user modify gender smallint default 0 null comment '性别,0-男，1-女，2-未知';

alter table site
	add province_id int null comment '对应addressid，省' after addr;

alter table site
	add city_id int null comment '对应addressid，市' after province_id;

alter table site
	add county_id int null comment '对应addressid，县、区...' after city_id;
alter table site drop column address_id;
alter table site
	add description varchar(200) null after addr;

create table file
(
	id int auto_increment,
	name varchar(150) not null,
	code varchar(64) null,
	extension varchar(10) null,
	size bigint null,
	updated_time datetime default now() null,
	created_time datetime default now() null,
	constraint file_pk
		primary key (id)
);
alter table file
	add url varchar(255) null;

alter table site
	add pics varchar(255) null after description;

alter table site
	add attach_files varchar(255) null after pics;
alter table class change county county_id int null comment '对应addressid，县、区...';
alter table student
	add org_id int null after type;

create table course_excute_arrange
(
    id           int auto_increment
        primary key,
    arrange_id   int                                not null comment '课表描述',
    content_id   int                                null comment '课程id，可能是点位id，也可能是课题id',
    begin_time   bigint                             null,
    end_time     bigint                             null,
    category     smallint default 0                 null comment '课程类型-0：普通，1：点位，2：其它',
    offset       int                                null comment '第几天的课程安排,日期偏移量',
    teacher_instead_id int null comment '替代老师id',
    updated_time datetime default CURRENT_TIMESTAMP null,
    created_time datetime default CURRENT_TIMESTAMP null
)
    comment '执行课程课题关系表详情，类似课表快照';

alter table class
    add addr varchar(150) null comment '详细地址' after county_id;

alter table type modify category smallint default 0 null comment '0:类型，1：主题,2：班级成分';

alter table class change appellation composition varchar(50) null comment '培训对象，比如 中组部，书记、干部。。。';

alter table user
	add politics varchar(20) null comment '政治面貌';
alter table teacher
	add pay int null comment '课酬，单位元/天' after area;


alter table address modify category smallint default 0 null comment '0:国家，1:省，2：市，3：县/区';

alter table user_menu modify menu_codes varchar(255) not null;

alter table menus
	add url varchar(100) null comment '前端路由' after code;

alter table menus
	add `order` int default 0 null after url;

alter table menus
	add icon varchar(50) null after `order`;

alter table subject
	add category int not null comment '0-党政综合，1-社会管理，2-农业农村，3-城建规划，4-经济与产业，5-能力素质提升，9-其他' after teacher_id;

create table greetings
(
	id int auto_increment,
	type smallint null comment '类型',
	name varchar(50) null comment '节假日名称',
	greet varchar(255) null comment '问候语',
	date datetime null comment '问候语发送时间',
	constraint greetings_pk
		primary key (id)
)
comment '问候语';
alter table greetings modify type varchar(30) null comment '类型';
alter table greetings change time send_time datetime null comment '问候语发送时间';


```