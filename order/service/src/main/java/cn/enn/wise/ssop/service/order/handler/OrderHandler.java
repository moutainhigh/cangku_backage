package cn.enn.wise.ssop.service.order.handler;
import cn.enn.wise.ssop.api.goods.dto.response.GoodsExtendOrderDTO;
import cn.enn.wise.ssop.api.goods.facade.GoodsExtendFacade;
import cn.enn.wise.ssop.api.member.dto.request.MemberParam;
import cn.enn.wise.ssop.api.member.dto.response.ContactDTO;
import cn.enn.wise.ssop.api.member.dto.response.MemberDTO;
import cn.enn.wise.ssop.api.member.facade.MemberFacade;
import cn.enn.wise.ssop.api.order.dto.request.BaseOrderParam;
import cn.enn.wise.ssop.api.order.dto.request.GoodsInfoParam;
import cn.enn.wise.ssop.api.order.enums.OrderExceptionAssert;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityCheckParam;
import cn.enn.wise.ssop.api.promotions.dto.request.GoodsPriceParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorInfoDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.SaleDTO;
import cn.enn.wise.ssop.api.promotions.facade.ActivityAppletsFacade;
import cn.enn.wise.ssop.api.promotions.facade.DistributorFacade;
import cn.enn.wise.ssop.service.order.feign.Goods;
import cn.enn.wise.ssop.service.order.feign.Sale;
import cn.enn.wise.ssop.service.order.handler.schedule.SaveOrderHandler;
import cn.enn.wise.ssop.service.order.model.*;
import cn.enn.wise.ssop.service.order.utils.IdGenerator;
import cn.enn.wise.uncs.base.pojo.TableBase;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用订单处理器
 */
@Slf4j
public abstract class OrderHandler implements SaveOrderHandler {

    public  Random random = new Random();

    public final static IdGenerator instance = new IdGenerator();

    private static Set orderSourceSet = new HashSet();

    static {
        orderSourceSet.add(1);
        orderSourceSet.add(2);
        orderSourceSet.add(3);
        orderSourceSet.add(4);
        orderSourceSet.add(5);
    }

    @Autowired
    private DistributorFacade distributorFacade;

    @Autowired
    private MemberFacade memberFacade;

    @Autowired
    private GoodsExtendFacade goodsExtendFacade;

    @Autowired
    private ActivityAppletsFacade activityAppletsFacade;

    @Override
    public OrderWrapper initOrder(BaseOrderParam defaultOrderSaveParam) {

        checkCommonParams(defaultOrderSaveParam);

        Long parentOrderId = instance.generatorId();

        Orders orders = buildOder(defaultOrderSaveParam);

        orders.setOrderId(parentOrderId);

        List<OrderSale> orderSaleList = buildOderSale(parentOrderId,checkSales(defaultOrderSaveParam));

        List<OrderGoods> orderGoodsList = buildOderGoods(parentOrderId,checkGoods(defaultOrderSaveParam),defaultOrderSaveParam,checkSales(defaultOrderSaveParam));

        List<OrderRelatePeople> orderRelatePeopleList = buildOderRelatePeople(parentOrderId,checkRelateUser(defaultOrderSaveParam));

        List<OrderDistributor> orderDistributorList = buildOrderDistributor(defaultOrderSaveParam);

        TableBase tableBase = null;
        Map<Long, String> orderMap = new HashMap<>();
        Map<Long, Long> skuMap = new HashMap<>();
        Map<Long, Map<Long, Integer>> relateMap = new HashMap<>();
        Map<Long, Long> saleMap = new HashMap<>();
        BigDecimal shouldPayPrice = BigDecimal.ZERO;
        BigDecimal decreasePrice = BigDecimal.ZERO;

        for (OrderGoods orderGoods : orderGoodsList) {

            skuMap.put(orderGoods.getSkuId(), orderGoods.getOrderId());
            orderMap.put(orderGoods.getOrderId(), orderGoods.getOrderNo());
            shouldPayPrice = shouldPayPrice.add(orderGoods.getShouldPayPrice() != null ? orderGoods.getShouldPayPrice() : BigDecimal.ZERO);
            decreasePrice = decreasePrice.add(orderGoods.getDecreasePrice() != null ? orderGoods.getDecreasePrice() : BigDecimal.ZERO);
            log.info("orderGoods ：{}", orderGoods);
            tableBase = handle(defaultOrderSaveParam,orders, orderGoods);
        }

        orders.setShouldPayPrice(shouldPayPrice);
        orders.setDecreasePrice(decreasePrice);

        if (orders.getShouldPayPrice() != null) {
            if (orders.getDecreasePrice() != null) {
                orders.setActualPayPrice(orders.getShouldPayPrice().subtract(orders.getDecreasePrice()));
            } else {
                orders.setActualPayPrice(orders.getShouldPayPrice().subtract(BigDecimal.ZERO));
            }
        }

        // *****************************************************************
        //营销策略关联orderId ,给订单用户关联orderId
        for (GoodsInfoParam goodsInfoParam : defaultOrderSaveParam.getGoodsInfoParamList()) {
            Long orderId;
            if ((orderId = skuMap.get(goodsInfoParam.getSkuId())) != null) {
                if (goodsInfoParam.getSaleId() != null && goodsInfoParam.getSaleId() != 0) {
                    saleMap.put(goodsInfoParam.getSaleId(), orderId);
                } else if (defaultOrderSaveParam.getActivityBaseId() != null && defaultOrderSaveParam.getActivityBaseId() != 0) {
                    saleMap.put(defaultOrderSaveParam.getActivityBaseId(), orderId);
                }

                if (goodsInfoParam.getCustomerId() != null) {
                    if (!relateMap.containsKey(goodsInfoParam.getCustomerId())) {
                        Map<Long, Integer> temp = new HashMap<>();
                        temp.put(orderId, 0);
                        relateMap.put(goodsInfoParam.getCustomerId(), temp);
                    } else {
                        relateMap.get(goodsInfoParam.getCustomerId()).put(orderId, 0);
                    }
                }
            }
        }

        //关联订单orderId (订单营销策略）
        for (OrderSale orderSale : orderSaleList) {
            if (orderSale.getSaleId() != null && orderSale.getSaleId() != 0) {
                orderSale.setOrderId(saleMap.get(orderSale.getSaleId()));
            }
            if (orderSale.getActivityBaseId() != null && orderSale.getActivityBaseId() != 0) {
                orderSale.setOrderId(saleMap.get(orderSale.getActivityBaseId()));
            }

            orderSale.setOrderNo(orderMap.get(orderSale.getOrderId()));
        }
        // *****************************************************************

        int length = orderRelatePeopleList.size();
        for (int i = 0; i < length; i++) {
            if (orderRelatePeopleList.get(i).getCustomerId() == null) {
                //主订单联系人
                orderRelatePeopleList.get(i).setOrderId(orderRelatePeopleList.get(i).getParentOrderId());
            } else {
                //非主订单联系人
                Map<Long, Integer> longIntegerMap = relateMap.get(orderRelatePeopleList.get(i).getCustomerId());
                for (Map.Entry<Long, Integer> map : longIntegerMap.entrySet()) {
                    if (map.getValue() == 0) {
                        Long orderId = map.getKey();
                        orderRelatePeopleList.get(i).setOrderId(orderId);
                        relateMap.get(orderRelatePeopleList.get(i).getCustomerId()).put(orderId, 1);
                        break;
                    }
                }
            }
        }
        return OrderWrapper.builder().orders(orders).orderGoodsList(orderGoodsList).orderSaleList(orderSaleList)
                .orderRelatePeopleList(orderRelatePeopleList).orderDistributorList(orderDistributorList).tableBase(tableBase).build();
    }

    @Override
    public boolean checkCommonParams(BaseOrderParam defaultOrderSaveParam) {
        if ((defaultOrderSaveParam.getMemberId() == null || defaultOrderSaveParam.getMemberId() == 0 ) && (
                StringUtils.isEmpty(defaultOrderSaveParam.getCustomerName()) ||
                        StringUtils.isEmpty(defaultOrderSaveParam.getPhone()))) {
            OrderExceptionAssert.ORDER_BUYER_NOT_EXISTS.assertFail();
        }

        String phone = defaultOrderSaveParam.getPhone();
        if(phone!=null && phone.length()>0) {
            if (phone.length() != 11) {
                OrderExceptionAssert.PHONE_CHECK_ERROR.assertFail();
            }
        }

        if(defaultOrderSaveParam.getChannelId()==null || defaultOrderSaveParam.getChannelId()<1){
            OrderExceptionAssert.CHANNEL_ID_CHECK_ERROR.assertFail();
        }
        if(StringUtils.isEmpty(defaultOrderSaveParam.getChannelName())){
            OrderExceptionAssert.CHANNEL_NAME_CHECK_ERROR.assertFail();
        }

        if(defaultOrderSaveParam.getOrderSource()==null || !orderSourceSet.contains(defaultOrderSaveParam.getOrderSource().intValue())){
            OrderExceptionAssert.SOURCE_CHECK_ERROR.assertFail();
        }

        List<GoodsInfoParam> goodsInfoParamList = defaultOrderSaveParam.getGoodsInfoParamList();
        if(goodsInfoParamList==null || goodsInfoParamList.size()<1){
            OrderExceptionAssert.GOODS_CHECK_ERROR.assertFail();
        }
        for(GoodsInfoParam goodsInfoParam: goodsInfoParamList){
            if(goodsInfoParam.getAmount()==null || goodsInfoParam.getAmount()<1){
                OrderExceptionAssert.GOODS_AMOUNT_CHECK_ERROR.assertFail();
            }
            if(goodsInfoParam.getSkuId()==null || goodsInfoParam.getSkuId()<1){
                OrderExceptionAssert.GOODS_CHECK_ERROR.assertFail();
            }
        }
        return true;
    }

    @Override
    public List<OrderRelatePeople> checkRelateUser(BaseOrderParam defaultOrderSaveParam) {
        List<OrderRelatePeople> userList = new ArrayList<>();
        try{
            if(defaultOrderSaveParam.getMemberId()!=null && defaultOrderSaveParam.getMemberId()!=0){
                MemberParam memberParam = new MemberParam();
                memberParam.setUserId(defaultOrderSaveParam.getMemberId());
                R<MemberDTO> memberInfo = memberFacade.getMemberInfo(memberParam);
                if(memberInfo.getCode()==0 && memberInfo.getData()!=null) {
                    MemberDTO data = memberInfo.getData();
                    OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
                    orderRelatePeople.setPhone(data.getPhone());
                    orderRelatePeople.setMemberId(defaultOrderSaveParam.getMemberId());
                    orderRelatePeople.setCustomerName(data.getName());
                    orderRelatePeople.setCertificateNo(data.getIdCard());
                    userList.add(orderRelatePeople);
                }else{
                    OrderExceptionAssert.ORDER_OF_USER_NOT_ONLINE.assertFail();
                }
            }
            List<Long> customerIdList = defaultOrderSaveParam.getGoodsInfoParamList().stream().filter(c->c.getCustomerId()!=null && c.getCustomerId()!=0).map(c -> c.getCustomerId()).collect(Collectors.toList());
            if(customerIdList!=null && customerIdList.size()>1){
                R<List<ContactDTO>> contactInfoList = memberFacade.getContactInfoList(StringUtils.join(customerIdList.toArray(),","));
                log.info("联系人信息：{}",contactInfoList);
                if(contactInfoList.getCode()!=0 || contactInfoList.getData()==null || contactInfoList.getData().size()<1)
                {
                    OrderExceptionAssert.ORDER_OF_USER_NOT_ONLINE.assertFail();
                }

                List<ContactDTO> contactDTOS = contactInfoList.getData();
                if(!CollectionUtils.isEmpty(contactDTOS)){
                    contactDTOS.forEach(data->{
                        OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
                        orderRelatePeople.setPhone(data.getPhoneNum());
                        orderRelatePeople.setMemberId(defaultOrderSaveParam.getMemberId());
                        orderRelatePeople.setCustomerName(data.getName());
                        orderRelatePeople.setCustomerId(data.getId());
                        orderRelatePeople.setCertificateNo(data.getIdCard());
                        userList.add(orderRelatePeople);
                    });
                }
            }
            if(!StringUtils.isEmpty(defaultOrderSaveParam.getCustomerName()) && !StringUtils.isEmpty(defaultOrderSaveParam.getPhone())){
                MemberParam memberParam = new MemberParam();
                memberParam.setPhone(defaultOrderSaveParam.getPhone());
                R<MemberDTO> memberInfo = memberFacade.getMemberInfo(memberParam);
                if(memberInfo.getCode()==0 && memberInfo.getData()!=null) {
                    MemberDTO data = memberInfo.getData();
                    OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
                    orderRelatePeople.setPhone(data.getPhone());
                    orderRelatePeople.setMemberId(defaultOrderSaveParam.getMemberId());
                    orderRelatePeople.setCustomerName(data.getName());
                    orderRelatePeople.setCertificateNo(data.getIdCard());
                    userList.add(orderRelatePeople);
                }else {
                    OrderRelatePeople orderRelatePeople = new OrderRelatePeople();
                    orderRelatePeople.setPhone(defaultOrderSaveParam.getPhone());
                    orderRelatePeople.setCustomerName(defaultOrderSaveParam.getCustomerName());
                    if(!StringUtils.isEmpty(defaultOrderSaveParam.getCertificateNo())){
                        orderRelatePeople.setCertificateNo(defaultOrderSaveParam.getCertificateNo());
                    }
                    userList.add(orderRelatePeople);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            OrderExceptionAssert.ORDER_OF_USER_NOT_ONLINE.assertFail();
        }
        return userList;
    }

    @Override
    public List<Goods> checkGoods(BaseOrderParam defaultOrderSaveParam) {
        List<Long> skuIds = new ArrayList<>();
        for(GoodsInfoParam goodsInfoParam:defaultOrderSaveParam.getGoodsInfoParamList()){
            skuIds.add(goodsInfoParam.getSkuId());
        }
        R<List<GoodsExtendOrderDTO>> goodsExtendListOrder = null;
        try {
            log.info("商品id：列表{}",StringUtils.join(skuIds.toArray(new Long[skuIds.size()]),","));
            goodsExtendListOrder = goodsExtendFacade.getGoodsExtendListOrder(StringUtils.join(skuIds.toArray(new Long[skuIds.size()]),","));
            log.info("商品返回列表：{}",goodsExtendListOrder);
        }catch (Exception e){
            OrderExceptionAssert.ORDER_GET_SKU_EXCEPTION.assertFail();
        }
        if(goodsExtendListOrder.getCode()!=0){
            OrderExceptionAssert.ORDER_GET_SKU_EXCEPTION.assertFail();
        }
        List<GoodsExtendOrderDTO> data = goodsExtendListOrder.getData();
        List<Goods> goodsList = new ArrayList<>();
        DistributorInfoDTO distributorInfoDTO = null;
        try {
            distributorInfoDTO = distributorFacade.getDistributionInfoByPhone(defaultOrderSaveParam.getDistributorPhone()).getData();
        }catch (Exception e){
            log.error("获取分销商信息异常：{}",e);
        }
        for(GoodsExtendOrderDTO goodsExtendOrderDTO : data){
            List<GoodsExtendOrderDTO> childGoods = goodsExtendOrderDTO.getChildGoods();
            if(childGoods!=null && childGoods.size()>0){
                for(GoodsExtendOrderDTO goodsExtendOrderDTO1 : childGoods){
                    Goods goods = Goods.builder().skuId(goodsExtendOrderDTO1.getSkuId()).goodsId(goodsExtendOrderDTO1.getGoodsId())
                            .goodsName(goodsExtendOrderDTO1.getGoodsName()).goodsPrice(goodsExtendOrderDTO.getGoodsPrice())
                            .goodsType(goodsExtendOrderDTO1.getGoodsType()).payTimeOut(goodsExtendOrderDTO.getPay())
                            .skuName(goodsExtendOrderDTO1.getSkuName()).expireTime(goodsExtendOrderDTO1.getExpireTime())
                            .storeCount(goodsExtendOrderDTO1.getStoreCount()).isPackage(1).parentSkuId(goodsExtendOrderDTO.getSkuId()).build();

                    goodsList.add(goods);
                }
            }else{
                Goods goods = Goods.builder().skuId(goodsExtendOrderDTO.getSkuId()).goodsId(goodsExtendOrderDTO.getGoodsId()).goodsName(goodsExtendOrderDTO.getGoodsName()).goodsPrice(goodsExtendOrderDTO.getGoodsPrice()).goodsType(goodsExtendOrderDTO.getGoodsType())
                        .skuName(goodsExtendOrderDTO.getSkuName()).expireTime(goodsExtendOrderDTO.getExpireTime()).payTimeOut(goodsExtendOrderDTO.getPay())
                        .storeCount(goodsExtendOrderDTO.getStoreCount()).isPackage(0).parentSkuId(null).build();
                goodsList.add(goods);
            }
        }
        return goodsList;
    }

    @Override
    public List<Sale> checkSales(BaseOrderParam defaultOrderSaveParam) {
        Long baseActivityBaseId = defaultOrderSaveParam.getActivityBaseId();
        Long saleId = defaultOrderSaveParam.getGoodsInfoParamList().get(0).getSaleId();
        if(baseActivityBaseId == null || baseActivityBaseId ==0 ){
            if(saleId == null || saleId == 0){
                return new ArrayList<>();
            }
        }

        ActivityCheckParam param = new ActivityCheckParam();
        param.setActivityBaseId(defaultOrderSaveParam.getActivityBaseId());
        //param.setActivityRuleId();
        //param.setCouponId();
        //param.setGroupOrderId();
        param.setPhone(defaultOrderSaveParam.getPhone());
        param.setUserId(defaultOrderSaveParam.getMemberId());

        List<GoodsPriceParam> goodsPriceParams = new ArrayList<>();
        for(GoodsInfoParam goodsInfoParam : defaultOrderSaveParam.getGoodsInfoParamList()){
            GoodsPriceParam goodsPriceParam = new GoodsPriceParam();
            goodsPriceParam.setGoodsNum(goodsInfoParam.getAmount());
            goodsPriceParam.setGoodsId(goodsInfoParam.getSkuId());
            goodsPriceParam.setGoodsName("");
            goodsPriceParam.setGoodsPrice(0);
            goodsPriceParams.add(goodsPriceParam);
        }
        param.setGoodsPriceParamList(goodsPriceParams);
        R r = activityAppletsFacade.checkActivityParam(param);
        log.info("R:"+r);
        if(r.getCode() == 0){
            List<SaleDTO> saleDTOS = ( List<SaleDTO>)r.getData();
            List<Sale> saleList = new ArrayList<>();
            saleDTOS.forEach(x->{
                Sale sale = Sale.builder().saleId(x.getSaleId()).skuId(x.getSkuId()).customerId(x.getCustomerId()).decreasePrice(x.getDecreasePrice()).saleName(x.getSaleName()).saleType(x.getSaleType()).ruleId(x.getRuleId()).activityBaseId(x.getActivityBaseId()).build();
                saleList.add(sale);
            });
            return saleList;
        }
        return new ArrayList<>();
    }

    /**
     * 创建主订单
     * @param defaultOrderSaveParam
     * @return
     */
    public static Orders buildOder(BaseOrderParam defaultOrderSaveParam){
        Orders orders = new Orders();
        BeanUtils.copyProperties(defaultOrderSaveParam,orders);
        return orders;
    }

    /**
     * 创建订单策略
     * @param parentOrderId
     * @param feignSaleList
     * @return List<OrderSale>
     */
    public static List<OrderSale> buildOderSale(Long parentOrderId, List<Sale> feignSaleList){
        List<OrderSale> orderSaleList = new ArrayList<>();
        for(Sale sale:feignSaleList){
            OrderSale orderSale = new OrderSale();
            BeanUtils.copyProperties(sale, orderSale);
            orderSale.setParentOrderId(parentOrderId);
            orderSale.setOrderId(parentOrderId);
            orderSale.setActivityBaseId(sale.getActivityBaseId());
            orderSale.setSaleId(sale.getSaleId());
            orderSale.setSaleType(sale.getSaleType());
            orderSaleList.add(orderSale);
        }
        log.info("feignSaleList:"+ JSON.toJSONString(feignSaleList));
        log.info("orderSaleList:"+ JSON.toJSONString(orderSaleList));
        return orderSaleList;
    }

    /**
     * 创建子订单
     * @param orderId
     * @param feignUserList
     * @return List<OrderRelatePeople>
     */
    public static List<OrderRelatePeople> buildOderRelatePeople(Long orderId,List<OrderRelatePeople> feignUserList){
        feignUserList.forEach(c->c.setParentOrderId(orderId));
        return feignUserList;
    }


    /**
     * 创建分销商订单
     * @param defaultOrderSaveParam
     * @return
     */
    public static List<OrderDistributor> buildOrderDistributor(BaseOrderParam defaultOrderSaveParam){
        List<OrderDistributor> orderDistributorList = new ArrayList<>();
        OrderDistributor orderDistributor = new OrderDistributor();
        BeanUtils.copyProperties(defaultOrderSaveParam,orderDistributor);
        orderDistributorList.add(orderDistributor);
        return orderDistributorList;
    }

    /**
     * 创建订单明细
     * @param parentOrderId
     * @param feignGoodsList
     * @return
     */
    public List<OrderGoods> buildOderGoods(Long parentOrderId, List<Goods> feignGoodsList, BaseOrderParam defaultOrderSaveParam, List<Sale> feignSaleList){
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        Map<Long,Long> skuMap = new HashMap<>();
        Map<Long,Integer> amountMap = new HashMap<>();
        Map<Long, BigDecimal> saleMap = new HashMap<>();

        for(Sale sale:feignSaleList){
            saleMap.put(sale.getSkuId(),sale.getDecreasePrice().divide(BigDecimal.ZERO));
        }

        log.info("优惠信息：{}",saleMap);
        //客户端传过来第一个商品作为主商品
        Integer amount = 0;
        for(GoodsInfoParam goodsInfoParam: defaultOrderSaveParam.getGoodsInfoParamList()){
            amountMap.put(goodsInfoParam.getSkuId(),goodsInfoParam.getAmount());
            amount += goodsInfoParam.getAmount();
        }

        log.info("数量信息：{}",amountMap);
        BigDecimal shouldPayPrice = BigDecimal.ZERO;
        BigDecimal decreasePrice = BigDecimal.ZERO;
        log.info("请求参数：{}",defaultOrderSaveParam);

        for(Goods goods:feignGoodsList){
            log.info("响应：{}",goods);
            OrderGoods orderGoods = new OrderGoods();
            Long orderId = null;
            if(feignGoodsList.size()>1){
                orderId = instance.generatorId();
            }else if(feignGoodsList.size()==1){
                orderId = parentOrderId;
            }
            orderGoods.setParentOrderId(parentOrderId);
            orderGoods.setGoodsType(goods.getGoodsType());
            orderGoods.setOrderId(orderId);
            orderGoods.setExtraInformation(goods.getExtraInfo());
            orderGoods.setOrderNo(generatorOrderNo());
            BeanUtils.copyProperties(goods,orderGoods);
            if(goods.getParentSkuId()!=null){
                //套餐
                orderGoods.setAmount(amountMap.get(goods.getParentSkuId()));
            }else{
                orderGoods.setAmount(amountMap.get(goods.getSkuId()));
            }
            log.info("应付价格：{}",BigDecimal.valueOf(goods.getGoodsPrice()).multiply(BigDecimal.valueOf(Long.valueOf(orderGoods.getAmount()))).divide(BigDecimal.valueOf(100)));
            orderGoods.setShouldPayPrice(BigDecimal.valueOf(goods.getGoodsPrice()).multiply(BigDecimal.valueOf(Long.valueOf(orderGoods.getAmount()))).divide(BigDecimal.valueOf(100)));
            orderGoods.setDecreasePrice(saleMap.get(goods.getSkuId()));
            if(orderGoods.getShouldPayPrice()!=null){
                if(orderGoods.getDecreasePrice()!=null){
                    orderGoods.setActualPayPrice(orderGoods.getShouldPayPrice().subtract(orderGoods.getDecreasePrice()));
                }else{
                    orderGoods.setActualPayPrice(orderGoods.getShouldPayPrice().subtract(BigDecimal.ZERO));
                }
            }
            orderGoodsList.add(orderGoods);
            skuMap.put(goods.getSkuId(),orderId);

            shouldPayPrice = shouldPayPrice.add(orderGoods.getShouldPayPrice()!=null?orderGoods.getShouldPayPrice():BigDecimal.ZERO);
            decreasePrice = decreasePrice.add(orderGoods.getDecreasePrice()!=null?orderGoods.getDecreasePrice():BigDecimal.ZERO);
        }

        if(feignGoodsList.size()>1){
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setParentOrderId(parentOrderId);
            orderGoods.setOrderId(parentOrderId);
            orderGoods.setAmount(amount);
            orderGoods.setShouldPayPrice(shouldPayPrice);
            orderGoods.setDecreasePrice(decreasePrice);
            orderGoodsList.add(orderGoods);
        }

        return orderGoodsList;
    }



}
