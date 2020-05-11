package cn.enn.wise.ssop.api.order.dto.response.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("退款详情")
public class RefundDetailBean {

    @ApiModelProperty("退单数量")
    private int amount;

    @ApiModelProperty("操作时间")
    private String applyTime;

    @ApiModelProperty("退款信息")
    private String buyerMsg;

    @ApiModelProperty("退款原因 1景区操作原因 2不可抗力 3游客原因")
    private int buyerMsgType;

    @ApiModelProperty("操作人")
    private String handleName;

    @ApiModelProperty("退款客人")
    private String name;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("商品名称")
    private String playName;

    @ApiModelProperty("退款金额")
    private String price;

    @ApiModelProperty("退款流水号")
    private String refundNum;

    @ApiModelProperty("退款状态 1退款处理中 2退款成功 3财务处理中 4退款失败")
    private int refundSts;

    @ApiModelProperty("退款商品列表")
    private List<RefundVoListBean> refundVoList;

    @ApiModelProperty("财务审核状态 1财务审核中 2退款中 3财务审核未通过")
    private int approvalsSts;

    @ApiModelProperty("优惠金额")
    private String couponPrice;

    public static class RefundVoListBean {

        @ApiModelProperty("退款数量")
        private int amount;

        @ApiModelProperty("退款商品名称")
        private String playName;

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

    }

}
