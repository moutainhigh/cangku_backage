package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品sku信息VO
 * @author baijie
 * @date 2019-12-19
 */
@Data
public class GoodsExtendInfoVO {

    @ApiModelProperty("项目Id")
    private  Long projectId;

    @ApiModelProperty(value = "是否分时段售卖")
    private Integer isByPeriodOperation;

    @ApiModelProperty(value = "时段id")
    private Long periodId;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("项目编码")
    private String projectCode;

    @ApiModelProperty("最大服务人数")
    private Integer maxNum;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品单价")
    private BigDecimal salePrice;

    @ApiModelProperty("项目名称")
    private String projectName;
}
