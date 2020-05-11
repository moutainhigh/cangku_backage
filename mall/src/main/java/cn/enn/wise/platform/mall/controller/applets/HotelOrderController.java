package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.vo.HotelOrderVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.HotelOrderService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 酒店订单APi
 *
 * @author baijie
 * @date 2019-09-20
 */
@RestController
@RequestMapping("/hotel/order")
@Api("酒店订单Api")
public class HotelOrderController extends BaseController {

    @Autowired
    private HotelOrderService hotelOrderService;

    @PostMapping("/save")
    @ApiOperation("创建订单")
    @OpenIdAuthRequired
    public ResponseEntity saveOrder(@RequestBody HotelOrderVo hotelOrderVo,
                                    @Value("#{request.getAttribute('currentUser')}") User user,
                                    HttpServletRequest httpServletRequest,
                                    @RequestHeader("openId") String openId,
                                    @RequestHeader("companyId") String scenicId) throws Exception {

        hotelOrderVo.setScenicId(Long.valueOf(scenicId));
        ParamValidateUtil.ValidateSaveOrderVo(hotelOrderVo);

        hotelOrderVo.setIp(IpAddressUtil.getIp(httpServletRequest));
        hotelOrderVo.setUserId(user.getId());
        hotelOrderVo.setOpenId(openId);

        Object o = hotelOrderService.saveOrder(hotelOrderVo);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,o);
    }

    @GetMapping("/list")
    @OpenIdAuthRequired
    @ApiOperation("获取用户订单列表")
    public ResponseEntity userOrderList(@Value("#{request.getAttribute('currentUser')}") User user){

        return  ResponseEntity.success(hotelOrderService.getUserOrderList(user.getId()));
    }

    @GetMapping("/detail")
    @OpenIdAuthRequired
    @ApiOperation("获取用户订单详情")
    public ResponseEntity userOrderDetail(@Value("#{request.getAttribute('currentUser')}") User user,String orderCode){
        if(StringUtils.isEmpty(orderCode)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"orderCode不能为空");
        }

        return ResponseEntity.success(hotelOrderService.getOrderInfo(orderCode,user.getId()));
    }


    @GetMapping("/cancel")
    @OpenIdAuthRequired
    @ApiOperation("用户取消订单")
    public ResponseEntity cancelOrder(@Value("#{request.getAttribute('currentUser')}") User user,String orderId) throws Exception {

        if(StringUtils.isEmpty(orderId)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"orderId不能为空");
        }

            hotelOrderService.userCancelOrder(orderId,user.getId());

        return ResponseEntity.success();
    }


    @PostMapping("/pay/old")
    @OpenIdAuthRequired
    @ApiOperation("待支付订单支付")
    public ResponseEntity payOldOrder(@Value("#{request.getAttribute('currentUser')}") User user,
                                      String orderCode,
                                      HttpServletRequest httpServletRequest,
                                      @RequestHeader("openId") String openId,
                                      @RequestHeader("companyId") String scenicId) throws Exception {

        HotelOrderVo orderVo = new HotelOrderVo();
        if(StringUtils.isEmpty(orderCode)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"订单号不能为空");
        }

        orderVo.setOpenId(openId);
        orderVo.setScenicId(Long.valueOf(scenicId));
        orderVo.setUserId(user.getId());
        orderVo.setOpenId(openId);
        orderVo.setOrderCode(orderCode);
        orderVo.setIp(IpAddressUtil.getIp(httpServletRequest));


        Object payParam = hotelOrderService.payOldOrder(orderVo);

        return ResponseEntity.success(payParam);
    }

}
