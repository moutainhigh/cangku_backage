
DROP TABLE IF EXISTS `activity_base`;
CREATE TABLE IF NOT EXISTS `activity_base` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scenic_id` bigint(20) DEFAULT NULL COMMENT '景区Id',
  `scenic_name` varchar(50) DEFAULT NULL COMMENT '景区名称',
  `activity_type` tinyint(255) DEFAULT '2' COMMENT '活动类型 1 优惠活动 2 拼团活动 3 抽奖活动',
  `activity_aim` varchar(200) DEFAULT NULL COMMENT '活动目标',
  `activity_name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  `activity_title` varchar(50) DEFAULT NULL COMMENT '活动标题',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '活动结束时间',
  `state` tinyint(255) DEFAULT '1' COMMENT '状态 1 活动中  2 未开始 3 结束 4 已失效 ',
  `cost` int(12) DEFAULT NULL COMMENT '预计成本',
  `activity_leador` varchar(50) DEFAULT NULL COMMENT '活动负责人',
  `leador_phone` varchar(50) DEFAULT NULL COMMENT '负责人电话',
  `divide_department` varchar(50) DEFAULT NULL COMMENT '分摊部门 [{divideDepartment:"部门1",“divideRatio”:"10"}]',
  `divide_ratio` varchar(50) DEFAULT NULL COMMENT '分摊比例',
  `activity_count` int(12) DEFAULT NULL COMMENT '活动总量',
  `description` text COMMENT '活动描述',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_benefit 结构
DROP TABLE IF EXISTS `activity_benefit`;
CREATE TABLE IF NOT EXISTS `activity_benefit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_base_id` bigint(20) DEFAULT NULL COMMENT '活动基础信息Id',
  `cost` int(12) DEFAULT NULL COMMENT '预计成本',
  `order_number` int(12) DEFAULT NULL COMMENT '收益订单数量',
  `gross_profit` int(12) DEFAULT NULL COMMENT '毛利收益',
  `customer_proportion` varchar(50) DEFAULT NULL COMMENT '客群占比',
  `goods_sort` varchar(50) DEFAULT NULL COMMENT '产品占比排序',
  `channel_sort` varchar(50) DEFAULT NULL COMMENT '分销渠道 l排序视图',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_discount_rule 结构
DROP TABLE IF EXISTS `activity_discount_rule`;
CREATE TABLE IF NOT EXISTS `activity_discount_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `algorithms` tinyint(255) DEFAULT '1' COMMENT '优惠算法 1 早定优惠 2 特价直减 3 满减优惠',
  `discount_rule` text COMMENT '优惠规则',
  `remark` varchar(500) DEFAULT NULL COMMENT '活动规则描述',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_draw_rule 结构
DROP TABLE IF EXISTS `activity_draw_rule`;
CREATE TABLE IF NOT EXISTS `activity_draw_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `draw_order_condition` tinyint(255) DEFAULT NULL COMMENT '抽奖订单条件,1单笔订单 2多笔订单',
  `draw_man_condition` tinyint(255) DEFAULT NULL COMMENT '抽奖条件,1人头 2金额',
  `satisfy_price` int(12) DEFAULT NULL COMMENT '满足多少价钱的金额抽奖1次',
  `isincrease` tinyint(255) DEFAULT NULL COMMENT '次数是否递增,1是 2否',
  `draw_size` int(12) DEFAULT NULL COMMENT '抽奖次数(次/天/人)',
  `draw_type` tinyint(255) DEFAULT NULL COMMENT '抽奖方式 1 九宫格 2 转盘 3 摇一摇',
  `open_draw_cycle` tinyint(255) DEFAULT NULL COMMENT '开奖频率 1 每日一次 2 活动终结一次',
  `cash_time_type` tinyint(255) DEFAULT NULL COMMENT '兑奖时间类型 1 活动结束后 2中奖后',
  `cash_time` int(12) DEFAULT NULL COMMENT '兑奖时间',
  `cash_code` tinyint(255) DEFAULT NULL COMMENT '兑奖标识 1 手机号 2微信号 3 身份证号 4地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `cash` varchar(255) DEFAULT NULL COMMENT '奖品类型(json)',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_goods 结构
DROP TABLE IF EXISTS `activity_goods`;
CREATE TABLE IF NOT EXISTS `activity_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id(如果是全部产品id为-1)',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_type` tinyint(255) DEFAULT '1' COMMENT '产品/资源类型，来自于产品',
  `sale_price` int(12) DEFAULT NULL COMMENT '销售价格',
  `refund_type` tinyint(255) DEFAULT '1' COMMENT '退款类型 1 常规退款 2 不予退款',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_group_rule 结构
DROP TABLE IF EXISTS `activity_group_rule`;
CREATE TABLE IF NOT EXISTS `activity_group_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `group_valid_hours` int(11) DEFAULT NULL COMMENT '拼团有效期,单位小时',
  `group_size` int(11) DEFAULT NULL COMMENT '拼团人数(2-10)',
  `isauto_create_group` tinyint(4) DEFAULT NULL COMMENT '是否自动成团,1是 2否',
  `auto_create_group_limit` tinyint(4) DEFAULT NULL COMMENT '自动成团人数',
  `group_type` tinyint(4) DEFAULT NULL COMMENT '1普通团 2 超级团',
  `group_limit` int(11) DEFAULT NULL COMMENT '每单限购数量',
  `state` tinyint(4) DEFAULT NULL COMMENT '1 可用 2 不可用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `period` tinyint(4) DEFAULT NULL COMMENT '体验时段 1 上午 2 下午 3 全天',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_platform 结构
DROP TABLE IF EXISTS `activity_platform`;
CREATE TABLE IF NOT EXISTS `activity_platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `platform_id` bigint(20) DEFAULT NULL COMMENT '投放平台Id',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态 1 投放  2 取消投放 ',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_rule 结构
DROP TABLE IF EXISTS `activity_rule`;
CREATE TABLE IF NOT EXISTS `activity_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_base_id` bigint(20) DEFAULT NULL COMMENT '活动基础信息Id',
  `activity_type` tinyint(255) DEFAULT '2' COMMENT '活动类型 1 优惠活动 2 拼团活动 3 抽奖活动',
  `refund_type` tinyint(255) DEFAULT '1' COMMENT '退款类型 1 常规退款 2 不予退款',
  `goods_limit` tinyint(255) DEFAULT '1' COMMENT '产品范围 1 全部产品 2 指定产品',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.activity_share 结构
DROP TABLE IF EXISTS `activity_share`;
CREATE TABLE IF NOT EXISTS `activity_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_base_id` bigint(20) DEFAULT NULL COMMENT '活动基础信息Id',
  `img_share` varchar(50) DEFAULT NULL COMMENT '图片分享',
  `link_share` varchar(50) DEFAULT NULL COMMENT '链接分享',
  `share_number` int(12) DEFAULT NULL COMMENT '分享次数',
  `share_get_coupon_id` bigint(20) DEFAULT NULL COMMENT '分享获得优惠券id',
  `share_get_coupon_number` int(12) DEFAULT NULL COMMENT '分享可获得优惠券数量',
  `scan_number` int(12) DEFAULT NULL COMMENT '分享后被扫描次数',
  `scan_get_coupon_id` bigint(20) DEFAULT NULL COMMENT '分享后被扫描获得优惠券id',
  `scan_get_coupon_number` int(12) DEFAULT NULL COMMENT '分享后被扫描可获得优惠券数量',
  `order_number` int(12) DEFAULT NULL COMMENT '分享后成功订单数',
  `order_get_coupon_id` bigint(20) DEFAULT NULL COMMENT '分享后成功下订单获得优惠券id',
  `order_get_coupon_number` int(12) DEFAULT NULL COMMENT '分享后成功下订单可获得优惠券数量',
  `qr_code` varchar(50) DEFAULT NULL COMMENT '活动二维码',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.application 结构
DROP TABLE IF EXISTS `application`;
CREATE TABLE IF NOT EXISTS `application` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.channel 结构
DROP TABLE IF EXISTS `channel`;
CREATE TABLE IF NOT EXISTS `channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) DEFAULT NULL COMMENT '营销渠道标识id',
  `channel_name` varchar(50) DEFAULT NULL COMMENT '营销渠道名',
  `scenic_id` bigint(20) DEFAULT NULL COMMENT '景区Id',
  `scenic_name` varchar(50) DEFAULT NULL COMMENT '景区名称',
  `state` tinyint(255) DEFAULT '2' COMMENT '状态 1 启用 2 关闭',
  `channel_type` tinyint(255) DEFAULT '2' COMMENT '渠道类型 1 直营 2 分销',
  `tag` varchar(255) DEFAULT NULL COMMENT '业务标签',
  `register_resource` varchar(50) DEFAULT NULL COMMENT '注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3 公众号注册',
  `app_register` varchar(50) DEFAULT NULL COMMENT 'app注册免审批 1 入驻资料免审 2 补充资料免审',
  `operation` varchar(50) DEFAULT NULL COMMENT '运营方式 1 线上运营 2 线下运营',
  `docking` varchar(50) DEFAULT NULL COMMENT '数据方式 1 线上对接 2 接口对接',
  `settlement` varchar(50) DEFAULT NULL COMMENT '结算方式 1 底价结算 2 返利结算',
  `sale_goods_type` tinyint(255) DEFAULT '1' COMMENT '指定销售产品 1 不限 2 指定',
  `ishave_rule` tinyint(255) DEFAULT '2' COMMENT '是否有可用政策 1 是 2 否',
  `goods_number` int(12) DEFAULT NULL COMMENT '商品个数',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.channel_rule 结构
DROP TABLE IF EXISTS `channel_rule`;
CREATE TABLE IF NOT EXISTS `channel_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道Id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id(如果是全部产品id为-1)',
  `rule_day` timestamp NULL DEFAULT NULL COMMENT '渠道政策可用日期',
  `rebate_unit` tinyint(255) DEFAULT '1' COMMENT '返利单位  1 商品个数 ',
  `rebate_format` tinyint(255) DEFAULT '2' COMMENT '返利格式  1 百分比 2金额 ',
  `isdistribuor_level` tinyint(255) DEFAULT '2' COMMENT '是否渠道商等级  1 是 2 否',
  `multiple_server` varchar(500) DEFAULT NULL COMMENT '其他服务政策保存Byte数组',
  `base_rule` text COMMENT '基础政策信息',
  `award_distribuor_level` text COMMENT '渠道商等级  1 初级 2 中级 3 高级(多个等级逗号隔开)',
  `award_rule` text COMMENT '奖励政策信息',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.coupon 结构
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(200) DEFAULT NULL COMMENT '券id-根据规则生成',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) DEFAULT '1' COMMENT '状态 1 有效 2 无效',
  `coupon_type` tinyint(1) DEFAULT NULL COMMENT '优惠券类型 1-体验券 2-满减券 3-代金券',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.coupon_cash_rule 结构
DROP TABLE IF EXISTS `coupon_cash_rule`;
CREATE TABLE IF NOT EXISTS `coupon_cash_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券ID',
  `rebate_method` tinyint(255) DEFAULT '1' COMMENT '优惠计算方式 1 金额  2 折扣/百分比',
  `rebate_price` int(12) DEFAULT NULL COMMENT '加价多少/折扣多少',
  `israndom` tinyint(255) DEFAULT '2' COMMENT '是否随机金额 1 是  2 不是',
  `min_price` int(12) DEFAULT NULL COMMENT '随机最小金额',
  `max_price` int(12) DEFAULT NULL COMMENT '随机最大金额',
  `goods_special` tinyint(255) DEFAULT '2' COMMENT '是否产品专用  1 是 2 否',
  `issend` tinyint(255) DEFAULT '1' COMMENT '是否可以转赠 1 是 2 否',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间（有效时间）',
  `validity_day` int(10) DEFAULT '0' COMMENT '有效期类型 2领取后几日可用 有效天数',
  `validity_type` int(10) DEFAULT '1' COMMENT '有效期类型 1-固定期限 2领取后几日可用',
  `unuse_start_time` timestamp NULL DEFAULT NULL COMMENT '不可用开始时间',
  `unuse_end_time` timestamp NULL DEFAULT NULL COMMENT '不可用结束时间',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.coupon_experience_rule 结构
DROP TABLE IF EXISTS `coupon_experience_rule`;
CREATE TABLE IF NOT EXISTS `coupon_experience_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券ID',
  `experience_method` tinyint(255) DEFAULT '1' COMMENT '体验次数是否共享 1 共享次数  2各产品次数 ',
  `experience_number` int(12) DEFAULT NULL COMMENT '体验次数',
  `period_type` tinyint(1) DEFAULT NULL COMMENT '时段类型 1 上午08-13  2 下午13-18 3 全天08-18',
  `issend` tinyint(255) DEFAULT '1' COMMENT '是否可以转赠 1 是 2 否',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间（有效时间）',
  `validity_day` int(10) DEFAULT '0' COMMENT '有效期类型 2领取后几日可用 有效天数',
  `validity_type` int(10) DEFAULT '1' COMMENT '有效期类型 1-固定期限 2领取后几日可用',
  `unuse_start_time` timestamp NULL DEFAULT NULL COMMENT '不可用开始时间',
  `unuse_end_time` timestamp NULL DEFAULT NULL COMMENT '不可用结束时间',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.coupon_full_reduce_rule 结构
DROP TABLE IF EXISTS `coupon_full_reduce_rule`;
CREATE TABLE IF NOT EXISTS `coupon_full_reduce_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券ID',
  `satisfy_price` int(12) DEFAULT NULL COMMENT '满足多少价钱的金额',
  `rebate_method` tinyint(255) DEFAULT '1' COMMENT '优惠计算方式 1 金额  2 折扣/百分比',
  `rebate_price` int(12) DEFAULT NULL COMMENT '加价多少/折扣多少',
  `goods_special` tinyint(255) DEFAULT '2' COMMENT '是否产品专用  1 是 2 否',
  `issend` tinyint(255) DEFAULT '1' COMMENT '是否可以转赠 1 是 2 否',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间（有效时间）',
  `validity_day` int(10) DEFAULT '0' COMMENT '有效期类型 2领取后几日可用 有效天数',
  `validity_type` int(10) DEFAULT '1' COMMENT '有效期类型 1-固定期限 2领取后几日可用',
  `unuse_start_time` timestamp NULL DEFAULT NULL COMMENT '不可用开始时间',
  `unuse_end_time` timestamp NULL DEFAULT NULL COMMENT '不可用结束时间',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.coupon_goods 结构
DROP TABLE IF EXISTS `coupon_goods`;
CREATE TABLE IF NOT EXISTS `coupon_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券ID',
  `coupon_type` tinyint(1) DEFAULT NULL COMMENT '优惠券类型 1-体验券 2-满减券 3-代金券',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `goods_extend_id` bigint(20) DEFAULT NULL COMMENT '商品Id(如果是全部产品id为-1)',
  `goods_extend_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_type` tinyint(255) DEFAULT '1' COMMENT '产品/资源类型，来自于产品',
  `price_type` tinyint(255) DEFAULT '1' COMMENT '价格类目根据产品中记录',
  `cost_price` int(12) DEFAULT NULL COMMENT '成本价格',
  `refund_type` tinyint(255) DEFAULT '1' COMMENT '退款类型 1 常规退款 2 不予退款',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `sell_price` int(12) DEFAULT NULL COMMENT '销售价格',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商ID',
  `supplier_name` varchar(50) DEFAULT NULL COMMENT '供应商名称',
  `project_id` bigint(20) DEFAULT NULL COMMENT '产品/资源类型，来自于产品',
  `project_name` varchar(50) DEFAULT NULL COMMENT '产品/资源类型，来自于产品',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_account 结构
DROP TABLE IF EXISTS `distributor_account`;
CREATE TABLE IF NOT EXISTS `distributor_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_base_id` bigint(20) DEFAULT NULL COMMENT '分销商Id',
  `account_number` varchar(50) DEFAULT NULL COMMENT '账号',
  `account_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `state` tinyint(255) DEFAULT '2' COMMENT '状态 1正常 2 锁定',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `send_message` tinyint(255) DEFAULT '2' COMMENT '是否短信通知 1是 2 否',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_add 结构
DROP TABLE IF EXISTS `distributor_add`;
CREATE TABLE IF NOT EXISTS `distributor_add` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_base_id` bigint(20) DEFAULT NULL COMMENT '分销商Id',
  `business_license1` varchar(255) DEFAULT NULL COMMENT '营业执照正面',
  `business_license2` varchar(255) DEFAULT NULL COMMENT '营业执照反面',
  `id_card_page1` varchar(255) DEFAULT NULL COMMENT '身份证正面',
  `id_card_page2` varchar(255) DEFAULT NULL COMMENT '身份证背面',
  `driver_card_page1` varchar(255) DEFAULT NULL COMMENT '驾驶证正面',
  `driver_card_page2` varchar(255) DEFAULT NULL COMMENT '驾驶证反面',
  `guide_card_page1` varchar(255) DEFAULT NULL COMMENT '导游证正面',
  `guide_card_page2` varchar(255) DEFAULT NULL COMMENT '导游证反面',
  `withdraw_account_type` tinyint(255) DEFAULT '2' COMMENT '提现账户类型 1 微信 2 银行卡',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名',
  `bank_code` varchar(8) DEFAULT NULL COMMENT '银行代码',
  `verify_state` tinyint(255) DEFAULT '2' COMMENT '审核状态：0-待审核 1-审核通过 2-审核未通过，补充信息审核',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `bank_account_name` varchar(50) DEFAULT NULL COMMENT '银行账户名',
  `bank_card_number` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  `wechat_nickname` varchar(50) DEFAULT NULL COMMENT '微信',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_bank 结构
DROP TABLE IF EXISTS `distributor_bank`;
CREATE TABLE IF NOT EXISTS `distributor_bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_base_id` bigint(20) DEFAULT NULL COMMENT '分销商Id',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `bank_code` varchar(8) DEFAULT NULL COMMENT '银行代码',
  `user_name` varchar(20) DEFAULT NULL COMMENT '账户名',
  `card_number` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `state` tinyint(255) DEFAULT '2' COMMENT '状态 1 正常 2 停用',
  `bank_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注(账户用途)',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_base 结构
DROP TABLE IF EXISTS `distributor_base`;
CREATE TABLE IF NOT EXISTS `distributor_base` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_name` varchar(50) DEFAULT NULL COMMENT '分销商名称',
  `scenic_id` bigint(20) DEFAULT NULL COMMENT '景区Id',
  `scenic_name` varchar(50) DEFAULT NULL COMMENT '景区名称',
  `city_id` bigint(20) DEFAULT NULL COMMENT '城市Id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区Id',
  `area_name` varchar(50) DEFAULT NULL COMMENT '区名称',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道Id',
  `channel_name` varchar(50) DEFAULT NULL COMMENT '渠道名称',
  `distributor_type` tinyint(255) DEFAULT '2' COMMENT '分销商类型 1 集团企业 2 个人企业 3 个人',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '景区Id',
  `state` tinyint(255) DEFAULT '2' COMMENT '状态 1 有效 2 无效',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `verify_state` tinyint(255) DEFAULT '2' COMMENT '审核状态：0-待审核 1-审核通过 2-审核未通过，分销身份审核',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `code` varchar(50) DEFAULT NULL COMMENT '分销商编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_business 结构
DROP TABLE IF EXISTS `distributor_business`;
CREATE TABLE IF NOT EXISTS `distributor_business` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_base_id` bigint(20) DEFAULT NULL COMMENT '分销商Id',
  `level` tinyint(255) DEFAULT '2' COMMENT '综合等级 0 特级 1 一级 2 二级',
  `channel_type` tinyint(255) DEFAULT '2' COMMENT '渠道类型  1 直营 2 分销',
  `resource_type` varchar(50) DEFAULT NULL COMMENT '资源类型  1 酒店 2 导游 3 租车',
  `grade` bigint(20) DEFAULT NULL COMMENT '积分',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `cooperation_method` tinyint(255) DEFAULT '2' COMMENT '合作方式  1 线上运营 2 线下运营',
  `settlement_method` tinyint(255) DEFAULT '2' COMMENT '结算方式  1 底价结算 2 返利结算',
  `settlement_type` tinyint(255) DEFAULT '2' COMMENT '结算周期（类型）  1 月结 2 日结',
  `enjoy_service` varchar(20) DEFAULT NULL COMMENT '享受服务',
  `business_scope` varchar(20) DEFAULT NULL COMMENT '业务范围',
  `business_counterpart` varchar(20) DEFAULT NULL COMMENT '业务对接人',
  `preferential_strength` varchar(20) DEFAULT NULL COMMENT '优惠力度',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xinao.distributor_contact 结构
DROP TABLE IF EXISTS `distributor_contact`;
CREATE TABLE IF NOT EXISTS `distributor_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `distributor_base_id` bigint(20) DEFAULT NULL COMMENT '分销商Id',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信',
  `position` varchar(20) DEFAULT NULL COMMENT '职务',
  `province_id` bigint(20) DEFAULT NULL COMMENT '省Id',
  `province_name` varchar(50) DEFAULT NULL COMMENT '省名称',
  `city_id` bigint(20) DEFAULT NULL COMMENT '城市Id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `area_id` bigint(20) DEFAULT NULL COMMENT '区Id',
  `area_name` varchar(50) DEFAULT NULL COMMENT '区名称',
  `contact_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xinao.group_order 结构
DROP TABLE IF EXISTS `group_order`;
CREATE TABLE IF NOT EXISTS `group_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_activity_id` bigint(20) DEFAULT NULL COMMENT '外键,指向活动id',
  `group_order_code` varchar(50) DEFAULT NULL COMMENT '拼团编码',
  `goods_num` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `group_size` int(11) DEFAULT NULL COMMENT '成团人数',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(1待成团 2 拼团中 3拼团成功 4拼团失败)',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '拼团结束时间',
  `available_time` timestamp NULL DEFAULT NULL COMMENT '拼团有效时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '团长用户id',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_group_order_code` (`group_order_code`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.group_order_item 结构
DROP TABLE IF EXISTS `group_order_item`;
CREATE TABLE IF NOT EXISTS `group_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_order_id` bigint(20) DEFAULT NULL COMMENT '拼团表外键',
  `group_order_code` varchar(50) DEFAULT NULL COMMENT '拼团编码',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `join_time` timestamp NULL DEFAULT NULL COMMENT '加入时间',
  `order_id` bigint(20) DEFAULT NULL COMMENT '外键 指向订单库订单表中的id',
  `is_header` tinyint(4) DEFAULT NULL COMMENT '是否是团长',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1 已付款 2已退款 ）',
  `refund_code` varchar(50) DEFAULT NULL COMMENT '退款单号',
  `goods_extend_id` bigint(20) DEFAULT NULL COMMENT '时段Id',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `refund_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',
  `group_order_type` tinyint(4) DEFAULT NULL COMMENT '参团类型 1好友邀请 2游客参团',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_order_id` (`group_order_id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.partner 结构
DROP TABLE IF EXISTS `partner`;
CREATE TABLE IF NOT EXISTS `partner` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.platform 结构
DROP TABLE IF EXISTS `platform`;
CREATE TABLE IF NOT EXISTS `platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) DEFAULT NULL COMMENT '投放平台名称',
  `state` tinyint(1) DEFAULT NULL COMMENT '状态 1 可用  2 不可用',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

-- 数据导出被取消选择。

-- 导出  表 xinao.user_of_coupons 结构
DROP TABLE IF EXISTS `user_of_coupons`;
CREATE TABLE IF NOT EXISTS `user_of_coupons` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券id',
  `coupon_status` tinyint(1) DEFAULT NULL COMMENT '优惠券状态 1 有效 2 无效',
  `coupon_type` tinyint(1) DEFAULT NULL COMMENT '优惠券状态 1 有效 2 无效',
  `coupon_code` varchar(50) DEFAULT NULL COMMENT '券码',
  `grant_time` timestamp NULL DEFAULT NULL COMMENT '发放日期',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '领取日期',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id(会员id)',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称（会员名称）',
  `consumption_time` timestamp NULL DEFAULT NULL COMMENT '消费日期',
  `state` tinyint(1) DEFAULT '1' COMMENT '状态( 1未领取 2领取未使用 3已使用  4已过期 5转让中 6已转让)',
  `activity_base_id` bigint(20) DEFAULT NULL COMMENT '活动基础信息Id',
  `activity_rule_id` bigint(20) DEFAULT NULL COMMENT '活动规则Id',
  `activity_name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  `activity_status` tinyint(1) DEFAULT NULL COMMENT '活动状态 1 活动中 2 未开始 3 结束 4 已失效',
  `coupon_resource` tinyint(1) DEFAULT '1' COMMENT '优惠券来源 1-自己领取 2-朋友赠送',
  `validity_time` timestamp NULL DEFAULT NULL COMMENT '有效期',
  `open_id` varchar(100) DEFAULT NULL COMMENT 'openId-用户信息-用户用户转赠记录',
  `order_price` int(12) DEFAULT NULL COMMENT '订单价格',
  `isdelete` tinyint(255) DEFAULT '1' COMMENT '逻辑删除 1 未删除 2 已删除',
  `version` tinyint(255) DEFAULT '1' COMMENT '乐观锁',
  `company_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `updator_id` bigint(20) DEFAULT NULL COMMENT '更改人id',
  `creator_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `updator_name` varchar(20) DEFAULT NULL COMMENT '更改人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ;

