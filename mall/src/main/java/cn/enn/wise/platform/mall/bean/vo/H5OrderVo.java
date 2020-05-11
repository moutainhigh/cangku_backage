package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/5 14:39
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class H5OrderVo {

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品总价")
    private String goodsPrice;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer state;

    @ApiModelProperty(value = "使用时段")
    private String timespan;

    @ApiModelProperty(value = "时段")
    private String timespans;


    @ApiModelProperty(value = "商品图片")
    private String picture;

    @ApiModelProperty(value = "1.正常订单 2.交易关闭 3.已付款,待成团")
    private Integer orderStsType;

    @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单 3.拼团订单")
    private Integer orderType;

    private Integer id;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "单价")
    private String siglePrice;

    @ApiModelProperty(value = "评论状态： 1:未评价  2:已评价")
    private Integer commSts;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer returnMoneySts;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "支付状态:1 未支付 2已付款 3已退款")
    private Integer payStatus;

    @ApiModelProperty(value = "1.可申请 2.不可申请退款")
    private Integer refundSts = 1;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty(value = "核销时间")
    private String checkInTime;

    @ApiModelProperty(value = "付款时间")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp payTime;

    @ApiModelProperty(value = "联系人姓名")
    private String name;

    @ApiModelProperty(value = "联系人手机号")
    private String phone;

    @ApiModelProperty(value = "核销人")
    private String checkName;

    @ApiModelProperty(value = "核销二维码")
    private String qrCode;

    @ApiModelProperty(value = "凭证码")
    private String proof;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredTime;

    @ApiModelProperty(value = "剩余时间")
    private Long countDown;

    @ApiModelProperty(value = "拼团团id")
    private Integer groupOrderId;

    @ApiModelProperty(value = "活动id")
    private Long promotionId;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品主键")
    private Integer goodId;

    @ApiModelProperty(value = "成团时间")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;

    @ApiModelProperty(value = "是否退款 1.无退款 2.已退款")
    private Integer isRefund =1;

    @ApiModelProperty(value = "申请来源 1.PC端 2.App端 3.小程序")
    private Integer platform;

    @ApiModelProperty(value = "1.不可退 2.可退")
    private Integer isCanRefund=1;

    @ApiModelProperty(value = "优惠金额")
    private String couponPrice;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "用户领取优惠券记录ID")
    private Long userOfCouponId;

    @ApiModelProperty(value = "应付金额")
    private String shouldPay;

    @ApiModelProperty(value = "小程序订单展示： 1:没有删除,2:删除")
    private Integer deleteStatus;






    @ApiModelProperty(value = "起点")
    private String lineFrom;

    @ApiModelProperty(value = "终点")
    private String lineTo;

    @ApiModelProperty(value = "船舱名称")
    private String cabinName;

    @ApiModelProperty(value = "船名称")
    private String nickName;

    @ApiModelProperty(value = "航班开始时间")
    private String boatStartTime;

    @ApiModelProperty(value = "航班结束时间")
    private String boatEndTime;


    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineDate;

    @ApiModelProperty(value = "开船日期")
    @JsonFormat(shape = JsonFormat.Shape.SCALAR,pattern = "MM月-dd日")
    private Date lineEndDate;

    @ApiModelProperty(value = "来游吧订单号")
    private String ticketOrderCode;

    @ApiModelProperty(value = "乘客信息")
    private List<PassengerVo> passengerVoList;

    @ApiModelProperty(value = "取票须知")
    private String shouldKnow;

    @ApiModelProperty(value = "航班信息")
    private String shipLineInfo;

    @ApiModelProperty(value = "经过时间(单位分钟)")
    private Integer afterTime;

    @ApiModelProperty(value = "客服电话")
    private String serverPhone;


    @ApiModelProperty(value = "票务信息集合")
    private List<OrderTicketVo> orderTicketVoList;

    @ApiModelProperty(value = "1.已出票 2.已核验")
    private Integer discriminateBBDSts=1;

    @ApiModelProperty(value = "取票码")
    private String ticketCode;


}
