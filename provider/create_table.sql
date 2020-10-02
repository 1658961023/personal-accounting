-- auto-generated definition
create table t_budget
(
    id            int auto_increment
        primary key,
    budget_amount varchar(255) null comment '预算金额',
    date_type     varchar(255) null comment '周期类型',
    start_date    date         null comment '开始日期',
    end_date      date         null comment '结束日期',
    total_amount  varchar(255) null comment '总金额',
    category      varchar(255) null comment '分类',
    d_amount      varchar(255) null comment '差值',
    expire_flag   varchar(255) null comment '过期标志',
    acct          varchar(255) null comment '账号'
);

-- auto-generated definition
create table t_acct_record
(
    serial_no   varchar(255)  not null comment '流水号'
        primary key,
    budget_type smallint(255) null comment '收支类型',
    category    varchar(255)  null comment '分类',
    amount      varchar(255)  null comment '金额',
    date        date          null comment '记账日期',
    summary     varchar(255)  null comment '摘要',
    acct        varchar(255)  null comment '账号',
    pay         varchar(255)  null comment '支付方式'
);

-- auto-generated definition
create table t_category
(
    id          int auto_increment
        primary key,
    name        varchar(255) null comment '分类名称',
    budget_type smallint     null comment '收支类型',
    acct        varchar(255) null comment '账号'
);

-- auto-generated definition
create table t_schedule
(
    id    int auto_increment
        primary key,
    cron  varchar(255) null comment 'cron表达式',
    today varchar(255) null comment '定时任务中的今天日期'
);

-- auto-generated definition
create table t_target
(
    id            int auto_increment
        primary key,
    target_amount varchar(255) null comment '目标金额',
    date_type     varchar(255) null comment '周期类型',
    start_date    date         null comment '开始日期',
    end_date      date         null comment '结束日期',
    total_amount  varchar(255) null comment '总金额',
    category      varchar(255) null comment '分类',
    d_amount      varchar(255) null comment '差值',
    expire_flag   varchar(255) null comment '过期标志',
    acct          varchar(255) null comment '账号'
);

-- auto-generated definition
create table t_user
(
    id       int auto_increment
        primary key,
    acct     varchar(255) null comment '账号',
    password varchar(255) null comment '密码',
    nickname varchar(255) null comment '昵称',
    sex      varchar(2)   null comment '性别',
    phone    varchar(255) null comment '手机',
    email    varchar(255) null comment '邮箱',
    profile  varchar(255) null comment '头像'
);

-- auto-generated definition
create table t_virtual_acct
(
    id        int auto_increment
        primary key,
    acct_name varchar(255) null comment '账户名',
    income    varchar(255) null comment '收入',
    expend    varchar(255) null comment '支出',
    acct      varchar(255) null comment '用户',
    balance   varchar(255) null comment '余额'
);


