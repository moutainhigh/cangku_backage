package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBaseAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityBaseListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityBaseDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityImgDTO;
import cn.enn.wise.ssop.service.promotions.consts.EditStepEnum;
import cn.enn.wise.ssop.service.promotions.consts.StateEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ActivityBaseMapper;
import cn.enn.wise.ssop.service.promotions.model.ActivityBase;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 活动基础信息
 */
@Service("activityBaseService")
public class ActivityBaseService extends ServiceImpl<ActivityBaseMapper, ActivityBase> {

    @Resource
    ActivityRuleService ruleService;
    /**
     * 添加活动基本信息
     * @author 安辉
     * @param param param.getId() == 0 添加
     *              param.getId() != 0 更新
     *
     */
    public R<ActivityBaseDTO> saveActivityBase(ActivityBaseAddParam param) {
        if(param == null || param.getActivityName().isEmpty()){
            PARAM_ERROR.assertFail();
        }
        if(param.getCost()<1){
            ACTIVITY_COST_ERROR.assertFail();
        }
        if(param.getActivityCount()<1){
            ACTIVITY_COUNT_ERROR.assertFail();
        }
        if(param.getActivityAim()==null||"".equals(param.getActivityAim().trim())){
            ACTIVITY_AIM_ERROR.assertFail();
        }
        if (param.getId() == 0) {
            QueryWrapper<ActivityBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_name", param.getActivityName());
            queryWrapper.last(" limit 1 ");
            ActivityBase target = getOne(queryWrapper);
            if (target != null){
                ACTIVITY_IS_EXIST.assertIsNull(param.getId(),param.getId());
            }
        }

        ActivityBase activityBase = new ActivityBase();
        BeanUtils.copyProperties(param, activityBase,getNullPropertyNames(param));
        activityBase.setStartTime(Timestamp.valueOf(param.getStartTime()));
        activityBase.setEndTime(Timestamp.valueOf(param.getEndTime()));
        activityBase.setDivideDepartment(JSON.toJSONString(param.getDepartmentList()));
        activityBase.setDescription(JSON.toJSONString(param.getDescription()));
        activityBase.setActivityGroup(JSON.toJSONString(param.getActivityGroup()));
        // 添加的时候 填充编号 以及 编辑步骤
        if(param.getId() == 0){
            activityBase.setActivityCode("AC000000"+(this.count()+1));
            activityBase.setEditStep(EditStepEnum.One.getValue());
        }
        Boolean result = saveOrUpdate(activityBase);
        if(!result){
            SAVE_ACTIVITY_ERROR.assertIsNull("");
        }
        ActivityBaseDTO target = new ActivityBaseDTO();
        BeanUtils.copyProperties(activityBase,target,getNullPropertyNames(activityBase));
        target.setDepartmentList(JSON.parseArray(activityBase.getDivideDepartment()));
        target.setEditStep(activityBase.getEditStep());
        return new R(activityBase,"添加成功");
    }

    /**
     * 获取基本活动详情
     * @param id
     * @return
     */
    public R<ActivityBaseDTO> getActivityBaseById(Long id) {
        ActivityBase activityBase = getById(id);
        if(activityBase == null){
            ACTIVITY_IS_NOT_EXIST.assertIsNull("");
        }
        ActivityBaseDTO target = new ActivityBaseDTO();
        BeanUtils.copyProperties(activityBase,target,getNullPropertyNames(activityBase));
        if(activityBase.getDivideDepartment() != null) {
            target.setDepartmentList(JSON.parseArray(activityBase.getDivideDepartment()));
        }
        if(activityBase.getDescription()!=null){
            target.setDescription(JSON.parseArray(activityBase.getDescription()));
        }
        if(activityBase.getActivityGroup() != null) {
            target.setActivityGroup(JSON.parseArray(activityBase.getActivityGroup()));
        }
        return new R<>(target);
    }

    /**
     * 列表
     * @param param
     * @return
     */
    public R<QueryData<List<ActivityBaseDTO>>> listByPage(ActivityBaseListParam param) {
        QueryWrapper<ActivityBase> queryWrapper = new QueryWrapper<>();
        if(param.getActivityName() != null && !param.getActivityName().isEmpty()){
            queryWrapper.like("activity_name",param.getActivityName());
        }
        if(param.getActivityType() != null && param.getActivityType() != -1 && param.getActivityType() != 0){
            queryWrapper.eq("activity_type",param.getActivityType());
        }
        if(param.getState() != null && param.getState() != -1 && param.getState() != 0){
            queryWrapper.eq("state",param.getState());
        }
        if(param.getStartTime() != null && !param.getStartTime().isEmpty()){
            queryWrapper.ge("start_time",param.getStartTime());
        }
        if(param.getEndTime() != null && !param.getEndTime().isEmpty()){
            queryWrapper.le("end_time",param.getEndTime());
        }
        if(param.getDepartment() != null && !param.getDepartment().isEmpty()){
            queryWrapper.like("divide_department",param.getDepartment());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<ActivityBase> page = new Page<>(param.getPageNo(),param.getPageSize());
        page = page(page,queryWrapper);

        QueryData<ActivityBaseDTO> result = new QueryData<>();
        result.setPageNo(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotalCount(page.getTotal());

        List<ActivityBaseDTO> targetList = new ArrayList<>();
        page.getRecords().forEach(x->{
            ActivityBaseDTO target = new ActivityBaseDTO();
            if(!x.getDivideDepartment().isEmpty()){
                target.setDepartmentList(JSON.parseArray(x.getDivideDepartment()));
            }
            BeanUtils.copyProperties(x,target,getNullPropertyNames(x));
            targetList.add(target);
        });

        result.setRecords(targetList);
        return new R(result);
    }

    /**
     * 主键id
     * @param id
     * @return
     */
    public R<Boolean> setActivityEnable(Long id) {
        ActivityBase activityBase = getById(id);
        if(activityBase==null){
            ACTIVITY_IS_NOT_EXIST.assertFail();
        }
        activityBase.setState(StateEnum.ENABLE.getValue());

        return new R(updateById(activityBase));
    }

    /**
     * 获取活动推广图
     * @return
     */
    public List<ActivityImgDTO> getActivityImg(){
        List<ActivityImgDTO> activityImgDTOS = new ArrayList<>();
        List<ActivityBase> list = this.list(new QueryWrapper<ActivityBase>().eq("state",2));
        list.forEach(x->{
            ActivityImgDTO activityImgDTO = new ActivityImgDTO();
            activityImgDTO.setActivityBaseId(x.getId());
            activityImgDTO.setGeneralizeImg(x.getGeneralizeImg());
            activityImgDTO.setDescription(x.getDescription());
            activityImgDTOS.add(activityImgDTO);
        });
        return activityImgDTOS;
    }
}
