package cn.enn.wise.ssop.service.promotions.controller.app;

import cn.enn.wise.ssop.api.promotions.dto.response.CouponVerifyDTO;
import cn.enn.wise.ssop.service.promotions.service.UserOfCouponsService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangliheng
 * @date 2020/4/20 10:52 上午
 */
@RestController
@Api(value = "体验券核销", tags = {"体验券核销"})
@RequestMapping("")
public class CouponVerificationController {

    @Autowired
    private UserOfCouponsService userOfCouponsService;

    @ApiOperation(value = "体验券详情", notes = "体验券详情")
    @GetMapping(value = "/coupon/verify/detail")
    public R<CouponVerifyDTO> couponDetail(String userId,String couponCode) {
        return new R<>(userOfCouponsService.couponDetail(userId, couponCode));
    }

    @ApiOperation(value = "体验券核销", notes = "体验券核销")
    @GetMapping(value = "/coupon/verify/status")
    public R<Boolean> couponVerify(String couponCode) {
        return new R<>(userOfCouponsService.couponVerify(couponCode));
    }
}
