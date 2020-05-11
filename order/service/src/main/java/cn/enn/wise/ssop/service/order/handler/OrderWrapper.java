package cn.enn.wise.ssop.service.order.handler;

import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.uncs.base.pojo.TableBase;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Builder
@Data
public class OrderWrapper {

    private TableBase tableBase;

    private  Orders orders;

    private List<OrderSale> orderSaleList;

    private List<OrderGoods> orderGoodsList;

    private List<OrderRelatePeople> orderRelatePeopleList;

    private List<OrderDistributor> orderDistributorList;

}
