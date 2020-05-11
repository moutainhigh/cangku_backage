package cn.enn.wise.ssop.api.order.dto.response.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("订单详情")
public class OrderDetailBean {

    @ApiModelProperty("景区名称")
    private String scenicName;//景区名称
    @ApiModelProperty("商品数量")
    private int amount;//商品数量
    @ApiModelProperty("下单时间")
    private String createTime;//下单时间
    @ApiModelProperty("商品名称")
    private String goodsName;//商品名称
    @ApiModelProperty("订单金额")
    private double goodsPrice;//订单金额
    @ApiModelProperty("ID")
    private int id;
    @ApiModelProperty("是否分销订单 2是分销单 ,")
    private int isDistributeOrder;//是否分销订单 1是 0 否 ,
    @ApiModelProperty("联系人姓名")
    private String name;//联系人姓名
    @ApiModelProperty("订单编号")
    private String orderCode;//订单编号
    @ApiModelProperty("联系人电话")
    private String phone;//联系人电话
    @ApiModelProperty("实付金额")
    private double shouldPay;//实付金额
    @ApiModelProperty("商品单价")
    private double siglePrice;//商品单价
    @ApiModelProperty("订单状态")
    private int state;//订单状态
    @ApiModelProperty("分销商姓名")
    private String distributorName;//分销商姓名
    @ApiModelProperty("分销商电话")
    private String distributorPhone;//分销商电话
    @ApiModelProperty("核销人姓名")
    private String checkName;//核销人姓名
    @ApiModelProperty("核销时间")
    private String checkInTime;//核销时间
    @ApiModelProperty("商品Id")
    private int goodsId; //商品Id
    @ApiModelProperty("分销商信息快照")
    private String snapshot;//分销商信息快照
    @ApiModelProperty("分销商角色Id")
    private String roleId;//分销商角色Id
    @ApiModelProperty("1 普通订单 2分销商订单 3拼团订单 4优惠券订单 5 活动订单")
    private int orderType;//1 线上订单 2 离线订单 3拼团订单 4,组合商品 5船票 6 百邦达订单
    @ApiModelProperty("拼团列表")
    private List<GroupOrderInfoBean> groupOrderVoList;//拼团列表
    @ApiModelProperty("体验时间")
    private String experienceTime;//体验时间
    @ApiModelProperty("是否退单 1:没有 2.有")
    private int isRefund;//是否退单 1:没有 2.有
    @ApiModelProperty("是否可退 1:不可退 2:可退")
    private int isCanRefund;//是否可退 1:不可退 2:可退
    @ApiModelProperty("退单平台 1.PC端 2.App端 3.小程序")
    private int platform;//退单平台 1.PC端 2.App端 3.小程序
    @ApiModelProperty("判断是否有没有票务信息 1 有 2 没有")
    private int isTicketing;
    @ApiModelProperty("优惠金额")
    private double couponPrice;//优惠金额
}
