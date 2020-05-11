package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.api.order.dto.response.PayResponseDto;
import cn.enn.wise.uncs.base.pojo.response.R;

import java.util.Map;

public interface PayService {

    Map unifiedOrder(String orderSn, String body, String total, String openId) throws Exception;

    R pay(String prepayId) throws Exception;
}
