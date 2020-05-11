package cn.enn.wise.ssop.service.order.thirdparty;

import lombok.Data;

/**
 * @author Administrator
 * 深大票务系统
 */
@Data
public class ThirdPartyOrderItem {
    /**
     * 身份证名称
     */
    private String  consumerName;

    /**
     * 身份证号码
     */
    private String idNumber;
}
