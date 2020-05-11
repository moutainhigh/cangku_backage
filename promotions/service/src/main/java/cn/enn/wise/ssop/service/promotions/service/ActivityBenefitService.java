package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityBaseDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityBenefitDTO;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.consts.StateEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityBaseMapper;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityBenefitMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityBase;
import cn.enn.wise.ssop.service.promotions.model.ActivityBenefit;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 活动效果预估信息
 */
@Service("activityBenefitService")
public class ActivityBenefitService extends ServiceImpl<ActivityBenefitMapper, ActivityBenefit> {

    @Autowired
    private ActivityBaseService activityBaseService;

    /**
     * 保存
     * @param param
     * @return
     */
    public R<Boolean> saveBenefitByBatch(List<ActivityBenefitAddParam> param) {
        List<ActivityBenefit> list = new ArrayList<>();

        final Long[] activityBaseId = {0L};
        param.forEach(x -> {
            if(activityBaseId[0] ==0L){
                activityBaseId[0] = x.getActivityBaseId();
            }
            QueryWrapper<ActivityBenefit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_base_id", x.getActivityBaseId());
            queryWrapper.eq("activity_benefit_type", x.getActivityBenefitType());
            queryWrapper.last(" limit 1");

            ActivityBenefit activityBenefit = getOne(queryWrapper);
            // activityBenefit == null  新增  !=null 更新
            if (activityBenefit == null) {
                activityBenefit = new ActivityBenefit();
            }
            BeanUtils.copyProperties(x, activityBenefit,getNullPropertyNames(x));
            activityBenefit.setCustomerProportion(JSON.toJSONString(x.getCustomerList()));
            activityBenefit.setGoodsSort(JSON.toJSONString(x.getGoodsSort()));
            list.add(activityBenefit);
        });
        Boolean result = saveOrUpdateBatch(list);

        // 更新编辑步骤为 2
        ActivityBase activityBase = activityBaseService.getById( activityBaseId[0] );
        activityBase.setEditStep(EditStepEnum.FOUR.getValue());
        activityBaseService.updateById(activityBase);
        return new R<>(result);
    }

    /**
     * 获取详情
     * @param activityBaseId
     * @return
     */
    public R<List<ActivityBenefitDTO>> getBenefitByBatch(Long activityBaseId) {

        QueryWrapper<ActivityBenefit> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("activity_base_id",activityBaseId);
        List<ActivityBenefit> activityBenefits = list(queryWrapper);
        List<ActivityBenefitDTO> activityBenefitDTOS = new ArrayList<>();

        for (ActivityBenefit activityBenefit : activityBenefits) {
            ActivityBenefitDTO activityBenefitDTO = new ActivityBenefitDTO();
            BeanUtils.copyProperties(activityBenefit,activityBenefitDTO,getNullPropertyNames(activityBenefit));
            activityBenefitDTO.setCustomerList(JSON.parseArray(activityBenefit.getCustomerProportion(), CustomerProportionParam.class));
            activityBenefitDTO.setGoodsSort(JSON.parseArray(activityBenefit.getGoodsSort(), GoodsSortParam.class));
            activityBenefitDTOS.add(activityBenefitDTO);
        }
        return new R<>(activityBenefitDTOS);
    }
}
