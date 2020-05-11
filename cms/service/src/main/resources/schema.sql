-- we don't know how to generate schema partner (class Schema) :(
create table application
(
	id bigint auto_increment comment '主键'
		primary key,
	partner_id bigint not null comment '合作伙伴id',
	client_type int not null comment '客户端类型 1 微信小程序',
	app_id varchar(50) not null comment '微信或支付宝appId',
	mch_id varchar(100) null comment '微信商户号id',
	mch_key varchar(100) null comment '微信支付商户公匙',
	mch_private_key blob null comment '微信支付商户私匙',
	create_time datetime null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null,
	state int default '1' null comment '禁启用状态 1启用 2禁用',
	is_delete int default '1' null comment '是否删除 1正常 2已删除'
)
comment '合作伙伴客户端'
;

create table partner
(
	id bigint auto_increment comment '主键'
		primary key,
	name varchar(50) not null comment '合作伙伴',
	address varchar(50) not null comment '地址',
	descs varchar(50) not null comment '介绍',
	bank_account varchar(50) not null comment '开户账号',
	bank_name varchar(50) not null comment '银行用户名',
	bank_address varchar(50) not null comment '开户行',
	contact_name varchar(50) not null comment '联系人名称',
	contact_phone varchar(50) not null comment '联系方式',
	contact_email varchar(50) not null comment '联系邮箱地址',
	ip_white_list varchar(50) null comment '服务器白名单',
	server_notice_url varchar(50) null comment '服务器通知地址',
	appkey varchar(50) not null comment 'appKey',
	appsecret varchar(50) not null comment 'appSecret',
	create_time datetime not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
	state int default '1' not null comment '禁启用状态 1启用 2禁用',
	is_delete int default '1' not null comment '是否删除 1正常 2已删除',
	constraint appsecret
		unique (appsecret),
	constraint name
		unique (name)
)
comment '合作伙伴
'
;

