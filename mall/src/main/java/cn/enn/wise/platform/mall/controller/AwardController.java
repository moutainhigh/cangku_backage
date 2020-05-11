package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.vo.DrawCouponVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.bean.vo.UserDrawVo;
import cn.enn.wise.platform.mall.service.AwardService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/31 17:02
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Api(value = "抽奖管理Controller", tags = {"抽奖管理Controller"})
@RestController
@RequestMapping("/award")
@Slf4j
public class AwardController extends BaseController {

    @Autowired
    private AwardService awardService;


    @GetMapping("/draw")
    @OpenIdAuthRequired
    @ApiOperation(value = "小程序用户抽奖", notes = "小程序用户抽奖")
    public ResponseEntity<UserDrawVo> userDraw(@Value("#{request.getAttribute('currentUser')}") User user, @RequestHeader("openId") String openId) throws Exception {
        ResponseEntity<UserDrawVo> resultVo = new ResponseEntity<>();
        long startTime = System.currentTimeMillis();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isEmpty(openId)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }

        UserDrawVo userDrawVo = awardService.userDraw(user, openId);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(userDrawVo);
        long endTime = System.currentTimeMillis();
        log.info("draw use time is:" + ((endTime - startTime)) / 1000d + " s");
        return resultVo;
    }

    @GetMapping("/coupon/list")
    @OpenIdAuthRequired
    @ApiOperation(value = "小程序优惠券列表", notes = "小程序优惠券列表")
    public ResponseEntity<List<DrawCouponVo>> findDrawCouponList(@Value("#{request.getAttribute('currentUser')}") User user, @RequestHeader("openId") String openId) throws Exception {
        ResponseEntity<List<DrawCouponVo>> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;

        }

        if (Strings.isEmpty(openId)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }
        List<DrawCouponVo> drawCouponVo = awardService.findDrawCouponList(user, openId);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(drawCouponVo);
        return resultVo;
    }


    @GetMapping("/judge")
    @OpenIdAuthRequired
    @ApiOperation(value = "判断优惠券是否可用", notes = "判断优惠券是否可用")
    public ResponseEntity<DrawCouponVo> judgeCouponUsable(@Value("#{request.getAttribute('currentUser')}") User user, @RequestHeader("openId") String openId, Integer goodsId, Integer couponId) throws Exception {
        ResponseEntity<DrawCouponVo> resultVo = new ResponseEntity<>();
        if (null == user) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;

        }
        if (Strings.isEmpty(openId)) {
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }
        DrawCouponVo drawCouponVo = awardService.judgeCouponUsable(user, openId, goodsId, couponId);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(drawCouponVo);

        return resultVo;
    }


}
