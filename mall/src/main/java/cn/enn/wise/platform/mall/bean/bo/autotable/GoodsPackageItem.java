package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/19
 */
@Data
@Table(name = "goods_package_item")
public class GoodsPackageItem {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "package_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "套装id")
    @ApiModelProperty("主键套装")
    private Long packageId;

    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商品id")
    @ApiModelProperty("商品Id")
    private Long goodsId;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商品推展id")
    @ApiModelProperty("SKUId")
    private Long goodsExtendId;

    @ApiModelProperty("基础价格")
    @Column(name = "base_sale_price",type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength =2,comment = "基础价格")
    private BigDecimal baseSalePrice;

    @ApiModelProperty("分销价格")
    @Column(name = "base_retail_price",type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength =2,comment = "基础分销价格")
    private BigDecimal baseRetailPrice;

    @Column(name = "retail_price",type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength =2,comment = "分销价格")
    @ApiModelProperty("分销分摊价格")
    private BigDecimal retailPrice;

    @ApiModelProperty("销售分摊价格")
    @Column(name = "sale_price",type = MySqlTypeConstant.DECIMAL, length = 10, decimalLength =2,comment = "分销价格")
    private BigDecimal salePrice;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "项目id")
    @ApiModelProperty("项目id")
    private Long projectId;

    @Column(name = "project_name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "项目项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;

    @Column(name = "service_place_id",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "项目项目名称")
    @ApiModelProperty("服务地点")
    private String servicePlaceId;

    @Column(name = "route_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "线路id")
    @ApiModelProperty("路线id")
    private Long routeId;
}
