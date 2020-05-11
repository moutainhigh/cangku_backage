CREATE TABLE `short_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `real_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短链接' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

# mall项目初始化数据项
-- 1. 初始化分类和标签
# INSERT INTO `tag_category` VALUES (1, '一级标签', NULL, NULL, NULL, NULL);
# INSERT INTO `tag_category`(`id`, `name`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, '一级标签', NULL, NULL, NULL, NULL);
# INSERT INTO `tag_category` VALUES (2, '产品分类标签（二级）', NULL, NULL, NULL, NULL);
# INSERT INTO `tag_category` VALUES (3, '产品细分标签（二级）', NULL, NULL, NULL, NULL);
# INSERT INTO `tag_category` VALUES (4, '营销标签', NULL, NULL, NULL, NULL);

# INSERT INTO `tag` VALUES (1, 1, NULL, '最受欢迎', 1, -1, '2019-11-20 14:15:00', -1, '2019-11-20 14:15:00', 0);
# INSERT INTO `tag` VALUES (2, 1, NULL, '一日玩法', 1, -1, '2019-11-20 14:15:14', -1, '2019-11-20 14:15:14', 0);
# INSERT INTO `tag` VALUES (3, 2, NULL, '热门单品', 1, -1, '2019-11-20 16:18:36', -1, '2019-11-20 16:18:36', 1);
# INSERT INTO `tag` VALUES (4, 2, NULL, '精选套餐', 1, -1, '2019-11-20 16:18:55', -1, '2019-11-20 16:18:55', 1);
# INSERT INTO `tag` VALUES (5, 3, NULL, '上午风光', 1, -1, '2019-11-20 16:19:15', -1, '2019-11-20 16:19:15', 2);
# INSERT INTO `tag` VALUES (6, 3, NULL, '正午清凉', 1, -1, '2019-11-20 16:19:33', -1, '2019-11-20 16:19:33', 2);
# INSERT INTO `tag` VALUES (7, 3, NULL, '午后欢乐', 1, -1, '2019-11-20 16:19:54', -1, '2019-11-20 16:19:54', 2);
# INSERT INTO `tag` VALUES (8, 3, NULL, '海上暮色', 1, -1, '2019-11-20 16:20:09', -1, '2019-11-20 16:20:09', 2);
# INSERT INTO `tag` VALUES (9, 3, NULL, '夜晚聚会', 1, -1, '2019-11-20 16:20:52', -1, '2019-11-20 16:20:52', 2);
# INSERT INTO `tag` VALUES (10, 1, NULL, '最受欢迎', 2, -1, '2019-11-20 16:21:54', -1, '2019-11-20 16:21:54', 0);
# INSERT INTO `tag` VALUES (11, 1, NULL, '一日玩法', 2, -1, '2019-11-20 16:22:17', -1, '2019-11-20 16:22:17', 0);
# INSERT INTO `tag` VALUES (12, 2, NULL, '热门单品', 2, -1, '2019-11-20 16:22:40', -1, '2019-11-20 16:22:40', 10);
# INSERT INTO `tag` VALUES (13, 2, NULL, '精选套餐', 2, -1, '2019-11-20 16:23:02', -1, '2019-11-20 16:23:02', 10);
# INSERT INTO `tag` VALUES (14, 3, NULL, '上午风光', 2, -1, '2019-11-20 16:23:30', -1, '2019-11-20 16:23:30', 11);
# INSERT INTO `tag` VALUES (15, 3, NULL, '正午清凉', 2, -1, '2019-11-20 16:23:46', -1, '2019-11-20 16:23:46', 11);
# INSERT INTO `tag` VALUES (16, 3, NULL, '午后欢乐', 2, -1, '2019-11-20 16:24:10', -1, '2019-11-20 16:24:10', 11);
# INSERT INTO `tag` VALUES (17, 3, NULL, '海上暮色', 2, -1, '2019-11-20 16:24:29', -1, '2019-11-20 16:24:29', 11);
# INSERT INTO `tag` VALUES (18, 3, NULL, '夜晚聚会', 2, -1, '2019-11-20 16:24:46', -1, '2019-11-20 16:24:46', 11);
# INSERT INTO `tag` VALUES (19, 4, NULL, '火热拼团', 2, -1, '2019-11-20 16:26:11', -1, '2019-11-20 16:26:11', NULL);
# INSERT INTO `tag` VALUES (20, 4, NULL, '火热拼团', 1, -1, '2019-11-20 16:26:30', -1, '2019-11-20 16:26:30', NULL);
# INSERT INTO `tag` VALUES (97, 4, NULL, '特价优惠', 2, -1, '2019-12-10 14:18:55', -1, '2019-12-10 14:18:55', NULL);
# INSERT INTO `tag` VALUES (98, 4, NULL, '爆款售卖', 2, -1, '2019-12-10 14:19:25', -1, '2019-12-10 14:19:25', NULL);
# INSERT INTO `tag` VALUES (99, 4, NULL, '二销商品', 2, -1, '2020-01-02 15:05:55', -1, '2020-01-02 15:05:55', NULL);


-- 2.初始化套餐项目
INSERT INTO `goods_project` (`id`,`name`, `project_code`, `create_time`, `company_id`, `operation_time`, `operation_staff`, `project_status`, `service_line`, `project_start_date`, `max_service_amount`, `service_place_id`, `device_amount`, `project_present`, `project_manager`, `project_manager_id`, `abbreviation`, `create_user_id`, `create_user_name`, `update_time`, `update_user_id`, `update_user_name`, `day_operation_time`) VALUES (10,'套餐项目', 'TZ', '2019-11-22 11:37:57', '11', '8:00-18:00', '', '1', '2', '2019-11-22', '1', '1', '1', '', '', '0', '', '3', 'wzdadmin', '2019-12-04 15:19:59', '3', 'wzdadmin', '24');

-- 3. 初始化视图
-- 3.1 初始化优惠券活动相关视图
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
-- 3.2 初始化优惠券消费记录视图
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
       (`o`.`user_of_coupon_id` IS NOT NULL));

-- 4. 初始化定时任务
INSERT INTO `schedule_jobs` (id, `group_name`, `job_name`, `description`, `cron`, `url`, `status` )
VALUES
       (1, 'activity', 'batch_expired', '批量更新已失效的活动，状态更新为不可用', '0 0 0 * * ?', '/nxj-distribute-v1/activity/batch/expired', 1 ),
       (2, 'activity', 'batch_enable', '批量更新已生效的活动，状态更新为可用', '0 0 0 * * ?', '/nxj-distribute-v1/activity/batch/enable', 1 ),
       (3, 'order', 'batch_expire_order', '批量取消未支付的订单', '0 0/1 * * * ?', '/nxj-mall-v1/orders/wzd/expire', 1 ),
       (4, 'mall', 'sync_route', '同步景区服务线路', '0 0/5 * * * ? ', '/nxj-mall-v1/route/sync', 1 ),
       (5, 'distribute', 'generate_code', '定时更新审核成功的分销商账户的二维码', '0 0/2 * * * ?', '/member-v1/job/getcode', 2 );

-- 5. 初始化服务地点

INSERT INTO `service_place` (id,`service_place_name`, `status`, `company_id`) VALUES (1,'楠溪江服务点', '1', '13');


-- 6. 初始化自增序列
INSERT INTO `incr_num` VALUES (0, '1');
INSERT INTO `incr_num` VALUES (1, '2');
INSERT INTO `incr_num` VALUES (2, '3');
INSERT INTO `incr_num` VALUES (3, '4');
INSERT INTO `incr_num` VALUES (4, '5');
INSERT INTO `incr_num` VALUES (5, '6');
INSERT INTO `incr_num` VALUES (6, '7');

-- 7. 初始化AppVersion
INSERT INTO `app_version` VALUES (13, '1.2.1');


-- 8. 新增定时任务
INSERT INTO `schedule_jobs` ( `group_name`, `job_name`, `description`, `cron`, `url`, `status` )
VALUES
       (6, 'mall-refund', 'order-refund', '订单退款', '0 */1 * * * ?', '/nxj-mall-v1/pay/refund', 1 ),
       (7, 'mall-bill', 'distribute-bill', '当天检票晚上9点生成账单', '0 0 21 * * ?', '/nxj-mall-v1/bill/check', 1 ),
       (8, 'mall-goods', 'synchro-pftGoods', '同步票付通商品数据', '0 */5 * * * ?', '/nxj-mall-v1/task/syn/ticket', 1 );


# INSERT INTO `schedule_jobs` (group_name,job_name,description,cron,url,`status`) VALUES	('mall-nxj','coupon-expire','处理优惠券过期','0 */1 * * * ?','/nxj-mall-v1/pc/coupon/update/status','1');

# INSERT INTO `schedule_jobs` (group_name,job_name,description,cron,url,`status`) VALUES	('mall-nxj','coupon-promotion','优惠券活动','0 */1 * * * ?','/nxj-mall-v1/pc/coupon/promotion/open','1');
