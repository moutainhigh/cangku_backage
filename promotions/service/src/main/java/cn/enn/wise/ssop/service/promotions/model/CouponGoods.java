package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@Table(name = "coupon_goods")
public class CouponGoods extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "优惠券ID")
    @Column(name = "coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "优惠券ID")
    private Long couponId;

    @Column(name = "coupon_type",type = MySqlTypeConstant.TINYINT, length = 1,comment = "优惠券类型 1-体验券 2-满减券 3-代金券")
    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty(value = "产品id")
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "产品id")
    private Long goodsId;

    @ApiModelProperty("产品名称")
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "产品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品Id(如果是全部产品id为-1)")
    private Long goodsExtendId;

    @Column(name = "goods_extend_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsExtendName;

    @Column(name = "goods_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "产品/资源类型，来自于产品")
    @ApiModelProperty("产品/资源类型，来自于产品")
    private Byte goodsType;

    @Column(name = "sell_price",type = MySqlTypeConstant.INT,length = 12,comment = "销售价格")
    @ApiModelProperty("销售价格")
    private Integer sellPrice;

    @Column(name = "price_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "价格类目根据产品中记录")
    @ApiModelProperty("价格类目根据产品中记录")
    private Byte priceType;

    @Column(name = "cost_price",type = MySqlTypeConstant.INT,length = 12,comment = "成本价格")
    @ApiModelProperty("成本价格")
    private Integer costPrice;

    @ApiModelProperty(value = "供应商ID")
    @Column(name = "supplier_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "供应商ID")
    private Long supplierId;

    @Column(name = "supplier_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "供应商名称")
    @ApiModelProperty("供应商名称")
    private String supplierName;

    @Column(name = "refund_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "退款类型 1 常规退款 2 不予退款")
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;

    @ApiModelProperty("产品/资源类型，来自于产品")
    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "产品/资源类型，来自于产品")
    private Long projectId;

    @ApiModelProperty("产品/资源类型，来自于产品")
    @Column(name = "project_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "产品/资源类型，来自于产品")
    private String projectName;
}
