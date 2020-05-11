package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 渠道政策信息表
 * @author jiaby
 */
@Data
@Table(name = "channel_rule")
public class ChannelRule extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "渠道Id")
    @Column(name = "channel_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "渠道Id")
    private Long channelId;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品Id(如果是全部产品id为-1)")
    private Long goodsId;

    @Column(name = "rule_day",type = MySqlTypeConstant.TIMESTAMP,comment = "渠道政策可用日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @ApiModelProperty("渠道政策可用日期")
    private Timestamp ruleDay;

    @Column(name = "rebate_unit",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "返利单位  1 商品个数 ")
    @ApiModelProperty("返利单位  1 商品个数")
    private Byte rebateUnit;

    @Column(name = "rebate_format",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "返利格式  1 百分比 2金额 ")
    @ApiModelProperty("返利格式  1 百分比 2金额 ")
    private Byte rebateFormat;

    @Column(name = "isdistribuor_level",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "是否渠道商等级  1 是 2 否")
    @ApiModelProperty("是否渠道商等级  1 是 2 否 ")
    private Byte isdistribuorLevel;

    @ApiModelProperty(value = "其他服务政策保存Byte数组")
    @Column(name = "multiple_server",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "其他服务政策保存Byte数组")
    private String multipleServer;

    @ApiModelProperty(value = "基础政策信息")
    @Column(name = "base_rule",type = MySqlTypeConstant.TEXT,length = 1500,comment = "基础政策信息")
    private String baseRule;

    @ApiModelProperty(value = "渠道商等级  1 初级 2 中级 3 高级(多个等级逗号隔开)")
    @Column(name = "award_distribuor_level",type = MySqlTypeConstant.TEXT,length = 1500,comment = "渠道商等级  1 初级 2 中级 3 高级(多个等级逗号隔开)")
    private String awardDistribuorLevel;

    @ApiModelProperty(value = "奖励政策信息")
    @Column(name = "award_rule",type = MySqlTypeConstant.TEXT,length = 1500,comment = "奖励政策信息")
    private String awardRule;




//    @Column(name = "base_distribuor_level",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "渠道商等级  1 初级 2 中级 3 高级")
//    @ApiModelProperty("渠道商等级  1 初级 2 中级 3 高级")
//    private Byte baseDistribuorLevel;
//
//    @Column(name = "base_sale_price",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道基准价格 1 成本价  2 销售价")
//    @ApiModelProperty("渠道基准价格 1 成本价  2 销售价")
//    private Byte baseSalePrice;
//
//    @Column(name = "base_sale_compute_method",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道售价计算方式 1 金额  2 折扣/百分比")
//    @ApiModelProperty("渠道售价计算方式 1 金额  2 折扣/百分比")
//    private Byte baseSaleComputeMethod;
//
//    @Column(name = "base_sale_sign",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道价加价方式 1 加价  2 减价")
//    @ApiModelProperty("渠道价加价方式 1 加价  2 减价")
//    private Byte baseSaleSign;
//
//    @Column(name = "base_sale_add_price",type = MySqlTypeConstant.INT,length = 12,comment = "加价多少/折扣多少")
//    @ApiModelProperty("加价多少/折扣多少")
//    private Integer baseSaleAddPrice;
//
//
//    @Column(name = "base_settlement_price",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算基准价格 1 成本价  2 销售价")
//    @ApiModelProperty("渠结算基准价格 1 成本价  2 销售价")
//    private Byte baseSettlementPrice;
//
//    @Column(name = "base_settlement_compute_method",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算价计算方式 1 金额  2 折扣/百分比")
//    @ApiModelProperty("结算价计算方式 1 金额  2 折扣/百分比")
//    private Byte baseSettlementComputeMethod;
//
//    @Column(name = "base_settlement_sign",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算加价方式 1 加价  2 减价")
//    @ApiModelProperty("结算加价方式 1 加价  2 减价")
//    private Byte baseSettlementSign;
//
//    @Column(name = "base_settlement_add_price",type = MySqlTypeConstant.INT,length = 12,comment = "加价多少/折扣多少")
//    @ApiModelProperty("加价多少/折扣多少")
//    private Integer baseSettlementAddPrice;
//
//    @Column(name = "base_rebate",type = MySqlTypeConstant.INT,length = 12,comment = "返利按照百分比或金额计算")
//    @ApiModelProperty("返利按照百分比或金额计算")
//    private Integer baseRebate;
//
//
//
//
//
//    @Column(name = "award_distribuor_level",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "渠道商等级  1 初级 2 中级 3 高级")
//    @ApiModelProperty("渠道商等级  1 初级 2 中级 3 高级")
//    private Byte awardDistribuorLevel;
//
//    @Column(name = "product_performance",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "产品业绩  1 商品数量(个) 2销售业绩(元) ")
//    @ApiModelProperty("产品业绩  1 商品数量(个) 2销售业绩(元)")
//    private Byte productPerformance;
//
//    @Column(name = "award_to_count",type = MySqlTypeConstant.INT,length = 12,comment = "达到数量/达到销售金额")
//    @ApiModelProperty("达到数量/达到销售金额")
//    private Integer awardToCount;
//
//    @Column(name = "award_sale_price",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道基准价格 1 成本价  2 销售价")
//    @ApiModelProperty("渠道基准价格 1 成本价  2 销售价")
//    private Byte awardSalePrice;
//
//    @Column(name = "award_sale_compute_method",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道售价计算方式 1 金额  2 折扣/百分比")
//    @ApiModelProperty("渠道售价计算方式 1 金额  2 折扣/百分比")
//    private Byte awardSaleComputeMethod;
//
//    @Column(name = "award_sale_sign",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "渠道价加价方式 1 加价  2 减价")
//    @ApiModelProperty("渠道价加价方式 1 加价  2 减价")
//    private Byte awardSaleSign;
//
//    @Column(name = "award_sale_add_price",type = MySqlTypeConstant.INT,length = 12,comment = "加价多少/折扣多少")
//    @ApiModelProperty("加价多少/折扣多少")
//    private Integer awardSaleAddPrice;
//
//    @Column(name = "award_settlement_price",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算基准价格 1 成本价  2 销售价")
//    @ApiModelProperty("渠结算基准价格 1 成本价  2 销售价")
//    private Byte awardSettlementPrice;
//
//    @Column(name = "award_settlement_compute_method",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算价计算方式 1 金额  2 折扣/百分比")
//    @ApiModelProperty("结算价计算方式 1 金额  2 折扣/百分比")
//    private Byte awardSettlementComputeMethod;
//
//    @Column(name = "award_settlement_sign",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "结算加价方式 1 加价  2 减价")
//    @ApiModelProperty("结算加价方式 1 加价  2 减价")
//    private Byte awardSettlmentSign;
//
//    @Column(name = "award_settlement_add_price",type = MySqlTypeConstant.INT,length = 12,comment = "加价多少/折扣多少")
//    @ApiModelProperty("加价多少/折扣多少")
//    private Integer awardSettlementAddPrice;
//
//    @Column(name = "award_rebate",type = MySqlTypeConstant.INT,length = 12,comment = "返利按照百分比或金额计算")
//    @ApiModelProperty("返利按照百分比或金额计算")
//    private Integer awardRebate;

}
