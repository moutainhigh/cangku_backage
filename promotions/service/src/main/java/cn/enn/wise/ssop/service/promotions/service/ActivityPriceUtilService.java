package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityPriceUtilDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.CouponPriceDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.GoodsPriceDTO;
import cn.enn.wise.ssop.service.promotions.consts.CouponEnum;
import cn.enn.wise.ssop.service.promotions.mapper.*;
import cn.enn.wise.ssop.service.promotions.model.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;


/**
 * @author yangshuaiquan
 * 计算优惠券价格
 */
@Service("activityPriceUtilService")
public class ActivityPriceUtilService{
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponExperienceRuleMapper couponExperienceRuleMapper;

    @Autowired
    private CouponCashRuleMapper couponCashRuleMapper;

    @Autowired
    private CouponGoodsMapper couponGoodsMapper;

    @Autowired
    private CouponFullReduceRuleMapper couponFullReduceRuleMapper;

    @Autowired
    private UserOfCouponsMapper userOfCouponsMapper;

    @Autowired
    private ActivityBaseMapper activityBaseMapper;

    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;

    @Autowired
    private ActivityDiscountRuleMapper activityDiscountRuleMapper;

    public ActivityPriceUtilDTO getCouponPrice(ActivityPriceUtilParam activityPriceUtilParam) {
        //总价格
        Integer totalPrice = 0;
        //减免价格
        Integer wiverPrice = 0;
        //总价
        Integer afterPrice = 0;
        //商品数量
        Integer totalPerson = 0;

        List<GoodsPriceParam> goodsPriceParams = activityPriceUtilParam.getGoodsPriceParams();
        //对是否分销商进行判断 1 是 2否
        if(activityPriceUtilParam.getIsDistribute()!=1&&(activityPriceUtilParam.getIsDistribute()!=2)||
                (activityPriceUtilParam.getActivityPriceParams()==null&&activityPriceUtilParam.getCouponPriceParams()==null)
        ){
            ACTIVITY_COUPON_NO.assertFail();
        }
        List<Long> goodsId=goodsPriceParams.stream().map(GoodsPriceParam::getGoodsId).collect(Collectors.toList());
        Byte activityType = activityPriceUtilParam.getActivityType();
        ActivityPriceUtilDTO activityPriceUtilDTO = new ActivityPriceUtilDTO();
        List<CouponPriceDTO> couponPriceDTOS = new ArrayList<>();
        //判断是4 优惠券或者 1 优惠活动
        if (activityType==4) {
            //优惠券
            List<CouponPriceParam> couponPriceParams = activityPriceUtilParam.getCouponPriceParams();
            for (CouponPriceParam couponPriceParam : couponPriceParams) {
                Integer salePrice = 0;
                Coupon coupon = couponMapper.selectById(couponPriceParam.getCouponId());
                //对优惠券和用户匹配同时查询优惠券状态
                QueryWrapper<UserOfCoupons> userOfCouponsQueryWrapper = new QueryWrapper<>();
                userOfCouponsQueryWrapper.eq("coupon_id", couponPriceParam.getCouponId())
                        .eq("user_id", activityPriceUtilParam.getUserId()).eq("state", 2).eq("coupon_status", 1);
                List<UserOfCoupons> userOfCoupons = userOfCouponsMapper.selectList(userOfCouponsQueryWrapper);
                if (userOfCoupons.size() < 1) {
                    COUPON_STATE_NO.assertFail(coupon.getName());
                }
                //对优惠券对应的商品进行匹配
                List<GoodsPriceDTO> goodsPriceDTOS = new ArrayList<>();
                QueryWrapper<CouponGoods> couponGoodsQueryWrapper = new QueryWrapper<>();
                couponGoodsQueryWrapper.eq("company_id",couponPriceParam.getCouponId());
                List<CouponGoods> couponGoods = couponGoodsMapper.selectList(couponGoodsQueryWrapper);
                goodsPriceParams.forEach(c->{
                    couponGoods.forEach(d->{
                        if(c.getGoodsId()==d.getGoodsId()){
                            GoodsPriceDTO goodsPriceDTO = new GoodsPriceDTO();
                            BeanUtils.copyProperties(c,goodsPriceDTO);
                            goodsPriceDTOS.add(goodsPriceDTO);
                        }
                    });
                });

                if (activityPriceUtilParam.getIsDistribute()==1) {
                    //分销商
                    for (GoodsPriceDTO goodsPriceDTO : goodsPriceDTOS) {
                        totalPrice = Math.toIntExact(totalPrice + (goodsPriceDTO.getDistributePrice() * goodsPriceDTO.getGoodsNum()));
                    }
                } if (activityPriceUtilParam.getIsDistribute()==2) {
                    //普通用户
                    for (GoodsPriceDTO goodsPriceDTO : goodsPriceDTOS) {
                        totalPrice = Math.toIntExact(totalPrice + (goodsPriceDTO.getGoodsPrice() * goodsPriceDTO.getGoodsNum()));
                    }
                }
                //优惠券类型 1 体验券 2 满减券 3 代金券
                if (couponPriceParam.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())) {
                    //体验券
                    QueryWrapper<CouponExperienceRule> couponExperienceRuleQueryWrapper = new QueryWrapper<>();
                    couponExperienceRuleQueryWrapper.eq("coupon_id", couponPriceParam.getCouponId());
                    CouponExperienceRule couponExperienceRule = couponExperienceRuleMapper.selectOne(couponExperienceRuleQueryWrapper);
                    //查询体验券的次数
                    Integer experienceNumber = couponExperienceRule.getExperienceNumber();
                    //对折扣价格进行累加
                    if (activityPriceUtilParam.getIsDistribute() == 1) {
                        for (int i = 0; i < experienceNumber; i++) {
                            for (int j = 0; j < goodsPriceParams.get(i).getGoodsNum(); j++) {
                                salePrice = goodsPriceParams.get(i).getDistributePrice()+salePrice;
                                experienceNumber = experienceNumber - 1;
                                if (experienceNumber <= 0) {
                                    break;
                                }
                            }
                        }
                    }
                    if (activityPriceUtilParam.getIsDistribute() == 2) {
                        for (int i = 0; i < experienceNumber; i++) {
                            for (int j = 0; j < goodsPriceParams.get(i).getGoodsNum(); j++) {
                                salePrice = goodsPriceParams.get(i).getGoodsPrice()+salePrice;
                                experienceNumber = experienceNumber - 1;
                                if (experienceNumber <= 0) {
                                    break;
                                }
                            }
                        }
                    } else {
                        ACTIVITY_NOT.assertFail();
                    }
                }
                if (couponPriceParam.getCouponType().equals(CouponEnum.CASH.getValue())) {
                    //代金券
                    QueryWrapper<CouponCashRule> couponCashRuleQueryWrapper = new QueryWrapper<>();
                    couponCashRuleQueryWrapper.eq("coupon_id", couponPriceParam.getCouponId());
                    CouponCashRule couponCashRule = couponCashRuleMapper.selectOne(couponCashRuleQueryWrapper);
                    //对代金券使用方式进行判断 1 金额 2 折扣
                    if (couponCashRule.getRebateMethod() == 1) {
                        //判断是否为随机金额 1 是 2 否
                        if (couponCashRule.getIsrandom() == 1) {
                            Random random = new Random();
                            int minPrice = couponCashRule.getMinPrice();
                            int maxPrice = couponCashRule.getMaxPrice();
                            int randomnum = (int) (Math.random() * maxPrice + minPrice);
                            //折扣价格累加
                            salePrice = randomnum+salePrice;

                        }
                        if (couponCashRule.getIsrandom() == 2) {
                            salePrice = couponCashRule.getRebatePrice()+salePrice;

                        }
                    }
                    if (couponCashRule.getRebateMethod() == 2) {
                        Integer rebatePrice = couponCashRule.getRebatePrice();
                        salePrice = ((100 - rebatePrice) / 100) * totalPrice+salePrice;

                    } else {
                        ACTIVITY_NOT.assertFail();
                    }
                }
                if (couponPriceParam.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())) {
                    //满减卷
                    QueryWrapper<CouponFullReduceRule> couponFullReduceRuleQueryWrapper = new QueryWrapper<>();
                    couponFullReduceRuleQueryWrapper.eq("coupon_id", couponPriceParam.getCouponId());
                    CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleMapper.selectOne(couponFullReduceRuleQueryWrapper);
                    //对满减卷类型进行判断 1 金额  2  折扣
                    if (couponFullReduceRule.getSatisfyPrice() <= totalPrice && couponFullReduceRule.getRebateMethod() == 1) {
                        salePrice = couponFullReduceRule.getRebatePrice()+salePrice;

                    }
                    if (couponFullReduceRule.getSatisfyPrice() <= totalPrice && couponFullReduceRule.getRebateMethod() == 2) {
                        salePrice = (totalPrice * (100 - couponFullReduceRule.getRebatePrice()) / 100)+salePrice;

                    } else {
                        ACTIVITY_NOT.assertFail();
                    }
                } else {
                    COUPON_DATA_NO.assertFail();
                }
                CouponPriceDTO couponPriceDTO  = new CouponPriceDTO();
                couponPriceDTO.setSalePrice(salePrice);
                couponPriceDTO.setGoodsPriceDTOS(goodsPriceDTOS);
                couponPriceDTO.setCouponId(couponPriceParam.getCouponId());
                couponPriceDTO.setCouponType(couponPriceParam.getCouponType());
                couponPriceDTOS.add(couponPriceDTO);
                wiverPrice = wiverPrice+salePrice;
            }
        }
        if(activityType==1) {
                //优惠活动
            List<ActivityPriceParam> activityPriceParams = activityPriceUtilParam.getActivityPriceParams();
            if(activityPriceParams.size()!=0){
                for (ActivityPriceParam activityPriceParam : activityPriceParams) {
                    //判断是否为全部商品 对活动和商品进行匹配


                    QueryWrapper<ActivityDiscountRule> activityDiscountRuleQueryWrapper = new QueryWrapper<>();
                    activityDiscountRuleQueryWrapper.eq("activity_rule_id", activityPriceParam.getActivityRuleId());
                    ActivityDiscountRule activityDiscountRule = activityDiscountRuleMapper.selectOne(activityDiscountRuleQueryWrapper);
                    JSONObject jsonObject = JSONObject.parseObject(activityDiscountRule.getDiscountRule());
                    //对活动类型进行判断 1 早定优惠 2 特价直减 3满减优惠
                    if((activityPriceParam.getAlgorithms()==1)&&(activityPriceParam.getSaleType()==2)){
                        JSONArray reservationRoleAlgorithmsJson = jsonObject.getJSONArray("reservationRoleAlgorithms");
                        List<ActivityDiscountRuleAddParam.ReservationRoleAlgorithm> reservationRoleAlgorithms = JSONObject.parseArray(reservationRoleAlgorithmsJson.toJSONString(), ActivityDiscountRuleAddParam.ReservationRoleAlgorithm.class);
                        //计算提前时间
                        int leadTime = (int) ((activityPriceUtilParam.getOrderUseTime().getTime()-System.currentTimeMillis())/(600000*24));
                        List<Integer> prices = new ArrayList<>();
                        reservationRoleAlgorithms.forEach(c->{
                            if(c.dayCount<=leadTime){
                                Integer salePrice = c.salePrice;
                                prices.add(salePrice);
                            }
                        });
                        Collections.sort(prices);
                        //减免
                        wiverPrice = wiverPrice+prices.get(prices.get(prices.size()-1));
                    }
                    if(activityPriceParam.getAlgorithms()==2){
                        Integer saleDiscountOrMoney = jsonObject.getInteger("saleDiscountOrMoney");
                        //对特价直减类型进行判断 1 金额 2 折扣
                        if(activityPriceParam.getSaleType()==1){
                            //金额
                            wiverPrice = wiverPrice + saleDiscountOrMoney;
                        }if(activityPriceParam.getSaleType()==2) {
                            //折扣
                            wiverPrice = wiverPrice+(totalPrice*(100-saleDiscountOrMoney)/100);
                        }
                    }
                    if(activityPriceParam.getAlgorithms()==3){
                        Byte discountConditions = jsonObject.getByte("discountConditions");
                        JSONArray minusRuleAlgorithmList = jsonObject.getJSONArray("minusRuleAlgorithmList");
                        List<Integer> prices = new ArrayList<>();
                        List<MinusRule.MinusRuleAlgorithm> reservationRoleAlgorithms = JSONObject.parseArray(minusRuleAlgorithmList.toJSONString(), MinusRule.MinusRuleAlgorithm.class);
                        if(discountConditions==1&&activityPriceParam.getSaleType()==2){
                            //满减优惠订单金额
                            final Integer totalPrice1 = totalPrice;
                            reservationRoleAlgorithms.forEach(c->{
                                Integer start = c.start;
                                Integer end = c.end;
                                Integer salePrice = c.salePrice;
                                if(totalPrice1>=start&&totalPrice1<=end){
                                    prices.add(salePrice);
                                }
                            });
                            Collections.sort(prices);
                            wiverPrice = wiverPrice+prices.get(prices.get(prices.size()-1));
                        }
                        if(discountConditions==2&&activityPriceParam.getSaleType()==2){
                            //订单人头
                            final Integer totalPerson1 = totalPerson;
                            reservationRoleAlgorithms.forEach(c->{
                                Integer start = c.start;
                                Integer end = c.end;
                                Integer salePrice = c.salePrice;
                                if(totalPerson1>=start&&totalPerson1<=end){
                                    prices.add(salePrice);
                                }
                            });
                            Collections.sort(prices);
                            wiverPrice = wiverPrice+prices.get(prices.get(prices.size()-1));
                        }
                        else {
                            //ACTIVITY_PRICE_NO.assertFail(activityBase.getActivityName());
                        }
                    }
                    else {
                        ACTIVITY_NOT.assertFail();
                    }
                }
            }
        }
        if (activityPriceUtilParam.getIsDistribute()==1) {
            //分销商
            for (GoodsPriceParam goodsPriceParam : goodsPriceParams) {
                totalPrice = Math.toIntExact(totalPrice + (goodsPriceParam.getDistributePrice() * goodsPriceParam.getGoodsNum()));
            }
        } if (activityPriceUtilParam.getIsDistribute()==2) {
            //普通用户
            for (GoodsPriceParam goodsPriceParam : goodsPriceParams) {
                totalPrice = Math.toIntExact(totalPrice + (goodsPriceParam.getGoodsPrice() * goodsPriceParam.getGoodsNum()));
            }
        }
        activityPriceUtilDTO.setCouponPriceDTOS(couponPriceDTOS);
        activityPriceUtilDTO.setTotalPrice(totalPrice);
        activityPriceUtilDTO.setUserId(activityPriceUtilParam.getUserId());
        activityPriceUtilDTO.setIsDistribute(activityPriceUtilParam.getIsDistribute());
        activityPriceUtilDTO.setActivityType(activityType);
        afterPrice = totalPrice - wiverPrice;
        activityPriceUtilDTO.setAfterPrice(afterPrice);
        return activityPriceUtilDTO;
    }
}
