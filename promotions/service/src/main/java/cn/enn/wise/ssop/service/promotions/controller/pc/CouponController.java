package cn.enn.wise.ssop.service.promotions.controller.pc;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.api.promotions.facade.CouponFacade;
import cn.enn.wise.ssop.service.promotions.service.CouponService;
import cn.enn.wise.ssop.service.promotions.service.UserOfCouponsService;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@RestController
@Api(value = "优惠券", tags = {"优惠券"})
@RequestMapping("")
public class CouponController implements CouponFacade {

    @Autowired
    private CouponService couponService;
    @Autowired
    private UserOfCouponsService userOfCouponsService;


    @ApiOperation(value = "新增优惠券", notes = "新增优惠券")
    @PostMapping(value = "/coupon/save")
    @Override
    public R<Boolean> save(@RequestBody CouponSaveParam couponSaveParam) {
        return new R<>(couponService.saveCoupon(couponSaveParam));
    }

    @ApiOperation(value = "电子券列表", notes = "电子券列表")
    @GetMapping(value = "/coupon/list")
    @Override
    public R<QueryData<CouponListDTO>> list(CouponListParam couponListParam) {
        return new R<>(couponService.getCouponList(couponListParam));
    }

    @ApiOperation(value = "电子券列表-全部，扫码", notes = "电子券列表-全部，扫码")
    @GetMapping(value = "/coupon/list/all")
//    @Override
    public R<List<CouponListAllDTO>> list() {
        return new R<>(couponService.getAll());
    }

    @ApiOperation(value = "电子券详情", notes = "电子券详情")
    @GetMapping(value = "/coupon/detail")
    @Override
    public R<CouponDetailDTO> detail(@RequestParam Long id) {
        return new R<>(couponService.getCouponDetail(id));
    }

    @ApiOperation(value = "编辑优惠券", notes = "编辑优惠券")
    @PostMapping(value = "/coupon/update")
    @Override
    public R<Boolean> update(@RequestBody CouponUpdateParam couponUpdateParam) {
        return new R<>(couponService.updateCoupon(couponUpdateParam));
    }

    @ApiOperation(value = "电子券状态修改", notes = "电子券状态修改")
    @PutMapping(value = "/coupon/update/status")
    @Override
    public R<Boolean> updateStatus(@RequestParam Long id) {
        return new R<>(couponService.updateState(id));
    }

    @ApiOperation(value = "消费明细", notes = "消费明细")
    @GetMapping(value = "/coupon/consumption/list")
    @Override
    public R<UserOfCouponDTO> consumptionList(UserOfCouponsListParam userOfCouponsListParam) {
        return new R<>(userOfCouponsService.getListByPage(userOfCouponsListParam));
    }

    @ApiOperation(value = "电子券列表-优惠活动", notes = "电子券列表-优惠活动")
    @GetMapping(value = "/coupon/list/pro")
    @Override
    public R<QueryData<CouponListProDTO>> listPro(CouponListProParam couponListProParam) {
        return new R<>(couponService.getCouponProList(couponListProParam));
    }


    @ApiOperation(value = "电子券信息-订单", notes = "电子券信息-订单")
    @PostMapping(value = "/coupon/order")
    @Override
    public R<List<CouponOrderDTO>> couponOrder(@RequestBody List<CouponOrderParam> couponOrderParamList) {
        return new R<>(couponService.getCouponOrder(couponOrderParamList));
    }

}
