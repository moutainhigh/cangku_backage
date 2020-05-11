package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jiaby
 */

@Data
@ApiModel(description = "火热拼团商品列表信息")
public class GoodsHotDTO {

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品头图")
    private String goodsImg;

    @ApiModelProperty(value = "商品拼团价格")
    private Integer groupPrice;

    @ApiModelProperty(value = "商品销售价格")
    private Integer sellPrice;

    @ApiModelProperty(value = "还差几人拼团成功")
    private Integer groupSizeDiff;

    @ApiModelProperty(value = "有效时间")
    private Timestamp availableTime;

    @ApiModelProperty(value = "参团的其他用户的头像")
    private List<String> userImgs;

    @ApiModelProperty(value = "已经拼团人数")
    private Long groupAllSize;
}
