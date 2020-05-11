package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 最低价Vo
 *
 * @author baijie
 * @date 2019-10-30
 */
@Data
public class MinPriceVo {

    private Long projectId;
    /**
     * 普通最低价
     */
    private BigDecimal minPrice;
    /**
     * 分销最低价
     */
    private BigDecimal minDistributePrice;

    /**
     * 商品Id
     */
    private Long goodsId;
}
