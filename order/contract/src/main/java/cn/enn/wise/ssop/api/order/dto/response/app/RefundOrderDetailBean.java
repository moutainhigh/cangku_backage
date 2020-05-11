package cn.enn.wise.ssop.api.order.dto.response.app;

import lombok.Data;

import java.util.List;

@Data
public class RefundOrderDetailBean {

    private int orderSts;//订单状态
    private String orderCode;//订单编号
    private String price;//单价
    private int amount;//人数
    private String actualPay;//实付金额
    //如果是套餐 加这个字段包含住宿的套餐
    private String packageName;
    private List<NoPlayVoListBean> noPlayVoList;//未体验商品明细
    private List<RefundDetailVoListBean> refundDetailVoList;//已退商品明细
    //添加优惠字段
    private String couponTotalPrice;//优惠总金额
    private String orderTotalPrice;//订单总金额
    private int isJoinCoupon;//是否参加优惠 1.没参加 2.参加
    private Integer discriminateBBDSts;//1已出票 2已核验(已使用状态下区分)

    public static class NoPlayVoListBean {
        /**
         * id : 20
         * itemName : 项目名称
         * goodsName : 商品1
         * amount : 1
         * playName : 项目名称-商品1
         * price : 0.01
         */

        private int id;
        private String goodsName;//商品名称
        private int amount;//住宿房间数
        private String playName;//项目名称+商品名称
        private String price;//单价
        private int refundAmount; //设置的需要退的数量(接口没有) //不需要
        private boolean isChecked;//设置的是否需要退此商品（接口没有）//不需要
        private int goodsId;//商品ID
        private String status;//状态

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String couponPrice;//单品优惠分摊金额

        private String ticketSerialBbd;//百邦达票号 ,
        private Integer ticketStateBbd;//百邦达票状态，0:待出票 -1出票失败/已取消 1出票成功 100 已检票 230退成功已结款

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getPlayName() {
            return playName;
        }

        public void setPlayName(String playName) {
            this.playName = playName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(int refundAmount) {
            this.refundAmount = refundAmount;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getTicketSerialBbd() {
            return ticketSerialBbd;
        }

        public void setTicketSerialBbd(String ticketSerialBbd) {
            this.ticketSerialBbd = ticketSerialBbd;
        }

        public Integer getTicketStateBbd() {
            return ticketStateBbd;
        }

        public void setTicketStateBbd(Integer ticketStateBbd) {
            this.ticketStateBbd = ticketStateBbd;
        }

        public NoPlayVoListBean myCopy() {
            NoPlayVoListBean noPlayVoListBean = new NoPlayVoListBean();
            noPlayVoListBean.setRefundAmount(refundAmount);
            noPlayVoListBean.setAmount(amount);
            noPlayVoListBean.setChecked(isChecked);
            noPlayVoListBean.setCouponPrice(couponPrice);
            noPlayVoListBean.setId(id);
            noPlayVoListBean.setGoodsId(goodsId);
            noPlayVoListBean.setPlayName(playName);
            noPlayVoListBean.setPrice(price);
            noPlayVoListBean.setTicketSerialBbd(ticketSerialBbd);
            noPlayVoListBean.setTicketStateBbd(ticketStateBbd);
            return noPlayVoListBean;
        }

    }

    public static class RefundDetailVoListBean {
        /**
         * totalPrice : 0.01
         * refundTime : 2019-12-03 11:52:26
         * refundVoList : [{"playName":"项目名称-商品1","price":"2.00","amount":1,"totalPrice":"0.01","refundTime":"2019-12-03 11:52:26","refundId":23},{"playName":"项目名称-商品1","price":"3.00","amount":2,"totalPrice":"0.01","refundTime":"2019-12-03 11:52:26","refundId":23}]
         */

        private String totalPrice;//退款金额合计
        private String refundTime;//退款时间
        private List<RefundVoListBean> refundVoList;//已退商品明细

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(String refundTime) {
            this.refundTime = refundTime;
        }

        public List<RefundVoListBean> getRefundVoList() {
            return refundVoList;
        }

        public void setRefundVoList(List<RefundVoListBean> refundVoList) {
            this.refundVoList = refundVoList;
        }

        public static class RefundVoListBean {
            /**
             * playName : 项目名称-商品1
             * price : 2.00
             * amount : 1
             * totalPrice : 0.01
             * refundTime : 2019-12-03 11:52:26
             * refundId : 23
             */

            private String playName;//体验名称
            private String price;//金额
            private int amount;//数量

            public String getPlayName() {
                return playName;
            }

            public void setPlayName(String playName) {
                this.playName = playName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }
        }
    }
}
