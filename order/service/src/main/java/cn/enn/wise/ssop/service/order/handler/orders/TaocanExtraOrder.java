package cn.enn.wise.ssop.service.order.handler.orders;

import cn.enn.wise.ssop.api.order.dto.response.OrderOperatorsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.applet.SkuExtraOrderDto;
import cn.enn.wise.ssop.api.order.dto.response.applet.TicketInfoExtraOrderDto;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
@Data
public class TaocanExtraOrder  extends ExtraOrder {

    //票号
    private String ticketNo;
    //会员等级
    private String vipLevel;
    //手机号
    private String phone;
    //房间数
    private Integer roomNum;
    //房间类型
    private Integer roomType;
    //住的天数
    private Integer daysNum;
    //客户名称
    private String customerName;
    //运营人员供应商 信息
    private OrderOperatorsListDTO operator;

    //商品名称
    private String goodsName;
    //商品单价
    private Integer goodsPrice;
    //套餐类别说明
    private String pcType;

    //pc状态
    private Byte pcStatus;
    //微信支付状态
    private Byte wechatStatus;
    //app支付状态
    private Byte appStatus;
    //微信订单号
    private String wxNo;
    //有效时间
    private Timestamp effectiveTime;
    //订单联系人身份证
    private String certificateNo;
    //订单联系人的证明类型 1身份证 2护照 3学生证明
    private Byte certificateType;
    //分销id
    private Byte distributionOrder;
    //重新支付id
    private String prepayId;
    //套餐规格
    private SkuExtraOrderDto skuExtraOrderDto;
    //如果是小程序套餐所有的内容在这里
    private TicketInfoExtraOrderDto ticketInfoExtraOrderDto;
}
