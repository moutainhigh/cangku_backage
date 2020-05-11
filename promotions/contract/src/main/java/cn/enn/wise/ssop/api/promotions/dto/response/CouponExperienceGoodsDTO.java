package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/4/20 11:09 上午
 */
@Data
@ApiModel("优惠券体验商品")
public class CouponExperienceGoodsDTO {

    @ApiModelProperty("商品id")
    private Long goodsExtendId;

    @ApiModelProperty("产品名称")
    private String goodsName;

    @ApiModelProperty("商品名称")
    private String goodsExtendName;

    @ApiModelProperty("规格信息")
    private String title;


}
