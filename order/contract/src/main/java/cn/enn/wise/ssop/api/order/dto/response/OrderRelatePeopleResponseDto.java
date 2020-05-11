package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 订单联系人
 *
 * @author lishuiquan
 * @date 2020-4-01
 */
@Data
public class OrderRelatePeopleResponseDto{

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 联系人证件号
     */
    @ApiModelProperty(value = "联系人证件号")
    private String certificateNo;

    /**
     * 联系人证件类型
     */
    @ApiModelProperty(value = "联系人证件类型")
    private Byte certificateType;

    /**
     * 父订单id
     */
    @ApiModelProperty(value = "父订单id")
    private Long parentOrderId;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private String extraInformation;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Timestamp createTime;
}
