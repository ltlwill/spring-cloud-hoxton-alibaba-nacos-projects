
--------------------------------------业务相关SQL----------------------------------------

--------------------- 用户表 ------------------------
create table if not exists t_user(
  id bigint (21) AUTO_INCREMENT COMMENT '主键',
  name varchar(100) COMMENT '账号',
  nick_name varchar(100) COMMENT '姓名',
  password varchar(50) COMMENT '密码',
  phone_no varchar(50) COMMENT '电话号码',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8mb4 COMMENT='用户表';

----------------------- 2019-11-19 表结构变更  begin ---------------------
alter table t_excel_imp_detail add column file_name varchar(200) comment '文件名';
alter table t_excel_imp_detail add column file_lower_name varchar(200) comment '文件名(小写)';
alter table t_excel_imp_detail add column balance double(20,2) comment '余额';
alter table t_excel_imp_detail add column to_account_name varchar(200) comment '对方户名';
alter table t_excel_imp_detail add column to_account_no varchar(50) comment '对方账号';
alter table t_excel_imp_detail add column to_bank_name varchar(200) comment '对方银行';
alter table t_excel_imp_detail add column summary varchar(1000) comment '摘要';
alter table t_excel_imp_detail add column ip varchar(100) comment 'IP地址';
----------------------- 2019-11-19 表结构变更  end ---------------------

----------------------- 2019-11-06  begin -----------------------
-- excel 导入记录表
create table if not exists t_excel_imp(
	id bigint(21) AUTO_INCREMENT COMMENT '编号',
  file_name varchar(200) not null COMMENT '文件名',
  file_lower_name varchar(200) not null COMMENT '文件名小写',
  file_ext varchar(20) COMMENT '文件扩展名',
  abs_path varchar(500) not NULL COMMENT '文件存放的绝对路径',
  status tinyint(1) not null COMMENT '处理状态(0:失败,1:成功)',
  user_id varchar(10) COMMENT '导入人ID',
  user_name varchar(100) COMMENT '导入人名称',
  import_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8mb4 AUTO_INCREMENT=1000 COMMENT='excel导入记录表';

-- excel 导入明细表
create table if not exists t_excel_imp_detail(
  id bigint (21) AUTO_INCREMENT COMMENT '主键',
  imp_id bigint(21) not null COMMENT '编号(t_excel_imp表的主键)',
  account_name varchar(200) COMMENT '账户名',
  account_no varchar(50) COMMENT '账号',
  bank_name varchar(200) COMMENT '开户行名称',
  bank_no varchar(50) COMMENT '开户行编号',
  transaction_date varchar(100) COMMENT '交易日期',
  transaction_time varchar(100) COMMENT '交易时间',
  income double(20,2) COMMENT '收入',
  expend double(20,2) COMMENT '支出',
  user_id varchar(10) COMMENT '导入人ID',
  user_name varchar(100) COMMENT '导入人名称',
  import_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8mb4 COMMENT='excel导入明细表';

----------------------- 2019-11-06  end -----------------------
