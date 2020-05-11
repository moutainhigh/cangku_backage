package cn.enn.wise.ssop.service.order.feign;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Builder
public class Goods {

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     *商品单价
     */
    private Integer goodsPrice;

    /**
     * 商品分类
     */
    private Byte goodsType;

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 商品skuName
     */
    private String skuName;

    /**
     * 商品有效期
     */
    private Date expireTime;

    /**
     * 商品有效期
     */
    private Integer payTimeOut;
    /**
     * 商品上下架
     */
    private Byte onlineStatus;

    /**
     * 商品库存量
     */
    private Integer storeCount;

    /**
     * 是否套餐
     */
    private Integer isPackage;
    /**
     * 父商品ID
     */
    private Long parentSkuId;

    private String extraInfo;


}
