package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 订单更新记录
 *
 * @author baijie
 * @date 2019-12-10
 */
@Data
@Table(name="order_change_record")
public class OrderChangeRecord  extends TableBase {

    /**
     * 主键id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单Id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @Column(name = "pay_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "支付状态 1 待支付 2 已支付")
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer payStatus;

    /**
     * 交易状态
     */
    @Column(name = "transaction_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "交易状态")
    @ApiModelProperty(value = "交易状态")
    private Integer transactionStatus;

    /**
     * 订单状态
     */
    @Column(name = "order_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单状态 ")
    @ApiModelProperty(value = "订单状态")
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
    @ApiModelProperty(value = "退款状态")
    private Integer systemStatus;

    /**
     * 支付状态 1 待支付 2 已支付
     */
    @Column(name = "new_pay_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "支付状态 1 待支付 2 已支付")
    @ApiModelProperty(value = "支付状态 1 待支付 2 已支付")
    private Integer newPayStatus;

    /**
     * 交易状态
     */
    @Column(name = "new_transaction_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "交易状态")
    @ApiModelProperty(value = "交易状态")
    private Integer newTransactionStatus;

    /**
     * 订单状态
     */
    @Column(name = "new_order_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "订单状态")
    @ApiModelProperty(value = "订单状态")
    private Integer newOrderStatus;

    /**
     * 退款状态
     */
    @Column(name = "new_refund_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "退款状态")
    @ApiModelProperty(value = "退款状态")
    private Integer newRefundStatus;

    /**
     * 系统状态
     */
    @Column(name = "new_system_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "系统状态")
    @ApiModelProperty(value = "退款状态")
    private Integer newSystemStatus;

    /**
     * 更改原因
     */
    @Column(name = "change_reason",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "更改原因")
    private String changeReason;

    /**
     * 更改时间
     */
    @Column(name = "change_time",type = MySqlTypeConstant.DATETIME,length = 0,comment = "更改时间")
    private Date changeTime;
}
