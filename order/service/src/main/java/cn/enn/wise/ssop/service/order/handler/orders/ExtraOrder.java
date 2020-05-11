package cn.enn.wise.ssop.service.order.handler.orders;

import lombok.Data;

@Data
public class ExtraOrder {

    //景点名
    public String scenicName;
    //图片
    public String imgUrl;

    //商品类型
    public Byte type;

    //入住时间
    public String startTime;
    //离店时间
    public String outTime;
}
