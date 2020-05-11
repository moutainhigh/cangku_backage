package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 15:21
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Table(name = "order_refund")
public class OrderRefundApply {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Column(name = "order_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "订单号")
    private String orderId;

    @Column(name = "order_amount",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "订单总金额")
    private BigDecimal orderAmount;

    @Column(name = "order_item_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "订单项Id")
    private String orderItemId;

    @Column(name = "out_refund_no",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "退款单号")
    private String outRefundNo;

    @Column(name = "flow_trade_no",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "支付单号")
    private String flowTradeNo;

    @Column(name = "user_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "用户Id")
    private String userId;

    @Column(name = "goods_num",type = MySqlTypeConstant.INT,length = 50,comment = "退票数量")
    private Integer goodsNum;

    @Column(name = "refund_amount",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "退款金额")
    private BigDecimal refundAmount;

    @Column(name = "return_money_sts",type = MySqlTypeConstant.INT,length = 2,comment = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer returnMoneySts;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "apply_time",type =MySqlTypeConstant.DATETIME,comment = "申请时间")
    private Date applyTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "refund_time",type =MySqlTypeConstant.DATETIME,comment = "退款时间")
    private Date refundTime;

    @Column(name = "buyer_msg",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "退款原因(运营)")
    private String buyerMsg;

    @Column(name = "buyer_msg_type",type = MySqlTypeConstant.INT,length = 2,comment = "运营1.景区操作原因 2.不可抗力 3.游客原因")
    private Integer buyerMsgType;

    @Column(name = "reason_label",type = MySqlTypeConstant.INT,length = 2,comment = "退款原因标签(用户)")
    private Integer reasonLabel;

    @Column(name = "handle_name",type = MySqlTypeConstant.VARCHAR,length = 10,comment = "处理人名称")
    private String handleName;

    @Column(name = "platform",type = MySqlTypeConstant.INT,length = 2,comment = "操作平台")
    private Integer platform;

    @Column(name = "count_num",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "次数")
    private Integer countNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "approvals_time",type =MySqlTypeConstant.DATETIME,comment = "审批时间")
    private Date approvalsTime;

    @Column(name = "approvals_sts",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

    @Column(name = "no_pass_reason",type = MySqlTypeConstant.VARCHAR,length = 110,comment = "审核不通过原因")
    private String noPassReason;

    @Column(name = "coupon_price",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "优惠金额")
    private BigDecimal couponPrice;


}
