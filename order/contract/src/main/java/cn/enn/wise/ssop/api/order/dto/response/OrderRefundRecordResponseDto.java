package cn.enn.wise.ssop.api.order.dto.response;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 订单退单记录实体
 *
 * @author lishuiquan
 * @date 2020-04-01
 */

@Data
public class OrderRefundRecordResponseDto{

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    /**
     * 父订单Id
     */
    @ApiModelProperty(value = "父订单Id")
    private Long parentOrderId;


    /**
     * 订单商品名称
     */
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品名称")
    private Long goodsName;

    /**
     * 退款类型
     */
    @ApiModelProperty(value = "退款类型")
    private Byte refundType;

    /**
     * 退款原因类型
     */
    @ApiModelProperty(value = "退款原因类型")
    private Byte refundReasonType;

    /**
     * 退款原因说明
     */
    @ApiModelProperty(value = "退款原因说明")
    private String refundReasonDesc;

    /**
     * 退款价格
     */
    @ApiModelProperty(value = "退款价格")
    private BigDecimal refundPrice;


    /**
     * 退款状态
     */
    @ApiModelProperty(value = "退款状态")
    private Byte refundStatus;

    /**
     * 优惠类型
     */
    @ApiModelProperty(value = "优惠类型")
    private Byte benefitOption;

    /**
     * 退款流水号
     */
    @ApiModelProperty(value = "退款流水号")
    private String refundNo;


    /**
     * 退款创建时间
     */
    @ApiModelProperty(value = "退款创建时间")
    private Timestamp createTime;
    /**
     * 退款的修改时间
     */
    @ApiModelProperty(value = "退款修改时间")
    private Timestamp updateTime;

}
