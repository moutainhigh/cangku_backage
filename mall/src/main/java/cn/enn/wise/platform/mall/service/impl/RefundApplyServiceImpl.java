package cn.enn.wise.platform.mall.service.impl;


import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.constants.KnowConstants;
import cn.enn.wise.platform.mall.controller.applets.WzdOrderAppletsController;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.RefundApplyMapper;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.service.RefundApplyService;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.service.WzdOrderAppletsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.GeneUtil;
import cn.enn.wise.platform.mall.util.MessageSender;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.OrderDetailVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.RefundPriceVo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShipBaseVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 13:58
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
@Slf4j
public class RefundApplyServiceImpl extends ServiceImpl<RefundApplyMapper, RefundApply> implements RefundApplyService {

    @Autowired
    private RefundApplyMapper refundApplyMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WzdOrderAppletsService wzdOrderAppletsService;

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private MessageSender messageSender;

    @Value("${spring.profiles.active}")
    private String profiles;

    private static int count = 0;

    private static String STR_FORMAT = "00";

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long refundApply(RefundApplyParam refundApplyParam, String openId) {

        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        RefundApply refundApply;
        String wxOrderCode = GeneUtil.getRefundCode();
        switch (refundApplyParam.getPlatform()) {
            case 1:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(refundApplyParam.getUserId())
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(3)
                        .applyTime(new Date())
                        .buyerMsg(refundApplyParam.getBuyerMsg())
                        .buyerMsgType(refundApplyParam.getBuyerMsgType())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(1)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                return handleRefund(refundApplyParam, refundApply, orderInfo);
            case 2:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(refundApplyParam.getUserId())
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(3)
                        .applyTime(new Date())
                        .buyerMsg(refundApplyParam.getBuyerMsg())
                        .buyerMsgType(refundApplyParam.getBuyerMsgType())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(1)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                return handleRefund(refundApplyParam, refundApply, orderInfo);
            default:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(openId)
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(1)
                        .applyTime(new Date())
                        .reasonLabel(refundApplyParam.getReasonLable())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(2)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                Orders orders = new Orders();
                orders.setOrderCode(refundApplyParam.getOrderId());
                orders.setReason(String.valueOf(refundApplyParam.getReasonLable()));
                try {
                    int refundSts = wzdOrderAppletsService.refundOrder(orders);
                    return refundSts > 0 ? refundApplyMapper.insert(refundApply) :
                            GeneConstant.INT_0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return GeneConstant.INT_0;
    }


    @Transactional(rollbackFor = Exception.class)
    public long handleRefund(RefundApplyParam refundApplyParam, RefundApply refundApply, List<Order> orderList) {
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApplyParam.getOrderId());

        orderTicketVoList.stream().forEach(orderTicketVo -> {
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                if (orderTicketVo.getGoodsId().equals(refundApplyDetailedParam.getGoodsId())) {
                    refundApplyDetailedParam.setGoodsName(orderTicketVo.getGoodsName());
                    refundApplyDetailedParam.setItemId(orderTicketVo.getProjectId());
                    refundApplyDetailedParam.setItemName(orderTicketVo.getProjectName());
                }
            });
        });

        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, refundApplyParam.getOrderId());
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

        if (CollectionUtils.isNotEmpty(refundApplyList)) {
            int insert = refundApplyMapper.insert(refundApply);

            int sum = refundApplyList.stream().mapToInt(RefundApply::getCountNum).sum();
            DecimalFormat dft = new DecimalFormat(STR_FORMAT);
            String orderCode = refundApply.getOrderId() + dft.format(sum + 1);
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                refundApplyDetailedParam.setRefundNum(orderCode);
                refundApplyDetailedParam.setOrderRefundId(refundApply.getId());
            });

            boolean isSend = withdrawalService.sendRefundNoticeSMS(orderCode);
            log.info("is send message" + isSend);
            if (!isSend) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "发送短信失败");
            }
            if (orderList.get(0).getCouponPrice() != null) {
                List<Integer> collect = Arrays.asList(refundApplyParam.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                collect.parallelStream().forEach(c -> {
                    refundApplyMapper.updateOrderTicketRefundSts(c);
                });
            } else {
                refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                    List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1) && refundApplyDetailedParam.getGoodsId().equals(orderTicketVo.getGoodsId())).limit(refundApplyDetailedParam.getAmount()).collect(Collectors.toList());
                    collect.stream().forEach(c -> {
                        refundApplyMapper.updateOrderTicketRefundSts(c.getId());
                    });
                });
            }
            return insert > 0 ? refundApplyMapper.addOrderRefundExtend(refundApplyParam.getRefundApplyDetailedParamList()) :
                    GeneConstant.INT_0;
        } else {
            int insert = refundApplyMapper.insert(refundApply);

            DecimalFormat dft = new DecimalFormat(STR_FORMAT);
            String orderCode = refundApply.getOrderId() + dft.format(count + 1);
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                refundApplyDetailedParam.setRefundNum(orderCode);
                refundApplyDetailedParam.setOrderRefundId(refundApply.getId());
            });

            boolean isSend = withdrawalService.sendRefundNoticeSMS(orderCode);
            log.info("is send message" + isSend);
            if (!isSend) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "发送短信失败");
            }

            if (orderList.get(0).getCouponPrice() != null) {
                List<Integer> itemIdList = Arrays.asList(refundApplyParam.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                itemIdList.stream().forEach(itemId -> {
                    refundApplyMapper.updateOrderTicketRefundSts(itemId);
                });
            } else {
                refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                    List<OrderTicketVo> orderTicketVoList1 = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1) && refundApplyDetailedParam.getGoodsId().equals(orderTicketVo.getGoodsId())).limit(refundApplyDetailedParam.getAmount()).collect(Collectors.toList());
                    orderTicketVoList1.stream().forEach(orderTicketVo -> {
                        refundApplyMapper.updateOrderTicketRefundSts(orderTicketVo.getId());
                    });
                });
            }
            return insert > 0 ? refundApplyMapper.addOrderRefundExtend(refundApplyParam.getRefundApplyDetailedParamList()) :
                    GeneConstant.INT_0;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public long unifyRefundApply(RefundApplyParam refundApplyParam) {
        List<Order> orderInfo = orderService.findOrderInfo(refundApplyParam.getOrderId());
        RefundApply refundApply;
        String wxOrderCode = GeneUtil.getRefundCode();
        switch (refundApplyParam.getPlatform()) {
            case 1:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(refundApplyParam.getUserId())
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(3)
                        .applyTime(new Date())
                        .buyerMsg(refundApplyParam.getBuyerMsg())
                        .buyerMsgType(refundApplyParam.getBuyerMsgType())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(1)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                return handleUnifyRefund(refundApplyParam, refundApply);
            case 2:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(refundApplyParam.getUserId())
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(3)
                        .applyTime(new Date())
                        .buyerMsg(refundApplyParam.getBuyerMsg())
                        .buyerMsgType(refundApplyParam.getBuyerMsgType())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(1)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                return handleUnifyRefund(refundApplyParam, refundApply);
            default:
                refundApply = RefundApply.builder().orderId(refundApplyParam.getOrderId())
                        .orderItemId(refundApplyParam.getOrderItemId())
                        .orderAmount(orderInfo.get(0).getShouldPay())
                        .outRefundNo(wxOrderCode)
                        .flowTradeNo(orderInfo.get(0).getBatCode())
                        .userId(refundApplyParam.getUserId())
                        .goodsNum(refundApplyParam.getGoodsNum())
                        .refundAmount(refundApplyParam.getRefundAmount())
                        .returnMoneySts(1)
                        .applyTime(new Date())
                        .reasonLabel(refundApplyParam.getReasonLable())
                        .handleName(refundApplyParam.getHandleName())
                        .platform(refundApplyParam.getPlatform())
                        .approvalsSts(2)
                        .couponPrice(refundApplyParam.getCouponPrice())
                        .build();
                Orders orders = new Orders();
                orders.setOrderCode(refundApplyParam.getOrderId());
                orders.setReason(String.valueOf(refundApplyParam.getReasonLable()));
                try {
                    int refundSts = wzdOrderAppletsService.refundOrder(orders);
                    return refundSts > 0 ? refundApplyMapper.insert(refundApply) :
                            GeneConstant.INT_0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return GeneConstant.INT_0;
    }



    @Transactional(rollbackFor = Exception.class)
    public long handleUnifyRefund(RefundApplyParam refundApplyParam, RefundApply refundApply) {
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApplyParam.getOrderId());

        orderTicketVoList.stream().forEach(orderTicketVo -> {
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                if (orderTicketVo.getGoodsId().equals(refundApplyDetailedParam.getGoodsId())) {
                    refundApplyDetailedParam.setGoodsName(orderTicketVo.getGoodsName());
                    refundApplyDetailedParam.setItemId(orderTicketVo.getProjectId());
                    refundApplyDetailedParam.setItemName(orderTicketVo.getProjectName());
                }
            });
        });

        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, refundApplyParam.getOrderId());
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

        if (CollectionUtils.isNotEmpty(refundApplyList)) {
            int insert = refundApplyMapper.insert(refundApply);

            int sum = refundApplyList.stream().mapToInt(RefundApply::getCountNum).sum();
            DecimalFormat dft = new DecimalFormat(STR_FORMAT);
            String orderCode = refundApply.getOrderId() + dft.format(sum + 1);
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                refundApplyDetailedParam.setRefundNum(orderCode);
                refundApplyDetailedParam.setOrderRefundId(refundApply.getId());
            });

            boolean isSend = withdrawalService.sendRefundNoticeSMS(orderCode);
            log.info("is send message" + isSend);
            if (!isSend) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "发送短信失败");
            }

            List<Integer> collect = Arrays.asList(refundApplyParam.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            collect.parallelStream().forEach(c -> {
                refundApplyMapper.updateOrderTicketRefundSts(c);
            });
            handleBBDRefundOrder(refundApplyParam.getOrderItemId(),orderTicketVoList,1);

            return insert > 0 ? refundApplyMapper.addOrderRefundExtend(refundApplyParam.getRefundApplyDetailedParamList()) :
                    GeneConstant.INT_0;
        } else {
            int insert = refundApplyMapper.insert(refundApply);

            DecimalFormat dft = new DecimalFormat(STR_FORMAT);
            String orderCode = refundApply.getOrderId() + dft.format(count + 1);
            refundApplyParam.getRefundApplyDetailedParamList().stream().forEach(refundApplyDetailedParam -> {
                refundApplyDetailedParam.setRefundNum(orderCode);
                refundApplyDetailedParam.setOrderRefundId(refundApply.getId());
            });

            boolean isSend = withdrawalService.sendRefundNoticeSMS(orderCode);
            log.info("is send message" + isSend);
            if (!isSend) {
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "发送短信失败");
            }

            List<Integer> itemIdList = Arrays.asList(refundApplyParam.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            itemIdList.stream().forEach(itemId -> {
                refundApplyMapper.updateOrderTicketRefundSts(itemId);
            });
            handleBBDRefundOrder(refundApplyParam.getOrderItemId(),orderTicketVoList,1);

            return insert > 0 ? refundApplyMapper.addOrderRefundExtend(refundApplyParam.getRefundApplyDetailedParamList()) :
                    GeneConstant.INT_0;
        }
    }

    @Override
    public long updateRefundSts(String refundNum) {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOutRefundNo, refundNum);
        RefundApply refundApply = RefundApply.builder()
                .returnMoneySts(2)
                .refundTime(new Date())
                .build();
        return refundApplyMapper.update(refundApply, refundApplyQueryWrapper);
    }

    @Override
    public RefundApply findRefundDetail(String orderCode) {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, orderCode);
        RefundApply refundApply = refundApplyMapper.selectOne(refundApplyQueryWrapper);
        if (refundApply.getReasonLabel() != null) {
            String reason = WzdOrderAppletsController.getReason(Long.valueOf(refundApply.getReasonLabel()));
            refundApply.setReasonLabels(reason);
        }
        return refundApply;
    }

    @Override
    public ComposeRefundOrderVo appRefundDetailV2(String orderCode) {
        ComposeRefundOrderVo composeRefundOrderVo = new ComposeRefundOrderVo();
        Order order = orderDao.findComposeOrderDetail(orderCode);
        composeRefundOrderVo.setOrderCode(order.getOrderCode());
        composeRefundOrderVo.setOrderSts(order.getState());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(order.getSiglePrice() + "元");
        stringBuffer.append("×" + order.getAmount() + "人");
        composeRefundOrderVo.setPrice(String.valueOf(order.getSiglePrice()));
        composeRefundOrderVo.setAmount(order.getAmount());
        composeRefundOrderVo.setPrices(String.valueOf(stringBuffer));
        composeRefundOrderVo.setOrderTotalPrice(String.valueOf(order.getGoodsPrice()));
        composeRefundOrderVo.setActualPay(String.valueOf(order.getShouldPay()));
        if (order.getCouponPrice() != null) {
            composeRefundOrderVo.setIsJoinCoupon(2);
        }
        composeRefundOrderVo.setCouponTotalPrice(String.valueOf(order.getCouponPrice()));

        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, orderCode);
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

        if (CollectionUtils.isNotEmpty(orderTicketVoList)) {
            List<OrderTicketVo> collect4 = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());

            List<NoPlayVo> noPlayVoList = collect4.parallelStream().map(orderTicketVo -> {
                NoPlayVo noPlayVo = new NoPlayVo();
                noPlayVo.setId(orderTicketVo.getId());
                noPlayVo.setItemName(orderTicketVo.getProjectName());
                noPlayVo.setGoodsName(orderTicketVo.getGoodsName());
                noPlayVo.setPlayName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
                noPlayVo.setPrice(orderTicketVo.getSinglePrice());
                noPlayVo.setGoodsId(orderTicketVo.getGoodsId());
                noPlayVo.setCouponPrice(orderTicketVo.getCouponPrice());
                return noPlayVo;
            }).collect(Collectors.toList());

            Map<String, Long> collect1 = noPlayVoList
                    .stream()
                    .map(NoPlayVo::getGoodsName)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            ArrayList<NoPlayVo> collect2 = noPlayVoList.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getGoodsName()))),
                            ArrayList::new));

            collect2.stream().forEach(noPlayVo -> {
                collect1.entrySet().stream().forEach(e -> {
                    if (noPlayVo.getGoodsName().equals(e.getKey())) {
                        noPlayVo.setAmount(e.getValue());
                    }
                });
            });

            composeRefundOrderVo.setNoPlayVoList(collect2);

            if (order.getOrderType().equals(1)) {
                List<NoPlayVo> noPlayVoList1 = collect4.stream().map(orderTicketVo -> {
                    NoPlayVo noPlayVo = new NoPlayVo();
                    noPlayVo.setId(orderTicketVo.getId());
                    noPlayVo.setItemName(orderTicketVo.getProjectName());
                    noPlayVo.setGoodsName(orderTicketVo.getGoodsName());
                    noPlayVo.setPlayName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
                    noPlayVo.setPrice(orderTicketVo.getSinglePrice());
                    noPlayVo.setGoodsId(orderTicketVo.getGoodsId());
                    noPlayVo.setCouponPrice(orderTicketVo.getCouponPrice());
                    noPlayVo.setAmount(Long.valueOf(orderTicketVoList.stream().filter(orderTicketVo1 -> orderTicketVo1.getRefundSts().equals(1)).collect(Collectors.toList()).size()));
                    return noPlayVo;
                }).collect(Collectors.toList());
                composeRefundOrderVo.setNoPlayVoList(noPlayVoList1);
            }

            if (CollectionUtils.isNotEmpty(refundApplyList)) {
                composeRefundOrderVo.setHandleName(refundApplyList.get(0).getHandleName());
                orderTicketVoList.parallelStream().forEach(orderTicketVo -> {
                    refundApplyList.parallelStream().forEach(refundApply -> {
                        if (orderTicketVo.getId().equals(refundApply.getOrderItemId())) {
                            refundApply.setPlaysName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
                        }
                    });
                });

                List<Integer> refundApplyIdList = refundApplyList.stream().map(refundApply -> refundApply.getId()).collect(Collectors.toList());
                List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundExtend(refundApplyIdList);
                List<RefundVo> refundVoList = new ArrayList<>();
                refundApplyList.stream().forEach(refundApply -> {
                    orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                        if (refundApply.getId().equals(orderRefundExtend.getOrderRefundId())) {
                            RefundVo refundVo = new RefundVo();
                            refundVo.setPrice(orderRefundExtend.getRefundPrice());
                            refundVo.setRefundTime(refundApply.getApplyTime());
                            refundVo.setHandleName(refundApply.getHandleName());
                            refundVo.setReturnMoneySts(refundApply.getReturnMoneySts());
                            refundVo.setRefundNum(orderRefundExtend.getRefundNum());
                            refundVo.setPlayName(orderRefundExtend.getProjectName() + "-" + orderRefundExtend.getGoodsName());
                            refundVo.setTotalPrice(String.valueOf(refundApply.getRefundAmount()));
                            refundVo.setAmount(orderRefundExtend.getRefundAmount());
                            refundVo.setRefundId(orderRefundExtend.getOrderRefundId());
                            refundVo.setApprovalsSts(refundApply.getApprovalsSts());
                            refundVoList.add(refundVo);
                        }
                    });
                });

                Map<Integer, List<RefundVo>> collect = refundVoList.stream().collect(Collectors.groupingBy(RefundVo::getRefundId));
                List<RefundDetailVo> collect3 = collect.entrySet().stream().map(entry -> {
                    RefundDetailVo refundDetailVo = new RefundDetailVo();
                    refundDetailVo.setRefundVoList(entry.getValue());
                    refundDetailVo.setRefundTime(entry.getValue().get(0).getRefundTime());
                    refundDetailVo.setRefundNum(entry.getValue().get(0).getRefundNum());
                    refundDetailVo.setHandleName(entry.getValue().get(0).getHandleName());
                    refundDetailVo.setReturnMoneySts(entry.getValue().get(0).getReturnMoneySts());
                    refundDetailVo.setTotalPrice(refundVoList.stream().filter(r -> r.getRefundId().equals(entry.getKey())).collect(Collectors.toList()).get(0).getTotalPrice());
                    refundDetailVo.setApprovalsSts(entry.getValue().get(0).getApprovalsSts());
                    return refundDetailVo;
                }).collect(Collectors.toList());

                composeRefundOrderVo.setRefundDetailVoList(collect3.stream().filter(c -> c.getReturnMoneySts().equals(2)).collect(Collectors.toList()));
            }

            return composeRefundOrderVo;
        }

        return composeRefundOrderVo;
    }

    @Override
    public ComposeRefundOrderVo laiU8RefundDetail(String orderCode) {
        ComposeRefundOrderVo composeRefundOrderVo = new ComposeRefundOrderVo();
        Order order = orderDao.findComposeOrderDetail(orderCode);
        composeRefundOrderVo.setOrderCode(order.getOrderCode());
        composeRefundOrderVo.setOrderSts(order.getState());
        composeRefundOrderVo.setPrice(String.valueOf(order.getSiglePrice()));
        composeRefundOrderVo.setAmount(order.getAmount());
        composeRefundOrderVo.setOrderTotalPrice(String.valueOf(order.getGoodsPrice()));
        composeRefundOrderVo.setActualPay(String.valueOf(order.getShouldPay()));
        if (order.getCouponPrice() != null) {
            composeRefundOrderVo.setIsJoinCoupon(2);
        }
        composeRefundOrderVo.setCouponTotalPrice(String.valueOf(order.getCouponPrice()));
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, orderCode);
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

        if (CollectionUtils.isNotEmpty(orderTicketVoList)) {
            List<OrderTicketVo> orderTicketVos = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getTicketStateBbd() != null && orderTicketVo.getTicketStateBbd().equals(100)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orderTicketVos)){
                composeRefundOrderVo.setDiscriminateBBDSts(2);
            }

            List<NoPlayVo> noPlayVoList = handleOrderTicket(orderTicketVoList);

            if (CollectionUtils.isNotEmpty(refundApplyList)) {
                composeRefundOrderVo.setHandleName(refundApplyList.get(0).getHandleName());
                orderTicketVoList.parallelStream().forEach(orderTicketVo -> {
                    refundApplyList.parallelStream().forEach(refundApply -> {
                        if (orderTicketVo.getId().equals(refundApply.getOrderItemId())) {
                            refundApply.setPlaysName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
                        }
                    });
                });

                List<Integer> refundApplyIdList = refundApplyList.stream().map(refundApply -> refundApply.getId()).collect(Collectors.toList());
                List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundExtend(refundApplyIdList);
                List<RefundVo> refundVoList = new ArrayList<>();
                refundApplyList.stream().forEach(refundApply -> {
                    orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                        if (refundApply.getId().equals(orderRefundExtend.getOrderRefundId())) {
                            RefundVo refundVo = new RefundVo();
                            refundVo.setPrice(orderRefundExtend.getRefundPrice());
                            refundVo.setRefundTime(refundApply.getApplyTime());
                            refundVo.setHandleName(refundApply.getHandleName());
                            refundVo.setReturnMoneySts(refundApply.getReturnMoneySts());
                            refundVo.setRefundNum(orderRefundExtend.getRefundNum());
                            refundVo.setPlayName(orderRefundExtend.getProjectName() + "-" + orderRefundExtend.getGoodsName());
                            refundVo.setTotalPrice(String.valueOf(refundApply.getRefundAmount()));
                            refundVo.setAmount(orderRefundExtend.getRefundAmount());
                            refundVo.setRefundId(orderRefundExtend.getOrderRefundId());
                            refundVo.setApprovalsSts(refundApply.getApprovalsSts());
                            refundVoList.add(refundVo);
                        }
                    });
                });

                Map<Integer, List<RefundVo>> collect = refundVoList.stream().collect(Collectors.groupingBy(RefundVo::getRefundId));
                List<RefundDetailVo> collect3 = collect.entrySet().stream().map(entry -> {
                    RefundDetailVo refundDetailVo = new RefundDetailVo();
                    refundDetailVo.setRefundVoList(entry.getValue());
                    refundDetailVo.setRefundTime(entry.getValue().get(0).getRefundTime());
                    refundDetailVo.setRefundNum(entry.getValue().get(0).getRefundNum());
                    refundDetailVo.setHandleName(entry.getValue().get(0).getHandleName());
                    refundDetailVo.setReturnMoneySts(entry.getValue().get(0).getReturnMoneySts());
                    refundDetailVo.setTotalPrice(refundVoList.stream().filter(r -> r.getRefundId().equals(entry.getKey())).collect(Collectors.toList()).get(0).getTotalPrice());
                    refundDetailVo.setApprovalsSts(entry.getValue().get(0).getApprovalsSts());
                    return refundDetailVo;
                }).collect(Collectors.toList());

                composeRefundOrderVo.setRefundDetailVoList(collect3.stream().filter(c -> c.getReturnMoneySts().equals(2)).collect(Collectors.toList()));
            }
            composeRefundOrderVo.setNoPlayVoList(noPlayVoList);
            return composeRefundOrderVo;
        }

        return composeRefundOrderVo;
    }

    @Override
    public ComposeRefundOrderVo appRefundDetail(String orderCode) {
        ComposeRefundOrderVo composeRefundOrderVo = new ComposeRefundOrderVo();
        Order order = orderDao.findComposeOrderDetail(orderCode);
        composeRefundOrderVo.setOrderCode(order.getOrderCode());
        composeRefundOrderVo.setOrderSts(order.getState());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(order.getSiglePrice() + "元");
        stringBuffer.append("×" + order.getAmount() + "人");
        composeRefundOrderVo.setPrice(String.valueOf(order.getSiglePrice()));
        composeRefundOrderVo.setAmount(order.getAmount());
        composeRefundOrderVo.setPrices(String.valueOf(stringBuffer));
        composeRefundOrderVo.setActualPay(String.valueOf(order.getGoodsPrice()));

        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, orderCode);
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

        if (CollectionUtils.isNotEmpty(orderTicketVoList)) {

            List<NoPlayVo> noPlayVoList = handleOrderTicket(orderTicketVoList);

            Map<String, Long> collect1 = noPlayVoList
                    .stream()
                    .map(NoPlayVo::getGoodsName)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            ArrayList<NoPlayVo> collect2 = noPlayVoList.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getGoodsName()))),
                            ArrayList::new));

            collect2.stream().forEach(noPlayVo -> {
                collect1.entrySet().stream().forEach(e -> {
                    if (noPlayVo.getGoodsName().equals(e.getKey())) {
                        noPlayVo.setAmount(e.getValue());
                    }
                });
            });

            if (CollectionUtils.isNotEmpty(refundApplyList)) {
                composeRefundOrderVo.setHandleName(refundApplyList.get(0).getHandleName());
                orderTicketVoList.parallelStream().forEach(orderTicketVo -> {
                    refundApplyList.parallelStream().forEach(refundApply -> {
                        if (orderTicketVo.getId().equals(refundApply.getOrderItemId())) {
                            refundApply.setPlaysName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
                        }
                    });
                });

                List<Integer> refundApplyIdList = refundApplyList.stream().map(refundApply -> refundApply.getId()).collect(Collectors.toList());
                List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundExtend(refundApplyIdList);
                List<RefundVo> refundVoList = new ArrayList<>();
                refundApplyList.stream().forEach(refundApply -> {
                    orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                        if (refundApply.getId().equals(orderRefundExtend.getOrderRefundId())) {
                            RefundVo refundVo = new RefundVo();
                            refundVo.setPrice(orderRefundExtend.getRefundPrice());
                            refundVo.setRefundTime(refundApply.getApplyTime());
                            refundVo.setHandleName(refundApply.getHandleName());
                            refundVo.setReturnMoneySts(refundApply.getReturnMoneySts());
                            refundVo.setRefundNum(orderRefundExtend.getRefundNum());
                            refundVo.setPlayName(orderRefundExtend.getProjectName() + "-" + orderRefundExtend.getGoodsName());
                            refundVo.setTotalPrice(String.valueOf(refundApply.getRefundAmount()));
                            refundVo.setAmount(orderRefundExtend.getRefundAmount());
                            refundVo.setRefundId(orderRefundExtend.getOrderRefundId());
                            refundVo.setApprovalsSts(refundApply.getApprovalsSts());
                            refundVoList.add(refundVo);
                        }
                    });
                });

                Map<Integer, List<RefundVo>> collect = refundVoList.stream().collect(Collectors.groupingBy(RefundVo::getRefundId));
                List<RefundDetailVo> collect3 = collect.entrySet().stream().map(entry -> {
                    RefundDetailVo refundDetailVo = new RefundDetailVo();
                    refundDetailVo.setRefundVoList(entry.getValue());
                    refundDetailVo.setRefundTime(entry.getValue().get(0).getRefundTime());
                    refundDetailVo.setRefundNum(entry.getValue().get(0).getRefundNum());
                    refundDetailVo.setHandleName(entry.getValue().get(0).getHandleName());
                    refundDetailVo.setReturnMoneySts(entry.getValue().get(0).getReturnMoneySts());
                    refundDetailVo.setTotalPrice(refundVoList.stream().filter(r -> r.getRefundId().equals(entry.getKey())).collect(Collectors.toList()).get(0).getTotalPrice());
                    refundDetailVo.setApprovalsSts(entry.getValue().get(0).getApprovalsSts());
                    return refundDetailVo;
                }).collect(Collectors.toList());

                composeRefundOrderVo.setRefundDetailVoList(collect3.stream().filter(c -> c.getReturnMoneySts().equals(2)).collect(Collectors.toList()));
            }

            composeRefundOrderVo.setNoPlayVoList(collect2);
            return composeRefundOrderVo;
        }

        return composeRefundOrderVo;
    }


    private List<NoPlayVo> handleOrderTicket(List<OrderTicketVo> orderTicketVoList) {
        List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(1)).collect(Collectors.toList());
        List<NoPlayVo> noPlayVoList = collect.parallelStream().map(orderTicketVo -> {
            NoPlayVo noPlayVo = new NoPlayVo();
            noPlayVo.setId(orderTicketVo.getId());
            noPlayVo.setItemName(orderTicketVo.getProjectName());
            noPlayVo.setGoodsName(orderTicketVo.getGoodsName());
            noPlayVo.setPlayName(orderTicketVo.getProjectName() + "-" + orderTicketVo.getGoodsName());
            noPlayVo.setPrice(orderTicketVo.getSinglePrice());
            noPlayVo.setGoodsId(orderTicketVo.getGoodsId());
            noPlayVo.setCouponPrice(orderTicketVo.getCouponPrice());
            String ticketSerialBbd = orderTicketVo.getTicketSerialBbd();
            if (ticketSerialBbd != "") {
                noPlayVo.setTicketSerialBbd(orderTicketVo.getTicketSerialBbd());
            }
            Integer ticketStateBbd = orderTicketVo.getTicketStateBbd();
            if (ticketStateBbd != null) {
                noPlayVo.setTicketStateBbd(orderTicketVo.getTicketStateBbd());
            }
            return noPlayVo;
        }).collect(Collectors.toList());
        return noPlayVoList;
    }

    @Override
    public List<OrderRefundVo> refundOrderDetail(String orderCode) {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getOrderId, orderCode);
        List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);
        if (CollectionUtils.isNotEmpty(refundApplyList)) {
            List<Integer> refundApplyIdList = refundApplyList.stream().map(refundApply -> refundApply.getId()).collect(Collectors.toList());
            List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundExtend(refundApplyIdList);
            List<RefundVo> refundVoList = new ArrayList<>();
            refundApplyList.stream().forEach(refundApply -> {
                orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                    if (refundApply.getId().equals(orderRefundExtend.getOrderRefundId())) {
                        RefundVo refundVo = new RefundVo();
                        refundVo.setPrice(orderRefundExtend.getRefundPrice());
                        refundVo.setPlayName(orderRefundExtend.getProjectName() + "-" + orderRefundExtend.getGoodsName());
                        refundVo.setAmount(orderRefundExtend.getRefundAmount());
                        refundVo.setRefundNum(orderRefundExtend.getRefundNum());
                        refundApply.setRefundNum(orderRefundExtend.getRefundNum());
                        refundVoList.add(refundVo);
                    }
                });
            });
            Order order = orderDao.findComposeOrderDetail(orderCode);
            List<OrderRefundVo> orderRefundVoList = refundApplyList.stream().map(refundApply -> {
                OrderRefundVo orderRefundVo = new OrderRefundVo();
                orderRefundVo.setAmount(refundApply.getGoodsNum());
                orderRefundVo.setApplyTime(refundApply.getApplyTime());
                orderRefundVo.setBuyerMsgType(refundApply.getBuyerMsgType());
                orderRefundVo.setBuyerMsg(refundApply.getBuyerMsg());
                orderRefundVo.setHandleName(refundApply.getHandleName());
                orderRefundVo.setApprovalsSts(refundApply.getApprovalsSts());
                orderRefundVo.setName(order.getName());
                orderRefundVo.setPhone(order.getPhone());
                orderRefundVo.setPlayName(order.getGoodsName());
                orderRefundVo.setPrice(String.valueOf(refundApply.getRefundAmount()));
                orderRefundVo.setRefundSts(refundApply.getReturnMoneySts());
                orderRefundVo.setRefundNum(refundApply.getRefundNum());
                orderRefundVo.setCouponPrice(String.valueOf(refundApply.getCouponPrice()));
                return orderRefundVo;
            }).collect(Collectors.toList());
            Map<String, List<RefundVo>> refundVoMap = refundVoList.stream().collect(Collectors.groupingBy(RefundVo::getRefundNum));
            refundVoMap.entrySet().stream().forEach(entry -> {
                orderRefundVoList.stream().forEach(orderRefundVo -> {
                    if (entry.getKey().equals(orderRefundVo.getRefundNum())) {
                        orderRefundVo.setRefundVoList(entry.getValue());
                    }
                });
            });
            return orderRefundVoList;
        }
        return null;
    }

    @Override
    public PageInfo<RefundOrderPc> findRefundListPc(RefundReqParam refundReqParam) {
        return new PageInfo<>(refundApplyMapper.findRefundListPc(refundReqParam));
    }

    @Override
    public RefundDetailPcVo findRefundDetailPc(String refundNum) {
        List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundByRefundNum(refundNum);
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getId, orderRefundExtendList.get(0).getOrderRefundId());
        RefundApply refundApply = refundApplyMapper.selectOne(refundApplyQueryWrapper);
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApply.getOrderId());

        if (CollectionUtils.isNotEmpty(orderRefundExtendList)) {
            List<RefundVo> refundVoList = new ArrayList<>();
            orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                RefundVo refundVo = new RefundVo();
                refundVo.setRefundNum(orderRefundExtend.getRefundNum());
                refundVo.setAmount(orderRefundExtend.getRefundAmount());
                refundVo.setTotalPrice(orderRefundExtend.getRefundPrice());
                refundVo.setPlayName(orderRefundExtend.getGoodsName());
                refundVo.setId(orderRefundExtend.getId());
                refundVo.setGoodsId(orderRefundExtend.getGoodsId());
                orderTicketVoList.stream().forEach(orderTicketVo -> {
                    if (orderRefundExtend.getGoodsId().equals(orderTicketVo.getGoodsId())) {
                        refundVo.setPrice(orderTicketVo.getSinglePrice());
                    }
                });
                refundVoList.add(refundVo);

            });
            if (CollectionUtils.isNotEmpty(refundVoList)) {
                List<Double> refundTicketPrice = refundVoList.stream().map(refundVo1 -> {
                    double sum = 0.0;
                    DoubleSummaryStatistics collect = orderTicketVoList.stream().filter(orderTicketVo -> refundVo1.getGoodsId().equals(orderTicketVo.getGoodsId())).limit(refundVo1.getAmount()).collect(Collectors.summarizingDouble(OrderTicketVo::getSinglePrices));
                    sum = sum + collect.getSum();
                    return sum;
                }).collect(Collectors.toList());

                return RefundDetailPcVo.builder().applyTime(refundApply.getApplyTime())
                        .buyerMsg(refundApply.getBuyerMsg())
                        .handleName(refundApply.getHandleName())
                        .orderCode(refundApply.getOrderId())
                        .refundAmount(String.valueOf(refundApply.getRefundAmount()))
                        .refundVoList(refundVoList)
                        .refundNum(refundVoList.get(0).getRefundNum())
                        .id(refundApply.getId())
                        .approvalsSts(refundApply.getApprovalsSts())
                        .buyerMsgType(refundApply.getBuyerMsgType())
                        .refundOrderAmount(String.valueOf(refundTicketPrice.stream().mapToDouble((s) -> s).sum()))
                        .build();
            }

            return RefundDetailPcVo.builder().applyTime(refundApply.getApplyTime())
                    .buyerMsg(refundApply.getBuyerMsg())
                    .handleName(refundApply.getHandleName())
                    .orderCode(refundApply.getOrderId())
                    .refundAmount(String.valueOf(refundApply.getRefundAmount()))
                    .refundVoList(refundVoList)
                    .refundNum(refundVoList.get(0).getRefundNum())
                    .id(refundApply.getId())
                    .approvalsSts(refundApply.getApprovalsSts())
                    .buyerMsgType(refundApply.getBuyerMsgType())
                    .build();
        }
        return null;
    }

    @Override
    public long batchRefund(String[] id) {
        return refundApplyMapper.batchRefund(id);
    }

    @Override
    public long updateApprovalsSts(UpdateApprovalsParam updateApprovalsParam) {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getId, updateApprovalsParam.getId());
        RefundApply refundApply = new RefundApply();
        refundApply.setId(updateApprovalsParam.getId());
        refundApply.setNoPassReason(updateApprovalsParam.getNoPassReason());
        refundApply.setApprovalsSts(updateApprovalsParam.getApprovalsSts());
        refundApply.setApprovalsTime(new Date());
        RefundApply refundApply1 = refundApplyMapper.selectOne(refundApplyQueryWrapper);
        Order order = orderDao.findComposeOrderDetail(refundApply1.getOrderId());
        log.info("order message " + order);
        switch (updateApprovalsParam.getApprovalsSts()) {
            case 1:
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "待审批订单不可操作");
            case 2:
                refundApplyMapper.update(refundApply, refundApplyQueryWrapper);
                QueryWrapper<RefundApply> refundApplyQuery = new QueryWrapper<>();
                refundApplyQuery.lambda().eq(RefundApply::getOrderId, refundApply1.getOrderId());
                List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQuery);
                Integer refundTicketAmount = refundApplyList.stream().filter(refundApply2 -> refundApply2.getApprovalsSts().equals(2) && refundApply2.getGoodsNum() != null).collect(Collectors.summingInt(RefundApply::getGoodsNum));
                List<OrderTicketVo> orderTicketVoLists = orderDao.findComposeOrderInfo(refundApply1.getOrderId());
                Integer totalTicketAmount = orderTicketVoLists.size();
                if (totalTicketAmount.equals(refundTicketAmount)) {
                    orderDao.retreatAppOrders(order.getOrderCode());
                }
                return GeneConstant.INT_1;
            case 3:
                List<OrderRefundExtend> orderRefundExtendList = refundApplyMapper.findOrderRefundExtends(refundApply1.getId());
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApply1.getOrderId());
                if (order.getCouponPrice() != null) {
                    List<Integer> itemIdList = Arrays.asList(refundApply1.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                    itemIdList.stream().forEach(itemId -> {
                        refundApplyMapper.updateOrderTicketById(itemId);
                    });
                } else {
                    orderRefundExtendList.stream().forEach(orderRefundExtend -> {
                        List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getRefundSts().equals(2) && orderRefundExtend.getGoodsId().equals(orderTicketVo.getGoodsId())).limit(orderRefundExtend.getRefundAmount()).collect(Collectors.toList());
                        collect.parallelStream().forEach(orderTicketVo -> {
                            refundApplyMapper.updateOrderTicketById(orderTicketVo.getId());
                        });
                    });
                }
                refundApply.setReturnMoneySts(-1);
                return refundApplyMapper.update(refundApply, refundApplyQueryWrapper);
            default:
                return GeneConstant.INT_0;
        }
    }


    @Override
    public long unifyUpdateApprovalsSts(UpdateApprovalsParam updateApprovalsParam) {
        QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
        refundApplyQueryWrapper.lambda().eq(RefundApply::getId, updateApprovalsParam.getId());
        RefundApply refundApply = new RefundApply();
        refundApply.setId(updateApprovalsParam.getId());
        refundApply.setNoPassReason(updateApprovalsParam.getNoPassReason());
        refundApply.setApprovalsSts(updateApprovalsParam.getApprovalsSts());
        refundApply.setApprovalsTime(new Date());
        RefundApply refundApply1 = refundApplyMapper.selectOne(refundApplyQueryWrapper);
        Order order = orderDao.findComposeOrderDetail(refundApply1.getOrderId());
        log.info("order message " + order);
        switch (updateApprovalsParam.getApprovalsSts()) {
            case 1:
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "待审批订单不可操作");
            case 2:
                refundApplyMapper.update(refundApply, refundApplyQueryWrapper);
                QueryWrapper<RefundApply> refundApplyQuery = new QueryWrapper<>();
                refundApplyQuery.lambda().eq(RefundApply::getOrderId, refundApply1.getOrderId());
                List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQuery);
                Integer refundTicketAmount = refundApplyList.stream().filter(refundApply2 -> refundApply2.getApprovalsSts().equals(2) && refundApply2.getGoodsNum() != null).collect(Collectors.summingInt(RefundApply::getGoodsNum));
                List<OrderTicketVo> orderTicketVoLists = orderDao.findComposeOrderInfo(refundApply1.getOrderId());
                Integer totalTicketAmount = orderTicketVoLists.size();
                if (totalTicketAmount.equals(refundTicketAmount)) {
                    orderDao.retreatAppOrders(order.getOrderCode());
                }
                handleBBDRefundOrder(refundApply1.getOrderItemId(),orderTicketVoLists,2);
                return GeneConstant.INT_1;
            case 3:
                List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(refundApply1.getOrderId());
                List<Integer> itemIdList = Arrays.asList(refundApply1.getOrderItemId().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
                itemIdList.parallelStream().forEach(itemId -> {
                    refundApplyMapper.updateOrderTicketById(itemId);
                });
                refundApply.setReturnMoneySts(-1);
                handleBBDRefundOrder(refundApply1.getOrderItemId(),orderTicketVoList,3);
                return refundApplyMapper.update(refundApply, refundApplyQueryWrapper);
            default:
                return GeneConstant.INT_0;
        }
    }

    public void handleBBDRefundOrder(String orderItemId,List<OrderTicketVo> orderTicketVoList,Integer approvalSts){
        try {
            List<Integer> itemIdList = Arrays.asList(orderItemId.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            if  (CollectionUtils.isNotEmpty(itemIdList)){
                List<OrderTicketVo> orderTicketVos = new ArrayList<>();
                itemIdList.stream().forEach(item ->{
                    orderTicketVoList.stream().forEach(orderTicketVo -> {
                        if (item.equals(orderTicketVo.getId())){
                            orderTicketVos.add(orderTicketVo);
                        }
                    });
                });
                if (CollectionUtils.isNotEmpty(orderTicketVos)){
                    List<String> ticketIds = orderTicketVos.stream().map(orderTicketVo -> orderTicketVo.getTicketIdBbd()).collect(Collectors.toList());
                    UpdateSeaTicketApprovalStatusDTO updateSeaTicketApprovalStatusDTO = new UpdateSeaTicketApprovalStatusDTO();
                    updateSeaTicketApprovalStatusDTO.setTicketIds(ticketIds);
                    updateSeaTicketApprovalStatusDTO.setApprovalStatus(approvalSts);
                    log.info("调用百邦达票更新审批状态 start "+updateSeaTicketApprovalStatusDTO);
                    BaiBangDaHttpApiUtil.approvalsRefundSts(updateSeaTicketApprovalStatusDTO);
                    log.info("调用百邦达票更新审批状态 end ");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public long sendRefund(String refundNum) {
        boolean isSend = withdrawalService.sendRefundNoticeSMS(refundNum);
        if (isSend) {
            return 1;
        }
        return 0;
    }

    @Override
    public RefundBoatOrderVo refundBoatOrderDetail(String orderCode, Long userId) {
        RefundBoatOrderVo refundBoatOrderVo = new RefundBoatOrderVo();
        List<H5OrderVo> h5OrderInfo = orderService.findH5OrderInfo(orderCode, userId);
        h5OrderInfo.stream().forEach(h5OrderVo -> {
            ShipLineInfo shipLineInfo = JSONObject.parseObject(h5OrderVo.getShipLineInfo(), ShipLineInfo.class);
            refundBoatOrderVo.setLineFrom(h5OrderVo.getLineFrom() + shipLineInfo.getFromInfo());
            refundBoatOrderVo.setLineTo(h5OrderVo.getLineTo() + shipLineInfo.getArriveInfo());
            refundBoatOrderVo.setAfterTime(shipLineInfo.getAfterTime());
            refundBoatOrderVo.setBoatStartTime(shipLineInfo.getStartTime());
            refundBoatOrderVo.setBoatEndTime(shipLineInfo.getArriveTime());
            refundBoatOrderVo.setLineEndDate(h5OrderVo.getLineDate());
            refundBoatOrderVo.setTicketOrderCode(h5OrderVo.getTicketOrderCode());
            int i = shipLineInfo.getStartTime().compareTo(shipLineInfo.getArriveTime());
            if (i >= 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(h5OrderVo.getLineDate());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                refundBoatOrderVo.setLineEndDate(calendar.getTime());
                refundBoatOrderVo.setLineDate(h5OrderVo.getLineDate());
            }
        });
        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(orderCode);
        List<OrderTicketVo> collect = orderTicketVoList.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus().equals(3)).collect(Collectors.toList());
        List<PassengerVo> passengerVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(collect)) {
            collect.stream().forEach(orderTicketVo -> {
                ShipBaseVo<RefundPriceVo> shipBaseVo = LalyoubaShipHttpApiUtil.getRefundPrice(profiles, h5OrderInfo.get(0).getTicketOrderCode(), orderTicketVo.getTicketId(), 2);
                if (shipBaseVo != null && shipBaseVo.getStatus() == 1) {
                    passengerVoList.add(PassengerVo.builder().passengerName(orderTicketVo.getTicketUserName())
                            .babyInfo(orderTicketVo.getBabyInfo())
                            .idCard(orderTicketVo.getIdCard())
                            .phone(orderTicketVo.getPhone())
                            .seatNumber(orderTicketVo.getSeatNumber())
                            .shipTicketStatus(orderTicketVo.getShipTicketStatus())
                            .ticketType(orderTicketVo.getTicketType())
                            .price(shipBaseVo.getData().getTotalAmount())
                            .refundPrice(shipBaseVo.getData().getTotalBack())
                            .fee(shipBaseVo.getData().getTotalBackRate())
                            .ticketId(orderTicketVo.getTicketId())
                            .build());
                } else {
                    passengerVoList.add(PassengerVo.builder().passengerName(orderTicketVo.getTicketUserName())
                            .babyInfo(orderTicketVo.getBabyInfo())
                            .idCard(orderTicketVo.getIdCard())
                            .phone(orderTicketVo.getPhone())
                            .seatNumber(orderTicketVo.getSeatNumber())
                            .shipTicketStatus(orderTicketVo.getShipTicketStatus())
                            .ticketType(orderTicketVo.getTicketType())
                            .ticketId(orderTicketVo.getTicketId())
                            .build());
                }
            });
        }
        refundBoatOrderVo.setPassengerVoList(passengerVoList);
        return refundBoatOrderVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long refundApplyBoat(BoatRefundApplyParam boatRefundApplyParam, Long userId) {
        List<H5OrderVo> h5OrderInfo = orderService.findH5OrderInfo(boatRefundApplyParam.getOrderCode(), userId);
        List<String> ticketList = Arrays.asList(boatRefundApplyParam.getTicketId().split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());

        if (h5OrderInfo.get(0).getPassengerVoList().size() == ticketList.size()) {
            ShipBaseVo shipBaseVo = LalyoubaShipHttpApiUtil.refundTicket(profiles, 1, 0, h5OrderInfo.get(0).getTicketOrderCode(), boatRefundApplyParam.getReason());
            if (shipBaseVo.getStatus() == 1) {
                ShipBaseVo<OrderDetailVo> orderDetail = LalyoubaShipHttpApiUtil.orderDetail(profiles, h5OrderInfo.get(0).getTicketOrderCode(), boatRefundApplyParam.getOrderCode());
                if (orderDetail != null) {
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(boatRefundApplyParam.getOrderCode());
                    orderDetail.getData().getOinfo().stream().forEach(oinfoVo -> {
                        orderTicketVoList.stream().forEach(orderTicketVo -> {
                            if (Integer.valueOf(oinfoVo.getTkdtID()).equals(orderTicketVo.getTicketId())) {
                                refundApplyMapper.updateBoatOrderSts(String.valueOf(orderTicketVo.getTicketId()), 2, String.valueOf(oinfoVo.getBackMoney()), oinfoVo.getRatio());
                            }
                        });
                    });
                }
                log.info("lai you 8 message " + JSON.toJSONString(orderDetail));
            }
            refundSendMessage(boatRefundApplyParam, ticketList, h5OrderInfo, userId);
            log.info("lai you 8 message" + shipBaseVo.getMessage());
        } else {
            ticketList.stream().forEach(ticketIds -> {
                ShipBaseVo<RefundPriceVo> shipBaseVos = LalyoubaShipHttpApiUtil.getRefundPrice(profiles, h5OrderInfo.get(0).getTicketOrderCode(), Integer.valueOf(ticketIds), 2);
                String fee = shipBaseVos.getData().getTotalBackRate();
                String refundPrice = shipBaseVos.getData().getTotalBack();
                ShipBaseVo shipBaseVo = LalyoubaShipHttpApiUtil.refundTicket(profiles, 2, Integer.valueOf(ticketIds), h5OrderInfo.get(0).getTicketOrderCode(), boatRefundApplyParam.getReason());
                if (shipBaseVo.getStatus() == 1) {
                    List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(boatRefundApplyParam.getOrderCode());
                    orderTicketVoList.stream().forEach(orderTicketVo -> {
                        if (Integer.valueOf(orderTicketVo.getTicketId()).equals(Integer.valueOf(ticketIds))) {
                            refundApplyMapper.updateBoatOrderSts(ticketIds, 2, refundPrice, fee);
                        }
                    });
                }
                log.info("lai you 8 message" + shipBaseVo.getMessage());
            });
            refundSendMessage(boatRefundApplyParam, ticketList, h5OrderInfo, userId);
        }

        List<OrderTicketVo> orderTicketVoLists = orderDao.findComposeOrderInfo(boatRefundApplyParam.getOrderCode());
        List<OrderTicketVo> collect = orderTicketVoLists.stream().filter(orderTicketVo -> orderTicketVo.getShipTicketStatus()!=null && orderTicketVo.getShipTicketStatus().equals(3)).collect(Collectors.toList());
        Integer refundTotalTicketAmount = collect.size();

        if (refundTotalTicketAmount.equals(orderTicketVoLists.size())) {
            orderDao.retreatAppOrders(boatRefundApplyParam.getOrderCode());
        }
        return 1;
    }


    public void refundSendMessage(BoatRefundApplyParam boatRefundApplyParam, List<String> ticketList, List<H5OrderVo> h5OrderInfo, Long userId) {
        log.info("refund send messages start");

        OrderQueryBean orderQueryBean = new OrderQueryBean();
        orderQueryBean.setScenicId(Long.valueOf(companyId));
        orderQueryBean.setUserId(userId);
        List<H5OrderVo> h5AllOrderList = orderDao.findH5AllOrderList(orderQueryBean);
        List<H5OrderVo> h5OrderVos = h5AllOrderList.stream().filter(h5OrderVo -> h5OrderVo.getOrderCode().equals(boatRefundApplyParam.getOrderCode())).collect(Collectors.toList());


        h5OrderVos.stream().forEach(h5OrderVo -> {
            ShipLineInfo shipLineInfo = JSONObject.parseObject(h5OrderVo.getShipLineInfo(), ShipLineInfo.class);
            h5OrderVo.setLineFrom(h5OrderVo.getLineFrom());
            h5OrderVo.setLineTo(h5OrderVo.getLineTo());
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
        });


        List<OrderTicketVo> orderTicketVoList = orderDao.findComposeOrderInfo(boatRefundApplyParam.getOrderCode());
        List<OrderTicketVo> ticketVoList = new ArrayList<>();
        orderTicketVoList.stream().forEach(orderTicketVo -> {
            ticketList.stream().forEach(tickets -> {
                if (orderTicketVo.getTicketId().equals(Integer.valueOf(tickets))) {
                    ticketVoList.add(orderTicketVo);
                }
            });
        });
        double refundSum = ticketVoList.stream().mapToDouble(OrderTicketVo::getRefund).sum();

        double singlePrices = ticketVoList.stream().mapToDouble(OrderTicketVo::getSinglePrices).sum();

        double feePrice = singlePrices - refundSum;

        BigDecimal bigDecimal = new BigDecimal(feePrice);
        double fee = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        Map<String, String> map = new HashMap<>();
        map.put("companyId", "11");
        map.put("phone", h5OrderInfo.get(0).getPhone());
        if (h5OrderVos.get(0).getLineTo().contains("涠洲") || h5OrderVos.get(0).getLineFrom().contains("涠洲")) {
            map.put("content", GeneConstant.MESSAGE_SIGN_SHIP + String.format(GeneConstant.REFUND_MESSAGE,
                    h5OrderVos.get(0).getLineFrom() + "-" + h5OrderVos.get(0).getLineTo(),
                    h5OrderVos.get(0).getTimespan(),
                    String.valueOf(ticketList.size()),
                    h5OrderVos.get(0).getTicketOrderCode(),
                    String.valueOf(refundSum),
                    String.valueOf(fee)));
        } else {
            map.put("content", GeneConstant.MESSAGE_SIGN_SHIP + String.format(GeneConstant.REFUND_MESSAGES,
                    h5OrderVos.get(0).getLineFrom() + "-" + h5OrderVos.get(0).getLineTo(),
                    h5OrderVos.get(0).getTimespan(),
                    String.valueOf(ticketList.size()),
                    h5OrderVos.get(0).getTicketOrderCode(),
                    String.valueOf(refundSum),
                    String.valueOf(fee)));
        }

        messageSender.sendSmsV3(map);
        log.info("refund send messages end");
    }
}
