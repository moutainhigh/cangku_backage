package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.bo.UserOfCouponBo;
import cn.enn.wise.platform.mall.bean.param.AppCouParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.GoodsCouponMapper;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.UserOfCouponMapper;
import cn.enn.wise.platform.mall.service.NxjCouponService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-04-01 10:17
 **/
@Slf4j
@Service
public class NxjCouponServiceImpl implements NxjCouponService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserOfCouponMapper userOfCouponMapper;


    @Autowired
    private GoodsCouponMapper goodsCouponMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Override
    public OrderCouInfo getOrderCouInfo(String orderCode) {
        log.info("==============>订单详情"+orderCode);
        return orderDao.getOrderCouInfo(orderCode);
    }

    @Override
    public Boolean orderCan(String orderCode, Long businessId) {
        log.info("==============>开始核销"+orderCode);
        OrderCouInfo info = orderDao.getOrderCouInfo(orderCode);
        if(null == info){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"请输入有效编号");
        }
        if(info.getState() == 2 && info.getPayType() == 2){
            log.info("==============>核销该订单"+info);
            orderDao.updateOrderTicketStatusByOrderId(info.getId());
            orderDao.updateOrderSts(info.getOrderCode(),3);
            orderDao.updateUserCouId(info.getCouId(),1,businessId,info.getCouponPrice());
            return true;
        }
        return false;
    }

    @Override
    public Boolean couCan(Long businessId,Long id,Double couponPrice,String name) {
        UserOfCouponBo user = userOfCouponMapper.selectById(id);
        if(null == user){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"请输入有效优惠卷");
        }
        Double price = userOfCouponMapper.getPrice(user.getGoodsCouponId());
        if(price < couponPrice){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"不能超过面额");
        }
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if(user.getValidityTime().getTime() < nowTime.getTime()){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"优惠卷已过期");
        }
        List<SysStaff> listBusinesss = remoteServiceUtil.getListBusinesss();
        if(null == listBusinesss){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商家信息有误");
        }
        GoodsCouponBo goodsCouponBo = goodsCouponMapper.selectById(user.getGoodsCouponId());
        if(null == goodsCouponBo){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"请输入有效优惠卷");
        }
        //if(goodsCouponBo.getKind() == 1){
        //    throw new BusinessException(GeneConstant.BUSINESS_ERROR,"门票优惠卷不支持线下核销");
        //}
        List<SysStaff> list = listBusinesss;
        for (SysStaff sysStaff:list) {
            if(businessId.equals(sysStaff.getId())){
                if(sysStaff.getBusinessType().equals(goodsCouponBo.getKind())){
                    user.setStatus(GeneConstant.BYTE_2);
                    user.setOrderResource(2);
                    user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    user.setCouponPrice(couponPrice);
                    user.setBusinessId(businessId);
                    user.setBusinessName(name);
                    user.setEnterTime(new Timestamp(System.currentTimeMillis()));
                    userOfCouponMapper.updateById(user);
                    return true;
                }
            }
        }
        throw new BusinessException(GeneConstant.BUSINESS_ERROR,"此商家不能使用该优惠券");
    }

    @Override
    public CouInfo getCouInfo(Long id,Long userId) {
        CouInfo couInfo = new CouInfo();
        UserOfCouponBo user = userOfCouponMapper.selectById(id);
        if(null == user){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"此优惠卷不存在");
        }
        GoodsCouponBo goodsCouponBo = goodsCouponMapper.selectById(user.getGoodsCouponId());
        if(null == goodsCouponBo){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"请输入有效优惠卷");
        }
        List<SysStaff> listBusinesss = remoteServiceUtil.getListBusinesss();
        if(null == listBusinesss){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"商家信息有误");
        }
        for (SysStaff sysStaff:listBusinesss) {
            if(userId.equals(sysStaff.getId())){
                if(sysStaff.getBusinessType().equals(goodsCouponBo.getKind())){
                    couInfo.setId(user.getId());
                    couInfo.setCouponPrice(user.getCouponPrice());
                    couInfo.setStatus(user.getStatus().toString());
                    couInfo.setEnterTime(user.getEnterTime());
                    Double price = userOfCouponMapper.getPrice(user.getGoodsCouponId());
                    couInfo.setPrice(price);
                    return couInfo;
                }
            }
        }
        throw new BusinessException(GeneConstant.BUSINESS_ERROR,"此商家不能使用该优惠券");
    }

    @Override
    public PageInfo<OrderCouVo> list(AppCouParam appCouParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrderCouVo> list = orderDao.listCou(appCouParam);
        PageInfo<OrderCouVo> pageInfo = new PageInfo<OrderCouVo>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<OrderCouInfo> couList(Long businessId,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrderCouInfo> list = orderDao.getOrderCouInfoList(businessId);
        PageInfo<OrderCouInfo> pageInfo = new PageInfo<OrderCouInfo>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<CouInfo> downList(Long businessId,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CouInfo> list = userOfCouponMapper.getCouInfoList(businessId);
        PageInfo<CouInfo> pageInfo = new PageInfo<CouInfo>(list);
        return pageInfo;
    }

    @Override
    public CouCountInfo count(Long businessId,Integer pageNum, Integer pageSize, String time) {
        CouCountInfo couCountInfo = new CouCountInfo();
        String count = userOfCouponMapper.count(time,businessId);
        log.info("=========>核销总券数"+count);
        Double totalPrice = userOfCouponMapper.totalPrice(time,businessId);
        log.info("=========>核销总额"+totalPrice);
        //Double upPrice = userOfCouponMapper.upPrice(time);
        Double downPrice = userOfCouponMapper.downPrice(time,businessId);
        //Double kouPrice = upPrice+downPrice;
        log.info("=========>抵扣总额"+downPrice);
        List<CouVo> list1 = userOfCouponMapper.finPrice(time,businessId);
        Double finPrice = 0.00;
        if(null != list1 && list1.size()>0){
            //优惠券种类 1.门票券 2.民宿券 3.餐饮券
            for (CouVo couVo : list1) {
                if (couVo.getKind() == 1){
                    finPrice += couVo.getCouponPrice()*0.35;
                }else if (couVo.getKind() == 2){
                    finPrice += couVo.getCouponPrice()*0.4;
                }else if (couVo.getKind() == 3){
                    finPrice += couVo.getCouponPrice()*0.6;
                }
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CouVo> list = userOfCouponMapper.finPrice(time,businessId);
        if(null != list && list.size()>0){
            //优惠券种类 1.门票券 2.民宿券 3.餐饮券
            for (CouVo couVo : list) {
                if (couVo.getKind() == 1){
                    couVo.setFinPrice(couVo.getCouponPrice()*0.35);
                }else if (couVo.getKind() == 2){
                    couVo.setFinPrice(couVo.getCouponPrice()*0.4);
                }else if (couVo.getKind() == 3){
                    couVo.setFinPrice(couVo.getCouponPrice()*0.6);
                }
            }
        }
        PageInfo<CouVo> pageInfo = new PageInfo<CouVo>(list);
        couCountInfo.setPageInfo(pageInfo);
        couCountInfo.setNum(Long.valueOf(count));
        couCountInfo.setTotalPrice(totalPrice);
        couCountInfo.setKouPrice(downPrice);
        couCountInfo.setFinPrice(finPrice);
        return couCountInfo;
    }
}
