package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/30 10:16
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class BoatRefundFeeVo {

    private String orderSerial;

    private Integer Count;

    private String totalAmount;

    private String totalBackRate;

    private String totalBack;
}
