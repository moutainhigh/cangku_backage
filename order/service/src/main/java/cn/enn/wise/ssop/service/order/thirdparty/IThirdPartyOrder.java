package cn.enn.wise.ssop.service.order.thirdparty;

import cn.enn.wise.uncs.base.pojo.response.R;

import java.util.Map;

/**
 * @author 安辉
 */
public interface IThirdPartyOrder {
    /**
     * 三方下单
     * @param order
     * @return
     */
    R order(ThirdPartyOrder order);
}
