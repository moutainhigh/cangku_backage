package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.ssop.api.promotions.dto.request.CustomerProportionParam;
import cn.enn.wise.ssop.api.promotions.dto.request.GoodsSortParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("活动预估DTO")
public class ActivityBenefitDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("预计成本")
    private Integer cost;

    @ApiModelProperty("收益订单数量")
    private Integer orderNumber;

    @ApiModelProperty("毛利收益")
    private Integer grossProfit;

    @ApiModelProperty("客群占比")
    private List<CustomerProportionParam> customerList;

    @ApiModelProperty("产品占比排序")
    private List<GoodsSortParam> goodsSort;

    @ApiModelProperty("分销渠道排序视图")
    private String channelSort;

    @ApiModelProperty("活动收益预估类型：1 没有基线 2 有基线")
    private Byte activityBenefitType;


}
