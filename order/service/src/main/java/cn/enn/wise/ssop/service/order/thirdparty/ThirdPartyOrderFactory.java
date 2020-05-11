package cn.enn.wise.ssop.service.order.thirdparty;

import cn.enn.wise.ssop.service.order.thirdparty.shenda.ShendaOrder;

/**
 * @author anhui257@163.com
 */
public class ThirdPartyOrderFactory {
    public static IThirdPartyOrder getFactory(Long scenicId){
        if(scenicId == 5L){
            return new ShendaOrder();
        }else {
            // TODO 添加别的平台
            return new ShendaOrder();
        }
    }
}
