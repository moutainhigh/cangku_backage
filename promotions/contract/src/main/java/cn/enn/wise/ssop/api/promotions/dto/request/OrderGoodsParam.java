package cn.enn.wise.ssop.api.promotions.dto.request;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data

@ApiModel("订单关联的商品信息类")
//@Table(name = "order_goods")
public class OrderGoodsParam {

        /**
         * 主键Id
         */
        @ApiModelProperty(value = "id")
        private Long id ;

        /**
         * 订单Id
         */
        @ApiModelProperty(value = "第三方订单号")
        private Long orderId;

        /**
         * 订单号
         */

        @ApiModelProperty(value = "第三方订单号")
        private String orderNo;


        /**
         * 商品ID
         */
        @ApiModelProperty(value = "第三方订单号")
        private Long goodsId;

        /**
         * 商品名称
         */
        @ApiModelProperty(value = "商品名称")
        private String goodsName;

        /**
         *商品单价
         */
        @ApiModelProperty(value = "商品单价")
        private BigDecimal goodsPrice;

        /**
         * 商品分类
         */
        @ApiModelProperty(value = "商品分类")
        private Byte goodsType;

        /**
         * 商品skuId
         */
        @ApiModelProperty(value = "商品skuId")
        private Long skuId;

        /**
         * 商品SkuName
         */
        @ApiModelProperty(value = "商品SkuName")
        private String skuName;

        /**
         * 商品数量
         */
        @ApiModelProperty(value = "商品数量")
        private Integer amount;

        /**
         * 渠道id
         */
        @ApiModelProperty(value = "渠道id")
        private Integer channelId;

        /**
         * 渠道名称
         */
        @ApiModelProperty(value = "渠道名称")
        private String channelName;

        /**
         * 渠道类型
         */
        @ApiModelProperty(value = "渠道类型")
        private String channelType;

        /**
         * 商家类型
         */
        @ApiModelProperty(value = "商家类型")
        private Byte businessType;

        /**
         * 商家名称
         */
        @ApiModelProperty(value = "商家名称")
        private String businessName;


        /**
         *商品总价
         */
        @ApiModelProperty(value = "商品总价")
        private BigDecimal shouldPayPrice;

        /**
         *商品优惠价格
         */
        @ApiModelProperty(value = "商品优惠价格")
        private BigDecimal decreasePrice;

        /**
         *商品退款价格
         */
        @ApiModelProperty(value = "商品退款价格")
        private BigDecimal refundPrice;

        /**
         * 使用日期
         */
        @ApiModelProperty("使用日期")
        private String useTime;

        /**
         * 支付状态 1 待支付 2 已支付
         */
        @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
        private Byte payStatus;

        /**
         * 交易状态 1交易创建 2 交易关闭 3 交易成功
         */
        @ApiModelProperty(value = "交易状态 1交易创建 2 交易关闭 3 交易成功")
        private Byte transactionStatus;

        /**
         * 评价状态 1待评价 2已评价
         */
        @ApiModelProperty(value = "评价状态 1待评价 2已评价")
        private Byte evaluationStatus;

        /**
         * 订单状态1待付款 2 待使用 3已取消 4已使用 5部分使用 6 退款中 7已退款 8 体验完成
         */
        @ApiModelProperty(value = "订单状态1待付款 2 待使用 3已取消 4已使用 5部分使用 6 退款中 7已退款 8 体验完成")
        private Byte orderStatus;

        /**
         * 父订单id
         */
        @ApiModelProperty(value = "父订单id")
        private Long parentOrderId;

        /**
         * 附加信息
         */
        @ApiModelProperty(value = "附加信息")
        private String extraInformation;

}
