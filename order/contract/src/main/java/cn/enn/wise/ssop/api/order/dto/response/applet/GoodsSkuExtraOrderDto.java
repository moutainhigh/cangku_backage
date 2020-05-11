package cn.enn.wise.ssop.api.order.dto.response.applet;

import lombok.Data;

@Data
public class GoodsSkuExtraOrderDto {

    //标准票30分钟
    private String typeTime;
    //时间说明 9:00-12:00 或者 1:00-5:00
    private String timeSlot;

}
