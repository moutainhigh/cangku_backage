package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author bj
 * @Description 订单子表实体
 * @Date19-6-13 下午12:41
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTicket {

    /**
     * 主键Id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 状态
     */
    private Long status;
    /**
     * 订单号
     */
    private String ticketCode;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 商品Id
     */
    private Long goodsId;

    /**
     * 商品sku Id
     */
    private Long goodsExtendId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 项目Id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目服务场地
     */
    private String projectPlaceId;

    /**
     * 单价
     */
    private BigDecimal singlePrice;

    @ApiModelProperty("优惠的金额")
    private BigDecimal couponPrice;

    /**
     * 乘客姓名
     */
    private String ticketUserName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 座位号
     */
    private String seatNumber;
    /**
     * 船票状态
     */
    private Byte shipTicketStatus;
    /**
     * 婴儿信息
     */
    private String babyInfo;
    /**
     * 票类型101是成人203是儿童308是小童票
     */
    private String ticketType;

    /**
     * 票Id，只有支付成功的船票此Id才有值
     */
    private String ticketId;

    /**
     * 百邦达票Id
     */
    private String  ticketIdBbd;

    private Integer ticketStateBbd;


    @ApiModelProperty("佰邦达订单号")
    private String orderSerialBbd;

    @ApiModelProperty("佰邦达票二维码")
    private String ticketQrCodeBbd;

    @ApiModelProperty("佰邦达票号")
    private String ticketSerialBbd;

    @ApiModelProperty("票改签信息")
    private String changeInfo;

    @ApiModelProperty("打印状态 0.未打印 1.已打印")
    private Integer isTicketPrinted;

}
