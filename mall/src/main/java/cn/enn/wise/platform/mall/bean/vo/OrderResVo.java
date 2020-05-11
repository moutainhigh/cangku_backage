package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单APi响应VO
 *
 * @author baijie
 * @date 2019-07-17
 */
@Data
public class OrderResVo extends Orders {

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty(value = "商品时段Id")
    private Long goodsExtendId;

    @ApiModelProperty(value = "是否分时段运营")
    private Integer isByPeriodOperation;

    @ApiModelProperty(value = "项目运营地点")
    private Integer servicePlaceId;

    @ApiModelProperty("分销策略Id")
    private Long strategyItemId;
}
