/** Create Tables */
CREATE TABLE goods_project_period
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_id BIGINT NULL COMMENT '项目id',
  title VARCHAR(50) NULL COMMENT '时段名称',
  start_time VARCHAR(50) NULL COMMENT '开始时间',
  end_time VARCHAR(50) NULL COMMENT '结束时间',
  orderby TINYINT NULL COMMENT '排序字段',
  status TINYINT NULL COMMENT '1 可用 2 不可用',
  PRIMARY KEY (id)
)
;


CREATE TABLE orders
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  order_code VARCHAR(50) NULL COMMENT '订单号',
  user_id BIGINT NULL COMMENT '用户id',
  scenic_id BIGINT NULL COMMENT '景区',
  type BIGINT NULL COMMENT '类型：5，门票；6，二销产品',
  goods_Id BIGINT NULL COMMENT '商品id',
  goods_name VARCHAR(50) NULL COMMENT '商品名称',
  goods_price DECIMAL(10,2) NULL COMMENT '价格',
  state TINYINT NULL COMMENT '1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单 ',
  expired_time TIMESTAMP NULL COMMENT '过期时间',
  amount INTEGER NULL COMMENT '数量',
  pay_type VARCHAR(50) NULL COMMENT '支付方式：weixin',
  enter_time TIMESTAMP NULL COMMENT '入园时间',
  ticket_order_code VARCHAR(50) NULL COMMENT '对接平台编号',
  id_number VARCHAR(50) NULL COMMENT '购票人身份证',
  name VARCHAR(50) NULL COMMENT '购票人姓名',
  prepay_id VARCHAR(50) NULL COMMENT '支付凭证',
  bat_code VARCHAR(50) NULL COMMENT '第三方底单号',
  bat_code_other VARCHAR(255) NULL COMMENT '第三方其它信息',
  checked_num INTEGER NULL COMMENT '检票数量',
  return_num INTEGER NULL COMMENT '退款数量',
  uncheked_num INTEGER NULL COMMENT '未检票数量',
  sigle_price DECIMAL(10,2) NULL COMMENT '单价',
  is_distribute_order TINYINT NULL COMMENT '是否分销订单 1是 0 否 ',
  order_source INTEGER NULL COMMENT '订单来源 1 大峡谷 2 二维码 ',
  pay_status INTEGER NULL COMMENT '支付状态:待补充 1 未支付 2已付款 3已退款',
  actual_pay DECIMAL(10,2) NULL COMMENT '实付',
  should_pay DECIMAL(10,2) NULL COMMENT '应收',
  user_wechat_name VARCHAR(50) NULL COMMENT '用户微信',
  phone VARCHAR(50) NULL COMMENT '购票人手机号',
  max_number_of_users INTEGER NULL COMMENT '最大使用人数',
  PRIMARY KEY (id)
)  COMMENT='二销商品订单表'
;


CREATE TABLE goods_extend
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  goods_id BIGINT NOT NULL COMMENT '商品id',
  period_id BIGINT NULL COMMENT '时段id',
  timespan VARCHAR(50) NULL COMMENT '商品使用时段',
  orderby TINYINT NULL COMMENT '排序字段',
  sale_price DECIMAL(10,2) NULL COMMENT '售价',
  max_num INTEGER NULL COMMENT '每时段最大服务人数',
  duration SMALLINT NULL COMMENT '持续时间 分钟 为单位',
  status TINYINT NULL COMMENT '1 可用 2 不可用',
  PRIMARY KEY (id)
)  COMMENT='热气球时段表'
;


CREATE TABLE goods_sales_unit
(
  name VARCHAR(50) NULL COMMENT '计价单位'
)
;


CREATE TABLE goods_category
(
  id BIGINT NOT NULL COMMENT '主键',
  parent_id BIGINT NOT NULL COMMENT '父分类id',
  name VARCHAR(50) NULL COMMENT '名称',
  PRIMARY KEY (id)
)  COMMENT='商品分类'
;


CREATE TABLE goods
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_id BIGINT NOT NULL COMMENT '所属项目',
  resource_id BIGINT NULL COMMENT '资源类别',
  category_id BIGINT NOT NULL COMMENT '分类id',
  sales_unit VARCHAR(50) NULL COMMENT '计价单位',
  goods_name VARCHAR(100) NULL,
  is_package TINYINT NULL COMMENT '是否套餐 1 是 0 否',
  base_price DECIMAL(10,2) NULL COMMENT '基础价格',
  max_num INTEGER NULL COMMENT '人数上限',
  service_place VARCHAR(50) NULL COMMENT '使用场地',
  goods_type TINYINT NULL COMMENT '1:热气球 2:船',
  goods_status TINYINT NULL COMMENT '商品状态：1 上架 2 下架',
  goods_code VARCHAR(50) NULL COMMENT '商品编码',
  PRIMARY KEY (id)
)
;


CREATE TABLE goods_resource
(
  id BIGINT NOT NULL COMMENT '主键',
  name VARCHAR(50) NULL COMMENT '名称',
  PRIMARY KEY (id)
)  COMMENT='资源类别'
;


CREATE TABLE goods_project
(
  id BIGINT NOT NULL COMMENT '主键',
  name VARCHAR(50) NULL COMMENT '项目名称',
  project_code VARCHAR(50) NULL COMMENT '项目编号',
  PRIMARY KEY (id)
)  COMMENT='二销产品所属项目'
;


CREATE TABLE goods_project_operation
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  period_id BIGINT NULL COMMENT '时段id',
  service_place_id BIGINT NULL COMMENT '1 地点1 2 地点2 ',
  operation_date DATE NULL COMMENT '时段标题',
  period_title VARCHAR(50) NULL COMMENT '标题',
  probability INTEGER NULL COMMENT '100 表示 100%',
  status TINYINT NULL COMMENT '1 正常 2 停飞',
  remark VARCHAR(50) NULL COMMENT '原因',
  PRIMARY KEY (id)
)  COMMENT='项目运营表'
;


CREATE TABLE goods_project_operation_action
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  from_status BIGINT NULL COMMENT '变化前状态',
  to_status TINYINT NULL COMMENT '变化后状态',
  from_probability SMALLINT NULL COMMENT '变化前百分比',
  to_probability SMALLINT NULL COMMENT '变化后百分比',
  remark VARCHAR(255) NULL COMMENT '变化原因',
  operation_by BIGINT NULL COMMENT '操作人id',
  operation_time TIMESTAMP NULL COMMENT '操作时间',
  operation_id BIGINT NULL COMMENT '运营单元id',
  PRIMARY KEY (id)
)
;


CREATE TABLE order_ticket
(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  order_id BIGINT NULL COMMENT '订单id',
  ticket_code VARCHAR(50) NULL COMMENT '订单明细编码',
  check_in_time TIMESTAMP NULL COMMENT '核销时间',
  timespan INTEGER NULL COMMENT '时间段',
  check_in_operation_id BIGINT NULL COMMENT '核销人',
  confirm_completed_time TIMESTAMP NULL COMMENT '确认结束时间',
  confirm_operator_id BIGINT NULL COMMENT '确认完成运营id',
  settle_time TIMESTAMP NULL COMMENT '完结时间',
  settler_operator_id BIGINT NULL COMMENT '确认人',
  remark VARCHAR(255) NULL COMMENT '标注',
  status INTEGER NULL COMMENT '订单明细状态',
  PRIMARY KEY (id)
)  COMMENT='订单明细表'
;



ALTER TABLE goods_project_period

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE orders

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN distributor_id BIGINT NULL COMMENT '分销商id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;


ALTER TABLE goods_extend

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_sales_unit

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_category

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_resource

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_project

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_project_operation

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE goods_project_operation_action

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

ALTER TABLE order_ticket

  ADD COLUMN create_time TIMESTAMP NULL COMMENT '创建时间' ,

  ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id' ,

  ADD COLUMN create_user_name VARCHAR(45) NULL COMMENT '创建人名称' ,

  ADD COLUMN update_time TIMESTAMP NULL COMMENT '更新时间' ,

  ADD COLUMN update_user_id BIGINT NULL COMMENT '更新人id',

  ADD COLUMN update_user_name VARCHAR(45) NULL COMMENT '修改人名称' ;

-- 入园数据
CREATE TABLE `sys_ticket_report` (
                                   `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `report_date` DATE NULL COMMENT '日期',
                                   `sell_out_count` INT NULL COMMENT '卖出票数',
                                   `check_in_count` INT NULL COMMENT '检票人数',
                                   PRIMARY KEY (`id`),
                                   UNIQUE INDEX `report_date_UNIQUE` (`report_date` ASC))
  COMMENT = '入园分析';

ALTER TABLE goods ADD COLUMN rules text NULL COMMENT '商品使用规则' AFTER goods_code;

CREATE TABLE `schedule_jobs`  (
                                `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '定时任务主键',
                                `group_name` varchar(50) NOT NULL COMMENT '任务分组（可按业务分）',
                                `job_name` varchar(50) NOT NULL COMMENT '任务名称（英文）',
                                `description` varchar(100) COMMENT '定时任务描述',
                                `cron` varchar(50) NOT NULL COMMENT '定时任务执行频率（cron表达式）',
                                `url` varchar(255) NOT NULL COMMENT '访问路径',
                                `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-暂停执行 1-执行',
                                PRIMARY KEY (`id`),
                                UNIQUE INDEX `idx_group_job_unique`(`group_name`, `job_name`)
);

CREATE TABLE `weather_sun` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `search_text` varchar(22) DEFAULT NULL COMMENT '搜索词',
                             `city_code` varchar(22) DEFAULT NULL COMMENT '城市代码',
                             `humidity` float(22,2) DEFAULT NULL COMMENT '湿度',
                             `temperature` int(22) DEFAULT NULL COMMENT '温度',
                             `wind` varchar(255) DEFAULT NULL COMMENT '风向',
                             `windp` int(255) DEFAULT NULL COMMENT '风级',
                             `date_time` date DEFAULT NULL COMMENT '时间',
                             `pub_time` time DEFAULT NULL COMMENT '刷新时间',
                             `weather` varchar(22) DEFAULT NULL COMMENT '天气',
                             `sunrise_time` time DEFAULT NULL COMMENT '日出时间',
                             `sunset_time` time DEFAULT NULL COMMENT '日落时间',
                             `forecast` text COMMENT '预告天气',
                             `spider_code` varchar(255) DEFAULT NULL COMMENT '采集批次(爬虫试用)',
                             `spider_time` datetime DEFAULT NULL COMMENT '采集时间(爬虫试用)',
                             `md5` varchar(100) DEFAULT NULL COMMENT 'md5(爬虫试用)',
                             `maxtemp` varchar(22) DEFAULT NULL COMMENT '最高温',
                             `mintemp` varchar(22) DEFAULT NULL COMMENT '最低温',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `md5` (`md5`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1103 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;




-- 定时任务初始化
-- 注意：根据唯一索引判断是否存在，如果存在则做更新操作，不存在则做新增操作
INSERT INTO `schedule_jobs` ( `group_name`, `job_name`, `description`, `cron`, `url`, `status` )
VALUES
( 'activity', 'batch_expired', '批量更新已失效的活动，状态更新为不可用', '0 0 0 * * ?', '/wzd-distribute-v1/activity/batch/expired', 1 ),
( 'activity', 'batch_enable', '批量更新已生效的活动，状态更新为可用', '0 0 0 * * ?', '/wzd-distribute-v1/activity/batch/enable', 1 ),
( 'order', 'batch_expire_order', '批量取消未支付的订单', '0 0/1 * * * ?', '/wzd-mall-v1/orders/wzd/expire', 1 ),
( 'mall', 'sync_route', '同步景区服务线路', '0 0/5 * * * ? ', '/wzd-mall-v1/route/sync', 1 ),
( 'distribute', 'generate_code', '定时更新审核成功的分销商账户的二维码', '0 0/2 * * * ?', '/member-v1/job/getcode', 1 );

ALTER TABLE goods_extend ADD COLUMN time_label VARCHAR ( 50 ) COMMENT '标签' AFTER `STATUS`;

-- 参与分润状态
ALTER TABLE `orders`
  ADD COLUMN `profit_status` SMALLINT NULL COMMENT '参与分润状态  1 参与分润 2 不参与分润';



-- 添加分销商信息快照
ALTER TABLE `orders`
ADD COLUMN `snapshot` TEXT NULL COMMENT '分销商信息快照';

-- 添加唯一索引
ALTER TABLE `orders`
ADD UNIQUE INDEX `order_code_UNIQUE` (`order_code` ASC);


ALTER TABLE `sys_ticket_report`
ADD COLUMN `scans_count` INT(11) NULL COMMENT '扫码次数' AFTER `check_in_count`,
ADD COLUMN `scans_percent` DOUBLE NULL COMMENT '扫码转化率' AFTER `scans_count`,
ADD COLUMN `order_count` INT(11) NULL COMMENT '预订人数' AFTER `scans_percent`,
ADD COLUMN `order_percent` DOUBLE NULL COMMENT '预订转化率' AFTER `order_count`,
ADD COLUMN `order_finish_count` INT(11) NULL COMMENT '体验成功人数' AFTER `order_percent`,
ADD COLUMN `order_finish_percent` DOUBLE NULL COMMENT '体验成功率' AFTER `order_finish_count`;



-- 添加离线订单sql脚本
ALTER TABLE  `orders`
  ADD COLUMN `order_type` SMALLINT(6) NULL DEFAULT '1' COMMENT '订单类型 1 线上订单 2 离线订单' AFTER `profit_status`,
  ADD COLUMN `offline_user` VARCHAR(45) NULL DEFAULT NULL COMMENT '离线操作人' AFTER `order_type`,
  ADD COLUMN `offline_status` SMALLINT(6) NULL DEFAULT NULL COMMENT '离线订单的状态 1 可编辑可删除 2 不可编辑不可删除 3 删除' AFTER `offline_user`;



ALTER TABLE `orders`
  CHANGE COLUMN `offline_status` `offline_status` SMALLINT(6) NULL DEFAULT 2 COMMENT '离线订单的状态 1 可编辑可删除 2 不可编辑不可删除 3 删除' ;

CREATE TABLE `scan_code_data_col` (
                                    `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `column_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '接触点名称',
                                    `app_id` INT(11) NULL DEFAULT 1 COMMENT '接触点所属app 1 西藏小程序      ',
                                    PRIMARY KEY (`id`));
CREATE TABLE `scan_code_data_value` (
                                             `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `scan_date` DATE NULL COMMENT '扫码日期 yyy-MM-dd',
                                             `scan_amount` BIGINT(20) NULL DEFAULT 0 COMMENT '扫码次数 默认为0',
                                             `scan_type` BIGINT(20) NULL DEFAULT 1 COMMENT '数据类型 1 扫码次数 2 扫码人次 3 扫码未进入小程序的人数',
                                             `col_id` BIGINT(20) NULL COMMENT '关联列',
                                             PRIMARY KEY (`id`));

ALTER TABLE `goods_project`
  ADD COLUMN `company_id` INT(11) NULL COMMENT '所属景区Id' AFTER `create_time`,
  ADD COLUMN `operation_time` VARCHAR(45) NULL COMMENT '项目运营时间' AFTER `company_id`,
  ADD COLUMN `operation_staff` VARCHAR(255) NULL COMMENT '运营人员，格式为Id 多个运营人员以逗号分割' AFTER `operation_time`,
  ADD COLUMN `project_status` INT(11) NULL COMMENT '项目状态 1 启用  2  禁用' AFTER `operation_staff`,
  ADD COLUMN `service_line` VARCHAR(45) NULL COMMENT '服务线路 多个线路用逗号隔开' AFTER `project_status`,
  ADD COLUMN `project_start_date` DATE NULL COMMENT '项目开始时间' AFTER `service_line`,
  ADD COLUMN `max_service_amount` INT(11) NULL COMMENT '单次服务总人数' AFTER `project_start_date`,
  ADD COLUMN `service_place_id` VARCHAR(45) NULL COMMENT '项目运营地点 多个运营地点用逗号分割' AFTER `max_service_amount`,
  ADD COLUMN `device_amount` INT(11) NULL COMMENT '设备数量' AFTER `service_place_id`,
  ADD COLUMN `project_present` text NULL COMMENT '项目介绍' AFTER `device_amount`,
  ADD COLUMN `project_manager` varchar(45) NULL COMMENT '项目负责人' AFTER `project_present`,
  ADD COLUMN `project_manager_id` BIGINT(20) default 0 COMMENT '项目负责人id' AFTER `project_manager`;

ALTER TABLE `goods_project`
  CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ;



CREATE TABLE `service_place` (
                                              `id` BIGINT(20) NOT NULL  auto_increment COMMENT '主键',
                                              `service_place_name` VARCHAR(45) NULL COMMENT '服务地点名称',
                                              `status` INT NULL COMMENT '1 启用 2 禁用',
                                              `company_id` INT NULL COMMENT '所属景区的id',
                                              PRIMARY KEY (`id`));

CREATE TABLE `route` (
                                  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `code` VARCHAR(45) NULL COMMENT '线路编号',
                                  `name` VARCHAR(45) NULL COMMENT '线路名称',
                                  `scenic` BIGINT(20) NULL COMMENT '景区Id',
                                  `state` INT NULL COMMENT '线路状态：1代表启用，2代表停用。默认1',
                                  `type` INT NULL COMMENT '景区内外1、内；2、外',
                                  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                  `create_user_name` varchar(45) DEFAULT NULL COMMENT '创建人名称',
                                  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `update_user_name` varchar(45) DEFAULT NULL COMMENT '修改人名称',
                                  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                  PRIMARY KEY (`id`));


-- 初始化服务地点
INSERT INTO `service_place` (id,`service_place_name`, `status`, `company_id`) VALUES (1,'海上运动基地', '1', '11');

alter table `goods` add `service_route` varchar(200) DEFAULT NULL COMMENT '服务路线';
alter table `goods` add `single_service_duration` int(11) DEFAULT NULL COMMENT '单次服务时间';
alter table `goods` add `retail_price` decimal(10,2) DEFAULT NULL COMMENT '分销价格';

-- 新增项目ID
ALTER TABLE orders ADD project_id int(2) DEFAULT NULL COMMENT '项目id';

-- 新增订单二维码和凭证码
ALTER TABLE `orders`
  ADD COLUMN `qr_code` VARCHAR(255) NULL COMMENT '核销二维码地址' AFTER `project_id`,
  ADD COLUMN `proof` VARCHAR(255) NULL COMMENT '凭证码' AFTER `qr_code`;

-- 新增项目运营地点
  CREATE TABLE `goods_project_staff_place` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_id` bigint(20) DEFAULT NULL COMMENT '运营人员',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `place_id` bigint(20) DEFAULT NULL COMMENT '地点id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_goods_project_staff_place_staff_id_project_id_place_id` (`staff_id`,`project_id`,`place_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='运营人员项目运营地点';



-- 商品归属项目初始化
-- 注意：根据主键判断是否存在，如果存在则做更新操作，不存在则做新增操作
INSERT INTO `goods_project_period` ( `id`, `project_id`, `title`, `start_time`, `end_time`, `orderby`, `status` )
VALUES
( 3, 1, '08:00-09:00', '08:00', '09:00', 3, 1 ),
( 4, 1, '09:00-10:00', '09:00', '10:00', 4, 1 ),
( 5, 1, '10:00-11:00', '10:00', '11:00', 5, 1 ),
( 6, 1, '11:00-12:00', '11:00', '12:00', 6, 1 ),
( 7, 1, '12:00-13:00', '12:00', '13:00', 7, 1 ),
( 8, 1, '13:30-14:00', '13:00', '14:00', 8, 1 ),
( 9, 1, '14:00-15:00', '14:00', '15:00', 9, 1 ),
( 10, 1, '15:00-16:00', '15:00', '16:00', 10, 1 ),
( 11, 1, '16:00-17:00', '16:00', '17:00', 11, 1 ),
( 12, 1, '17:00-18:00', '17:00', '18:00', 12, 1 )
ON DUPLICATE KEY UPDATE update_time = now( );

-- 添加线路图编码唯一索引
ALTER TABLE `route`
  ADD UNIQUE INDEX `code_UNIQUE` (`code` ASC);
-- 添加项目简称
ALTER TABLE `goods_project`
  CHANGE COLUMN `update_user_name` `update_user_name` VARCHAR(45) NULL DEFAULT NULL COMMENT '项目简称' ,
  ADD COLUMN `abbreviation` VARCHAR(45) NULL AFTER `project_manager_id`;
-- 项目编号唯一
ALTER TABLE `goods_project`
  ADD UNIQUE INDEX `project_code_UNIQUE` (`project_code` ASC);

-- 添加定时
INSERT INTO `schedule_jobs` (group_name,job_name,description,cron,url,`status`) VALUES	('distribute_wzd','generate_code_wzd','定时更新审核成功的分销商账户的二维码涠洲岛','0 0/2 * * * ?','/member-v1/job/getcodeWZD','1');
-- 更改第三方订单号长度
ALTER TABLE  `orders`
  CHANGE COLUMN `bat_code` `bat_code` VARCHAR(255) NULL DEFAULT NULL COMMENT '第三方底单号' ;


ALTER TABLE `order_ticket` ADD COLUMN `snapshot` TEXT NULL COMMENT '核销信息';



/** Drop Tables, Stored Procedures and Views */
update service_place set service_place_name='海上运动公园' where id=1;

-- 商品是否分时段售卖
alter table `goods` add `is_by_period_operation` tinyint(4) DEFAULT 1 COMMENT '是否分时段运营 2不分时段，1分时段';

-- 核销信息
ALTER TABLE `order_ticket` ADD COLUMN `snapshot` TEXT NULL COMMENT '核销信息';
/** 增加默认运营项目*/
CREATE TABLE `goods_project_staff` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `staff_id` bigint(20) DEFAULT NULL COMMENT '运营人员',
                                     `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `idx_goods_project_staff_place_staff_id_project_id` (`staff_id`,`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='运营人员项目管理';


ALTER TABLE `scan_code_data_col`
  CHANGE COLUMN `app_id` `company_id` INT(11) NULL DEFAULT NULL COMMENT '景区Id 西藏小程序7 涠洲岛11 巴松措10' ;


--  自增序列,用于mysql逗号分割列转行查询
CREATE TABLE `incr_num` (
                          `id` int(11) NOT NULL  COMMENT '自增序列',
                          `value` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ;

-- 自增序列从 0 开始
INSERT INTO `incr_num` VALUES (0, '1');
INSERT INTO `incr_num` VALUES (1, '2');
INSERT INTO `incr_num` VALUES (2, '3');
INSERT INTO `incr_num` VALUES (3, '4');
INSERT INTO `incr_num` VALUES (4, '5');
INSERT INTO `incr_num` VALUES (5, '6');
INSERT INTO `incr_num` VALUES (6, '7');

CREATE TABLE `app_version` (
                             `companyId` int(11) NOT NULL COMMENT '公司id',
                             `version` varchar(20) DEFAULT '' COMMENT '版本',
                             PRIMARY KEY (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `app_version` VALUES (11, '1.2.1');

-- 涠洲岛上线
-- 添加项目运营因素表
CREATE TABLE  `project_operation_factors` (
                                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `project_id` BIGINT(20) NULL COMMENT '项目ID',
                                            `factor_type` TINYINT(6) NULL COMMENT '因素类型 1 运营时段因素 2 非时段因素',
                                            `operation_status` TINYINT(6) NULL COMMENT '运营状态1 正常 2 停止',
                                            `operation_status_type` TINYINT(6) NULL COMMENT '1 数值 2 文字标签',
                                            `status` TINYINT(6) NULL COMMENT '状态 1 有效 2无效',
                                            `desc_value` VARCHAR(45) NULL COMMENT '文字描述',
                                            `reason` VARCHAR(45) NULL COMMENT '原因,多个原因用逗号分割',
                                            `degree_of_influence` VARCHAR(45) NULL COMMENT '影响范围程度',
                                            `label` VARCHAR(45) NULL COMMENT '文字描述标签',
                                            PRIMARY KEY (`id`));

-- 增加订单取消原因
ALTER TABLE `orders`
  ADD COLUMN `reason` VARCHAR(100) NULL COMMENT '订单取消原因' AFTER `proof`;

ALTER TABLE `goods_project_operation`
  ADD COLUMN `degree_of_influence` VARCHAR(45) NULL AFTER `probability`;

ALTER TABLE `goods_project_operation_action`
  ADD COLUMN `degree_of_influence` VARCHAR(45) NULL;
-- 更新服务地点名称
UPDATE `service_place` SET `service_place_name`='海洋运动公园' WHERE `id`='1';


-- 增加订单评价状态
ALTER TABLE orders ADD comm_sts int(1) DEFAULT '1' COMMENT '评论状态： 1:未评价  2:已评价';

-- 小程序是否展示
ALTER TABLE orders ADD delete_status int(1) DEFAULT '1' COMMENT '小程序订单展示： 1:没有删除,2:删除';

-- 增加订单退款状态
ALTER TABLE order_ticket ADD refund_sts int(1) DEFAULT '1' COMMENT '退款状态 1.未退款 2.已退款';





-- 商品 航班信息
ALTER TABLE goods_extend ADD ship_line_info varchar(1000) NULL COMMENT '航班信息，json格式';


-- 百邦达订单状态
ALTER TABLE order_ticket ADD COLUMN ticket_state_bbd INT NULL COMMENT '百邦达票状态，0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款 410 票改签' ;

ALTER TABLE order_ticket ADD COLUMN ticket_serial_bbd VARCHAR(45) NULL COMMENT '百邦达票号' ;

ALTER TABLE order_ticket ADD COLUMN order_serial_bbd VARCHAR(45) NULL COMMENT '百邦达订单ID' ;

ALTER TABLE order_ticket ADD COLUMN ticket_id_bbd VARCHAR(45) NULL COMMENT '百邦达票ID' ;

ALTER TABLE order_ticket ADD COLUMN ticket_qr_code_bbd VARCHAR(45) NULL COMMENT '百邦达票二维码';


ALTER TABLE order_ticket
  modify COLUMN `refund_ ratio` `refund_ratio` VARCHAR(45) NULL DEFAULT NULL COMMENT '退款费率' ;

-- 下单同步至百邦达失败后保存数据，后续重试
CREATE TABLE IF NOT EXISTS `synchr_order_to_bbd_faild` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_date` datetime DEFAULT NULL COMMENT '同步时间',
  `order_code` varchar(45) DEFAULT NULL COMMENT '本地订单号',
  `fixed` int(10) DEFAULT '0' COMMENT '是否已经修复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE order_ticket ADD COLUMN is_ticket_printed int(2) DEFAULT '0' COMMENT '打印状态 0.未打印 1.已打印';

ALTER TABLE `order_ticket`
  ADD COLUMN `change_info` TEXT(0) NULL COMMENT '佰邦达票改签信息';


ALTER TABLE `goods`
  CHANGE COLUMN `goods_type` `goods_type` INT NULL DEFAULT NULL COMMENT '1:热气球 2:船' ;
