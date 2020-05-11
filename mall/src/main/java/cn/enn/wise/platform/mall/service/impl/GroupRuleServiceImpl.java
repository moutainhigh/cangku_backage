package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import cn.enn.wise.platform.mall.bean.param.AddGroupRuleParam;
import cn.enn.wise.platform.mall.bean.param.GroupRuleParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupRuleVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.mapper.GroupRuleMapper;
import cn.enn.wise.platform.mall.service.GroupPromotionService;
import cn.enn.wise.platform.mall.service.GroupRuleService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class GroupRuleServiceImpl extends ServiceImpl<GroupRuleMapper, GroupRuleBo>  implements GroupRuleService {


    @Autowired
    GroupPromotionService groupPromotionService;

    @Override
    public ResponseEntity<ResPageInfoVO<List<GroupRuleVo>>> listRuleByPage(ReqPageInfoQry<GroupRuleParam> param) {
        GroupRuleParam params = param.getReqObj();
        QueryWrapper<GroupRuleBo> wrapper = new QueryWrapper<>();
        if (params != null) {
            // 名称
            if (StringUtils.isNotEmpty(params.getName())) {
                wrapper.like("name", params.getName());
            }

            // 状态
            if (params.getStatus() != null && params.getStatus() > 0) {
                wrapper.in("status", params.getStatus());
            }
        }

        // mybatis 查询数据
        Page<GroupRuleBo> pageInfo = new Page<>(param.getPageNum(), param.getPageSize());
        wrapper.orderByDesc("id");
        IPage<GroupRuleBo> ruleList = this.page(pageInfo, wrapper);

        // 分页
        ResPageInfoVO resPageInfoVO = new ResPageInfoVO();
        if (ruleList == null) {
            resPageInfoVO.setPageNum(0L);
            resPageInfoVO.setPageSize(0L);
            resPageInfoVO.setTotal(0L);
            return new ResponseEntity<ResPageInfoVO<List<GroupRuleVo>>>(resPageInfoVO);
        }
        // 分页
        resPageInfoVO.setPageNum(ruleList.getCurrent());
        resPageInfoVO.setPageSize(ruleList.getSize());
        resPageInfoVO.setTotal(ruleList.getTotal());

        List<GroupRuleVo> list = getRuleVoList(ruleList.getRecords());

        // 如果无数据则直接返回
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<GroupRuleVo>>>(resPageInfoVO);
        }
        resPageInfoVO.setRecords(list);
        return new ResponseEntity<>(resPageInfoVO);

    }

    /**
     * 获取规则详情
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<GroupRuleVo> getRuleById(Integer id) {
        GroupRuleBo bo = this.getById(id);
        return new ResponseEntity(getGroupRuleVo(bo));
    }

    @Override
    public GroupRuleBo saveRule(AddGroupRuleParam param) {
        //TODO 暂时没有用到
        param.setGroupType(new Byte("1"));
        GroupRuleBo bo = new GroupRuleBo();

        if (param.getId() != null && param.getId()>0) {
            bo = this.getById(param.getId());


            // 编辑名称//
            if(!bo.getName().equals(param.getName())){
                QueryWrapper<GroupRuleBo> wrapper = new QueryWrapper<>();
                wrapper.eq("name", param.getName());
                wrapper.eq("status", 1);
                List<GroupRuleBo> boList = this.list(wrapper);
                if (boList.size() > 0) {
                    return null;
                }
            }

            bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            bo.setCreateBy("wzdadmin");
            bo.setGroupLimit(param.getGroupLimit()==null?1001:param.getGroupLimit());
            bo.setGroupSize(param.getGroupSize());
            bo.setGroupType(param.getGroupType());
            bo.setGroupValidHours(param.getGroupValidHours());
            bo.setIsAutoCreateGroup(param.getIsAutoCreateGroup());
            bo.setName(param.getName());
            bo.setRemark(param.getRemark());
            bo.setStatus(param.getStatus());
            bo.setType(param.getType());
            bo.setAutoCreateGroupLimit(param.getAutoCreateGroupLimit());
            bo.setPeriod(param.getPeriod());
            this.saveOrUpdate(bo);
        }else{
            QueryWrapper<GroupRuleBo> wrapper = new QueryWrapper<>();
            wrapper.eq("name", param.getName());
            wrapper.eq("status", 1);
            List<GroupRuleBo> boList = this.list(wrapper);
            if (boList.size() > 0) {
                return null;
            }
            bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            bo.setCreateBy("wzdadmin");
            bo.setGroupLimit(param.getGroupLimit()==null?1001:param.getGroupLimit());
            bo.setGroupSize(param.getGroupSize());
            bo.setGroupType(param.getGroupType());
            bo.setGroupValidHours(param.getGroupValidHours());
            bo.setIsAutoCreateGroup(param.getIsAutoCreateGroup());
            bo.setName(param.getName());
            bo.setRemark(param.getRemark());
            bo.setStatus(param.getStatus());
            bo.setType(param.getType());
            bo.setAutoCreateGroupLimit(param.getAutoCreateGroupLimit());
            bo.setPeriod(param.getPeriod());
            this.saveOrUpdate(bo);
        }
        // 添加判断是否重名
        return bo;
    }

    /**
     * 组装Vo类
     *
     * @param records
     * @return
     */
    private List<GroupRuleVo> getRuleVoList(List<GroupRuleBo> records) {

        List<GroupRuleVo> list = new ArrayList<>();

        for (GroupRuleBo record : records) {
            GroupRuleVo rule = getGroupRuleVo(record);

            list.add(rule);
        }
        return list;
    }

    /**
     * 转化实体类
     *
     * @param record
     * @return
     */
    private GroupRuleVo getGroupRuleVo(GroupRuleBo record) {
        if (record == null) {
            return null;
        }
        GroupRuleVo rule = new GroupRuleVo();
        rule.setCreateBy(record.getCreateBy());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rule.setCreateTime(simpleDateFormat.format(record.getCreateTime()));
        rule.setId(record.getId());
        String isGroupRule = "否";
        if (record.getGroupType() == 1) {
            isGroupRule = "是";
        }
        rule.setIsGroupRule(isGroupRule);
        rule.setName(record.getName());
        rule.setType(record.getType());
        rule.setStatus(record.getStatus());
        rule.setLimit(record.getGroupLimit());
        rule.setRemark(record.getRemark());
        rule.setSize(record.getGroupSize());
        rule.setAutoFinish(record.getIsAutoCreateGroup().intValue());
        rule.setPeriod(record.getPeriod());
        rule.setAutoCreateGroupLimit(record.getAutoCreateGroupLimit());
        rule.setGroupValidHours(Byte.valueOf(record.getGroupValidHours().toString()));
        rule.setGroupSize(record.getGroupSize());
        rule.setGroupLimit(record.getGroupLimit()==1001L?null:record.getGroupLimit());
        rule.setIsAutoCreateGroup(record.getIsAutoCreateGroup());


        QueryWrapper<GroupPromotionBo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_id",rule.getId());
        queryWrapper.eq("status",2);
        List<GroupPromotionBo> groupPromotionBoList = groupPromotionService.list(queryWrapper);
        // TODO 查询数据库
        rule.setIsInUse(new Byte("0"));
        if(groupPromotionBoList.size()>0){
            rule.setIsInUse(new Byte("1"));
        }
        return rule;
    }
}
