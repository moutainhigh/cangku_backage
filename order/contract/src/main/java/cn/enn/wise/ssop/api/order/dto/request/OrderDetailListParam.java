package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

/**
 * 简单订单信息列表
 *
 * @author yangshuaiquan
 * @date 2020-4-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "简单订单信息列表参数")
public class OrderDetailListParam {
    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private List<Long> goodsId;


    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Timestamp startTime;


    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Timestamp endTime;
}
