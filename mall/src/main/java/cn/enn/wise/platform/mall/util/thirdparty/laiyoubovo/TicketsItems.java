package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下单，票列表参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketsItems {

    /**
     * 票型ID
     * 101是成人203是儿童308是小童票
     * 获取其他优惠id
     */
    private Long TicketType;
    /**
     * 航线ID
     */
    private String LineID;
    /**
     * 船舶ID
     */
    private String ShipID;
    /**
     * 开航时间
     */
    private String ClTime;
    /**
     * 票价
     */
    private Float TicketPrice;
    /**
     * 航班船舱ID/航班信息
     */
    private String ClCabinID;
    /**
     * 旅游者姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String IDNo;

    /**
     * 手机号，此参数下单时不需要使用
     */
    private String phone;
    /**
     * 证件类型,1 身份证
     */
    private Integer type;
    /**
     * 婴儿信息
     * 婴儿名字加证件件号
     * 例　王诗龄450521XX
     */
    private String babyinfo;


    @JSONField(name = "TicketType")
    public Long getTicketType() {
        return TicketType;
    }

    @JSONField(name = "LineID")
    public String getLineID() {
        return LineID;
    }

    @JSONField(name = "ShipID")
    public String getShipID() {
        return ShipID;
    }

    @JSONField(name = "ClTime")
    public String getClTime() {
        return ClTime;
    }

    @JSONField(name = "TicketPrice")
    public Float getTicketPrice() {
        return TicketPrice;
    }

    @JSONField(name = "ClCabinID")
    public String getClCabinID() {
        return ClCabinID;
    }

    @JSONField(name = "name")
    public String getName() {
        return name;
    }

    @JSONField(name = "IDNo")
    public String getIDNo() {
        return IDNo;
    }

    @JSONField(name = "type")
    public Integer getType() {
        return type;
    }

    @JSONField(name = "babyinfo")
    public String getBabyinfo() {
        return babyinfo;
    }
}
