package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
@ApiModel("活动明细")
public class GroupPromotionGoodsVo extends GroupPromotionGoodsBo {

    @ApiModelProperty("拼团价格")
    private BigDecimal pinPrice;

    @ApiModelProperty("基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty("所属项目")
    private String  projectName;

    @ApiModelProperty("商品编码")
    private String  goodsCode;

    @ApiModelProperty("商品状态")
    private String  goodsStatus;



}
