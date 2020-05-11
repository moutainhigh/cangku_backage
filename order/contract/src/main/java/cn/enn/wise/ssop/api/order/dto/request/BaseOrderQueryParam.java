package cn.enn.wise.ssop.api.order.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道订单查询参数列表
 *
 * @author baijie
 * @date 2019-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单查询参数")
public class BaseOrderQueryParam extends QueryParam {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;


    /**
     * 第三方订单号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 合作伙伴id
     */
    @ApiModelProperty(value = "合作伙伴id")
    private Long partnerId;

}
