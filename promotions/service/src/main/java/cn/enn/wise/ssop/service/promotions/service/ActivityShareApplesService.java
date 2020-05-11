package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.response.ActivityShareAppetsPageInfoDTO;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityShareMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorAddMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityShare;
import cn.enn.wise.ssop.service.promotions.model.DistributorAdd;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.PARAM_ERROR;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/*
 * @author 耿小洋
 * 小程序端促销活动相关
 */

@Service("activityShareApplesService")
public class ActivityShareApplesService extends ServiceImpl<DistributorAddMapper, DistributorAdd> {


    @Autowired
    ActivityShareMapper activityShareMapper;


    /**
     * 根据促销活动id获取分享页面相关信息
     *
     * @param
     * @return
     */
    public R<ActivityShareAppetsPageInfoDTO> getActivitySharePageInfoByActivityBaseId(Long activityBaseId) {
        //必传参数验空
        if (null == activityBaseId) {
            PARAM_ERROR.assertFail();
        }
        ActivityShare activityShare = activityShareMapper.selectOne(new QueryWrapper<ActivityShare>().eq("activity_base_id", activityBaseId));
        ActivityShareAppetsPageInfoDTO activityShareAppetsPageInfoDTO = new ActivityShareAppetsPageInfoDTO();
        if (activityShare!=null) {
            BeanUtils.copyProperties(activityShare,activityShareAppetsPageInfoDTO,getNullPropertyNames(activityShare));
        }
        return new R<>(activityShareAppetsPageInfoDTO);
    }


}
