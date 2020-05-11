package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderEvaluationParam {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 分数
     */
    @ApiModelProperty(value = "分数")
    private Byte score;

    /**
     * 评价描述
     */
    @ApiModelProperty(value = "评价描述")
    private String description;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String label;

    /**
     * 评价时间
     */
    @ApiModelProperty(value = "订单号")
    private Date evaluateTime;
}
