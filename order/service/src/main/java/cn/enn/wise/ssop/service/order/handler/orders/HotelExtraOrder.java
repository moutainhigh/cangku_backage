package cn.enn.wise.ssop.service.order.handler.orders;


import cn.enn.wise.ssop.api.order.dto.response.OrderOperatorsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.TicketInfoListDTO;
import cn.enn.wise.ssop.api.order.dto.response.applet.SkuExtraOrderDto;
import cn.enn.wise.ssop.api.order.dto.response.applet.TicketInfoExtraOrderDto;
import cn.enn.wise.ssop.api.order.dto.response.applet.TicketMsgOrderDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
public class HotelExtraOrder extends ExtraOrder {
    //sku信息
    private SkuExtraOrderDto sku;
    //单张票信息
    private TicketMsgOrderDto ticketInfo;
    //是否可退
    private Byte isCanRefund;
    //出票状态
    private Byte discriminateBBDSts;
    //会员等级
    private String vipLevel;
    //营业时间
    private String timespan;
    //房间数
    private String roomNum;
    //微信号
    private String wxNo;
    //运营人员供应商 信息
    private OrderOperatorsListDTO operator;

    //天数
    private String daysNum;
    //房型
    private String roomType;
    //是否分销商
    private String isDistributor;
    //是否有团信息
    private String isAssemble;
    //核销人
    private String  writeoffName;
    //分销商姓名
    private String distributorName;
    //分销商电话
    private String distributorPhone;
    //是否有票务信息
    private String isTicketing;
    //票务信息
    private List<TicketInfoListDTO> ticketInfoList;
    //核销时间
    private Timestamp writeoffTime;
    //pc状态
    private String pcType;
    //支付单号
    private String prepayId;
    //wx状态
    private String wechatStatus;
    //app状态
    private String appStatus;

    //pc状态
    private String pcStatus;

    //分销商订单号
    private String distributionOrder;
    //票号
    private String ticketNo;

}
