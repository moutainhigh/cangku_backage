package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.PromotionAndGoodsBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/20
 */

@Data
@ApiModel("商品")
public class PromotionAndGoodsVo extends PromotionAndGoodsBo {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品编号")
    private String goodsCode;

    @ApiModelProperty("商品状态 1 上架 2 下架")
    private String goodsStatus;

    @ApiModelProperty("基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty("分销价格")
    private BigDecimal retailPrice;

    @ApiModelProperty("项目名称")
    private String projectName;
}
