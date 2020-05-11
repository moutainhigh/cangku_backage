package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.Data;

/**
 * 港口信息
 */
@Data
public class PortInfo {

    /**
     * 港口ID
     */
    private Integer PortID;
    /**
     * 港口编码
     */
    private String PortCode;
    /**
     * 港口英文名
     */
    private String PortEName;
    /**
     * 港口中文名
     */
    private String PortCName;
    /**
     * 港口名(中文加英文)
     */
    private String PortName;
}