package cn.enn.wise.ssop.service.promotions.controller.applets;

import cn.enn.wise.ssop.api.promotions.dto.request.GroupOrderCreateParam;
import cn.enn.wise.ssop.api.promotions.dto.request.GroupValidateParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.api.promotions.facade.GroupOrderAppletsFacade;
import cn.enn.wise.ssop.service.promotions.service.ActivityBaseService;
import cn.enn.wise.ssop.service.promotions.service.GroupOrderService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author jiabaiye
 * @Description 小程序拼团API
 * @Version V1.0
 **/
@RestController
@RequestMapping("/applets/grouporder")
@Api(value = "小程序拼团API" , tags = {"小程序拼团API"})
public class GroupOrderAppletsController implements GroupOrderAppletsFacade {

    private static final Logger logger = LoggerFactory.getLogger(GroupOrderAppletsController.class);
    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private ActivityBaseService activityBaseService;

    @PostMapping("/create")
    @ApiOperation(value = "小程序用户发起拼团",notes = "小程序用户发起拼团")
    @Override
    public R<OrderQueryDTO> createGroupOrder(@Value("#{request.getAttribute('currentUser')}") UserParam user , @RequestBody GroupOrderCreateParam groupOrderCreateParam)  {
        OrderQueryDTO groupOrder = groupOrderService.createGroupOrder(user.getId(),groupOrderCreateParam);
        return new R(groupOrder);
    }

    @PostMapping("/insert/grouporder")
    @ApiOperation(value = "参与拼团", notes = "参与拼团")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", paramType = "query"),
    })
    @Override
    public R insertGroupOrder( String  groupId, @Value("#{request.getAttribute('currentUser')}") UserParam user,Long orderCode)  throws Exception {
        logger.info("===参与拼团===");
        HashMap<String, Object> hashMap = groupOrderService.insertGroupOrder( Long.parseLong(groupId),  user.getId(), orderCode);
        return  new R(hashMap);
    }

    @GetMapping("/listByGoodsId")
    @ApiOperation(value = "获取拼团列表根据商品id", notes = "获取拼团列表根据商品id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", paramType = "query"),
    })
    @Override
    public R<List<GroupOrderDTO>> getGroupOrderList(@Validated @NotNull Long goodsId, @RequestHeader String companyId) {
        logger.info("===获取拼团列表===");
        List<GroupOrderDTO> groupOrderDTOS = groupOrderService.getGroupOrderList(goodsId,Long.parseLong(companyId));
        return  new R(groupOrderDTOS);
    }


    @GetMapping("/validate/create")
    @ApiOperation(value = "验证用户是否可以开团或拼团",notes = "验证用户是否可以开团或拼团")
    @Override
    public R<Boolean> validateCreateGroupOrder(@Value("#{request.getAttribute('currentUser')}") UserParam user, @Validated GroupValidateParam groupValidateParam){

        if(user == null || user.getId() == null){
            //throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"用户验证失败");
        }

        Map<String, String> memberIfInTheGroup = groupOrderService.getMemberIfInTheGroup(user.getId(),groupValidateParam);
        return new R(true);
    }

    @GetMapping("/rule/{id}")
    @ApiOperation(value = "小程序拼团规则描述",notes = "小程序拼团规则描述")
    @Override
    public R<String> groupRuleDetail(@PathVariable("id") Long activityBaseId){
        return new R(activityBaseService.getById(activityBaseId).getDescription());
    }

    @GetMapping("/getgroupgoodslist")
    @ApiOperation(value = "拼团活动商品展示",notes = "拼团活动商品展示")
    @Override
    public R<List<GroupGoodsListDTO>> getGroupGoodsList(){
        return new R(groupOrderService.getGroupGoodsList());
    }

    @Override
    @GetMapping("/getgrouporderdetail")
    @ApiOperation(value = "参团信息展示",notes = "参团信息展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrderId", value = "拼团Id", paramType = "query",dataType = "Long",required = true),
    })
    public R<GroupOrderDetaiInfoDTO> getGroupOrderDetail(Long groupOrderId) {
        return new R(groupOrderService.getGroupOrderDetail(groupOrderId));
    }

    @GetMapping("/getgrouporderapplets/{id}")
    @ApiOperation(value = "拼团详情页展示",notes = "拼团详情页展示")
    @Override
    public R<GroupOrderAppletsDTO> getGroupOrderApplets(@PathVariable("id") Long groupOrderId){
        return new R(groupOrderService.getGroupOrderApplets(groupOrderId));
    }

    @GetMapping("/applets/grouporder/getHotGroupOrderList")
    @ApiOperation(value = "火热拼团列表",notes = "火热拼团列表")
    @Override
    public R<GroupGoodsHotDTO> getHotGroupOrderList(@Validated @NotNull Long groupOrderId){
        return new R(groupOrderService.getHotGroupOrderList());
    }
}
