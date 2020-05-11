package cn.enn.wise.platform.mall.bean.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author bj
 * @Description 商品Sku Info vo
 * @Date19-6-13 上午10:59
 * @Version V1.0
 **/
public class GoodsApiSkuVo {

    /**
     * priceRange : 168
     * skuInfo : [{"id":16,"timespan":"20:00-21:00","salePrice":168,"periodId":42,"operationSatus":1,"probability":null,"realTips":null,"operationDate":"2019-06-13"}]
     */

    /**
     * 价格区间
     */
    private BigDecimal priceRange;

    /**
     * 时段 标签
     */
    private String timeLabel;

    /**
     * 优惠标签
     */
    private BigDecimal tips;

    /**
     * sku信息
     */
    private List<GoodsApiExtendResVo> skuInfo;




    public BigDecimal getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(BigDecimal priceRange) {
        this.priceRange = priceRange;
    }

    public List<GoodsApiExtendResVo> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<GoodsApiExtendResVo> skuInfo) {
        this.skuInfo = skuInfo;
    }

    public BigDecimal getTips() {
        return tips;
    }

    public void setTips(BigDecimal tips) {
        this.tips = tips;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }
}
