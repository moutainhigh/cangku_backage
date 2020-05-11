package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 14:28
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Builder
@TableName("order_refund")
@NoArgsConstructor
@AllArgsConstructor
public class RefundApply extends Model<RefundApply> implements Serializable{
    private static final long serialVersionUID = -7083493731271077556L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderAmount;

    private String orderItemId;

    @ApiModelProperty(value = "退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "支付单号")
    private String flowTradeNo;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "退票数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer returnMoneySts;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "退款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    @ApiModelProperty(value = "退款原因(运营)")
    private String buyerMsg;

    @ApiModelProperty(value = "运营1.景区操作原因 2.不可抗力 3.游客原因")
    private Integer buyerMsgType;

    @ApiModelProperty(value = "退款原因标签(用户)")
    private Integer reasonLabel;

    @ApiModelProperty(value = "处理人名称")
    private String handleName;

    @ApiModelProperty(value = "操作平台 1.PC端 2.App端 3.小程序")
    private Integer platform;

    @ApiModelProperty(value = "次数")
    private Integer countNum;

    @ApiModelProperty(value = "审批时间")
    private Date approvalsTime;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

    @ApiModelProperty(value = "审核不通过原因")
    private String noPassReason;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponPrice;

    @ApiModelProperty(value = "退款原因标签(用户)H5")
    @TableField(exist = false)
    private String reasonLabels;

    @ApiModelProperty(value = "商品名称")
    @TableField(exist = false)
    private String playsName;

    @ApiModelProperty(value = "流水号")
    @TableField(exist = false)
    private String refundNum;

}
