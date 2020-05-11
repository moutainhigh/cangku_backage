package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/19
 */
@Data
@TableName("goods_package_item")
public class GoodsPackageItemBo  extends Model<GoodsPackageItemBo> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主键套装")
    private Long packageId;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("SKUId")
    private Long goodsExtendId;

    @ApiModelProperty("基础价格")
    private BigDecimal baseSalePrice;

    @ApiModelProperty("分销价格")
    private BigDecimal baseRetailPrice;

    @ApiModelProperty("分销分摊价格")
    private BigDecimal retailPrice;

    @ApiModelProperty("销售分摊价格")
    private BigDecimal salePrice;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("服务地点")
    private String servicePlaceId;

    @ApiModelProperty("线路")
    private Long routeId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
