package cn.enn.wise.platform.mall.service.impl;


import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.autotable.*;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.constants.KnowConstants;
import cn.enn.wise.platform.mall.constants.PeriodEnum;
import cn.enn.wise.platform.mall.controller.applets.WzdOrderAppletsController;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.BBDTicketService;
import cn.enn.wise.platform.mall.service.GroupOrderService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.service.RefundApplyService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.WX.WxPayUtils;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.OinfoVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.OrderDetailVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShipBaseVo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 18:34
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
@Slf4j
@DS("master")
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsProjectMapper goodsProjectMapper;

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Autowired
    private OrderAppletsMapper orderAppletsMapper;

    @Autowired
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @Autowired
    private RefundApplyMapper refundApplyMapper;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RefundApplyService refundApplyService;

    @Autowired
    private GoodsCouponPromotionMapper goodsCouponPromotionMapper;

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private OrdersTicketMapper ordersTicketMapper;

    @Autowired
    private BBDTicketService bbdTicketService;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    private OrderCheckMapper orderCheckMapper;


    private static final Integer COMPANY_XZ = 5;

    private static final Integer COMPANY_WZD = 11;

    private static final Integer COMPANY_BSC = 10;

    private static final Integer NO_PAY = 1;

    private static final int GROUP_ORDER = 3;

    private static final int CANCEL_ORDER = 5;

    private static final int REFUND_ORDER = 11;

    private static final int BOAT_ORDER = 5;

    private static int count = 0;

    @Value("${companyId}")
    private String companyId;


    @Override
    public PageInfo<Order> findAllOrderList(OrderBean orderBean) {

        List<Order> list = orderDao.findAllOrderList(orderBean);

        PageInfo<Order> orderInfoList = new PageInfo<>(list);

        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(order -> {
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(order.getOrderCode());
                List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    order.setIsCanRefund(2);
                }
            });

            list.stream().forEach(order -> {
                QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
                refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, order.getOrderCode());
                List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);
                if (CollectionUtils.isNotEmpty(refundApplyList)) {
                    order.setRefundPrice(refundApplyList.stream().filter(o -> o.getApprovalsSts().equals(2)).map(RefundApply::getRefundAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                }
            });

            List<Order> resultList = list.stream().map(o -> {
                o.setTimeSpan(o.getEnterTime() + " " + o.getTimeSpan());
                if (StringUtils.isNotEmpty(o.getSnapshot())) {
                    JSONObject distributeMap = JSONObject.parseObject(o.getSnapshot());
                    o.setDistributorName(distributeMap.getString("userName"));
                    o.setDistributorPhone(distributeMap.getString("phone"));
                }
                return o;
            }).collect(Collectors.toList());

            resultList.parallelStream().forEach(order -> {
                if (StringUtils.isNotEmpty(order.getReason())) {
                    String reason = WzdOrderAppletsController.getReason(Long.valueOf(order.getReason()));
                    order.setReason(reason);
                }
            });

            List<Order> orderList = resultList.stream().sorted(Comparator.comparing(Order::getTimeSpan).reversed()).collect(Collectors.toList());

            orderInfoList.setList(orderList);
            return orderInfoList;
        }
        return orderInfoList;
    }

    @Override
    public PageInfo<BoatOrderVo> findAllBoatOrderList(BoatPcOrderBean boatPcOrderBean) {
        List<BoatOrderVo> boatOrderVoList = orderDao.findAllBoatOrderList(boatPcOrderBean);

        PageInfo<BoatOrderVo> boatOrderVoPageInfo = new PageInfo<>(boatOrderVoList);

        if (CollectionUtils.isNotEmpty(boatOrderVoList)) {
            boatOrderVoList.stream().forEach(boatOrderVo -> {
                ShipLineInfo shipLineInfo = JSONObject.parseObject(boatOrderVo.getShipLineInfo(), ShipLineInfo.class);
                boatOrderVo.setLineFrom(boatOrderVo.getLineFrom() + shipLineInfo.getFromInfo());
                boatOrderVo.setLineTo(boatOrderVo.getLineTo() + shipLineInfo.getArriveInfo());
                boatOrderVo.setBoatStartTime(shipLineInfo.getStartTime());
                boatOrderVo.setBoatEndTime(shipLineInfo.getArriveTime());
                boatOrderVo.setLineEndDate(boatOrderVo.getLineDate());
                int i = shipLineInfo.getStartTime().compareTo(shipLineInfo.getArriveTime());
                if (i >= 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(boatOrderVo.getLineDate());
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    boatOrderVo.setLineEndDate(calendar.getTime());
                    boatOrderVo.setLineDate(boatOrderVo.getLineDate());
                }
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(boatOrderVo.getOrderCode());
                List<OrderTicketVo> useTicketList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus().equals(3)).collect(Collectors.toList());
                List<OrderTicketVo> noUseTicketList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus().equals(2) || orderTicketVo.getShipTicketStatus().equals(3)).collect(Collectors.toList());
                List<OrderTicketVo> refundTicketList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus().equals(2)).collect(Collectors.toList());
                boatOrderVo.setUseAmount(useTicketList.size());
                boatOrderVo.setNoUseAmount(noUseTicketList.size());
                boatOrderVo.setRefundAmount(refundTicketList.size());
                if (CollectionUtils.isNotEmpty(noUseTicketList)) {
                    boatOrderVo.setNoCheckTicketSts("未检票");
                } else {
                    if (CollectionUtils.isNotEmpty(useTicketList)) {
                        boatOrderVo.setCheckTicketSts("检票完成");
                    } else {
                        if (refundTicketList.size() == orderTicketVoList.size()) {
                            boatOrderVo.setRefundTicketSts("全部退票");
                        }
                    }
                }
            });
            return boatOrderVoPageInfo;
        }
        return boatOrderVoPageInfo;
    }

    @Override
    public Boolean refundByOrderId(String orderCodes) {
        String[] orderCode = orderCodes.split(",");

        //region 检查退单时 订单状态：如果是已完成，不做操作，如果是已使用，通知mq 处理分销订单状态
        // 订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单
        // 2->5 已使用->已退款
        List<Order> orderList = orderDao.findOrderInfo(orderCode);
        for (Order order : orderList) {
            if (order.getState() == 10 || order.getState() == 3) {
                try {
                    Byte status = 5;
                    messageSender.sendSyncDistributorOrderStatus(order.getId(), status);
                } catch (Exception ex) {

                }
            }
        }
        int i = orderDao.refundByOrderId(orderCode);
        if (i > 0) {
            List<Order> list = orderDao.findOrderInfo(orderCode);
            list.stream().forEach(o -> {

                groupOrderService.updateItemStatusById(o.getBatCode());

                if (String.valueOf(COMPANY_XZ).equals(companyId) || String.valueOf(COMPANY_BSC).equals(companyId)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderNumber", o.getOrderCode());
                    messageSender.sendSms(o.getPhone(), "SMS_168250292", map);

                } else if (String.valueOf(COMPANY_WZD).equals(companyId)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("companyId", String.valueOf(11));
                    map.put("orderCode", o.getOrderCode());
                    map.put("phone", o.getPhone());
                    map.put("type", String.valueOf(2));
                    messageSender.sendSmsV2(map);

                }

            });
            return true;
        }
        return false;
    }

    @Override
    public List<Order> findOrderInfo(String orderCodes) {
        String[] orderCode = orderCodes.split(",");
        List<Order> orderList = orderDao.findOrderInfo(orderCode);

        if (CollectionUtils.isNotEmpty(orderList)) {
            QueryWrapper<ProdComm> refundApplyQueryWrapper = new QueryWrapper<>();
            refundApplyQueryWrapper.lambda().eq(ProdComm::getOrderId, orderCodes);
            ProdComm prodComm = prodCommMapper.selectOne(refundApplyQueryWrapper);
            orderList.stream().forEach(order -> order.setProdComm(prodComm));

            orderList.stream().forEach(h5OrderVo -> {
                QueryWrapper<RefundApply> refundApplyQuery = new QueryWrapper<>();
                refundApplyQuery.lambda().eq(RefundApply::getOrderId, h5OrderVo.getOrderCode());
                List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQuery);
                if (CollectionUtils.isNotEmpty(refundApplyList)) {
                    refundApplyList.stream().forEach(refundApply -> {
                        if (h5OrderVo.getOrderCode().equals(refundApply.getOrderId())) {
                            h5OrderVo.setPlatform(refundApply.getPlatform());
                            h5OrderVo.setIsRefund(2);
                        }
                    });
                }
            });

            List<OrderRefundVo> orderRefundVoList = refundApplyService.refundOrderDetail(orderList.get(0).getOrderCode());
            if (CollectionUtils.isNotEmpty(orderRefundVoList)) {
                orderList.stream().forEach(order -> {
                    order.setOrderRefundVoList(orderRefundVoList);
                });
            }

            orderList.stream().forEach(appOrderVo -> {
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(appOrderVo.getOrderCode());
                List<OrderTicketVo> collect = orderTicketVoList.stream().filter(ticketVo -> ticketVo.getRefundSts().equals(1)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    appOrderVo.setIsCanRefund(2);
                }
                List<OrderTicketVo> orderTicketVos = orderTicketVoList.stream().filter(ticketVo -> ticketVo.getTicketStateBbd() != null && ticketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(orderTicketVos)) {
                    appOrderVo.setDiscriminateBBDSts(2);

                    QueryWrapper<OrderHistory> orderHistoryQueryWrapper = new QueryWrapper<>();
                    List<Integer> list = new ArrayList<>();
                    orderTicketVoList.stream().forEach(orderTicketVo -> {
                        list.add(orderTicketVo.getId());
                    });
                    orderHistoryQueryWrapper.in("ticket_id", list);
                    List<OrderHistory> orderHistoryList = orderHistoryMapper.selectList(orderHistoryQueryWrapper);
                    if (CollectionUtils.isNotEmpty(orderHistoryList)) {
                        Map<Long, List<OrderHistory>> orderHistoryLists = orderHistoryList.stream().collect(Collectors.groupingBy(OrderHistory::getTicketId));
                        orderHistoryLists.entrySet().stream().forEach(orderHistory -> {
                            orderTicketVoList.stream().forEach(orderTicketVo -> {
                                count = 0;
                                if (orderTicketVo.getId().equals(orderHistory.getKey().intValue())) {
                                    Stream<OrderHistory> orderHistoryStream = orderHistory.getValue().stream().map(history -> {
                                        OrderHistory orderHistory1 = new OrderHistory();
                                        count = count + 1;
                                        orderHistory1.setNumbers(String.valueOf(count));
                                        orderHistory1.setCreateTime(history.getCreateTime());
                                        return orderHistory1;
                                    });
                                    orderTicketVo.setOrderHistoryList(orderHistoryStream.collect(Collectors.toList()));
                                }
                            });
                        });
                    }

                }
                appOrderVo.setOrderTicketVoList(orderTicketVoList);
            });

            orderList.parallelStream().forEach(order -> {
                if (StringUtils.isNotEmpty(order.getReason())) {
                    String reason = WzdOrderAppletsController.getReason(Long.valueOf(order.getReason()));
                    order.setReason(reason);
                }
            });

            orderList.parallelStream().forEach(order -> {
                if (StringUtils.isNotEmpty(order.getSnapshot())) {
                    JSONObject distributeMap = JSONObject.parseObject(order.getSnapshot());
                    order.setDistributorName(distributeMap.getString("userName"));
                    order.setDistributorPhone(distributeMap.getString("phone"));
                }
            });

            if (!StringUtil.isNullOrEmpty(orderList.get(0).getUserRole())) {
                orderList.stream().forEach(order -> {
                    order.setUserRole(order.getUserRole().replaceAll("\\\"", ""));
                });
            }
            if (orderList.get(0).getGoodsId() != null) {
                Order orders = orderDao.findGoodsId(orderList.get(0).getGoodsId());
                orderList.parallelStream().forEach(or -> {
                    or.setGoodsId(orders.getGoodsId());
                });
            }
        }

        if (CollectionUtils.isNotEmpty(orderList) && orderList.get(0).getOrderType().equals(GROUP_ORDER)) {
            GroupItemVo groupItemVo = orderDao.findGroupOrderInfo(orderList.get(0).getId());
            if (groupItemVo != null) {
                List<GroupItemVo> groupItemVos = new ArrayList<>();
                groupItemVos.add(groupItemVo);
                orderList.parallelStream().forEach(order -> {
                    order.setGroupOrderVoList(groupItemVos);
                });
                orderList.parallelStream().forEach(order -> {
                    PeriodEnum parse = PeriodEnum.parse(groupItemVo.getPeriod());
                    groupItemVo.setTimespan(parse.getText());
                });
            }
            orderList.parallelStream().forEach(order -> {
                order.setExperienceTime(order.getExperienceTimes());
            });
        }

        List<OrderTickets> orderTicketsList = orderDao.findOrderTicket(orderCode);

        if (CollectionUtils.isNotEmpty(orderTicketsList)) {
            orderList.stream().forEach(order -> {
                if (StringUtils.isNotEmpty(orderTicketsList.get(0).getSnapshot()) &&
                        StringUtils.isNotEmpty(orderTicketsList.get(0).getCheckInTime())) {
                    JSONObject ticketMap = JSONObject.parseObject(orderTicketsList.get(0).getSnapshot());
                    order.setCheckName(ticketMap.getString("name"));
                    order.setCheckInTime(orderTicketsList.get(0).getCheckInTime());
                }
            });
        }
        return orderList;
    }

    @Override
    public GoodsProject queryByStaffIdAndProjectId(Long userId, Long projectId) {
        return orderDao.queryByStaffIdAndProjectId(userId, projectId);
    }


    @Override
    public AppOrdersVo findAppAllOrderListV2(AppOrderBean appOrderBean) {
        AppOrdersVo appOrdersVo = new AppOrdersVo();
        List<AppOrderVo> appOrderVoList = orderDao.findAppAllOrderList(appOrderBean);
        if (CollectionUtils.isNotEmpty(appOrderVoList)) {
            appOrderVoList.parallelStream().forEach(appOrderVo -> {
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(appOrderVo.getOrderCode());
                List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    appOrderVo.setIsCanRefund(2);
                }
            });
            List<String> orderCodeList = appOrderVoList.stream().map(appOrderVo -> appOrderVo.getOrderCode()).collect(Collectors.toList());
            List<OrderTickets> orderTicket = orderDao.findOrderTicket(orderCodeList.stream().toArray(String[]::new));
            appOrderVoList.stream().forEach(appOrderVo -> {
                orderTicket.stream().forEach(orderTickets -> {
                    if (appOrderVo.getOrderCode().equals(orderTickets.getTicketCode())) {
                        if (orderTickets.getTicketStateBbd() != null) {
                            if (orderTickets.getTicketStateBbd().equals(100)) {
                                appOrderVo.setDiscriminateBBDSts(2);
                            }
                        }
                    }
                });
            });
        }
        appOrdersVo.setList(appOrderVoList);

        AppOrdersVo appOrderVos = orderDao.findAppAllNoUseOrder(appOrderBean);
        if (appOrderVos != null) {
            appOrdersVo.setPeopleNumBer(appOrderVos.getPeopleNumBer());
        }
        return appOrdersVo;
    }

    @Override
    public List<AppOrderVo> findAppAllOrderList(AppOrderBean appOrderBean) {
        return orderDao.findAppAllOrderList(appOrderBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long closeAppOrder(Integer type, String orderCode, User user, String roleName) {
        long result = 0;
        List<Order> list = findOrderInfo(orderCode);

        if (StringUtil.isNullOrEmpty(roleName)) {
            GoodsProject goodsProject = goodsProjectMapper.selectByStaffIdAndProjectId(user.getId(), Long.valueOf(list.get(0).getProjectId()));
            if (goodsProject == null) {
                return -1;
            }
        }
        switch (type) {
            case 1:
                result = orderDao.cancelAppOrder(orderCode);
                orderDao.updateOrderTicketState(orderCode, JSONObject.toJSONString(user));

                if (String.valueOf(COMPANY_WZD).equals(companyId)) {

                    if (list.get(0).getIsDistributeOrder() == 1) {

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        List<GoodsProject> goodsProjectList = orderDao.findProductName(list.get(0).getProjectId());
                        Order orders = orderDao.findDistributorPhone(list.get(0).getId());

                        if (orders != null) {
                            Date date = new Date();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("companyId", String.valueOf(11));
                            map.put("name", list.get(0).getName());
                            map.put("times", df.format(date));
                            map.put("projectName", goodsProjectList.get(0).getName());
                            map.put("amount", String.valueOf(list.get(0).getAmount()));
                            map.put("phones", list.get(0).getPhone());
                            String phone = orders.getPhone().replaceAll("\\\"", "");
                            map.put("phone", phone);
                            map.put("type", String.valueOf(6));
                            messageSender.sendSmsV2(map);

                            //App通知
                            String msg = "您好:您的分销客人%s已于今日%s成功体验%s,订单人数%s人,联系电话%s。";
                            msg = String.format(msg, list.get(0).getName(), DateUtil.getFormat(date, AppConstants.DATE_FORMAT_CHINESE), goodsProjectList.get(0).getName(), String.valueOf(list.get(0).getAmount()), list.get(0).getPhone());
                            messageSender.sendWriteOffOrder(msg, Long.valueOf(list.get(0).getProjectId()), Integer.valueOf(list.get(0).getIsDistributeOrder()), Long.valueOf(list.get(0).getDistributorId()), phone);

                            //sms(baseParams);
                        }

                    }
                } else if (String.valueOf(COMPANY_BSC).equals(companyId)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    List<GoodsProject> goodsProjectList = orderDao.findProductName(list.get(0).getProjectId());
                    Order orders = orderDao.findDistributorPhone(list.get(0).getId());
                    if (orders != null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", list.get(0).getName());
                        map.put("times", df.format(new Date()));
                        map.put("projectName", goodsProjectList.get(0).getName());
                        map.put("amount", String.valueOf(list.get(0).getAmount()));
                        map.put("phone", list.get(0).getPhone());
                        messageSender.sendSms(orders.getPhone().replaceAll("\\\"", ""), "SMS_172743964", map);
                    }
                }


                //region 分销订单流转 1->2 已支付->已使用(核销)
                for (Order order : list) {
                    try {
                        Byte status = 2;
                        messageSender.sendSyncDistributorOrderStatus(order.getId(), status);
                    } catch (Exception ex) {
                        continue;
                    }
                }
                //endregion
                break;
            case 2:

                //region 检查退单时 订单状态：如果是已完成，不做操作，如果是已使用，通知mq 处理分销订单状态
                // 订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单
                // 2->5 已使用->已退款
                Order order = list.get(0);
                if (order.getState() == 10 || order.getState() == 3) {
                    try {
                        Byte status = 5;
                        messageSender.sendSyncDistributorOrderStatus(order.getId(), status);
                    } catch (Exception ex) {

                    }
                }
                //endregion
                result = orderDao.retreatAppOrder(orderCode);
                groupOrderService.updateItemStatusById(order.getBatCode());
                RefundApply refundApply = RefundApply.builder().orderId(orderCode)
                        .orderItemId(order.getId().toString())
                        .orderAmount(order.getGoodsPrice())
                        .outRefundNo(order.getBatCode() + "TK")
                        .flowTradeNo(order.getBatCode())
                        .userId(String.valueOf(user.getId()))
                        .goodsNum(order.getAmount().intValue())
                        .refundAmount(order.getGoodsPrice())
                        .returnMoneySts(3)
                        .applyTime(new Date())
                        .buyerMsg("")
                        .buyerMsgType(null)
                        .handleName(roleName)
                        .platform(2)
                        .build();
                refundApplyMapper.insert(refundApply);
                if (String.valueOf(COMPANY_XZ).equals(companyId) || String.valueOf(COMPANY_BSC).equals(companyId)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderNumber", list.get(0).getOrderCode());
                    messageSender.sendSms(list.get(0).getPhone(), "SMS_168250292", map);

                } else if (String.valueOf(COMPANY_WZD).equals(companyId)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("companyId", String.valueOf(11));
                    map.put("orderCode", list.get(0).getOrderCode());
                    map.put("phone", list.get(0).getPhone());
                    map.put("type", String.valueOf(2));
                    messageSender.sendSmsV2(map);

                }
                break;
            default:
                return result;
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public long confirmAppOrder(String orderCode, User user, Integer tactics, String roleName) {
        long result = 0;
        List<Order> list = findOrderInfo(orderCode);

        if (CollectionUtils.isNotEmpty(list)) {

            if (StringUtil.isNullOrEmpty(roleName)) {
                GoodsProject goodsProject = goodsProjectMapper.selectByStaffIdAndProjectId(user.getId(), Long.valueOf(list.get(0).getProjectId()));
                if (goodsProject == null) {
                    return -1;
                }
            }

            result = orderDao.cancelAppOrder(orderCode);
            orderDao.updateOrderTicketState(orderCode, JSONObject.toJSONString(user));

            if (String.valueOf(COMPANY_WZD).equals(companyId)) {

                if (list.get(0).getIsDistributeOrder() == 1) {
                    sendMessages(list);
                }
            }

            //region 分销订单流转 1->2 已支付->已使用(核销)
            for (Order order : list) {
                try {
                    Byte status = 2;
                    // 核销同步分销订单状态
                    messageSender.sendSyncDistributorOrderStatus(order.getId(), status);

                    // 核销发送策略Id到队列
                    messageSender.sendCheckOrder(order.getId(), Long.valueOf(tactics));
                } catch (Exception ex) {
                    continue;
                }
            }
            // endregion

        }

        return result;
    }


    private void sendMessages(List<Order> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<GoodsProject> goodsProjectList = orderDao.findProductName(list.get(0).getProjectId());
        Order orders = orderDao.findDistributorPhone(list.get(0).getId());

        if (orders != null) {
            Date date = new Date();
            HashMap<String, String> map = new HashMap<>();
            map.put("companyId", String.valueOf(11));
            map.put("name", list.get(0).getName());
            map.put("times", df.format(date));
            map.put("projectName", goodsProjectList.get(0).getName());
            map.put("amount", String.valueOf(list.get(0).getAmount()));
            map.put("phones", list.get(0).getPhone());
            String phone = orders.getPhone().replaceAll("\\\"", "");
            map.put("phone", phone);
            map.put("type", String.valueOf(6));
            messageSender.sendSmsV2(map);

            //App通知
            String msg = "您好:您的分销客人%s已于今日%s成功体验%s,订单人数%s人,联系电话%s。";
            msg = String.format(msg, list.get(0).getName(), DateUtil.getFormat(date, AppConstants.DATE_FORMAT_CHINESE), goodsProjectList.get(0).getName(), String.valueOf(list.get(0).getAmount()), list.get(0).getPhone());
            messageSender.sendWriteOffOrder(msg, Long.valueOf(list.get(0).getProjectId()), Integer.valueOf(list.get(0).getIsDistributeOrder()), Long.valueOf(list.get(0).getDistributorId()), phone);

        }

    }

    @Override
    public void cancelOrder() {
        List<Order> list = orderDao.findCancelOrder();
        if (CollectionUtils.isNotEmpty(list)) {
            getCancelOrderList(list);
        }
    }

    @Override
    public void cancelWZDOrder() {
        List<Order> list = orderDao.findCancelWZDOrder();
        if (CollectionUtils.isNotEmpty(list)) {
            getCancelOrderList(list);
        }
    }

    @Override
    public List<Order> queryRefundOrder() {
        return orderDao.queryRefundOrder();
    }

    @Override
    public List<Order> queryTicketUnusedOrder() {
        return orderDao.queryTicketUnusedOrder();
    }


    @Override
    public void updateOrderPayStatus(String orderNum, BigDecimal actualPay) {
        orderDao.updateOrderPayStatus(orderNum, actualPay);
    }

    @Override
    public void updateOrderStateById(Integer state, Long id) {
        orderDao.updateOrderStateById(state, id);
    }


    @Override
    public List<Map<String, Object>> listReport(String startDate, String endDate) {

        List<Map<String, Object>> reportList = orderDao.listReport(startDate, endDate);

        List<Map<String, Object>> targetList = new ArrayList<>();


        /**
         * num, userName, phone, carNum, orderCount, orderAmount, orderCodes, siglePrice, goodsPrice, orderTime, isDistributeOrder
         */

        /**
         *
         *               id:2,
         *               name:"李四",
         *               phone:"13939121657",
         *               carcode:"车号123",
         *               ordernum:"订单数量2",
         *               :"订单人数2",
         *               :"2019-07-16",
         *               :"订单编号001",
         *               :"128",
         *               :"128",
         *               :'09:00-18:00',
         *               :1
         */

        for (Map<String, Object> item : reportList) {
            String num = item.get("num").toString();
            String userName = item.get("userName").toString();
            String phone = item.get("phone").toString();
            String carNum = item.get("carNum").toString();
            String orderCount = item.get("orderCount").toString();
            String orderAmount = item.get("orderAmount").toString();

            String[] orderCodes = item.get("orderCodes").toString().split(",");
            String[] siglePrice = item.get("siglePrice").toString().split(",");
            String[] goodsPrice = item.get("goodsPrice").toString().split(",");
            String[] orderTime = item.get("orderTime").toString().split(",");
            String[] isDistributeOrder = item.get("isDistributeOrder").toString().split(",");
            String[] orderDates = item.get("createTime").toString().split(",");
            String[] userNum = item.get("userNum").toString().split(",");

            Map<String, Object> reportItem = new HashMap<>();
            reportItem.put("id", num);
            reportItem.put("num", num);
            reportItem.put("name", userName);
            reportItem.put("phone", phone);
            reportItem.put("carcode", carNum);

            reportItem.put("ordernum", orderCount);
            reportItem.put("orderpeople", orderAmount);
            reportItem.put("ordercode", orderCodes);
            reportItem.put("price", siglePrice);
            reportItem.put("ordersum", goodsPrice);

            reportItem.put("timeframe", orderTime);
            reportItem.put("isDistribution", isDistributeOrder);
            reportItem.put("createTime", orderDates);
            reportItem.put("userNum", userNum);

            targetList.add(reportItem);

        }
        return targetList;
    }


    public void getCancelOrderList(List<Order> list) {
        list.stream().forEach(o -> {
            List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(o.getOrderCode());

            List<OrderTicketVo> orderTicketVos = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getStatus() != null && orderTicketVo.getStatus().equals(1)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orderTicketVos)){
                List<OrderTicketVo> refundOrderList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());
                updateOrderState(o, refundOrderList.size());
            }

            /*List<OrderTicketVo> orderTicketVos = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getTicketStateBbd().equals(1)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orderTicketVos)) {
                try {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.DATE, 3);
                    date = ca.getTime();
                    String endTime = format.format(date);
                    Date endTimes = format.parse(endTime);
                    orderCheckMapper.insert(OrderCheck.builder()
                            .orderCode(orderTicketVoList.get(0).getOrderCode())
                            .createTime(new Date()).distributorTime(endTimes).build());
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                List<OrderTicketVo> refundOrderList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1) && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                updateOrderState(o, refundOrderList.size());
            }*/
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOrderState(Order order, Integer amount) {

        //#region  发送消息到队列
        try {
            Byte status = 3;
            messageSender.sendSyncDistributorOrderStatusByAmount(order.getId(), status, amount);
        } catch (Exception ex) {

        }
        //#endregion

        orderDao.updateOrderState(order.getId());
        orderDao.updateOrderTicket(order.getId());
    }


    @Override
    public GroupOrderItem findGroupOrderItemByOrderId(Long orderId) {
        return orderDao.findGroupOrderItemByOrderId(orderId);
    }

    @Override
    public GroupOrder findGroupOrderByGroupOrderId(Long groupOrderId) {
        return orderDao.findGroupOrderByGroupOrderId(groupOrderId);
    }

    @Override
    public List<H5OrderVo> findH5AllOrderList(OrderQueryBean orderQueryBean) {
        List<H5OrderVo> h5AllOrderList = orderDao.findH5AllOrderList(orderQueryBean);
        if (CollectionUtils.isNotEmpty(h5AllOrderList)) {
            List<H5OrderVo> h5OrderVoLists = h5AllOrderList.stream().filter(h5OrderVo -> h5OrderVo.getDeleteStatus().equals(1)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(h5OrderVoLists)) {
                h5AllOrderList.stream().forEach(h5OrderVo -> {
                    QueryWrapper<RefundApply> refundApplyQuery = new QueryWrapper<>();
                    refundApplyQuery.lambda().eq(RefundApply::getOrderId, h5OrderVo.getOrderCode());
                    List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQuery);
                    if (CollectionUtils.isNotEmpty(refundApplyList)) {
                        refundApplyList.stream().forEach(refundApply -> {
                            if (h5OrderVo.getOrderCode().equals(refundApply.getOrderId())) {
                                h5OrderVo.setIsRefund(2);
                                h5OrderVo.setPlatform(refundApply.getPlatform());
                            }
                        });
                    }
                });

                h5AllOrderList.stream().forEach(h5OrderVo -> {
                    if (h5OrderVo.getOrderType().equals(BOAT_ORDER)) {
                        ShipLineInfo shipLineInfo = JSONObject.parseObject(h5OrderVo.getShipLineInfo(), ShipLineInfo.class);
                        h5OrderVo.setLineFrom(h5OrderVo.getLineFrom() + shipLineInfo.getFromInfo());
                        h5OrderVo.setLineTo(h5OrderVo.getLineTo() + shipLineInfo.getArriveInfo());
                        h5OrderVo.setAfterTime(shipLineInfo.getAfterTime());
                        h5OrderVo.setBoatStartTime(shipLineInfo.getStartTime());
                        h5OrderVo.setBoatEndTime(shipLineInfo.getArriveTime());
                        h5OrderVo.setLineEndDate(h5OrderVo.getLineDate());
                        int i = shipLineInfo.getStartTime().compareTo(shipLineInfo.getArriveTime());
                        if (i >= 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(h5OrderVo.getLineDate());
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            h5OrderVo.setLineDate(h5OrderVo.getLineDate());
                            h5OrderVo.setLineEndDate(calendar.getTime());
                        }
                    }
                });

                h5AllOrderList.stream().forEach(h5OrderVo -> {
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(h5OrderVo.getOrderCode());
                    List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect)) {
                        h5OrderVo.setIsCanRefund(2);
                    }
                });

                List<String> orderCodeList = h5AllOrderList.stream().map(orderVo -> orderVo.getOrderCode()).collect(Collectors.toList());
                List<RefundApply> refundApplyList = orderDao.findRefundOrderByOrderCode(orderCodeList);

                if (CollectionUtils.isNotEmpty(refundApplyList)) {
                    h5AllOrderList.stream().forEach(h5OrderVo -> {
                        refundApplyList.stream().forEach(refundApply -> {
                            if (refundApply.getOrderId().equals(h5OrderVo.getOrderCode())) {
                                h5OrderVo.setReturnMoneySts(refundApply.getReturnMoneySts());
                                h5OrderVo.setRefundSts(2);
                            }
                        });
                    });
                }

                List<OrderTickets> orderTicketsList = orderDao.findOrderTicket(orderCodeList.stream().toArray(String[]::new));
                if (CollectionUtils.isNotEmpty(orderTicketsList)) {
                    h5AllOrderList.stream().forEach(h5OrderVo -> {
                        orderTicketsList.stream().forEach(orderTickets -> {
                            if (StringUtils.isNotEmpty(orderTickets.getCheckInTime()) && StringUtils.isNotEmpty(orderTickets.getSnapshot())) {
                                if (h5OrderVo.getOrderCode().equals(orderTickets.getTicketCode())) {
                                    JSONObject jsonObject = JSONObject.parseObject(orderTickets.getSnapshot());
                                    h5OrderVo.setCheckInTime(orderTickets.getCheckInTime());
                                    h5OrderVo.setCheckName(jsonObject.getString("name"));
                                }
                            }
                        });
                        List<OrderTickets> collect = orderTicketsList.stream().filter(orderTicketVo -> h5OrderVo.getOrderCode().equals(orderTicketVo.getTicketCode()) && orderTicketVo.getTicketStateBbd() != null && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(collect)) {
                            h5OrderVo.setDiscriminateBBDSts(2);
                        }
                    });
                }

                h5AllOrderList.parallelStream().forEach(h5OrderVo -> {
                    if (h5OrderVo.getOrderType().equals(GROUP_ORDER)) {
                        h5OrderVo.setTimespan(h5OrderVo.getTimespans());
                    }
                });

                handleOrderSts(h5AllOrderList);
            }
            return h5OrderVoLists;
        }
        return h5AllOrderList;
    }

    @Override
    public long deleteH5Order(String orderCode) {
        return orderDao.deleteH5Order(orderCode);
    }


    @Override
    public List<H5OrderVo> findH5OrderInfo(String orderCode, Long userId) {
        OrderQueryBean orderQueryBean = new OrderQueryBean();
        orderQueryBean.setScenicId(Long.valueOf(companyId));
        orderQueryBean.setUserId(userId);
        List<H5OrderVo> h5AllOrderList = orderDao.findH5AllOrderList(orderQueryBean);
        if (CollectionUtils.isNotEmpty(h5AllOrderList)) {
            List<H5OrderVo> h5OrderVoList = h5AllOrderList.stream().filter(h5OrderVo -> h5OrderVo.getOrderCode().equals(orderCode)).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(h5OrderVoList)) {
                h5OrderVoList.stream().forEach(h5OrderVo -> {
                    if (StringUtils.isEmpty(h5OrderVo.getQrCode())) {
                        OrderQrCodeVo codeVo = QrCodeUtil.drawLogoQRCode(h5OrderVo.getOrderCode(), " ");
                        if (codeVo != null) {
                            String proof = codeVo.getNum();
                            String qrCodeUrl = codeVo.getQrCodeUrl();
                            wzdOrderAppletsMapper.updateQrCode(qrCodeUrl, proof, Long.valueOf(h5OrderVo.getId()));
                            h5OrderVo.setQrCode(qrCodeUrl);
                            h5OrderVo.setProof(proof);
                        }
                    }
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
                    List<OrderTicketVo> checkOrderList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getTicketStateBbd() != null && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkOrderList)) {
                        h5OrderVo.setCheckInTime(checkOrderList.get(0).getCheckInTime());
                    }
                    h5OrderVo.setOrderTicketVoList(orderTicketVoList);
                    List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getTicketStateBbd() != null && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect)) {
                        h5OrderVo.setDiscriminateBBDSts(2);
                    }
                    String orderSerialBbd = orderTicketVoList.get(0).getOrderSerialBbd();
                    if (Strings.isNotEmpty(orderSerialBbd)) {
                        JSONObject bbdOrderDetail = BaiBangDaHttpApiUtil.getBBDOrderDetail(orderSerialBbd);
                        if (bbdOrderDetail.getInteger("code") == 1) {
                            h5OrderVo.setTicketCode(bbdOrderDetail.getJSONObject("data").getString("qrCodeContent"));
                        }
                    }
                });
                handleOrderSts(h5OrderVoList);

                if (h5OrderVoList.get(0).getUserOfCouponId() != null) {
                    List<CouponParam> couponInfo = goodsCouponPromotionMapper.getCouponInfo(h5OrderVoList.get(0).getUserOfCouponId());
                    if (CollectionUtils.isNotEmpty(couponInfo)) {
                        h5OrderVoList.stream().forEach(h5OrderVo -> {
                            h5OrderVo.setCouponName(couponInfo.get(0).getName());
                        });
                    }
                }
                if (h5OrderVoList.get(0).getOrderType().equals(GROUP_ORDER)) {
                    h5OrderVoList.stream().forEach(h5OrderVo -> {
                        h5OrderVo.setTimespan(h5OrderVo.getTimespans());

                    });
                }

                List<String> orderCodeList = h5OrderVoList.stream().map(orderVo -> orderVo.getOrderCode()).collect(Collectors.toList());
                List<RefundApply> refundApplyList = orderDao.findRefundOrderByOrderCode(orderCodeList);

                if (CollectionUtils.isNotEmpty(refundApplyList)) {
                    h5OrderVoList.parallelStream().forEach(h5OrderVo -> {
                        refundApplyList.parallelStream().forEach(refundApply -> {
                            if (refundApply.getOrderId().equals(h5OrderVo.getOrderCode())) {
                                h5OrderVo.setReturnMoneySts(refundApply.getReturnMoneySts());
                                h5OrderVo.setRefundSts(2);
                            }
                        });
                    });
                }

                if (CollectionUtils.isNotEmpty(h5OrderVoList) && h5OrderVoList.get(0).getState().equals(NO_PAY)) {
                    Timestamp expiredTime = h5OrderVoList.get(0).getExpiredTime();
                    Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
                    long leaveTime = DateUtil.differentMunitessIsNotAbs(currentTime, expiredTime);
                    if (leaveTime <= 0) {
                        Orders orders = new Orders();
                        orders.setState(5);
                        orders.setActualPay(new BigDecimal(0));
                        orders.setId(Long.valueOf(h5OrderVoList.get(0).getId()));
                        h5OrderVoList.get(0).setState(5);
                        //手动修改订单状态
                        orderAppletsMapper.refundOrder(orders);
                    } else {
                        h5OrderVoList.get(0).setCountDown(leaveTime);
                    }

                }

                h5OrderVoList.stream().forEach(h5OrderVo -> {
                    if (h5OrderVo.getOrderType().equals(BOAT_ORDER)) {
                        ShipLineInfo shipLineInfo = JSONObject.parseObject(h5OrderVo.getShipLineInfo(), ShipLineInfo.class);
                        h5OrderVo.setLineFrom(h5OrderVo.getLineFrom() + shipLineInfo.getFromInfo());
                        h5OrderVo.setLineTo(h5OrderVo.getLineTo() + shipLineInfo.getArriveInfo());
                        h5OrderVo.setAfterTime(shipLineInfo.getAfterTime());
                        h5OrderVo.setBoatStartTime(shipLineInfo.getStartTime());
                        h5OrderVo.setBoatEndTime(shipLineInfo.getArriveTime());
                        h5OrderVo.setLineEndDate(h5OrderVo.getLineDate());
                        h5OrderVo.setShouldKnow(KnowConstants.KNOW);
                        int i = shipLineInfo.getStartTime().compareTo(shipLineInfo.getArriveTime());
                        if (i >= 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(h5OrderVo.getLineDate());
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            h5OrderVo.setLineEndDate(calendar.getTime());
                            h5OrderVo.setLineDate(h5OrderVo.getLineDate());
                        }
                    }
                });

                if (h5OrderVoList.get(0).getOrderType().equals(BOAT_ORDER)) {
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
                    ShipBaseVo<OrderDetailVo> orderDetail = LalyoubaShipHttpApiUtil.orderDetail(profiles, h5OrderVoList.get(0).getTicketOrderCode(), orderCode);
                    if (null==orderDetail){
                        throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "来游吧船票订单详情为空");
                    }
                    List<OinfoVo> laiU8OrderItemList = orderDetail.getData().getOinfo();
                    if (CollectionUtils.isEmpty(laiU8OrderItemList)){
                        throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "来游吧船票订单明细为空");
                    }
                    List<OinfoVo> refundOrderList = laiU8OrderItemList.stream().filter(laiU8OrderItem -> laiU8OrderItem.getStatusName().equals("已取消")).collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(refundOrderList)){
                        refundOrderList.parallelStream().forEach(refundApply ->{
                            orderDao.updateLaiU8OrderSts(refundApply.getTkdtID());
                            orderDao.updateOrderSts(orderCode,5);
                        });
                        orderTicketVoList.stream().forEach(orderTicketVo -> {
                            refundOrderList.stream().forEach(refundApply -> {
                                if (orderTicketVo.getTicketId().equals(Integer.valueOf(refundApply.getTkdtID()))){
                                    orderTicketVo.setShipTicketStatus(2);
                                }
                            });
                        });
                    }


                    List<OinfoVo> checkOrderList = laiU8OrderItemList.stream().filter(laiU8OrderItem -> laiU8OrderItem.getStatusName().equals("已检")).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkOrderList)){
                        checkOrderList.parallelStream().forEach(checkOrder ->{
                            orderDao.updateLaiU8OrderCheckSts(checkOrder.getTkdtID());
                            orderDao.updateOrderSts(orderCode,3);
                        });
                        orderTicketVoList.stream().forEach(orderTicketVo -> {
                            checkOrderList.stream().forEach(refundApply -> {
                                if (orderTicketVo.getTicketId().equals(Integer.valueOf(refundApply.getTkdtID()))){
                                    orderTicketVo.setShipTicketStatus(4);
                                }
                            });
                        });
                    }
                    List<PassengerVo> passengerVoList = new ArrayList<>();
                    h5OrderVoList.stream().forEach(h5OrderVo -> {
                        orderTicketVoList.stream().forEach(orderTicketVo -> {
                            passengerVoList.add(PassengerVo.builder().passengerName(orderTicketVo.getTicketUserName())
                                    .babyInfo(orderTicketVo.getBabyInfo())
                                    .idCard(orderTicketVo.getIdCard())
                                    .phone(orderTicketVo.getPhone())
                                    .seatNumber(orderTicketVo.getSeatNumber())
                                    .shipTicketStatus(orderTicketVo.getShipTicketStatus())
                                    .ticketType(orderTicketVo.getTicketType())
                                    .ticketId(orderTicketVo.getTicketId())
                                    .build());
                        });
                        h5OrderVo.setPassengerVoList(passengerVoList);
                        h5OrderVo.setServerPhone("0779-3071866");
                    });
                }
                return h5OrderVoList;
            }
            return h5OrderVoList;
        }
        return h5AllOrderList;
    }

    @Override
    public ComposeOrderVo findComposeOrderDetail(String orderCode) {
        ComposeOrderVo composeOrderVo = new ComposeOrderVo();
        Order order = orderDao.findComposeOrderDetail(orderCode);
        composeOrderVo.setOrderNum(order.getOrderCode());
        composeOrderVo.setOrderSts(order.getState());
        composeOrderVo.setTimeSpan(order.getExperienceTime());
        composeOrderVo.setName(order.getName());
        StringBuffer stringBuffers = new StringBuffer();
        stringBuffers.append(order.getSiglePrice());
        stringBuffers.append("×" + order.getAmount());
        composeOrderVo.setPriceNum(String.valueOf(stringBuffers));
        composeOrderVo.setPrice(String.valueOf(order.getSiglePrice()));
        composeOrderVo.setAmount(order.getAmount());
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        if (CollectionUtils.isNotEmpty(orderTicketVoList)) {

            List<ComposeItemVo> composeItemVoList = orderTicketVoList.stream().map(orderTicketVo -> {
                ComposeItemVo composeItemVo = new ComposeItemVo();
                composeItemVo.setItemName(orderTicketVo.getProjectName());
                composeItemVo.setItemId(orderTicketVo.getProjectId());
                composeItemVo.setGoodsName(orderTicketVo.getGoodsName());
                composeItemVo.setGoodsId(orderTicketVo.getGoodsId());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(orderTicketVo.getProjectName());
                stringBuffer.append("-" + orderTicketVo.getGoodsName());
                composeItemVo.setShopName(String.valueOf(stringBuffer));
                return composeItemVo;
            }).collect(Collectors.toList());

            Map<String, Long> collect1 = composeItemVoList
                    .stream()
                    .map(ComposeItemVo::getGoodsName)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            ArrayList<ComposeItemVo> collect2 = composeItemVoList.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getGoodsName()))),
                            ArrayList::new));

            collect2.stream().forEach(composeItemVo -> {
                collect1.entrySet().stream().forEach(e -> {
                    if (composeItemVo.getGoodsName().equals(e.getKey())) {
                        composeItemVo.setAmount(e.getValue());
                    }
                });
            });
            composeOrderVo.setComposeItemVoList(collect2);
            return composeOrderVo;
        }

        return composeOrderVo;
    }

    public List<H5OrderVo> handleOrderSts(List<H5OrderVo> h5OrderVoList) {
        h5OrderVoList.stream().forEach(h5OrderVo -> {
            switch (h5OrderVo.getOrderType()) {
                case 1:
                    if (h5OrderVo.getState().equals(CANCEL_ORDER) || h5OrderVo.getState().equals(REFUND_ORDER)) {
                        h5OrderVo.setOrderStsType(2);
                    } else {
                        h5OrderVo.setOrderStsType(1);
                    }
                    break;
                case 2:
                    h5OrderVo.setOrderStsType(1);
                    break;
                case 3:
                    if (h5OrderVo.getState().equals(CANCEL_ORDER) || h5OrderVo.getState().equals(REFUND_ORDER)) {
                        h5OrderVo.setOrderStsType(2);
                    } else {
                        if (h5OrderVo.getPayStatus().equals(1) || h5OrderVo.getPayStatus().equals(3)) {
                            h5OrderVo.setOrderStsType(1);
                        } else {
                            GroupOrderItem groupOrderItemByOrderId = orderDao.findGroupOrderItemByOrderId(Long.valueOf(h5OrderVo.getId()));
                            GroupOrder groupOrder = orderDao.findGroupOrderByGroupOrderId(groupOrderItemByOrderId.getGroupOrderId());
                            if (groupOrder.getStatus() == 2) {
                                h5OrderVo.setOrderStsType(3);
                                h5OrderVo.setEndTime(groupOrder.getEndTime());
                            } else {
                                h5OrderVo.setOrderStsType(1);
                                h5OrderVo.setEndTime(groupOrder.getEndTime());
                            }
                        }
                    }
                    break;
                case 4:
                    if (h5OrderVo.getState().equals(CANCEL_ORDER) || h5OrderVo.getState().equals(REFUND_ORDER)) {
                        h5OrderVo.setOrderStsType(2);
                    } else {
                        h5OrderVo.setOrderStsType(1);
                    }
                    break;
                case 5:
                    if (h5OrderVo.getState().equals(CANCEL_ORDER) || h5OrderVo.getState().equals(REFUND_ORDER)) {
                        h5OrderVo.setOrderStsType(2);
                    } else {
                        h5OrderVo.setOrderStsType(1);
                    }
                    break;
                default:
                    h5OrderVo.setOrderStsType(1);
                    break;
            }
        });
        return h5OrderVoList;
    }

    @Override
    public BoatPcOrderDetailVo findBoatOrderDetailPc(String orderCode) {
        List<Order> orderList = orderDao.findOrderInfo(orderCode.split(","));
        BoatPcOrderDetailVo boatPcOrderDetailVo = new BoatPcOrderDetailVo();
        orderList.stream().forEach(order -> {
            boatPcOrderDetailVo.setCreateTime(order.getCreateTime());
            boatPcOrderDetailVo.setExpiredTime(order.getExperienceTime());
            boatPcOrderDetailVo.setGoodsPrice(String.valueOf(order.getGoodsPrice()));
            boatPcOrderDetailVo.setIdCardType("身份证");
            boatPcOrderDetailVo.setIdNumber(order.getIdNumber());
            boatPcOrderDetailVo.setName(order.getName());
            boatPcOrderDetailVo.setOrderCode(order.getOrderCode());
            boatPcOrderDetailVo.setOrderSts(order.getState());
            boatPcOrderDetailVo.setOrderType(order.getOrderType());
            boatPcOrderDetailVo.setPayTime(order.getPayTime());
            boatPcOrderDetailVo.setTicketOrderCode(order.getTicketOrderCode());
            boatPcOrderDetailVo.setPhone(order.getPhone());
        });
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        boatPcOrderDetailVo.setOrderTicketVoList(orderTicketVoList);
        List<OrderTicketVo> ticketVoList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus().equals(2)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ticketVoList)) {
            List<BoatRefundOrderVo> boatRefundOrderVoList = new ArrayList<>();
            ticketVoList.stream().forEach(ticketVo ->{
                Double refund = ticketVo.getRefund();
                if (refund!=null){
                    double feePrice = Double.valueOf(ticketVo.getSinglePrice()) - Double.valueOf(ticketVo.getRefund());
                    BigDecimal bigDecimal = new BigDecimal(feePrice);
                    double fee = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    BoatRefundOrderVo boatRefundOrderVo = new BoatRefundOrderVo();
                    boatRefundOrderVo.setPrice(ticketVo.getSinglePrice());
                    boatRefundOrderVo.setRefundAmount("1");
                    boatRefundOrderVo.setFee(String.valueOf(fee));
                    boatRefundOrderVo.setRefundPrice(String.valueOf(ticketVo.getRefund()));
                    boatRefundOrderVoList.add(boatRefundOrderVo);
                }else {
                    BoatRefundOrderVo boatRefundOrderVo = new BoatRefundOrderVo();
                    boatRefundOrderVo.setPrice(ticketVo.getSinglePrice());
                    boatRefundOrderVo.setRefundAmount("1");
                    boatRefundOrderVo.setFee(String.valueOf(0));
                    boatRefundOrderVo.setRefundPrice(ticketVo.getSinglePrice());
                    boatRefundOrderVoList.add(boatRefundOrderVo);
                }
            });
            boatPcOrderDetailVo.setBoatRefundOrderVoList(boatRefundOrderVoList);
        }

        return boatPcOrderDetailVo;
    }

    @Override
    public List<OrderTicketVo> findTicketOrderDetail(String orderCode) {
        return orderDao.findComposeOrderInfo(orderCode);
    }

    @Override
    public OrderTicketVo findLaiU8OrderDetail(String orderCode) {
        return orderDao.findLaiU8OrderDetail(orderCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long updateLaiU8PrintedSts(OrderPrintedParams orderPrintedParams, String orderCode) {
        return orderDao.updateLaiU8PrintedSts(orderPrintedParams) > 0 ? orderDao.updateOrderUseSts(orderCode) : 0;
    }

    @Override
    public long distributeBindUser(String phone, Long userId) {
        return orderDao.distributeBindUser(DistributeBindUserParam.builder()
                .bindTime(new Date())
                .userId(String.valueOf(userId))
                .distributePhone(phone).build());
    }

    @Override
    public DistributeBindUser findIsDisBindUser(String phone, Long userId) {
        return orderDao.findIsDisBindUser(phone, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shipTicketSync() {
        //List<Orders> list = orderDao.selectList(new QueryWrapper<Orders>().eq("order_type", "5").eq("state","2"));
        List<Order> list = orderDao.findBystate(2, 5);
        log.info("======待同步的数量：" + list.size());
        for (Order order : list) {
            try {
                findH5OrderInfo(order.getOrderCode(),order.getUserId());
            }catch (Exception e){
                log.info("shipTicketSync:"+e);
            }finally {
                continue;
            }
        }
//        if (CollectionUtils.isNotEmpty(list)) {
//            list.stream().forEach(x -> {
//                log.info("订单号：" + x.getOrderCode() + "对接平台编号" + x.getTicketOrderCode());
//                ShipBaseVo<OrderDetailVo> shipBaseVo = LalyoubaShipHttpApiUtil.orderDetail(profiles, x.getTicketOrderCode(), x.getOrderCode());
//                if (shipBaseVo != null) {
//                    //1为正常响应
//                    if (shipBaseVo.getStatus() == 1) {
//                        log.info("订单状态" + shipBaseVo.getData().getStatus());
//                        //查看订单状态  "船票状态 1 未使用 2已使用 3 已退款"
//                        //2  需要修改订单状态为已使用
//                        //3  需要修改订单状态为已退单 同时修改为已退款
//                        if (shipBaseVo.getData().getStatus().equals("2")) {
//                            orderDao.updateOrderStateById(2, x.getId());
//                            log.info("=========修改订单状态为已使用===========");
//                        } else if (shipBaseVo.getData().getStatus().equals("3")) {
//                            //orderDao.updateOrderStateById(11, x.getId());
//                            orderDao.updateOrderPayStateById(11, 3, x.getId());
//                            log.info("=========修改订单状态为已退单，同时支付状态改为已退款===========");
//                        }
//                    }
//                }
//            });
//
//        }
    }

    @Override
    public long updateOrderFailureRefund(String orderCode) {
        return orderDao.updateOrderFailureRefund(orderCode);
    }


    @Override
    public long updateBBDOrderRefundSts(String ticketSerialBbd) {
        return orderDao.updateBBDOrderRefundSts(ticketSerialBbd);
    }

    @Override
    public long updateProdCommSts(String orderCode) {
        return orderDao.updateProdCommSts(orderCode);
    }


    @Override
    public List<Order> queryTicketPayedOrder() {
        return orderDao.queryTicketPayedOrder();
    }


    @Override
    public void setOrderTicketStatus(String ticketId, Integer status) {
        orderDao.setOrderTicketStatus(ticketId, status);
    }

    @Override
    public void setOrderTicketRefund(String ticketId, Double refund, String refundRatio) {
        orderDao.setOrderTicketRefund(ticketId, refund, refundRatio);
    }

    /**
     * 同步订单状态
     */
    @Override
    public void updateBaBangDaOrderStatus() {

        List<Map<String, Object>> orderList = ordersTicketMapper.getBaiBangDaOrderList();
        for (Map<String, Object> order : orderList) {

            Long ticketId = Long.parseLong(order.get("ticketId").toString());
            String orderCode = order.get("orderCode").toString();
            JSONObject object = BaiBangDaHttpApiUtil.getBbdTicketDetail(ticketId);
            JSONObject orderObject = object.getJSONObject("data");
            Integer status = orderObject.getInteger("status");

            // 同步订单状态
            bbdTicketService.updateBBDTicketState(ticketId, status);
            Integer isTicketPrinted = orderObject.getInteger("isTicketPrinted");
            String ticketNum = orderObject.getString("ticketNo");

            // 同步打印状态
            OrderPrintedParams params = new OrderPrintedParams();
            params.setOrderCode(ticketNum);
            params.setPrintedSts(isTicketPrinted);
            updateLaiU8PrintedSts(params, orderCode);

        }
    }

    @Override
    public void handleCheckOrder() {
        QueryWrapper<OrderCheck> orderCheckQueryWrapper = new QueryWrapper<>();
        orderCheckQueryWrapper.lambda().eq(OrderCheck::getHandleSts, 1);
        List<OrderCheck> orderCheckList = orderCheckMapper.selectList(orderCheckQueryWrapper);
        if (CollectionUtils.isNotEmpty(orderCheckList)) {

            orderCheckList.stream().forEach(orderChecks -> {
                boolean sameDay = DateUtils.isSameDay(orderChecks.getDistributorTime(), new Date());
                if (sameDay){
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCheckList.get(0).getOrderCode());
                    List<OrderTicketVo> refundOrderList = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1) && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
                    Order order = new Order();
                    order.setId(orderTicketVoList.get(0).getOrderId());
                    updateOrderState(order, refundOrderList.size());

                    try {
                        QueryWrapper<OrderCheck> orderCheck = new QueryWrapper<>();
                        orderCheck.lambda().eq(OrderCheck::getOrderCode, orderTicketVoList.get(0).getOrderCode());
                        OrderCheck orderCheck1 = OrderCheck.builder().handleSts(2).build();
                        orderCheckMapper.update(orderCheck1, orderCheck);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public List<Long> findValidOrder(String order16U) {
        List<Long> ticketIds = orderDao.findValidOrder(order16U);
        return ticketIds;
    }

    @Override
    public void updateOrderTicketStatus(List<Long> ids, int num) {
        //String idParam = StringUtils.join(ids, ",");

        for (int i=0; i<num; i++){
            orderDao.updateOrderTicketStatus(ids.get(i));
        }

    }

    @Override
    public String findOrderCodeByBatCode(String order16U) {
        return orderDao.findOrderCodeByBatCode(order16U);
    }

    @Override
    public void updateOrderStateByOrderCode(String orderCode) {
        orderDao.updateOrderSts(orderCode, 3);
    }


    @Override
    public void handlePftRefundOrder(PFTOrderCallBack pftOrderCallBack){
        Order composeOrderDetail = orderDao.findComposePftOrderDetail(pftOrderCallBack.getOrderCall());
        BigDecimal couponPrice = new BigDecimal(100);
        Integer totalPrice = composeOrderDetail.getGoodsPrice().multiply(couponPrice).intValue();
        Integer refundPrice = composeOrderDetail.getActualPay().multiply(couponPrice).intValue();
        String payCode = composeOrderDetail.getBatCode();
        String refundCode = composeOrderDetail.getBatCode()+"TK";
        try {
            Map<String, String> refundMap = WxPayUtils.refund(payCode, refundCode, String.valueOf(totalPrice), String.valueOf(refundPrice));
            log.info("微信退款返回值：" + refundMap);
            if(GeneUtil.isNotNullAndEmpty(refundMap) && refundMap.get("return_code").equals("SUCCESS") && refundMap.get("result_code").equals("SUCCESS")){
                orderDao.updateOrderFailureRefund(composeOrderDetail.getOrderCode());
            }else {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "退款失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void orderStateCallBack(PFTOrderCallBack orderStateCallBack) {
        LOGGER.info("/order/orderCheckIn  订单核销 --->入参对象" + orderStateCallBack);
        if(StringUtils.isNotBlank(orderStateCallBack.getAction()) && StringUtils.isNotBlank(orderStateCallBack.getOrderState())
                && StringUtils.isNotBlank(orderStateCallBack.getTnumber())){
            //有效期内的消费
            //if(orderStateCallBack.getAction().equals("1")){
                // 1 全部消费   7  部分消费
                if(orderStateCallBack.getOrderState().equals("1") || orderStateCallBack.getOrderState().equals("7")){
                    if(StringUtils.isNotBlank(orderStateCallBack.getOrderCall())){
                        List<Long> ticketIds = findValidOrder(orderStateCallBack.getOrderCall());
                        if(CollectionUtils.isNotEmpty(ticketIds)){
                            //核销数目和票数比较 确认一致 开始核销
                            if(Integer.parseInt(orderStateCallBack.getTnumber()) <= ticketIds.size()){
                                updateOrderTicketStatus(ticketIds, Integer.parseInt(orderStateCallBack.getTnumber()));
                                //查询出订单状态 如果不是"3"已使用 更改为已使用
                                String orderCode = findOrderCodeByBatCode(orderStateCallBack.getOrderCall());
                                if(StringUtils.isNotBlank(orderCode)){
                                    //此时更改状态为已使用
                                    updateOrderStateByOrderCode(orderCode);
                                }
                            }else{
                                LOGGER.info("=========核销数目超过待核销数目=========");
                            }
                        }else{
                            LOGGER.info("=========本次不存在核销订单=========");
                        }
                    }else{
                        LOGGER.info("=========第三方底单号为空=========");
                    }
                }else{
                    LOGGER.info("========不是全部消费或者部分消费==========");
                }
            //}else{
              //  LOGGER.info("========不在有效期内消费==========");
            //}
        } else{
            LOGGER.info("入参参数存在空值--->"+orderStateCallBack);
        }
    }
}
