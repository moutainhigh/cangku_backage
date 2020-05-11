package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.bean.vo.GroupOrderInfoVo;
import cn.enn.wise.platform.mall.bean.vo.OrderQueryVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GroupOrderService;
import cn.enn.wise.platform.mall.service.GroupPromotionService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author jiabaiye
 * @Description 小程序拼团API
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("/applets/grouporder")
@Api(value = "小程序拼团API")
public class GroupOrderAppletsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupOrderAppletsController.class);
    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private GroupPromotionService groupPromotionService;

    @GetMapping("/validate/create/group")
    @ApiOperation(value = "小程序验证是否可以拼团", notes = "小程序验证是否可以拼团")
    @OpenIdAuthRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrderId", value = "团id", paramType = "query"),
            @ApiImplicitParam(name = "groupPromotionId", value = "活动id", paramType = "query"),
            @ApiImplicitParam(name = "user", value = "用户信息", paramType = "query")
    })
    public ResponseEntity<Map<String, Object>> isIntoGroupOrder(Long groupOrderId,Long groupPromotionId,@Value("#{request.getAttribute('currentUser')}") User user){
        if(user == null || user.getId() == null){
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"用户验证失败");
        }
        logger.info("===验证是否可以拼团===");
        ResponseEntity responseEntity = groupOrderService.isIntoGroupOrder(groupOrderId,groupPromotionId,user.getId());

        return  responseEntity;
    }

    @GetMapping("/group/success")
    @ApiOperation(value = "小程序是否拼团成功", notes = "小程序是否拼团成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrderId", value = "团id", paramType = "query"),
    })
    public ResponseEntity<Map<String, Object>> isGroupOrderSuccess(Long groupOrderId) {
        logger.info("===是否拼团成功===");
        ResponseEntity responseEntity = groupOrderService.isGroupOrderSuccess(groupOrderId);

        return  responseEntity;
    }

    @RequestMapping(value = "/getgrouporderlist", method = RequestMethod.POST)
    @ApiOperation(value = "获取拼团列表", notes = "获取拼团列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", paramType = "query"),
    })
    public ResponseEntity getGroupOrderList(@RequestBody GroupOrderVo groupOrderVo,@RequestHeader String companyId) {
        logger.info("===获取拼团列表===");
        ResponseEntity responseEntity = groupOrderService.getGroupOrderList(groupOrderVo.getGoodsId(),Long.parseLong(companyId));

        return  responseEntity;
    }

    @RequestMapping(value = "/getgrouporderlistdetail", method = RequestMethod.POST)
    @ApiOperation(value = "获取拼团列表", notes = "获取拼团列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", paramType = "query"),
    })
    public ResponseEntity getGroupOrderListDetail(@RequestBody GroupOrderVo groupOrderVo) {
        logger.info("===获取拼团列表===");
        ResponseEntity responseEntity = groupOrderService.getGroupOrderListDetail(groupOrderVo.getGoodsId());

        return  responseEntity;
    }

    @RequestMapping(value = "/insert/grouporder", method = RequestMethod.POST)
    @ApiOperation(value = "参与拼团", notes = "参与拼团")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", paramType = "query"),
    })
    @OpenIdAuthRequired
    public ResponseEntity insertGroupOrder( String  groupId, @Value("#{request.getAttribute('currentUser')}") User user,String orderCode)  throws Exception {
        logger.info("===参与拼团===");
        ResponseEntity responseEntity = groupOrderService.insertGroupOrder( Long.parseLong(groupId),  user.getId(), orderCode);

        return  responseEntity;
    }

    @PostMapping("/create")
    @ApiOperation(value = "小程序用户发起拼团",notes = "小程序用户发起拼团")
    @OpenIdAuthRequired
    public ResponseEntity<OrderQueryVo> createGroupOrder(@Value("#{request.getAttribute('currentUser')}") User user , String orderCode,Long promotionId) throws Exception {

        if(promotionId == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"拼团活动Id不能为空");
        }

        OrderQueryVo groupOrder = groupOrderService.createGroupOrder(user.getId(), orderCode,promotionId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,groupOrder);
    }


    @GetMapping("/detail/{id}")
    @ApiOperation(value = "小程序拼团详情",notes = "小程序拼团详情")
    public ResponseEntity<GroupOrderInfoVo> groupOrderDetail(@PathVariable("id") Long id,@RequestHeader("openId") String openId){

        GroupOrderInfoVo groupOrderInfoVo = groupOrderService.groupOrderDetail(id);
        groupOrderInfoVo.setOpenId(openId);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,groupOrderInfoVo);

    }

    @PostMapping("/validate/create")
    @ApiOperation(value = "验证用户是否可以创建拼团",notes = "验证用户是否可以创建拼团")
    @OpenIdAuthRequired
    public ResponseEntity validateCreateGroupOrder(@Value("#{request.getAttribute('currentUser')}") User user,Long goodsId,Long promotionId){

        if(user == null || user.getId() == null){
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"用户验证失败");
        }

        if(goodsId == null || promotionId == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品Id或活动Id不能为空");
        }

        Map<String, String> memberIfInTheGroup = groupOrderService.getMemberIfInTheGroup(user.getId(),goodsId,promotionId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,memberIfInTheGroup);
    }

    @GetMapping("/rule/{id}")
    @ApiOperation(value = "小程序拼团规则",notes = "小程序拼团详情")
    public ResponseEntity groupRuleDetail(@PathVariable("id") Long promotionId,@RequestHeader("openId") String openId){
        GroupPromotionBo groupPromotionBo = groupPromotionService.getById(promotionId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,groupPromotionBo.getRemark());

    }
}
