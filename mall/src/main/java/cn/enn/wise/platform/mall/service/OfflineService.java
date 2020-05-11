package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.BbdOrderChangeParam;
import cn.enn.wise.platform.mall.bean.param.BbdOrderSaveParams;
import cn.enn.wise.platform.mall.bean.vo.OrderReqVo;
import cn.enn.wise.platform.mall.bean.vo.OrderResVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.util.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 离线订单业务层
 *
 * @author baijie
 * @date 2019-08-06
 */
public interface OfflineService {

    /**
     * 保存离线订单
     * @param ordersVo
     * @return
     */
    ResponseEntity saveOrUpdateOfflineOrder(OrderReqVo ordersVo) throws Exception;


    /**
     * 获取离线订单详情
     * @param id
     * @return
     */
    OrderResVo getOfflineOrderInfo(Long id);

    /**
     * 根据离线订单Id删除订单
     * @param id
     * @return
     */
    Boolean deleteOfflineOrderById(Long id);


    /**
     * 百邦达离线下单
     * @param bbdOrderParams 订单保存参数
     * @return
     * @throws Exception
     */
    Map<String,Object> saveBbdOfflineOrder(BbdOrderSaveParams bbdOrderParams)throws Exception;

    /**
     * 绑定离线订单至线上订单
     * @param phone
     * @param user
     * @throws Exception
     */
    void bindOfflineOrder(String phone,User user) throws Exception;


    /**
     * 佰邦达票改签
     * @param bbdOrderChangeParam
     * @return
     */
    List<Map<String,Object>> changeBbdTicket(BbdOrderChangeParam bbdOrderChangeParam);
}
