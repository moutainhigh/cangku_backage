package cn.enn.wise.ssop.service.promotions.controller.app;

import cn.enn.wise.ssop.api.promotions.dto.request.UserOfCouponsSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.UserCanUseCouponDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.UserHaveCouponDTO;
import cn.enn.wise.ssop.service.promotions.service.UserOfCouponsService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "用户领券", tags = {"用户领券"})
@RequestMapping("")
public class UserOfCouponsController {


    @Autowired
    private UserOfCouponsService userOfCouponsService;

    @ApiOperation(value = "发送给用户优惠券-用户下单后进行", notes = "发送给用户优惠券-用户下单后进行")
    @PostMapping(value = "/coupon/user/receive")
    public R<Boolean> saveUserOfCoupon(@RequestBody UserOfCouponsSaveParam userOfCouponsSaveParam) {
        return new R<>(userOfCouponsService.saveUserOfCoupons(userOfCouponsSaveParam));
    }

    @ApiOperation(value = "查询用户拥有的优惠券", notes = "查询用户拥有的优惠券")
    @GetMapping(value = "/coupon/user/have")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "userId", value = "用户id"),
            @ApiImplicitParam(paramType = "query", dataType = "byte", name = "state", value = "状态( 1未领取 2领取未使用 3已使用  4已过期 5转让中 6已转让)")
    })
    public R<List<UserHaveCouponDTO>> userOfCoupon(@RequestParam("userId") Long userId,
                                                   @RequestParam("state") Byte state)  {
        return new R<>(userOfCouponsService.userOfCoupon(userId,state));
    }

    @ApiOperation(value = "获取可用优惠券", notes = "获取可用优惠券")
    @GetMapping(value = "/coupon/user/use")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "goodsId", value = "产品id"),
    })
    public R<List<UserCanUseCouponDTO>> userOfCoupon(@RequestParam("goodsId") Long goodsId)  {
        return new R<>(userOfCouponsService.getUserCanUseCouponDTO(goodsId));
    }

    @ApiOperation(value = "用户领取优惠券", notes = "用户领取优惠券")
    @PutMapping(value = "/coupon/user/receive")
    public R<Boolean> userReceiveCoupon(Long id) {
        return new R<>(userOfCouponsService.userReceiveCoupon(id));
    }

    @ApiOperation(value = "用户使用优惠券", notes = "用户使用优惠券")
    @PutMapping(value = "/coupon/user/use")
    public R<Boolean> userUseCoupon(Long id) {
        return new R<>(userOfCouponsService.userUseCoupon(id));
    }

    @ApiOperation(value = "用户转让优惠券", notes = "用户转让优惠券")
    @PutMapping(value = "/coupon/user/transfer")
    public R<Boolean> userTransferCoupon(Long id,Long userId,String userName,String openId) {
        return new R<>(userOfCouponsService.userTransferCoupon(id,userId,userName,openId));
    }

}
