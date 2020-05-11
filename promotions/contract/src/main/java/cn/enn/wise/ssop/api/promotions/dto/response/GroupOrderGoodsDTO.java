package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 拼团活动商品信息
 *
 * @author yangshuaiquan
 */
@Data
@ApiModel(description = "拼团活动商品信息")
public class GroupOrderGoodsDTO {

    @ApiModelProperty(value = "商品名称")
     private String goodsName;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品图片")
    private String goodsImg;

    @ApiModelProperty(value = "运营时间")
    private String operationTime;

    @ApiModelProperty(value = "商品拼团价格")
    private Integer groupPrice;
}
