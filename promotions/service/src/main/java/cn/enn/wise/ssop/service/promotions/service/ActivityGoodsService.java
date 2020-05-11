package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.MinusRule;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityGoodsMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityGoods;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 平台信息
 */
@Service("activityGoodsService")
public class ActivityGoodsService extends ServiceImpl<ActivityGoodsMapper, ActivityGoods> {

    @Autowired
    ActivityGoodsMapper activityGoodsMapper;

    /**
     * 添加活动商品
     * @param goodsAddParams
     * @return
     */
    public Boolean addGoods(List<ActivityGoodsAddParam> goodsAddParams, Long ruleId) {

        Boolean result;
        List<ActivityGoods> activityGoods = new ArrayList<>();
        goodsAddParams.forEach(x->{
            ActivityGoods target = new ActivityGoods();
            BeanUtils.copyProperties(x,target,getNullPropertyNames(x));
            target.setActivityRuleId(ruleId);
            activityGoods.add(target);
        });
        // 商品列表无数据时，默认全部，增加一条id -1 的数据
        if(activityGoods.size()==0){
            ActivityGoods goods = new ActivityGoods();
            goods.setId(-1L);
            goods.setActivityRuleId(ruleId);
            activityGoods.add(goods);
        }
        result =this.saveBatch(activityGoods);
        return result;
    }

    public List<ActivityGoodsDTO> getActivityGoodsList(Long ruleId){
        QueryWrapper<ActivityGoods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("activity_rule_id",ruleId);
        //goodsQueryWrapper.last(" limit 1");
        List<ActivityGoods> goods = this.list(goodsQueryWrapper);
        List<ActivityGoodsDTO> goodsDTOList = new ArrayList<>();
        goods.forEach(x->{
            ActivityGoodsDTO goodsDTO = new ActivityGoodsDTO();
            BeanUtils.copyProperties(x,goodsDTO,getNullPropertyNames(1));
            goodsDTOList.add(goodsDTO);
        });

        return goodsDTOList;
    }

    /**
     * 修改活动商品
     * @param goodsAddParams
     * @return
     */
    public Boolean updateActivityGoods(List<ActivityGoodsAddParam> goodsAddParams, Long ruleId) {

        List<ActivityGoods> activityGoodsList = this.list(new QueryWrapper<ActivityGoods>().eq("activity_rule_id",ruleId));
        if(goodsAddParams==null||goodsAddParams.size()==0){
            if(activityGoodsList.size()==1&&activityGoodsList.get(0).getGoodsId().equals(-1L)){
                return true;
            }else{
                this.update(new UpdateWrapper<ActivityGoods>().set("isdelete",GeneConstant.BYTE_2).eq("activity_rule_id",ruleId));
                ActivityGoods goods = new ActivityGoods();
                goods.setId(-1L);
                goods.setActivityRuleId(ruleId);
                return this.save(goods);
            }
        }

        Boolean result;
        List<ActivityGoods> activityGoods = new ArrayList<>();
        goodsAddParams.forEach(x->{
            ActivityGoods target = new ActivityGoods();
            BeanUtils.copyProperties(x,target,getNullPropertyNames(x));
            target.setActivityRuleId(ruleId);
            if(target.getId()==null){
                for(ActivityGoods activityGood:activityGoodsList){
                    if(activityGood.getActivityRuleId().equals(target.getActivityRuleId())&&activityGood.getGoodsId().equals(target.getGoodsId())){
                        target.setId(activityGood.getId());
                        break;
                    }
                }
            }
            activityGoods.add(target);
        });
        result =this.saveOrUpdateBatch(activityGoods);
        List<ActivityGoods> activityGoods1 = new ArrayList<>();
        for(ActivityGoods activityGood:activityGoodsList){
            boolean tmp = false;
            for(ActivityGoodsAddParam target:goodsAddParams){
                if(activityGood.getActivityRuleId().equals(ruleId)&&activityGood.getGoodsId().equals(target.getGoodsId())){
                    tmp=true;
                    break;
                }
            }
            if(tmp==false){
                activityGood.setIsdelete(GeneConstant.BYTE_2);
                activityGoods1.add(activityGood);
            }
        }
        if(activityGoods1.size()>0){
            this.saveOrUpdateBatch(activityGoods1);
        }
        return result;
    }

    public List<ActivityTypeDTO> getActivityTypeListByGoods(List<Long> goods) {
        return activityGoodsMapper.getActivityTypeListByGoods(goods);
    }

    public List<ActivityRuleDetailsDTO> getActivityRuleDetailsByGoods(Long id) {
        List<ActivityRuleDetailsDTO> list = activityGoodsMapper.getActivityRuleDetailsByGoods(id);
        for (ActivityRuleDetailsDTO item : list) {
            if (item.getAlgorithms() == 1) {
                JSONObject jsonObject = JSONObject.parseObject(item.getDiscountRule());
                JSONArray jsonArray = jsonObject.getJSONArray("reservationRoleAlgorithms");
                List<ActivityDiscountRuleDTO.ReservationRole> reservationRoles = JSONObject.parseArray(jsonArray.toJSONString(),
                        ActivityDiscountRuleDTO.ReservationRole.class);
                item.setReservationRole(reservationRoles);
            } else if (item.getAlgorithms() == 2) {
                JSONObject jsonObject = JSONObject.parseObject(item.getDiscountRule());
                JSONArray jsonArray = jsonObject.getJSONArray("saleDiscountOrMoney");
                List<ActivityDiscountRuleDTO.SaleRole> saleRoles = JSONObject.parseArray(jsonArray.toJSONString(),
                        ActivityDiscountRuleDTO.SaleRole.class);
                item.setSaleRole(saleRoles);
            } else if (item.getAlgorithms() == 3) {
                JSONObject jsonObject = JSONObject.parseObject(item.getDiscountRule());
                JSONArray jsonArray = jsonObject.getJSONArray("minusRuleAlgorithmList");
                List<MinusRule.MinusRuleAlgorithm> ruleAlgorithm = JSONObject.parseArray(jsonArray.toJSONString(),
                        MinusRule.MinusRuleAlgorithm.class);
                item.setMinusRule(ruleAlgorithm);
            }
        }
        return list;
    }

  
}
