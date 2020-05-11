package cn.enn.wise.ssop.api.order.dto.response.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("退票记录保存")
public class RefundPostBean {

    private String buyerMsg;//退款原因
    private int buyerMsgType;//1.景区操作原因 2.不可抗力 3.游客原因
    private int goodsNum;//退票数量
    private String orderId;//订单Id ,
    private int platform;// 1.PC端 2.App端 3.小程序
    private double refundAmount;//退款金额
    private String userId;//操作用户Id
    private String handleName;//操作用户名称
    private List<RefundApplyDetailedParamListBean> refundApplyDetailedParamList;// 商品集合
    private double couponPrice;//优惠金额 没有优惠的话不传
    private String orderItemId;// 逗号分隔 比如"1,2,3"
    public static class RefundApplyDetailedParamListBean {
        private int amount;// 数量
        private int goodsId;//商品ID
        private String goodsName;//商品名称
        private String orderCode;//订单编号
        private String price;//金额
        private String refundNum;// 流水号

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRefundNum() {
            return refundNum;
        }

        public void setRefundNum(String refundNum) {
            this.refundNum = refundNum;
        }
    }
}
