package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/8/1 11:53
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderQrCodeVo {


    private String qrCodeUrl;

    private String num;

    public OrderQrCodeVo() {
    }
}
