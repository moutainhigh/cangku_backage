

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

CREATE TABLE `short_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `real_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短链接' ROW_FORMAT = Compact;

-- 优惠券相关sql
INSERT INTO `tag` VALUES ('4', '2', null, '精选套餐', '1', '-1', '2019-11-20 16:18:55', '-1', '2019-11-20 16:18:55', '1');
INSERT INTO `tag` VALUES ('13', '2', null, '精选套餐', '2', '-1', '2019-11-20 16:23:02', '-1', '2019-11-20 16:23:02', '10');

-- 添加订单表字段
ALTER TABLE `orders`
  ADD COLUMN `user_of_coupon_id` BIGINT(20) NULL COMMENT '用户领取的优惠券记录Id',
  ADD COLUMN `coupon_price` DECIMAL(10,2) NULL DEFAULT 0.00 COMMENT '优惠金额' ;

-- 添加订单子表字段
ALTER TABLE `order_ticket`
  ADD COLUMN `coupon_price` DECIMAL(10,2) NULL COMMENT '优惠后的金额，退款时使用';

ALTER TABLE order_ticket ADD COLUMN goods_id BIGINT(20) NULL COMMENT '商品id' ;
ALTER TABLE order_ticket ADD COLUMN goods_name varchar(200)NULL COMMENT '商品名称' ;
ALTER TABLE order_ticket ADD COLUMN project_id BIGINT(20)NULL COMMENT '项目id' ;
ALTER TABLE order_ticket ADD COLUMN project_name varchar(200)NULL COMMENT '项目名称' ;

-- 子订单船票字段
ALTER TABLE `order_ticket`
  ADD COLUMN `ticket_user_name` VARCHAR(45) NULL COMMENT '乘客姓名' ,
  ADD COLUMN `id_card` VARCHAR(45) NULL COMMENT '身份证号' ,
  ADD COLUMN `phone` VARCHAR(45) NULL COMMENT '手机号' ,
  ADD COLUMN `ticket_type` VARCHAR(45) NULL COMMENT '101是成人203是儿童308是小童票' ,
  ADD COLUMN `seat_number` VARCHAR(45) NULL COMMENT '座位号' ,
  ADD COLUMN `ship_ticket_status` VARCHAR(45) NULL COMMENT '船票状态 1 未使用 2已使用 3 已退款',
  ADD COLUMN `baby_info` VARCHAR(45) NULL COMMENT '携童信息';

-- 创建视图
CREATE  OR REPLACE VIEW `promotion` AS
  (
  select
         `id`,
         `code`,
         `rule_id`,
         `company_id`,
         `company_name`,

         `scenic_spots`,
         `name`,
         `status`,
         `cost`,
         `group_type`,

         `start_time`,
         `end_time`,
         `org_name`,
         `create_time`,
         `create_by`,

         `group_manager`,
         `remark`,
         `reason`,
         `reject_promotion`,
         `promotion_crowd_status`,

         `promotion_reject_status`,
         `crowd_promotion` from group_promotion
  )
  union all
  (
  select
         `id`,
         `code`,
         0 'rule_id',
         `company_id`,
         `company_name`,

         `scenic_spots`,
         `name`,
         `status`,
         `cost`,
         2 'group_type',

         `start_time`,
         `end_time`,
         `org_name`,
         `create_time`,
         `create_user_id` 'create_by',

         `manager` group_manager,
         `remark`,
         '' 'reason',
         `reject_promotion`,
         `promotion_crowd_type` promotion_crowd_status,

         `promotion_reject_type` promotion_reject_status,
         `promotion_crowd` crowd_promotion
  from goods_coupon_promotion
  );


ALTER TABLE `goods_extend`
  ADD COLUMN `line_date` DATE NULL COMMENT '航班日期' ,
  ADD COLUMN `line_from` VARCHAR(45) NULL COMMENT '出发地点' ,
  ADD COLUMN `line_to` VARCHAR(45) NULL COMMENT '目的地' ,
  ADD COLUMN `cabin_name` VARCHAR(45) NULL COMMENT '船舱名称',
  ADD COLUMN `ticket_type` VARCHAR(45) NULL COMMENT '1 成人票 2 儿童票';


CREATE
VIEW `coupon_records` AS
  (SELECT
          `o`.`user_of_coupon_id` AS `user_of_coupon_id`,
          `o`.`coupon_price` AS `coupon_price`,
          `o`.`sigle_price` AS `single_price`,
          `o`.`goods_name` AS `goods_name`,
          `o`.`user_id` AS `user_id`,
          `o`.`create_time` AS `create_time`,
          `o`.`goods_price` AS `goods_price`,
          `o`.`state` AS `status`,
          `gp`.`name` AS `project_name`,
          CONCAT_WS(' ',
                    DATE_FORMAT(`o`.`enter_time`, '%Y-%m-%d'),
                    '21:00:00') AS `times`,
          (CASE `gc`.`coupon_type`
             WHEN 1 THEN `gc`.`price`
             WHEN 2 THEN CONCAT(FORMAT((`gc`.`price` / 10), 1), '折')
             ELSE ''
              END) AS `price`,
          LPAD(`gc`.`id`, 8, '0') AS `code`,
          `gc`.`id` AS `coupon_id`,
          `gc`.`coupon_type` AS `coupon_type`
   FROM
        (((`orders` `o`
            JOIN `user_of_coupon` `uoc` ON ((`o`.`user_of_coupon_id` = `uoc`.`id`)))
            JOIN `goods_coupon` `gc` ON ((`uoc`.`goods_coupon_id` = `gc`.`id`)))
            JOIN `goods_project` `gp` ON ((`gp`.`id` = `o`.`project_id`)))
   WHERE
       (`o`.`user_of_coupon_id` IS NOT NULL))


INSERT INTO `goods_project` (`id`,`name`, `project_code`, `create_time`, `company_id`, `operation_time`, `operation_staff`, `project_status`, `service_line`, `project_start_date`, `max_service_amount`, `service_place_id`, `device_amount`, `project_present`, `project_manager`, `project_manager_id`, `abbreviation`, `create_user_id`, `create_user_name`, `update_time`, `update_user_id`, `update_user_name`, `day_operation_time`) VALUES (10,'套餐项目', 'TZ', '2019-11-22 11:37:57', '11', '8:00-18:00', '', '1', '2', '2019-11-22', '1', '1', '1', '', '', '0', '', '3', 'wzdadmin', '2019-12-04 15:19:59', '3', 'wzdadmin', '24');


ALTER TABLE `order_ticket`
  ADD COLUMN `single_price` DECIMAL(10,2) NULL  COMMENT '商品单价，套装商品为其分摊价格 ，单品为售卖价';

-- 增加订单取消原因
ALTER TABLE `orders`
  ADD COLUMN `reason` VARCHAR(100) NULL COMMENT '订单取消原因' AFTER `proof`;

ALTER TABLE `goods_project_operation`
  ADD COLUMN `degree_of_influence` VARCHAR(45) NULL AFTER `probability`;

ALTER TABLE `goods_project_operation_action`
  ADD COLUMN `degree_of_influence` VARCHAR(45) NULL;
-- 更新服务地点名称
UPDATE `service_place` SET `service_place_name`='海洋运动公园' WHERE `id`='1';

-- 添加单日运营时长
ALTER TABLE `goods_project`
  ADD COLUMN `day_operation_time` INT(11) NULL COMMENT '单日运营时长 ，单位 小时' AFTER `update_user_name`;

-- 添加运营因素在小程序端的控制
ALTER TABLE `project_operation_factors`
  ADD COLUMN `is_show_applet` INT(6) NULL COMMENT '是否在小程序端展示  1是 2 否' AFTER `label`;

-- 添加拼团团ID
ALTER TABLE `orders`
  ADD COLUMN `group_order_id` BIGINT(20) NULL COMMENT '拼团团Id' ;
-- 添加订单支付时间
ALTER TABLE `orders`
  ADD COLUMN `pay_time` TIMESTAMP NULL AFTER `group_order_id`;

-- 添加拼团订单拼团活动Id
ALTER TABLE `orders`
  ADD COLUMN `promotion_id` TIMESTAMP NULL AFTER `pay_time`;



ALTER TABLE `goods_extend`
  ADD COLUMN `start_time` TIMESTAMP NULL COMMENT '开始时间' ,
  ADD COLUMN `end_time` TIMESTAMP NULL COMMENT '结束时间';

-- 添加单日运营时长
ALTER TABLE `goods`
  ADD COLUMN `max_service_amount` INT(11) NULL COMMENT '单次服务人数' ;

--
ALTER TABLE `goods`
  ADD COLUMN `day_operation_time` INT(11) NULL COMMENT '单日运营时长 ，单位 小时' ;

-- 添加是否是套餐
ALTER TABLE `goods`
  ADD COLUMN `is_suit` INT(11) NULL COMMENT '是否是套装 1 套餐 2 单品' ;
-- 添加商品表两个字段
ALTER TABLE `goods`
  ADD COLUMN `img` VARCHAR(1024) NULL COMMENT '商品图片',
  ADD COLUMN `description` text NULL COMMENT '商品描述' ;


-- 添加商品表两个字段
ALTER TABLE `goods`
  ADD COLUMN `synopsis` VARCHAR(100) NULL COMMENT '商品简介'


INSERT INTO `tag_category` VALUES (1, '一级标签', NULL, NULL, NULL, NULL);
INSERT INTO `tag_category` VALUES (2, '产品分类标签（二级）', NULL, NULL, NULL, NULL);
INSERT INTO `tag_category` VALUES (3, '产品细分标签（二级）', NULL, NULL, NULL, NULL);
INSERT INTO `tag_category` VALUES (4, '营销标签', NULL, NULL, NULL, NULL);

INSERT INTO `tag` VALUES (1, 1, NULL, '最受欢迎', 1, -1, '2019-11-20 14:15:00', -1, '2019-11-20 14:15:00', 0);
INSERT INTO `tag` VALUES (2, 1, NULL, '一日玩法', 1, -1, '2019-11-20 14:15:14', -1, '2019-11-20 14:15:14', 0);
INSERT INTO `tag` VALUES (3, 2, NULL, '热门单品', 1, -1, '2019-11-20 16:18:36', -1, '2019-11-20 16:18:36', 1);
INSERT INTO `tag` VALUES (4, 2, NULL, '精选套餐', 1, -1, '2019-11-20 16:18:55', -1, '2019-11-20 16:18:55', 1);
INSERT INTO `tag` VALUES (5, 3, NULL, '上午风光', 1, -1, '2019-11-20 16:19:15', -1, '2019-11-20 16:19:15', 2);
INSERT INTO `tag` VALUES (6, 3, NULL, '正午清凉', 1, -1, '2019-11-20 16:19:33', -1, '2019-11-20 16:19:33', 2);
INSERT INTO `tag` VALUES (7, 3, NULL, '午后欢乐', 1, -1, '2019-11-20 16:19:54', -1, '2019-11-20 16:19:54', 2);
INSERT INTO `tag` VALUES (8, 3, NULL, '海上暮色', 1, -1, '2019-11-20 16:20:09', -1, '2019-11-20 16:20:09', 2);
INSERT INTO `tag` VALUES (9, 3, NULL, '夜晚聚会', 1, -1, '2019-11-20 16:20:52', -1, '2019-11-20 16:20:52', 2);
INSERT INTO `tag` VALUES (10, 1, NULL, '最受欢迎', 2, -1, '2019-11-20 16:21:54', -1, '2019-11-20 16:21:54', 0);
INSERT INTO `tag` VALUES (11, 1, NULL, '一日玩法', 2, -1, '2019-11-20 16:22:17', -1, '2019-11-20 16:22:17', 0);
INSERT INTO `tag` VALUES (12, 2, NULL, '热门单品', 2, -1, '2019-11-20 16:22:40', -1, '2019-11-20 16:22:40', 10);
INSERT INTO `tag` VALUES (13, 2, NULL, '精选套餐', 2, -1, '2019-11-20 16:23:02', -1, '2019-11-20 16:23:02', 10);
INSERT INTO `tag` VALUES (14, 3, NULL, '上午风光', 2, -1, '2019-11-20 16:23:30', -1, '2019-11-20 16:23:30', 11);
INSERT INTO `tag` VALUES (15, 3, NULL, '正午清凉', 2, -1, '2019-11-20 16:23:46', -1, '2019-11-20 16:23:46', 11);
INSERT INTO `tag` VALUES (16, 3, NULL, '午后欢乐', 2, -1, '2019-11-20 16:24:10', -1, '2019-11-20 16:24:10', 11);
INSERT INTO `tag` VALUES (17, 3, NULL, '海上暮色', 2, -1, '2019-11-20 16:24:29', -1, '2019-11-20 16:24:29', 11);
INSERT INTO `tag` VALUES (18, 3, NULL, '夜晚聚会', 2, -1, '2019-11-20 16:24:46', -1, '2019-11-20 16:24:46', 11);
INSERT INTO `tag` VALUES (19, 4, NULL, '火热拼团', 2, -1, '2019-11-20 16:26:11', -1, '2019-11-20 16:26:11', NULL);
INSERT INTO `tag` VALUES (20, 4, NULL, '火热拼团', 1, -1, '2019-11-20 16:26:30', -1, '2019-11-20 16:26:30', NULL);
INSERT INTO `tag` VALUES (97, 4, NULL, '特价优惠', 2, -1, '2019-12-10 14:18:55', -1, '2019-12-10 14:18:55', NULL);
INSERT INTO `tag` VALUES (98, 4, NULL, '爆款售卖', 2, -1, '2019-12-10 14:19:25', -1, '2019-12-10 14:19:25', NULL);
INSERT INTO `tag` VALUES (99, 4, NULL, '二销商品', 2, -1, '2020-01-02 15:05:55', -1, '2020-01-02 15:05:55', NULL);
