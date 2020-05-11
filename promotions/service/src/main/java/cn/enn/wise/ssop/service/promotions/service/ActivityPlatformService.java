package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.service.promotions.mapper.ActivityPlatformMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityPlatform;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 安辉
 * 活动投放平台信息
 */
@Service("activityPlatformService")
public class ActivityPlatformService extends ServiceImpl<ActivityPlatformMapper, ActivityPlatform> {

    /**
     * 添加渠道
     * @param ids 渠道id数组
     * @param ruleId 规则id
     * @return
     */
    public Boolean addPlatform(List<Long> ids, Long ruleId) {
        Boolean result;
        List<ActivityPlatform> platformList = new ArrayList<>();
        ids.forEach(x->{
            ActivityPlatform platform = new ActivityPlatform();
            platform.setActivityRuleId(ruleId);
            platform.setPlatformId(x);
            platform.setState(new Byte("1"));
            platformList.add(platform);
        });
        result = this.saveBatch(platformList);
        return result;
    }

    /**
     * 查询活动投放平台
     * @param ruleId
     * @return
     */
    public List<Long> getActivityPlatformList(Long ruleId){
        List<Long> ids = new ArrayList<>();
        QueryWrapper<ActivityPlatform> platformQueryWrapper = new QueryWrapper<>();
        platformQueryWrapper.eq("activity_rule_id",ruleId);
        List<ActivityPlatform> platforms = this.list(platformQueryWrapper);
        platforms.forEach(x->{
            ids.add(x.getPlatformId());
        });
        return ids;
    }
    /**
     * 修改活动投放平台
     * @param ids 渠道id数组
     * @param ruleId 规则id
     * @return
     */
    public Boolean updatePlatform(List<Long> ids, Long ruleId) {
        List<ActivityPlatform> list = this.list(new QueryWrapper<ActivityPlatform>().eq("activity_rule_id",ruleId));
        Boolean result;
        List<ActivityPlatform> platformList = new ArrayList<>();
        ids.forEach(x->{
            ActivityPlatform platform = new ActivityPlatform();
            platform.setActivityRuleId(ruleId);
            platform.setPlatformId(x);
            platform.setState(new Byte("1"));
            boolean tmp = false;
            for(ActivityPlatform activityPlatform:list ){
                if(activityPlatform.getPlatformId().equals(x)){
                    platform.setId(activityPlatform.getId());
                    break;
                }
            }
            platformList.add(platform);
        });
        result = this.saveOrUpdateBatch(platformList);
        List<ActivityPlatform> platforms = new ArrayList<>();
        for(ActivityPlatform activityPlatform:list ){
            boolean tmp = false;
            for(Long x :ids){
                if(activityPlatform.getPlatformId().equals(x)){
                    tmp=true;
                    break;
                }
            }
            if(tmp==false){
                activityPlatform.setIsdelete(GeneConstant.BYTE_2);
                platforms.add(activityPlatform);
            }
        }
        if(platforms.size()==0){
            return true;
        }
        this.saveOrUpdateBatch(platforms);
        return result;
    }

}
