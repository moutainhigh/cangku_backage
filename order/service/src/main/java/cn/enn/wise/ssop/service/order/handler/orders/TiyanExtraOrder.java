package cn.enn.wise.ssop.service.order.handler.orders;

import cn.enn.wise.ssop.api.order.dto.response.OrderOperatorsListDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class TiyanExtraOrder  extends ExtraOrder {

    private Map<String,String> sku;

    private Map<String,Object> ticketInfo;

    private List<Map<String,Object>> ticketMsg;
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
}
