
package cn.enn.wise.ssop.service.order.mapper;


import cn.enn.wise.ssop.api.order.dto.request.OrderDetailListParam;
import cn.enn.wise.ssop.api.order.dto.request.OrderSearchParam;
import cn.enn.wise.ssop.api.order.dto.request.WeChatTicketParam;
import cn.enn.wise.ssop.api.order.dto.response.app.OrderAppSearchDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderDetailListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.app.OrderAppSearchListDTO;
import cn.enn.wise.ssop.service.order.model.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 订单数据库处理层
 * @author mrwhite
 * @date 2019-12-11
 */
@Mapper
@Repository
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {

    /**
     * 批量添加订单商品信息
     *
     * @param orderDetailList
     */
    void batchInsert(List<OrderGoods> orderDetailList);

    void batchUpdate(List<OrderGoods> orderDetailList);

    void batchUpdateOrderStatus(List<OrderGoods> orderDetailList);

    List<OrderGoods> getOrderGoodsList(List<Long> orderIds);

    List<OrderGoods> getOrderGoodsListByOrderId(Long orderId);

    List<Map> getOrderList(Map map);

    Integer getOrderCount(Map map);

    Integer searchOrderCount(OrderSearchParam orderSearchParam);

    List<OrderGoods> getOrderGoodsListWithPage(WeChatTicketParam weChatTicketParam);

    Integer getOrderGoodsCountWithPage(WeChatTicketParam weChatTicketParam);

    IPage<OrderAppSearchListDTO> orderGoodsSearch(Page<?> page, @Param("orderSearchParam") OrderSearchParam orderSearchParam);

    List<OrderGoodsListDTO> searchOrderGoodsList(OrderSearchParam orderSearchParam);

    List<OrderDetailListDTO> getOrderDetailList(OrderDetailListParam orderDetailListParam);

    List<OrderGoods>  getOrderGoodsModel(Long orderId);

    OrderRelatePeople getOrderGoodsUser(Long orderId);

    Orders getParentOrders(Long parentOrderId);

    List<OrderCancelRecord> getCanRefundOrder(Long orderId);

    OrderRefundRecord getOrderGoodsRefund(Long orderId);

    Integer selectSkuIdAmount(Long skuId);
}
