package cn.enn.wise.ssop.api.promotions.facade;


import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@FeignClient("promotions-service")
public interface CouponFacade {

    @ApiOperation(value = "新增优惠券", notes = "新增优惠券")
    @PostMapping(value = "/coupon/save")
    R<Boolean> save(@RequestBody CouponSaveParam couponSaveParam);

    @ApiOperation(value = "电子券列表", notes = "电子券列表")
    @GetMapping(value = "/coupon/list")
    R<QueryData<CouponListDTO>> list(CouponListParam couponListParam);

    @ApiOperation(value = "电子券列表", notes = "电子券列表")
    @GetMapping(value = "/coupon/list/all")
    R<List<CouponListAllDTO>> list();

    @ApiOperation(value = "电子券详情", notes = "电子券详情")
    @GetMapping(value = "/coupon/detail")
    R<CouponDetailDTO> detail(@RequestParam("id") Long id);

    @ApiOperation(value = "编辑优惠券", notes = "编辑优惠券")
    @PutMapping(value = "/coupon/update")
    R<Boolean> update(@RequestBody CouponUpdateParam couponUpdateParam);

    @ApiOperation(value = "电子券状态修改", notes = "电子券状态修改")
    @PutMapping(value = "/coupon/update/status")
    R<Boolean> updateStatus(@RequestParam("id") Long id);

    @ApiOperation(value = "消费明细", notes = "消费明细")
    @GetMapping(value = "/coupon/consumption/list")
    R<UserOfCouponDTO> consumptionList(UserOfCouponsListParam userOfCouponsListParam);

    @ApiOperation(value = "电子券列表-优惠活动", notes = "电子券列表-优惠活动")
    @GetMapping(value = "/coupon/list/pro")
    R<QueryData<CouponListProDTO>> listPro(CouponListProParam couponListProParam);

    @ApiOperation(value = "电子券信息-订单", notes = "电子券信息-订单")
    @PostMapping(value = "/coupon/order")
    R<List<CouponOrderDTO>> couponOrder(@RequestBody List<CouponOrderParam> couponOrderParamList);
}
