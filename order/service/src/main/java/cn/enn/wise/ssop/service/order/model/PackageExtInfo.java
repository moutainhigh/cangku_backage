package cn.enn.wise.ssop.service.order.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 套餐订单扩展信息JSON实体
 *
 * @author baijie
 * @date 2020-05-03
 */
@NoArgsConstructor
@Data
public class PackageExtInfo {


    /**
     * scenicName : 涠洲岛帆船+游艇
     * imgUrl : D:/4.jpg
     * type : 套餐
     * startTime : 2020-04-02
     * outTime : 2020.05.06
     * sku : {"regin":" ","time":"2020.01.02","custGroup":"客群4"}
     * ticketInfo : [{"goodsName":"涠洲岛帆船","goodsSku":{"typeTime":"标准票30分钟","timeSlot":"9:00-12:00"},"ticketMsg":[{"ticketName":"普通舱A区","ticketCode":"12544","ticketStatus":"7"},{"ticketName":"普通舱A区","ticketCode":"12545","ticketStatus":"0"}]},{"goodsName":"游艇","goodsSku":{"typeTime":"标准票30分钟","timeSlot":"7:00-12:00"},"ticketMsg":[{"ticketName":"普通艇","ticketCode":"12544","ticketStatus":"7"},{"ticketName":"特快艇","ticketCode":"12545","ticketStatus":"0"}]}]
     */

    private String scenicName;
    private String imgUrl;
    private String type;
    private String startTime;
    private String outTime;
    private SkuBean sku;
    private List<TicketInfoBean> ticketInfo;

    @NoArgsConstructor
    @Data
    public static class SkuBean {
        /**
         * regin :
         * time : 2020.01.02
         * custGroup : 客群4
         */

        private String regin;
        private String time;
        private String custGroup;
    }

    @NoArgsConstructor
    @Data
    public static class TicketInfoBean {
        /**
         * goodsName : 涠洲岛帆船
         * goodsSku : {"typeTime":"标准票30分钟","timeSlot":"9:00-12:00"}
         * ticketMsg : [{"ticketName":"普通舱A区","ticketCode":"12544","ticketStatus":"7"},{"ticketName":"普通舱A区","ticketCode":"12545","ticketStatus":"0"}]
         */

        private String goodsName;
        private GoodsSkuBean goodsSku;
        private List<TicketMsgBean> ticketMsg;

        @NoArgsConstructor
        @Data
        public static class GoodsSkuBean {
            /**
             * typeTime : 标准票30分钟
             * timeSlot : 9:00-12:00
             */

            private String typeTime;
            private String timeSlot;
        }

        @NoArgsConstructor
        @Data
        public static class TicketMsgBean {
            /**
             * ticketName : 普通舱A区
             * ticketCode : 12544
             * ticketStatus : 7
             */

            private String ticketName;
            private String ticketCode;
            private String ticketStatus;
        }
    }
}
