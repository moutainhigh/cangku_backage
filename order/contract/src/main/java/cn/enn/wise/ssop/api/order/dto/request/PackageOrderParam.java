package cn.enn.wise.ssop.api.order.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 套餐商品下单参数
 *
 * @author baijie
 * @date 2020-05-03
 */
@NoArgsConstructor
@Data
public class PackageOrderParam extends BaseOrderParam{

    /**
     * distributorMobile :
     * extraInformation : {"useDate":"2020-04-30"}
     * goodsInfoParamList : [{"customerId":16,"saleId":"","amount":1,"skuId":360}]
     */

    private String distributorMobile;
    private ExtraInformationBean extraInformation;
    private List<GoodsInfoParamListBean> goodsInfoParamListX;

    @NoArgsConstructor
    @Data
    public static class ExtraInformationBean {
        /**
         * useDate : 2020-04-30
         */

        private String useDate;
    }

    @NoArgsConstructor
    @Data
    public static class GoodsInfoParamListBean {
        /**
         * customerId : 16
         * saleId :
         * amount : 1
         * skuId : 360
         */

        private Long customerId;
        private String saleId;
        private Integer amount;
        private Long skuId;
    }
}
