package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author jiaby
 */

@ApiModel(description = "商品信息")
@Data
public class GoodsExtendDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("对应goods_extend_id-商品id-产品类目id")
    private Long goodsExtendId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "价格类目(一级规格值)")
    private String goodsExtendName;

    @ApiModelProperty(value = "商品一级规格编码")
    private String goodsExtendCode;

    @ApiModelProperty(value = "第三方票号")
    private String thirdTicketNumber;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "产品/资源类型")
    private String projectName;

    @ApiModelProperty(value = "类型id")
    private Long projectId;

    @ApiModelProperty(value = "关键字")
    private String keyWords;

    @ApiModelProperty(value = "建议售价")
    private Integer sellPrice;

    @ApiModelProperty("售价")
    private Integer salePrice;

    @ApiModelProperty(value = "是否被选中 1 选中 2 未选中")
    private Integer state;
}
