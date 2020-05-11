package cn.enn.wise.ssop.api.order.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class PayResponseDto{

    private String appId;

    private String timestamp;

    private String nonceStr;

    private String signType;

    private String paySign;
}
