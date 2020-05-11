package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 离线订单请求VO
 *
 * @author baijie
 * @date 2019-07-17
 */
@Data
@ApiModel("离线订单添加请求实体")
public class OrderReqVo {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;



    @ApiModelProperty(value = "商品总价")
    private BigDecimal orderPrice;


    @ApiModelProperty(value = "商品数量")
    private Long amount;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty(value = "类型 5 门票 ,6 二销产品")
    private Long type;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品时段Id")
    private Long goodsExtendId;


    @ApiModelProperty(value = "支付方式：weixin")
    private String payType;

    @ApiModelProperty(value = "入园时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date enterTime;

    @ApiModelProperty(value = "是否分销订单 1是 0 否")
    private Integer isDistributeOrder;

    @ApiModelProperty(value = "订单来源 1 大峡谷 2 二维码 3 现场转化")
    private Integer orderSource;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualPay;

    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;

    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "订单日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty(value = "分销商姓名")
    private String  distributorName;

    @ApiModelProperty(value = "分销商电话")
    private String  distributorPhone;

    @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单")
    private Integer  orderType;

    @ApiModelProperty(value = "离线操作人")
    private String  offlineUser;

    @ApiModelProperty(value = "离线订单的状态 1 可编辑可删除 2 不可编辑不可删除 3 删除")
    private Integer  offlineStatus;


    @ApiModelProperty(value = "项目Id")
    private Integer  projectId;

    @ApiModelProperty("商品是否分时段售卖")
    private Integer isByPeriodOperation;

    @ApiModelProperty("分销策略Id")
    private Long strategyItemId;
}
