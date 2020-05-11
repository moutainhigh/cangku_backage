package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import cn.enn.wise.platform.mall.bean.param.AddGroupRuleParam;
import cn.enn.wise.platform.mall.bean.param.GroupRuleParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupRuleVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 团购活动
 * @author 安辉
 * @since 2019-09-12
 */
public interface GroupRuleService extends IService<GroupRuleBo> {

    /**
     * 分页查询
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GroupRuleVo>>> listRuleByPage(ReqPageInfoQry<GroupRuleParam> param);

    /**
     * 规则详情
     * @param id
     * @return
     */
    ResponseEntity<GroupRuleVo> getRuleById(Integer id);

    /**
     * 添加规则
     * @param param
     * @return
     */
    GroupRuleBo saveRule(AddGroupRuleParam param);
}