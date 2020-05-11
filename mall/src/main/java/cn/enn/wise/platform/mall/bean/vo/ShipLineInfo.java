package cn.enn.wise.platform.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipLineInfo {

    /**
     * 航线id
     */
    private String lineID;

    /**
     * 船舶Id
     */
    private String shipID;

    /**
     * 船舱id
     */
    private String clCabinID;

    /**
     * 出发港id
     */
    private Long startPortID;

    /**
     * 目的港id
     */
    private Long arrivePortID;

    /**
     * 出发时间
     */
    private String startTime;

    /**
     * 到达时间
     */
    private String arriveTime;

    /**
     * 经过时间(单位分钟)
     */
    private int afterTime;

    /**
     * 出港口名称，港口简介
     */
    private String fromInfo;

    /**
     * 目的港口名称，港口简介
     */
    private String arriveInfo;

    /**
     * 可售数量
     */
    private int freeSeats;

    /**
     * 全票价
     */
    private BigDecimal fullPrice;
}
