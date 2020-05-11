package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "order_goods")
public class OrderGoods extends TableBase {

        /**
         * 主键Id
         */
        @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
        @TableId(value = "id", type = IdType.AUTO)
        private Long id ;

         /**
         * 订单Id
         */
        @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
        private Long orderId;

        /**
         * 订单号
         */
        @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "订单号")
        private String orderNo;


        /**
         * 商品ID
         */
        @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品ID")
        private Long goodsId;

        /**
         * 商品名称
         */
        @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "商品名称")
        private String goodsName;

        /**
         *商品单价
         */
        @Column(name = "goods_price",type = MySqlTypeConstant.DECIMAL,length = 10,comment = "商品单价")
        private Integer goodsPrice;

        /**
         * 商品分类
         */
        @Column(name = "goods_type",type = MySqlTypeConstant.INT,length = 10,comment = "商品分类")
        private Byte goodsType;

        /**
         * 商品skuId
         */
        @Column(name = "sku_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品skuId")
        private Long skuId;

        /**
         * 商品SkuName
         */
        @Column(name = "sku_name",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "商品SkuName")
        private String skuName;

        /**
         * 商品数量
         */
        @Column(name = "amount",type = MySqlTypeConstant.INT,length = 11,comment = "商品数量")
        private Integer amount;

        /**
         * 商家类型
         */
        @Column(name = "business_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "商家类型")
        private Byte businessType;

        /**
         * 商家名称
         */
        @Column(name = "business_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商家名称")
        @ApiModelProperty(value = "商家名称")
        private String businessName;


        /**
         *商品总价
         */
        @Column(name = "should_pay_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "商品总价")
        private BigDecimal shouldPayPrice;


        /**
         * 实付金额
         */
        @Column(name = "actual_pay_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "实付金额")
        @ApiModelProperty(value = "实付金额")
        private BigDecimal actualPayPrice;

        /**
         *商品优惠价格
         */
        @Column(name = "decrease_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "商品优惠价格")
        private BigDecimal decreasePrice;

        /**
         *商品退款价格
         */
        @Column(name = "refund_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "商品退款价格")
        private BigDecimal refundPrice;

        /**
         * 使用日期
         */
        @Column(name = "use_time",type = MySqlTypeConstant.DATETIME,length = 0,comment = "使用日期")
        @ApiModelProperty("使用日期")
        private String useTime;

        /**
         * 支付超时时间
         */
        @Column(name = "pay_time_out",type = MySqlTypeConstant.INT,length = 0,comment = "支付超时时间")
        @ApiModelProperty("支付超时时间")
        private Integer payTimeOut;

        /**
         * 使用次数
         */
        @Column(name = "use_count",type = MySqlTypeConstant.INT,length = 0,comment = "使用次数")
        @ApiModelProperty("使用次数")
        private Integer useCount;

        /**
         * 支付状态 1 待支付 2 已支付
         */
        @Column(name = "pay_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "支付状态 1 待支付 2 已支付")
        private Integer payStatus;

        /**
         * 交易状态 1交易创建 2 交易关闭 3 交易成功
         */
        @Column(name = "transaction_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "交易状态")
        private Integer transactionStatus;

        /**
         * 订单状态
         */
        @Column(name = "order_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单状态")
        private Integer orderStatus;

        /**
         * 退款状态
         */
        @Column(name = "refund_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "退款状态")
        @ApiModelProperty(value = "退款状态")
        private Integer refundStatus;

        /**
         * 系统状态
         */
        @Column(name = "system_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "系统状态")
        @ApiModelProperty(value = "系统状态")
        private Integer systemStatus;

        /**
         * 父订单id
         */
        @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "父订单id")
        private Long parentOrderId;

        /**
         * 附加信息
         */
        @Column(name = "extra_information",type = MySqlTypeConstant.TEXT,comment = "附加信息")
        private String extraInformation;

        /**
         * 核销标示，关联order_charge_off 表，qr_tag字段
         */
        @Column(name = "charge_off_qr_tag",type = MySqlTypeConstant.VARCHAR,length = 90,comment = "核销标示，关联order_charge_off 表，qr_tag字段")
        private String chargeOffQrTag;

}
