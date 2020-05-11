package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponRuleBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.GoodsCouponRule;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GoodsCouponRuleService;
import cn.enn.wise.platform.mall.service.GoodsCouponService;
import cn.enn.wise.platform.mall.service.UserOfCouponService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.UserUtil;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author jiabaiye
 * @Description
 * @Date19-12-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("/applets-coupon")
@Api(value = "applets优惠券管理", tags = {"applets优惠券管理"})
public class GoodsCouponAppletController extends BaseController {

    @Autowired
    private GoodsCouponService goodsCouponService;

    @Autowired
    private UserOfCouponService userOfCouponService;

    @Autowired
    private GoodsCouponRuleService goodsCouponRuleService;

    @PostMapping("/newpeoplecouponlist")
    @ApiOperation(value = "新人优惠券列表", notes = "新人优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "景区id，在header中传递", required = true, dataType = "Long"),
    })
    public ResponseEntity<List<CouponParam>> getNewPeopleCouponList(@RequestHeader("companyId")String companyId,@RequestHeader("openId")String openId,@RequestHeader("appId")String appId) {
        if(openId==null||"".equals(openId)){
            return goodsCouponService.getNewPeopleCouponList(Long.parseLong(companyId),null);
        }else{
            if(companyId==null){
                return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"景区id不能为空");
            }else if(appId==null){
                return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"appId不能为空");
            }
            User user = UserUtil.getUserByToken(appId,companyId,openId);
            //根据openid过滤领取过的优惠券
            return goodsCouponService.getNewPeopleCouponList(Long.parseLong(companyId),user.getId());
        }
    }


    @PostMapping("/getcouponlist")
    @ApiOperation(value = "优惠券列表", notes = "优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "景区id，在header中传递", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态 0 未领取 1已领取未使用 2已使用 3已过期", required = true, dataType = "String"),
    })
    @OpenIdAuthRequired
    public ResponseEntity<List<CouponParam>> getCouponList(@Param("status") String status, @RequestHeader("companyId")String companyId, @Value("#{request.getAttribute('currentUser')}") User user) {

        if(companyId==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"景区id不能为空");
        }else if(status==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券状态不能为空");
        }
//        user = new User();
//        user.setId(13L);
        return goodsCouponService.getCouponList(Long.parseLong(companyId),user.getId(),status);

    }

    @PostMapping("/getcouponlistV2_1")
    @ApiOperation(value = "优惠券列表", notes = "优惠券列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "景区id，在header中传递", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态 0 未领取 1已领取未使用 2已使用 3已过期", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "分销商手机号", required = true, dataType = "String"),
    })
    @OpenIdAuthRequired
    public ResponseEntity<List<CouponParam>> getCouponListV2_1(@Param("phone") String phone,@Param("status") String status, @RequestHeader("companyId")String companyId, @Value("#{request.getAttribute('currentUser')}") User user) {

        if(companyId==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"景区id不能为空");
        }else if(status==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券状态不能为空");
        }
//        user = new User();
//        user.setId(13L);
        return goodsCouponService.getCouponListV2_1(Long.parseLong(companyId),user.getId(),status,phone);

    }

    @PostMapping("/isnewpeople")
    @ApiOperation(value = "判断是否为新人", notes = "判断是否为新人")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "openId", value = "openid，在header中传递", required = true, dataType = "String"),
    })
    @OpenIdAuthRequired
    public ResponseEntity<List<CouponParam>> isNewPeople( @Value("#{request.getAttribute('currentUser')}") User user) {
        return goodsCouponService.isNewPeople(user.getId());
    }

    @PostMapping("/getcouponinfo")
    @ApiOperation(value = "优惠券详情", notes = "优惠券详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券列表中UserOfCouponId", required = true, dataType = "Long"),
    })
    public ResponseEntity getCouponInfo(@Param("id") Long id) {
        if(id==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券id不能为空");
        }
        return goodsCouponService.getCouponInfo(id);
    }
    @PostMapping("/saveuserofcouponlist")
    @ApiOperation(value = "领取新人优惠券", notes = "领取新人优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsCouponId", value = "优惠券id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "转增人id，当优惠券来源为朋友赠送的时候，此处填写是谁赠送的", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "couponResource", value = "优惠券来源 1-自己领取 2-朋友赠送 ，此处批量领取只适用新人优惠券，自己领取", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "promotionId", value = "活动id", required = true, dataType = "Long"),
    })
    @OpenIdAuthRequired
    public ResponseEntity saveUserOfCoupons(@RequestBody List<UserOfCouponParam> userOfCoupons, @Value("#{request.getAttribute('currentUser')}") User user,@RequestHeader("openId")String openId) {
        if(user==null){
//            user = new User();
//            user.setId(13L);
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"用户不存在");
        }
        for(UserOfCouponParam userOfCoupon:userOfCoupons){
            userOfCoupon.setOpenId(openId);
            userOfCouponService.saveUserOfCoupon(userOfCoupon,user.getId());
        }
        //初始化用户id
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"领取成功");
    }

    @PostMapping("/saveuserofcoupon")
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsCouponId", value = "优惠券id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "转增人id，当优惠券来源为朋友赠送的时候，此处填写是谁赠送的", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "couponResource", value = "优惠券来源 1-自己领取 2-朋友赠送", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "promotionId", value = "活动id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "userOfCouponId", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "分销商手机号", required = true, dataType = "String"),
    })
    @OpenIdAuthRequired
    public ResponseEntity saveUserOfCoupon(@RequestBody UserOfCouponParam userOfCoupon, @Value("#{request.getAttribute('currentUser')}") User user,@RequestHeader("openId")String openId) {
        //判断参数是否为空
        ResponseEntity responseEntity = UserOfCouponParam.validateUserOfCouponParam(userOfCoupon);
        if(responseEntity.getResult()!=1){
            return responseEntity;
        }
        if(user==null){


//            user = new User();
//            user.setId(13L);
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"用户不存在");
        }
        //userOfCoupon.setUserId(user.getId());
        userOfCoupon.setOpenId(openId);
        return userOfCouponService.saveUserOfCoupon(userOfCoupon,user.getId());
    }



    @PostMapping("/updateuserofcoupon")
    @ApiOperation(value = "转增优惠券，修改状态为转增中", notes = "转增优惠券，修改状态为转增中")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券领取记录id（优惠券列表信息中的UserOfCouponId）", required = true, dataType = "Long"),@ApiImplicitParam(paramType = "query", name = "promotionId", value = "活动id", required = true, dataType = "Long"),
    })
    @OpenIdAuthRequired
    public ResponseEntity updadteUserOfCoupon(@RequestBody UserOfCouponParam userOfCoupon, @Value("#{request.getAttribute('currentUser')}") User user) {
        //判断参数是否为空
        if(userOfCoupon.getId()==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券领取记录id不能为空");
        }else if(user==null){
//            user = new User();
//            user.setId(13L);
           return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"用户不存在");
        }
        userOfCoupon.setUserId(user.getId());
        return userOfCouponService.updateUserOfCoupon(userOfCoupon);
    }


    @GetMapping("/coupon/list")
    @OpenIdAuthRequired
    @ApiOperation("预下单页面获取优惠券")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "projectId",value = "项目Id",required = true),
            @ApiImplicitParam(name = "status",value = " 1 未领取的优惠券 2 已领取并且可以使用的优惠券 3 已领取但不适用于当前项目的优惠券",required = true)
    })
    public ResponseEntity<List<CouponParam>> getCouponListByUser(@RequestHeader("companyId")Long companyId,
                                              @Value("#{request.getAttribute('currentUser')}") User user,
                                              Long projectId,
                                              Integer status,
                                              Long goodsId,
                                              String phone){
        if(projectId == null||goodsId == null || status == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不正确!");

        }



        List<CouponParam> coupon = goodsCouponService.getCoupon(companyId, user.getId(), projectId, status,goodsId,phone);

        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/coupon/amount")
    @OpenIdAuthRequired
    @ApiOperation("获取未领取的优惠券数量和已领取的优惠券数量")
    public ResponseEntity<Map<String,Object>> getCouponAmount( @Value("#{request.getAttribute('currentUser')}") User user,@RequestHeader("companyId")Long companyId,Long projectId,Long goodsId,String phone){

        if(goodsId == null || projectId == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不正确!");
        }

        Map<String,Object> map = new HashMap<>(2);
        map.put("notReceivedAmount",0);
        map.put("receivedAmount",0);
        try {
            List<CouponParam> notReceived = goodsCouponService.getCoupon(companyId, user.getId(), projectId, 1,goodsId,phone);
            List<CouponParam> received = goodsCouponService.getCoupon(companyId, user.getId(), projectId, 2,goodsId,phone);
            map.put("notReceivedAmount",CollectionUtils.isEmpty(notReceived)?0:notReceived.size());
            map.put("receivedAmount",CollectionUtils.isEmpty(received)?0:received.size());

        }catch (Exception e){
          e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }
    @ApiOperation(value = "统计个人拥有多少未使用的优惠券", notes = "统计个人拥有多少未使用的优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "Long")
    })
    @PostMapping("/getcount")
    @OpenIdAuthRequired
    public ResponseEntity getCount(@Value("#{request.getAttribute('currentUser')}") User user,@Param("phone") String phone)  {
        if(user==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"用户信息不能为空");
        }
        Integer couponSize = userOfCouponService.countCoupon(user.getId(),phone);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"统计成功",couponSize);
    }


    @ApiOperation(value = "优惠券使用规则", notes = "优惠券使用规则")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "promotionId", value = "活动id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券id", required = true, dataType = "Long")
    })
    @GetMapping("/rule")
    public ResponseEntity getRule(Long promotionId,Long couponId)  {
        QueryWrapper<GoodsCouponRuleBo> wrapper = new QueryWrapper<>();
        wrapper.eq("promotion_id",promotionId);
        wrapper.eq("goods_coupon_id",couponId);
        wrapper.last(" limit 1 ");
        GoodsCouponRule  rule = goodsCouponRuleService.getOne(wrapper);
        if(rule == null){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"暂时没有使用规则");
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",rule);
    }
}
