package cn.enn.wise.platform.mall.bean.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店订单相关实体
 *
 * @author baijie
 * @date 2019-09-20
 */
@Data
@ApiModel("订单信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "HotelOrder")
public class HotelOrder {



    @ApiModelProperty(value = "id")
    @Id
    private String  id;

    @ApiModelProperty(value = "订单号")
    @Indexed(unique = true)
    private String orderCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；5，已取消；6，已退票")
    private Integer state;

    @ApiModelProperty(value = "最近操作日期")
    private Date updateTime;

    @ApiModelProperty(value = "商品数量")
    private Integer amount;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty(value = "类型 5 门票 ,6 二销产品")
    private Long type;

    @ApiModelProperty(value = "商品Id")
    private String  goodsId;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiredTime;

    @ApiModelProperty(value = "支付方式：weixin")
    private String payType;

    @ApiModelProperty(value = "购票人身份证")
    private String idNumber;

    @ApiModelProperty(value = "支付凭证")
    private String prepayId;

    @ApiModelProperty(value = "第三方底单号")
    @Indexed(unique = true)
    private String batCode;


    @ApiModelProperty(value = "退款数量")
    private Integer returnNum;

    @ApiModelProperty(value = "体验时间")
    private String timespan;

    @ApiModelProperty(value = "订单日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "订单来源 1 小程序")
    private Integer orderSource;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualPay;

    @ApiModelProperty(value = "应收金额")
    private BigDecimal shouldPay;

    @ApiModelProperty(value = "用户微信名")
    private String userWechatName;

    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "单价")
    private BigDecimal siglePrice;

    @ApiModelProperty(value = "最大服务人数")
    private Integer maxNumberOfUsers;

    @ApiModelProperty(value = "订单剩余支付时间")
    private Long leaveTime;

    @ApiModelProperty(value = "分销商id")
    private Long distributorId;

    @ApiModelProperty(value = "分润状态")
    private Integer profitStatus;

    @ApiModelProperty(value = "分销商角色Id")
    private String roleId;

    @ApiModelProperty(value = "分销商信息快照")
    private String snapshot;

    @ApiModelProperty(value = "分销商姓名")
    private String distributorName;

    @ApiModelProperty(value = "分销商电话")
    private String distributorPhone;

    @ApiModelProperty(value = "订单类型 1 线上订单 2 离线订单")
    private Integer orderType;

    @ApiModelProperty(value = "离线操作人")
    private String offlineUser;

    @ApiModelProperty(value = "离线订单的状态 1 可编辑可删除 2 不可编辑不可删除")
    private Integer offlineStatus;

    private String profiles;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "项目编号")
    private String projectCode;

    @ApiModelProperty(value = "二维码地址")
    private String qrCode;

    @ApiModelProperty(value = "凭证码")
    private String proof;

    @ApiModelProperty(value = "原因")
    private String reason;


    @ApiModelProperty(value = "入住日期")
    private String incomeDate;

    @ApiModelProperty(value = "离店日期")
    private String departureDate;

    @ApiModelProperty(value = "入住天数")
    private Integer dayStayed;

}
