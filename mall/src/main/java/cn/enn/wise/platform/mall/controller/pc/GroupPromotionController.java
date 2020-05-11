package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionDetailVo;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionVo;
import cn.enn.wise.platform.mall.bean.vo.GroupRuleVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GroupPromotionService;
import cn.enn.wise.platform.mall.service.GroupRuleService;
import cn.enn.wise.platform.mall.service.PromotionService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author jiabaiye
 * @Description
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/group/promotion")
@Api(value = "团购活动", tags = {"团购活动管理"})
public class GroupPromotionController extends BaseController {

    @Autowired
    GroupPromotionService groupPromotionService;

    @Autowired
    GroupRuleService groupRuleService;

    @Autowired
    PromotionService promotionService;

    @PostMapping("/list")
    @ApiOperation(value = "拼团活动列表", notes = "拼团活动列表")
    public ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>> list(@RequestBody ReqPageInfoQry<GroupPromotionParam> param) {
        return promotionService.listByPage(param);
    }

    @PostMapping("/filter")
    @ApiOperation(value = "拼团活动筛选", notes = "拼团活动筛选")
    public ResponseEntity<List<GroupPromotionVo>> list(@RequestBody PromotionParam param) {
        return groupPromotionService.listByFilter(param);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加活动", notes = "添加活动")
    public ResponseEntity add(@RequestBody GroupPromotionParam param) throws ParseException {

        if(StringUtils.isEmpty(param.getName()) || StringUtils.isEmpty(param.getScenicSpots()) ||
            param.getStatus()==null || param.getCost()==null || param.getPromotionType()==null ||
                StringUtils.isEmpty( param.getStartTime())|| StringUtils.isEmpty(param.getEndTime()) ||
                StringUtils.isEmpty(param.getManager())){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }

        return groupPromotionService.addPromotion(param);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "拼团活动管详情", notes = "拼团活动管详情")
    public ResponseEntity<GroupPromotionDetailVo> detail(Integer id) throws Exception {
        return groupPromotionService.getPromotionById(id);
    }

    @PostMapping("/invalid")
    @ApiOperation(value = "活动失效", notes = "活动失效")
    public ResponseEntity invalid(@RequestBody PromotionInvalidParam param) {

        return groupPromotionService.invalid(param);
    }

    @PostMapping("rule/list")
    @ApiOperation(value = "拼团规则列表", notes = "拼团规则列表")
    public ResponseEntity<ResPageInfoVO<List<GroupRuleVo>>> ruleList(@RequestBody ReqPageInfoQry<GroupRuleParam> param) {
        return groupRuleService.listRuleByPage(param);
    }

    @PostMapping("rule/add")
    @ApiOperation(value = "添加拼团规则", notes = "添加拼团规则")
    public ResponseEntity addRule(@RequestBody AddGroupRuleParam param) {

        if ( param.getGroupSize() == 0 || param.getGroupValidHours() == 0 ||
                param.getIsAutoCreateGroup() == 0 || param.getPeriod() == 0 || !StringUtils.isNotEmpty(param.getName()) || param.getType() == 0) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "参数不完整", null);
        }

        QueryWrapper<GroupPromotionBo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_id",param.getId());
        queryWrapper.eq("status",2);
        List<GroupPromotionBo> promotionBoList = groupPromotionService.list(queryWrapper);
        if(promotionBoList.size()>0){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"规则在使用中，不能编辑");
        }
        GroupRuleBo bo = groupRuleService.saveRule(param);
        if (bo == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "策略已经存在", null);
        }
        return new ResponseEntity(bo);
    }

    @GetMapping("rule/detail")
    @ApiOperation(value = "拼团规则列表", notes = "拼团活动列表")
    public ResponseEntity<GroupRuleVo> ruleDetail(Integer id) {
        return groupRuleService.getRuleById(id);
    }




    @Scheduled(cron = "0 */1 * * * ?")
    @GetMapping("open")
    @ApiOperation(value = "拼团规则列表", notes = "拼团活动列表")
    public void cancelOrder() {
        //TODO 上线前更改成 每日凌晨执行

        // 自动上架
        QueryWrapper<GroupPromotionBo> wrapper   = new QueryWrapper<>();
        wrapper.lt("start_time",new Date());
        wrapper.gt("end_time",new Date());
        wrapper.eq("status",1);
        List<GroupPromotionBo> list = groupPromotionService.list(wrapper);
        list.forEach(x->{
            x.setStatus(GeneConstant.BYTE_2);
            groupPromotionService.updateById(x);
        });

        // 自动下架
        wrapper = new QueryWrapper<>();
        wrapper.lt("end_time",new Date());
        wrapper.eq("status",2);
        list = groupPromotionService.list(wrapper);
        list.forEach(x->{
            x.setStatus(GeneConstant.BYTE_3);
            groupPromotionService.updateById(x);
        });
    }


}
