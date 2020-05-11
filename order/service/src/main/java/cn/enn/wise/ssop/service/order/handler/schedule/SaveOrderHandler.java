package cn.enn.wise.ssop.service.order.handler.schedule;

import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.request.DefaultOrderSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.request.OrderGoodsParam;
import cn.enn.wise.ssop.service.order.feign.Goods;
import cn.enn.wise.ssop.service.order.feign.Sale;
import cn.enn.wise.ssop.service.order.handler.OrderWrapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.model.OrderRelatePeople;
import cn.enn.wise.ssop.service.order.model.Orders;
import cn.enn.wise.uncs.base.pojo.TableBase;

import java.util.List;

/**
 * 下单时不同业务线的订单处理逻辑约束
 */
public interface SaveOrderHandler {

    /**
     * TODO:生成订单编号（这个规则需要重新定义，规则定义的要有含义，不仅仅是一个编号）
     * 规则描述：
     * 1、前置符号：3？位；
     * 2、系统时间戳
     * 3、3位随机数；
     *
     * @return
     */
    String generatorOrderNo();

    /**
     * 1.校验公共参数
     * <p>
     * 入参的格式：
     *
     * @param baseOrder
     * @return
     * @link www.enn.cn(这里需要指向接口对应规则的wiki地址)
     */
    boolean checkCommonParams(BaseOrderParam baseOrder);

    /**
     * 校验订单联系人及消费者
     *
     * @param baseOrder
     * @return
     */
    List<OrderRelatePeople> checkRelateUser(BaseOrderParam baseOrder);

    /**
     * 订购商品参数校验,返回合规的订购商品信息
     *
     * @param baseOrder
     * @return
     */
    List<Goods> checkGoods(BaseOrderParam baseOrder);

    /**
     * 校验订单商品关联的活动策略
     *
     * @param baseOrder
     * @return
     */
    List<Sale> checkSales(BaseOrderParam baseOrder);

    /**
     * 装配订单成数据库需要的格式
     *
     * @param baseOrder
     * @return
     */
    OrderWrapper initOrder(BaseOrderParam baseOrder);

    /**
     * 保存订单到数据库
     *
     * @param baseOrder
     */
    Integer saveOrder(BaseOrderParam baseOrder);

    TableBase handle(BaseOrderParam defaultOrderSaveParam,Orders orders, OrderGoods orderGoods);

}
