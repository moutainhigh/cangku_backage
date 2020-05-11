package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.UserOfCouponsListParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserOfCouponsSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.consts.CouponEnum;
import cn.enn.wise.ssop.service.promotions.mapper.UserOfCouponsMapper;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Service
public class UserOfCouponsService extends ServiceImpl<UserOfCouponsMapper, UserOfCoupons> {


    @Autowired
    private CouponFullReduceRuleService couponFullReduceRuleService;
    @Autowired
    private CouponCashRuleService couponCashRuleService;
    @Autowired
    private CouponExperienceRuleService couponExperienceRuleService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponGoodsService couponGoodsService;

    /**
     * 券消费列表
     * @param userOfCouponsListParam
     * @return
     */
    public UserOfCouponDTO getListByPage(UserOfCouponsListParam userOfCouponsListParam){
        UserOfCouponDTO userOfCouponDTO = new UserOfCouponDTO();
        Long couponId = userOfCouponsListParam.getCouponId();
        Byte couponType = userOfCouponsListParam.getCouponType();
        Long couponCode = userOfCouponsListParam.getCouponCode();
        Date grantTimeStart = userOfCouponsListParam.getGrantTimeStart();
        Date grantTimeEnd = userOfCouponsListParam.getGrantTimeEnd();
        Date receiveTimeStart = userOfCouponsListParam.getReceiveTimeStart();
        Date receiveTimeEnd = userOfCouponsListParam.getReceiveTimeEnd();
        Long userId = userOfCouponsListParam.getUserId();
        Long activityBaseId = userOfCouponsListParam.getActivityBaseId();
        String userName = userOfCouponsListParam.getUserName();
        Byte state = userOfCouponsListParam.getState();
        LambdaQueryWrapper<UserOfCoupons> wrapper = new LambdaQueryWrapper<>();
        if(couponId != null){
            wrapper.eq(UserOfCoupons::getCouponId,couponId);
        }
        if(couponType != null){
            wrapper.eq(UserOfCoupons::getCouponType,couponType);
        }
        if(couponCode != null){
            wrapper.like(UserOfCoupons::getCouponCode,couponCode);
        }
        if(grantTimeStart != null && grantTimeEnd != null){
            wrapper.ge(UserOfCoupons::getGrantTime,grantTimeStart);
            wrapper.le(UserOfCoupons::getGrantTime,grantTimeEnd);
        }
        if(receiveTimeStart != null && receiveTimeEnd != null){
            wrapper.ge(UserOfCoupons::getReceiveTime,receiveTimeStart);
            wrapper.le(UserOfCoupons::getReceiveTime,receiveTimeEnd);
        }
        if(userId != null){
            wrapper.eq(UserOfCoupons::getUserId,userId);
        }
        if(StringUtils.isNotBlank(userName)){
            wrapper.eq(UserOfCoupons::getUserName,userName);
        }
        if(state != null){
            wrapper.eq(UserOfCoupons::getState,state);
        }
        if(activityBaseId != null){
            wrapper.eq(UserOfCoupons::getActivityBaseId,activityBaseId);
        }
        wrapper.orderByDesc(UserOfCoupons::getId);
        IPage<UserOfCoupons> userOfCouponsIPage = page(new QueryPage<>(userOfCouponsListParam),wrapper);
        QueryData<UserOfCouponsListDTO> queryData = new QueryData<>(userOfCouponsIPage,this::toUserOfCouponsListDTO);
        userOfCouponDTO.setQueryData(queryData);
        // 统计信息
        List<UserOfCoupons> userOfCouponsList = list(wrapper);
        UserOfCouponsCountDTO userOfCouponsCountDTO = getCount(userOfCouponsList);
        userOfCouponDTO.setUserOfCouponsCountDTO(userOfCouponsCountDTO);
        return userOfCouponDTO;
    }

    private UserOfCouponsListDTO toUserOfCouponsListDTO(UserOfCoupons userOfCoupons) {
        UserOfCouponsListDTO dto = new UserOfCouponsListDTO();
        BeanUtils.copyProperties(userOfCoupons,dto,getNullPropertyNames(userOfCoupons));
        Coupon coupon = couponService.getById(userOfCoupons.getCouponId());
        dto.setName(coupon.getName());
        dto.setState(coupon.getState());
        return dto;
    }

    private UserOfCouponsCountDTO getCount(List<UserOfCoupons> userOfCouponsList){
        UserOfCouponsCountDTO userOfCouponsCountDTO = new UserOfCouponsCountDTO();
        AtomicReference<Integer> sendNumber = new AtomicReference<>(0);//发放张数
        Integer sendPrice = 0;//发放金额
        AtomicReference<Integer> receivedNumber = new AtomicReference<>(0);//领取张数
        Integer receivedPrice = 0;//领取金额
        AtomicReference<Integer> useNumber = new AtomicReference<>(0);//使用张数
        AtomicReference<Integer> usePrice = new AtomicReference<Integer>(0);//使用金额
        AtomicReference<Integer> notUseNumber = new AtomicReference<>(0);//待使用张数
        Integer notUsePrice = 0;//待使用金额
        AtomicReference<Integer> overdueNumber = new AtomicReference<>(0); //过期张数
        Integer overduePrice = 0;//过期金额
        //发放状态
        List<Byte> sendStateList = Lists.newArrayList();
        sendStateList.add(CouponEnum.USER_COUPON_STATE_SEND.getValue());
        sendStateList.add(CouponEnum.USER_COUPON_STATE_RECEIVED.getValue());
        sendStateList.add(CouponEnum.USER_COUPON_STATE_USE.getValue());
        sendStateList.add(CouponEnum.USER_COUPON_STATE_OVERDUE.getValue());
        //领取状态
        List<Byte> receivedStateList = Lists.newLinkedList();
        receivedStateList.add(CouponEnum.USER_COUPON_STATE_RECEIVED.getValue());
        receivedStateList.add(CouponEnum.USER_COUPON_STATE_USE.getValue());
        receivedStateList.add(CouponEnum.USER_COUPON_STATE_OVERDUE.getValue());
        userOfCouponsList.forEach(userOfCoupons -> {
            if(sendStateList.contains(userOfCoupons.getState())){
                sendNumber.updateAndGet(v -> v + 1);
            }
            if(receivedStateList.contains(userOfCoupons.getState())){
                receivedNumber.updateAndGet(v -> v + 1);
            }
            if(userOfCoupons.getState().equals(CouponEnum.USER_COUPON_STATE_USE.getValue())){
                useNumber.updateAndGet(v -> v + 1);
                //处理使用金额
                //满减券
                if(userOfCoupons.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
                    //查询满减券金额
                    CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(
                            new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",userOfCoupons.getCouponId()));
                    if(couponFullReduceRule != null){
                        if(couponFullReduceRule.getRebateMethod().equals(CouponEnum.FULL_REDUCE_REBATE_MONEY.getValue())){
                            usePrice.updateAndGet(v -> v + couponFullReduceRule.getRebatePrice());
                        }
                        if(couponFullReduceRule.getRebateMethod().equals(CouponEnum.FULL_REDUCE_REBATE_DISCOUNT.getValue())){
                            usePrice.updateAndGet(v -> v + couponFullReduceRule.getSatisfyPrice() * couponFullReduceRule.getRebatePrice() / 100);
                        }
                    }
                }
                //代金券
                if(userOfCoupons.getCouponType().equals(CouponEnum.CASH.getValue())){
                    //查询代金券金额
                    CouponCashRule couponCashRule = couponCashRuleService.getOne(
                            new QueryWrapper<CouponCashRule>().eq("coupon_id",userOfCoupons.getCouponId()));
                    if(couponCashRule != null){
                        if(couponCashRule.getRebateMethod().equals(CouponEnum.FULL_REDUCE_REBATE_MONEY.getValue())){
                            usePrice.updateAndGet(v -> v + couponCashRule.getRebatePrice());
                        }
                        if(couponCashRule.getRebateMethod().equals(CouponEnum.FULL_REDUCE_REBATE_DISCOUNT.getValue())){
                            usePrice.updateAndGet(v -> v + userOfCoupons.getOrderPrice() * couponCashRule.getRebatePrice() / 100);
                        }
                    }
                }
                //体验券
                if(userOfCoupons.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
                    usePrice.updateAndGet(v -> v + userOfCoupons.getOrderPrice());
                }
            }
            if(userOfCoupons.getState().equals(CouponEnum.USER_COUPON_STATE_RECEIVED.getValue())){
                notUseNumber.updateAndGet(v -> v + 1);
            }
            if(userOfCoupons.getState().equals(CouponEnum.USER_COUPON_STATE_OVERDUE.getValue())){
                overdueNumber.updateAndGet(v -> v + 1);
            }

        });
        userOfCouponsCountDTO.setSendNumber(sendNumber.get());
        userOfCouponsCountDTO.setSendPrice(sendPrice);
        userOfCouponsCountDTO.setReceivedNumber(receivedNumber.get());
        userOfCouponsCountDTO.setReceivedPrice(receivedPrice);
        userOfCouponsCountDTO.setUseNumber(useNumber.get());
        userOfCouponsCountDTO.setUsePrice(usePrice.get());
        userOfCouponsCountDTO.setNotUseNumber(notUseNumber.get());
        userOfCouponsCountDTO.setNotUsePrice(notUsePrice);
        userOfCouponsCountDTO.setOverdueNumber(overdueNumber.get());
        userOfCouponsCountDTO.setOverduePrice(overduePrice);
        return userOfCouponsCountDTO;
    }

    /**
     * 发送给用户优惠券-用户下单后进行
     * @param userOfCouponsSaveParam
     * @return
     */
    public Boolean saveUserOfCoupons(UserOfCouponsSaveParam userOfCouponsSaveParam){
        UserOfCoupons userOfCoupons = new UserOfCoupons();
        BeanUtils.copyProperties(userOfCouponsSaveParam,userOfCoupons,getNullPropertyNames(userOfCouponsSaveParam));
        //todo 设置优惠券券码
        return save(userOfCoupons);
    }


    /**
     * 用户拥有的优惠券
     * @param userId
     * @return
     */
    public List<UserHaveCouponDTO> userOfCoupon(Long userId,Byte state){
        List<UserHaveCouponDTO> userHaveCouponDTOS = Lists.newArrayList();
        List<Byte> stateList = Lists.newLinkedList();
        if(state == null || state == -1){
            stateList.add(CouponEnum.USER_COUPON_STATE_SEND.getValue());
            stateList.add(CouponEnum.USER_COUPON_STATE_RECEIVED.getValue());
            stateList.add(CouponEnum.USER_COUPON_STATE_USE.getValue());
            stateList.add(CouponEnum.USER_COUPON_STATE_OVERDUE.getValue());
        }else {
            stateList.add(state);
        }
        List<UserOfCoupons> userOfCouponsList = list(
                new QueryWrapper<UserOfCoupons>().eq("user_id",userId).in("state",stateList));
        if(CollectionUtils.isNotEmpty(userOfCouponsList)){
            userOfCouponsList.forEach(userOfCoupons -> {
                UserHaveCouponDTO userHaveCouponDTO = new UserHaveCouponDTO();
                //查询用户用券信息
                BeanUtils.copyProperties(userOfCoupons,userHaveCouponDTO,getNullPropertyNames(userOfCoupons));
                //查询优惠券信息
                Coupon coupon = couponService.getById(userOfCoupons.getCouponId());
                BeanUtils.copyProperties(coupon,userHaveCouponDTO,getNullPropertyNames(coupon));
                if(coupon.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
                    CouponExperienceRule couponExperienceRule = couponExperienceRuleService.getOne(
                            new QueryWrapper<CouponExperienceRule>().eq("coupon_id",coupon.getId()));
                    BeanUtils.copyProperties(couponExperienceRule,userHaveCouponDTO,getNullPropertyNames(couponExperienceRule));
                }
                if(coupon.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
                    CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(
                            new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
                    BeanUtils.copyProperties(couponFullReduceRule,userHaveCouponDTO,getNullPropertyNames(couponFullReduceRule));
                }
                if(coupon.getCouponType().equals(CouponEnum.CASH.getValue())){
                    CouponCashRule couponCashRule = couponCashRuleService.getOne(
                            new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()) );
                    BeanUtils.copyProperties(couponCashRule,userHaveCouponDTO,getNullPropertyNames(couponCashRule));
                }
                userHaveCouponDTOS.add(userHaveCouponDTO);
            });
        }
        return userHaveCouponDTOS;
    }

    /**
     *  获取用户可用的优惠券
     * @return
     */
    public List<UserCanUseCouponDTO> getUserCanUseCouponDTO(Long goodsId){
        List<UserCanUseCouponDTO> userCanUseCouponDTOList = Lists.newLinkedList();
        // TODO: 2020/4/21 从token中获取用户id
        Long userId = 2L;
        //获取用户的优惠券-领取未使用-体验券除外
        List<UserOfCoupons> userOfCouponsList = list(new QueryWrapper<UserOfCoupons>().eq("user_id",userId)
                .eq("coupon_status",CouponEnum.STATE_YES.getValue())
                .eq("state",CouponEnum.USER_COUPON_STATE_RECEIVED.getValue()));
        userOfCouponsList.forEach(userOfCoupons -> {
            UserCanUseCouponDTO userCanUseCouponDTO = new UserCanUseCouponDTO();
            userCanUseCouponDTO.setCouponCode(userOfCoupons.getCouponCode());
            Coupon coupon = couponService.getById(userOfCoupons.getCouponId());
            userCanUseCouponDTO.setCouponId(coupon.getId());
            userCanUseCouponDTO.setCouponType(coupon.getCouponType());
            userCanUseCouponDTO.setName(coupon.getName());
            userCanUseCouponDTO.setRemark(coupon.getRemark());
            if(coupon.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
                CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(
                        new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
                if(couponFullReduceRule.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_YES.getValue())){
                    List<CouponGoods> couponGoodsList = couponGoodsService.list(new QueryWrapper<CouponGoods>().eq("goods_id",goodsId).eq("coupon_id",coupon.getId()));
                    if(CollectionUtils.isNotEmpty(couponGoodsList)){
                        userCanUseCouponDTO.setGoodsSpecial(couponFullReduceRule.getGoodsSpecial());
                        userCanUseCouponDTO.setRebateMethod(couponFullReduceRule.getRebateMethod());
                        userCanUseCouponDTO.setRebatePrice(couponFullReduceRule.getRebatePrice());
                        userCanUseCouponDTO.setStartTime(couponFullReduceRule.getStartTime());
                        userCanUseCouponDTO.setEndTime(couponFullReduceRule.getEndTime());
                        userCanUseCouponDTO.setValidityDay(couponFullReduceRule.getValidityDay());
                        userCanUseCouponDTO.setValidityType(couponFullReduceRule.getValidityType());
                        userCanUseCouponDTO.setIssend(couponFullReduceRule.getIssend());
                        userCanUseCouponDTO.setUnuseStartTime(couponFullReduceRule.getUnuseStartTime());
                        userCanUseCouponDTO.setUnuseEndTime(couponFullReduceRule.getUnuseEndTime());
                        userCanUseCouponDTOList.add(userCanUseCouponDTO);
                    }
                }else {
                    userCanUseCouponDTO.setGoodsSpecial(couponFullReduceRule.getGoodsSpecial());
                    userCanUseCouponDTO.setRebateMethod(couponFullReduceRule.getRebateMethod());
                    userCanUseCouponDTO.setRebatePrice(couponFullReduceRule.getRebatePrice());
                    userCanUseCouponDTO.setStartTime(couponFullReduceRule.getStartTime());
                    userCanUseCouponDTO.setEndTime(couponFullReduceRule.getEndTime());
                    userCanUseCouponDTO.setValidityDay(couponFullReduceRule.getValidityDay());
                    userCanUseCouponDTO.setValidityType(couponFullReduceRule.getValidityType());
                    userCanUseCouponDTO.setIssend(couponFullReduceRule.getIssend());
                    userCanUseCouponDTO.setUnuseStartTime(couponFullReduceRule.getUnuseStartTime());
                    userCanUseCouponDTO.setUnuseEndTime(couponFullReduceRule.getUnuseEndTime());
                    userCanUseCouponDTOList.add(userCanUseCouponDTO);
                }
            }
            if(coupon.getCouponType().equals(CouponEnum.CASH.getValue())){
                CouponCashRule couponCashRule = couponCashRuleService.getOne(new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()) );
                if(couponCashRule.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_YES.getValue())){
                    List<CouponGoods> couponGoodsList = couponGoodsService.list(new QueryWrapper<CouponGoods>().eq("goods_id",goodsId).eq("coupon_id",coupon.getId()));
                    if(CollectionUtils.isNotEmpty(couponGoodsList)){
                        userCanUseCouponDTO.setGoodsSpecial(couponCashRule.getGoodsSpecial());
                        userCanUseCouponDTO.setRebateMethod(couponCashRule.getRebateMethod());
                        userCanUseCouponDTO.setRebatePrice(couponCashRule.getRebatePrice());
                        userCanUseCouponDTO.setStartTime(couponCashRule.getStartTime());
                        userCanUseCouponDTO.setEndTime(couponCashRule.getEndTime());
                        userCanUseCouponDTO.setValidityDay(couponCashRule.getValidityDay());
                        userCanUseCouponDTO.setValidityType(couponCashRule.getValidityType());
                        userCanUseCouponDTO.setIssend(couponCashRule.getIssend());
                        userCanUseCouponDTO.setUnuseStartTime(couponCashRule.getUnuseStartTime());
                        userCanUseCouponDTO.setUnuseEndTime(couponCashRule.getUnuseEndTime());
                        userCanUseCouponDTOList.add(userCanUseCouponDTO);
                    }
                }else {
                    userCanUseCouponDTO.setGoodsSpecial(couponCashRule.getGoodsSpecial());
                    userCanUseCouponDTO.setRebateMethod(couponCashRule.getRebateMethod());
                    userCanUseCouponDTO.setRebatePrice(couponCashRule.getRebatePrice());
                    userCanUseCouponDTO.setStartTime(couponCashRule.getStartTime());
                    userCanUseCouponDTO.setEndTime(couponCashRule.getEndTime());
                    userCanUseCouponDTO.setValidityDay(couponCashRule.getValidityDay());
                    userCanUseCouponDTO.setValidityType(couponCashRule.getValidityType());
                    userCanUseCouponDTO.setIssend(couponCashRule.getIssend());
                    userCanUseCouponDTO.setUnuseStartTime(couponCashRule.getUnuseStartTime());
                    userCanUseCouponDTO.setUnuseEndTime(couponCashRule.getUnuseEndTime());
                    userCanUseCouponDTOList.add(userCanUseCouponDTO);
                }
            }

        });
        return userCanUseCouponDTOList;
    }

    /**
     * 用户使用优惠券
     * @param id
     * @return
     */
    public Boolean userUseCoupon(Long id){
        UserOfCoupons userOfCoupons = getById(id);
        if(userOfCoupons != null){
            userOfCoupons.setState(CouponEnum.USER_COUPON_STATE_USE.getValue());
        }
        return updateById(userOfCoupons);
    }

    /**
     * 用户转让优惠券
     * @param id
     * @return
     */
    public Boolean userTransferCoupon(Long id,Long userId,String userName,String openId){
        //更新原有的数据为转让中
        UserOfCoupons userOfCoupons = getById(id);
        if(userOfCoupons != null){
            userOfCoupons.setState(CouponEnum.USER_COUPON_STATE_TRANSFER_ING.getValue());
        }
        updateById(userOfCoupons);
        //新增数据
        userOfCoupons.setId(null);//将id初始化，确保新增
        userOfCoupons.setState(CouponEnum.USER_COUPON_STATE_SEND.getValue());
        userOfCoupons.setUserId(userId);
        userOfCoupons.setUserName(userName);
        userOfCoupons.setOpenId(openId);
        return save(userOfCoupons);
    }

    /**
     * 用户领取优惠券
     * @param id
     * @return
     */
    public Boolean userReceiveCoupon(Long id){
        UserOfCoupons userOfCoupons = getById(id);
        if(userOfCoupons != null){
            userOfCoupons.setState(CouponEnum.USER_COUPON_STATE_USE.getValue());
        }
        return updateById(userOfCoupons);
    }

    /**
     * 获取体验券信息-核销
     * @param userId
     * @param couponCode
     * @return
     */
    public CouponVerifyDTO couponDetail(String userId,String couponCode){
        CouponVerifyDTO couponVerifyDTO = new CouponVerifyDTO();
        UserOfCoupons userOfCoupons = getOne(new QueryWrapper<UserOfCoupons>().eq("coupon_code",couponCode).eq("user_id",userId));
        couponVerifyDTO.setCouponCode(userOfCoupons.getCouponCode());
        //: 2020/4/20 获取体验券的信息
        CouponExperienceRule couponExperienceRule = couponExperienceRuleService.getById(userOfCoupons.getCouponId());
        Coupon coupon = couponService.getById(couponExperienceRule.getCouponId());
        BeanUtils.copyProperties(couponExperienceRule,couponVerifyDTO,getNullPropertyNames(couponExperienceRule));
        couponVerifyDTO.setCouponStatus(coupon.getState());
        // TODO: 2020/4/20 获取体验产品的信息-根据活动id，获取该活动绑定的产品信息

        // TODO: 2020/4/20 获取使用记录



        return couponVerifyDTO;
    }

    /**
     * 体验券信息-核销
     * @param couponCode
     * @return
     */
    public Boolean couponVerify(String couponCode){

        return true;
    }


}
