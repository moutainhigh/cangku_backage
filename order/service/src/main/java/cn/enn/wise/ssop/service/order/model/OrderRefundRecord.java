package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 订单退单记录实体
 *
 * @author baijie
 * @date 2019-12-10
 */

@Data
@Table(name="order_refund_record")
public class OrderRefundRecord extends TableBase {

    /**
     * 主键id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 会员id
     */
    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "会员id")
    private Long memberId;

    /**
     * 父订单Id
     */
    @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "父订单id")
    private Long parentOrderId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品名称")
    private String goodsName;

    /**
     * 退款类型
     */
    @Column(name = "refund_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "退款类型")
    private Byte refundType;

    /**
     * 退款原因类型
     */
    @Column(name = "refund_reason_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "退款原因类型")
    private Byte refundReasonType;

    /**
     * 退款原因说明
     */
    @Column(name = "refund_reason_desc",type = MySqlTypeConstant.VARCHAR,length = 255,comment = "退款原因说明")
    private String refundReasonDesc;

    /**
     * 退款价格
     */
    @Column(name = "refund_price",type = MySqlTypeConstant.DECIMAL,length= 10,decimalLength = 2,comment = "退款价格")
    private BigDecimal refundPrice;


    /**
     * 退款状态
     */
    @Column(name = "refund_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "退款状态")
    private Byte refundStatus;

    /**
     * 优惠类型
     */
    @Column(name = "benefit_option",type = MySqlTypeConstant.TINYINT,length = 4,comment = "优惠类型")
    private Byte benefitOption;

    /**
     * 退款流水号
     */
    @Column(name = "refund_no",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "退款流水号")
    private String refundNo;

    /**
     * 退款创建时间
     */
    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "退款时间")
    private Timestamp createTime;
    /**
     * 退款的修改时间
     */
    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "修改时间")
    private Timestamp updateTime;

    /**
     * 订单来源
     */
    @ApiModelProperty("退单平台 1.PC端 2.App端 3.小程序")
    @Column(name = "plat_form",type = MySqlTypeConstant.INT,comment = "订单来源")
    private int platform;//退单平台 1.PC端 2.App端 3.小程序


}
