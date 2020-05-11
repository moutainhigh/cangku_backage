package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 班次信息，每条数据为班次的舱位
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftInfo {

    /**
     *实时航班ID
     */
    private String ClId;

    /**
     *航线ID
     */
    private String LineID;
    /**
     *航线名称
     */
    private String LineName;
    /**
     *航班代码
     */
    private String ClCode;
    /**
     *开航日期
     */
    private Date ClDate;
    /**
     *开航时间
     */
    private String ClTime;
    /**
     *船舶ID
     */
    private String ShipID;
    /**
     *船名
     */
    private String ShipName;
    /**
     *航班属性ID
     */
    private Long Prop;
    /**
     *航班属性名
     */
    private String PropName;
    /**
     *航班船舱ID
     */
    private String ClCabinID;
    /**
     *般舱类型名字
     */
    private String CabinName;
    /**
     *可售位位数
     */
    private Long FreeSeats;
    /**
     *出发港ID
     */
    private Long StartPortID;
    /**
     *出发港名称
     */
    private String StartPortName;
    /**
     *到达港ID
     */
    private Long ArrivePortID;
    /**
     *到达港名称
     */
    private String ArrivePortName;
    /**
     *已售座位数
     */
    private Long SellSeats;
    /**
     *是否加班
     */
    private String ClOverTime;
    /**
     *全票票价
     */
    private Float TicketFullPrice;
    /**
     *全票票型ID
     */
    private Long TicketFullType;
    /**
     *全票价名称
     */
    private String TicketFullName;
    /**
     *半票票价
     */
    private Float TicketChildPrice;
    /**
     *半票票型ID
     */
    private Long TicketChildType;
    /**
     *半票名称
     */
    private String TicketChildName;
    /**
     * 成人票优惠票类型
     * 当为0时，不存在优惠票，只有全价票,当有值时不卖成人票，只卖这个票型
     */
    private String discountType;
    /**
     * 成人票优惠票价格
     */
    private String discountPrice;




}

