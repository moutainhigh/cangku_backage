package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityShareAddParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityShareDTO;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityShareMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityBase;
import cn.enn.wise.ssop.service.promotions.model.ActivityShare;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.ACTIVITY_SHARE_EXIST;
import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.ACTIVITY_SHARE_IS_NOT_EXIST;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author 安辉
 * 活动分享基础信息
 */
@Service("activityShareService")
public class ActivityShareService extends ServiceImpl<ActivityShareMapper, ActivityShare> {

    @Autowired
    private ActivityBaseService activityBaseService;

    /**
     * 添加分享设置
     * @param param
     * @return
     */
    public R<Long> setActivityShare(ActivityShareAddParam param) {
        QueryWrapper<ActivityShare> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_base_id",param.getActivityBaseId());
        queryWrapper.last(" limit 1");
        ActivityShare share =getOne(queryWrapper);
        if(share == null ){
            share = new ActivityShare();
            BeanUtils.copyProperties(param,share,getNullPropertyNames(param));
            save(share);

            // 更新编辑步骤为 2
            ActivityBase activityBase = activityBaseService.getById(param.getActivityBaseId());
            activityBase.setEditStep(EditStepEnum.THREE.getValue());
            activityBaseService.updateById(activityBase);
        }else{
            BeanUtils.copyProperties(param,share);
            updateById(share);
        }
        return new R(share.getId());
    }

    /**
     * 获取分享详情
     * @param baseActivityId
     * @return
     */
    public R<ActivityShareDTO> getShareByBaseActivityId(Long baseActivityId) {
        QueryWrapper<ActivityShare> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_base_id",baseActivityId);
        queryWrapper.last(" limit 1");
        ActivityShare share =getOne(queryWrapper);
        if(share==null){
            ACTIVITY_SHARE_IS_NOT_EXIST.assertFail();
        }
        ActivityShareDTO target =new ActivityShareDTO();
        BeanUtils.copyProperties(share,target,getNullPropertyNames(share));
        return new R<>(target);
    }
}
