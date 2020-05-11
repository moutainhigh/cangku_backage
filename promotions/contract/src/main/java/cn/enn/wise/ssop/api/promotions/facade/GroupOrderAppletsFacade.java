package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.GroupOrderCreateParam;
import cn.enn.wise.ssop.api.promotions.dto.request.GroupValidateParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiaby
 */
@FeignClient("promotions-service")
@Repository
public interface GroupOrderAppletsFacade {

    @PostMapping("/applets/grouporder/create")
    @ApiOperation(value = "小程序用户发起拼团", notes = "小程序用户发起拼团")
    R<OrderQueryDTO> createGroupOrder(@Value("#{request.getAttribute('currentUser')}") UserParam user, @RequestBody GroupOrderCreateParam groupOrderCreateParam);

    @PostMapping( "/applets/grouporder/insert/grouporder")
    @ApiOperation(value = "参与拼团", notes = "参与拼团")
    R insertGroupOrder( String  groupId, @Value("#{request.getAttribute('currentUser')}") UserParam user,Long orderCode)  throws Exception ;

    @GetMapping("/applets/grouporder/listByGoodsId")
    @ApiOperation(value = "获取拼团列表根据商品id", notes = "获取拼团列表根据商品id")
    R<List<GroupOrderDTO>> getGroupOrderList(@Validated @NotNull Long goodsId, @RequestHeader String companyId);

    @PostMapping("/applets/grouporder/validate/create")
    @ApiOperation(value = "验证用户是否可以创建拼团", notes = "验证用户是否可以创建拼团")
    R<Boolean> validateCreateGroupOrder(@Value("#{request.getAttribute('currentUser')}") UserParam user, @Validated GroupValidateParam groupValidateParam);

    @GetMapping("/applets/grouporder/rule/{id}")
    @ApiOperation(value = "小程序拼团规则描述",notes = "小程序拼团规则描述")
    R<String> groupRuleDetail(@PathVariable("id") Long activityBaseId);

    @GetMapping("/applets/grouporder/getgroupgoodslist")
    @ApiOperation(value = "拼团活动商品展示",notes = "拼团活动商品展示")
    R<List<GroupGoodsListDTO>> getGroupGoodsList();

    @GetMapping("/applets/grouporder/getgrouporderdetail")
    @ApiOperation(value = "参团信息展示",notes = "参团信息展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrderId", value = "拼团Id", paramType = "query",dataType = "Long",required = true),
    })
    R<GroupOrderDetaiInfoDTO> getGroupOrderDetail(Long groupOrderId);

    @GetMapping("/applets/grouporder/getgrouporderapplets/{id}")
    @ApiOperation(value = "拼团详情页展示",notes = "拼团详情页展示")
    R<GroupOrderAppletsDTO> getGroupOrderApplets(@PathVariable("id") Long groupOrderId);

    @GetMapping("/applets/grouporder/getHotGroupOrderList")
    @ApiOperation(value = "火热拼团列表",notes = "火热拼团列表")
    R<GroupGoodsHotDTO> getHotGroupOrderList(@Validated @NotNull Long groupOrderId);


}
