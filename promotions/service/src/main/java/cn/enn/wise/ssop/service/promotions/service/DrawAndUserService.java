package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.api.promotions.dto.request.DrawOrderParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DrawAndUserDetailDTO;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityBaseMapper;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityDrawRuleMapper;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityGoodsMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DrawAndUserMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityBase;
import cn.enn.wise.ssop.service.promotions.model.ActivityDrawRule;
import cn.enn.wise.ssop.service.promotions.model.ActivityGoods;
import cn.enn.wise.ssop.service.promotions.model.DrawAndUser;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.ssop.service.promotions.consts.ActivityTypeEnum.DRAW;
import static cn.enn.wise.ssop.service.promotions.consts.DrawManConditionEnum.NUMBER_OF_PEOPLE;
import static cn.enn.wise.ssop.service.promotions.consts.DrawManConditionEnum.SUM_OF_MONEY;
import static cn.enn.wise.ssop.service.promotions.consts.StateEnum.ONGOING;


@Service("drawAndUserService")
public class DrawAndUserService extends ServiceImpl<DrawAndUserMapper, DrawAndUser> {

    private Logger logger = LoggerFactory.getLogger(GroupOrderService.class);


    @Autowired
    private ActivityBaseMapper activityBaseMapper;
    @Autowired
    private ActivityGoodsMapper activityGoodsMapper;


    @Autowired
    private DrawAndUserMapper drawAndUserMapper;

    @Autowired
    private ActivityDrawRuleMapper activityDrawRuleMapper;


    /**
     * 查询活动信息表
     *
     * @param userId 用户id
     * @return DrawAndUserDetailDTO 返回抽奖数据
     * @author 康康
     */
    public R<DrawAndUserDetailDTO> selectActivityBase(Long userId) {
        logger.info("==== 当前用户的id：[{}] ====", userId);
        QueryWrapper<ActivityBase> activityBaseQueryWrapper = new QueryWrapper<>();
        activityBaseQueryWrapper.eq("state", ONGOING.getValue());
        activityBaseQueryWrapper.eq("activity_type", DRAW.getValue());

        ActivityBase activity = activityBaseMapper.selectOne(activityBaseQueryWrapper);

        if (activity == null) {
            DRAW_IS_NOT_STARTED.assertFail();
        }

        QueryWrapper<DrawAndUser> DrawAndUserQueryWrapper = new QueryWrapper<>();
        DrawAndUserQueryWrapper.eq("activity_id", activity.getId());
        DrawAndUserQueryWrapper.eq("user_id", userId);
        List<DrawAndUser> drawAndUserList = drawAndUserMapper.selectList(DrawAndUserQueryWrapper);
        logger.info("==== 抽奖和用户关联表信息：[{}] ====", drawAndUserList);

        if (drawAndUserList.isEmpty()) {
            DRAW_IS_DISCONTENT.assertFail();
        }
        DrawAndUser drawAndUser = drawAndUserList.get(0);

        QueryWrapper<ActivityDrawRule> ActivityDrawRuleQueryWrapper = new QueryWrapper<>();
        ActivityDrawRuleQueryWrapper.eq("activity_rule_id", drawAndUser.getActivityRuleId());
        ActivityDrawRule activityDrawRule = activityDrawRuleMapper.selectOne(ActivityDrawRuleQueryWrapper);


        DrawAndUserDetailDTO drawAndUserDetailDTO = new DrawAndUserDetailDTO();

        drawAndUserDetailDTO.setUserId(userId);
        drawAndUserDetailDTO.setActivityId(drawAndUser.getActivityId());
        drawAndUserDetailDTO.setActivityName(activity.getActivityName());
        drawAndUserDetailDTO.setDrawAllSize(drawAndUser.getDrawAllSize());//总的
        drawAndUserDetailDTO.setDrawSize(drawAndUser.getDrawSize());//剩余可用的 可能是昨天剩的
        drawAndUserDetailDTO.setDrawUsed(drawAndUser.getDrawUsed());//当天已用的
        drawAndUserDetailDTO.setDayDrawSize(activityDrawRule.getDrawSize()-drawAndUser.getDrawUsed());//当天未用的   规定一天可用多少 减 今天已经用了的
        drawAndUserDetailDTO.setEndTime(drawAndUser.getEndTime());
        drawAndUserDetailDTO.setRuleDrawSize(activityDrawRule.getDrawSize());//每人每天可抽次数规定

        return new R<>(drawAndUserDetailDTO);
    }





    /**
     * 查询订单满足抽奖条件的用户
     *
     * @param drawOrderParam 请求参数
     * @return NULL  暂时不需要返回指定的数据 只需要更新数据到数据库
     * @author 康康
     */
    public Boolean selectActivityOrderAndUser(DrawOrderParam drawOrderParam) {
      Integer payPeopleNumber=drawOrderParam.getPayPeopleNumber();
      BigDecimal ActualPayPrice= drawOrderParam.getActualPayPrice();
      if(payPeopleNumber==null && ActualPayPrice==null){
          ACTIVITY_NOT.assertFail();
      }
      //活动商品表存在此商品
        QueryWrapper<ActivityGoods> activityGoodsQueryWrapper = new QueryWrapper<>();
        activityGoodsQueryWrapper.eq("goods_id", drawOrderParam.getGoodsId());
        activityGoodsQueryWrapper.eq("goods_name", drawOrderParam.getGoodsName());
        ActivityGoods activityGoods = activityGoodsMapper.selectById(activityGoodsQueryWrapper);

        if (activityGoods == null) {
            GOODS_NOT_IS_ACTIVE.assertFail();//当前商品不是活动商品
        }


        QueryWrapper<DrawAndUser> drawAndUserQueryWrapper = new QueryWrapper<>();
        drawAndUserQueryWrapper.eq("user_id", drawOrderParam.getUserId());
        drawAndUserQueryWrapper.eq("activity_rule_id", activityGoods.getActivityRuleId());
        List<DrawAndUser> drawAndUserlist = drawAndUserMapper.selectList(drawAndUserQueryWrapper);
        logger.info("==== 抽奖和用户信息：[{}] ====", drawAndUserlist);
        if (drawAndUserlist.isEmpty()) {
            USER_DATA_NO.assertFail();
        }

        for (DrawAndUser drawAndUser : drawAndUserlist) {
        Integer drawNumber=0;//初始化抽奖次数
        QueryWrapper<ActivityDrawRule> activityDrawRuleQueryWrapper = new QueryWrapper<>();
        activityDrawRuleQueryWrapper.eq("activity_rule_id", drawAndUser.getActivityRuleId());
        ActivityDrawRule activityDrawRule = activityDrawRuleMapper.selectById(activityDrawRuleQueryWrapper);
        Integer satisfyPrice=activityDrawRule.getSatisfyPrice();//活动规则规定的人头和金额

        //SatisfyPrice是满足条件 不可递增
        if(activityDrawRule.getIsincrease().equals(GeneConstant.BYTE_2)){
            if (activityDrawRule.getDrawManCondition().equals(NUMBER_OF_PEOPLE.getValue())) { //人头

                logger.info("判断活动规则的人头个数比对，当前的人头数是：[{}] ",activityDrawRule.getSatisfyPrice());
                if(payPeopleNumber.compareTo(satisfyPrice) >=0 ){

                    drawAndUser.setDrawAllSize(drawAndUser.getDrawAllSize()+1); //总数加一
                    drawAndUser.setDrawSize(drawAndUser.getDrawAllSize()-drawAndUser.getDrawUsed());//剩余可用次数
                    drawAndUser.setDrawUsed(0);//下单后调用此接口已经使用的抽奖数默认为0
                    drawAndUserMapper.update(drawAndUser,new QueryWrapper<DrawAndUser>().eq("activity_rule_id", drawAndUser.getActivityRuleId()));

                }
            }

            if (activityDrawRule.getDrawManCondition().equals(SUM_OF_MONEY.getValue())) {//金额
                logger.info("判断活动规则的支付总金额比对,当前的金额数是：[{}] ",activityDrawRule.getSatisfyPrice());

                if(ActualPayPrice.compareTo(new BigDecimal(satisfyPrice)) >= 0){
                    drawAndUser.setDrawAllSize(drawAndUser.getDrawAllSize()+1); //总数加一
                    drawAndUser.setDrawSize(drawAndUser.getDrawAllSize()-drawAndUser.getDrawUsed());//剩余可用次数
                    drawAndUserMapper.update(drawAndUser,new QueryWrapper<DrawAndUser>().eq("activity_rule_id", drawAndUser.getActivityRuleId()));

                }

            }


            return true;
        }

        //可递增
        if (activityDrawRule.getDrawManCondition().equals(NUMBER_OF_PEOPLE.getValue())) { //人头

            logger.info("判断活动规则的人头个数比对，当前的人头数是：[{}] ",activityDrawRule.getSatisfyPrice());
            if(payPeopleNumber.compareTo(satisfyPrice) >=0 ){
                drawNumber=payPeopleNumber/activityDrawRule.getSatisfyPrice();
                drawAndUser.setDrawAllSize(drawAndUser.getDrawAllSize()+drawNumber); //总数加一
                drawAndUser.setDrawSize((drawAndUser.getDrawAllSize()+drawNumber)-drawAndUser.getDrawUsed());//剩余可用次数
                drawAndUserMapper.update(drawAndUser,new QueryWrapper<DrawAndUser>().eq("activity_rule_id", drawAndUser.getActivityRuleId()));
            }
        }

        if (activityDrawRule.getDrawManCondition().equals(SUM_OF_MONEY.getValue())) {//金额
            logger.info("判断活动规则的支付总金额比对,当前的金额数是：[{}] ",activityDrawRule.getSatisfyPrice());

            if(ActualPayPrice.compareTo(new BigDecimal(satisfyPrice)) >= 0){
                drawNumber=ActualPayPrice.intValue()/activityDrawRule.getSatisfyPrice();
                drawAndUser.setDrawAllSize(drawAndUser.getDrawAllSize()+drawNumber); //总数
                drawAndUser.setDrawSize((drawAndUser.getDrawAllSize()+drawNumber)-drawAndUser.getDrawUsed());//剩余可用次数
                drawAndUserMapper.update(drawAndUser,new QueryWrapper<DrawAndUser>().eq("activity_rule_id", drawAndUser.getActivityRuleId()));

            }

        }


        }

        return true;
    }


}
