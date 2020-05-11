package cn.enn.wise.ssop.api.order.dto.response.applet;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class SkuExtraOrderDto {

    //规格票据名称
    private String regin;
    //游玩时间段
    private String time;
    //客户群
    private String custGroup;



}
