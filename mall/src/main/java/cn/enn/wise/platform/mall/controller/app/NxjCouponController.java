package cn.enn.wise.platform.mall.controller.app;

import cn.enn.wise.platform.mall.bean.param.AppCouParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.NxjCouponService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.StaffAuthRequired;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-03-31 14:59
 **/
@Slf4j
@RestController
@RequestMapping("/coupon")
@Api("app商家核销")
public class NxjCouponController extends BaseController {

    @Autowired
    private NxjCouponService nxjCouponService;

    @PostMapping("/tag/goods")
    @ApiOperation("列表")
    @StaffAuthRequired
    public ResponseEntity<PageInfo<OrderCouVo>> getGoodsOrProjectList(@RequestBody AppCouParam appCouParam, Integer pageNum, Integer pageSize,
                                                                      @Value("#{request.getAttribute('currentUser')}") User user){
        appCouParam.setBusinessId(user.getId());
        return ResponseEntity.success(nxjCouponService.list(appCouParam, pageNum, pageSize));
    }

    @GetMapping("/order/info")
    @ApiOperation("订单详情")
    public ResponseEntity<OrderCouInfo> getGoodsOrProjectList(String orderCode){
        if(StringUtils.isBlank(orderCode)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"orderCode不能为空");
        }
        return ResponseEntity.success(nxjCouponService.getOrderCouInfo(orderCode));
    }

    @GetMapping("/order/can")
    @ApiOperation("订单核销")
    @StaffAuthRequired
    public ResponseEntity<Boolean> orderCan(String orderCode,@Value("#{request.getAttribute('currentUser')}") User user){
        if(StringUtils.isBlank(orderCode)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"orderCode不能为空");
        }
        return ResponseEntity.success(nxjCouponService.orderCan(orderCode,user.getId()));
    }

    @GetMapping("/cou/can")
    @ApiOperation("优惠卷核销")
    @StaffAuthRequired
    public ResponseEntity<Boolean> couCan(Long id,Double couponPrice,
                                          @Value("#{request.getAttribute('currentUser')}") User user){
        if(null == id){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"id不能为空");
        }
        return ResponseEntity.success(nxjCouponService.couCan(user.getId(),id,couponPrice,user.getName()));
    }

    @GetMapping("/cou/user")
    @ApiOperation("优惠卷详情")
    @StaffAuthRequired
    public ResponseEntity<CouInfo> orderCan(Long id,@Value("#{request.getAttribute('currentUser')}") User user){
        if(null == id){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"id不能为空");
        }
        return ResponseEntity.success(nxjCouponService.getCouInfo(id,user.getId()));
    }

    @GetMapping("/up/list")
    @ApiOperation("线上列表")
    @StaffAuthRequired
    public ResponseEntity<PageInfo<OrderCouInfo>> upList(Integer pageNum, Integer pageSize,
                                                         @Value("#{request.getAttribute('currentUser')}") User user){
        return ResponseEntity.success(nxjCouponService.couList(user.getId(),pageNum, pageSize));
    }

    @GetMapping("/down/list")
    @ApiOperation("线下列表")
    @StaffAuthRequired
    public ResponseEntity<PageInfo<CouInfo>> downList(Integer pageNum, Integer pageSize,
                                                      @Value("#{request.getAttribute('currentUser')}") User user){
        return ResponseEntity.success(nxjCouponService.downList(user.getId(),pageNum, pageSize));
    }

    @GetMapping("/cou/total")
    @ApiOperation("统计")
    @StaffAuthRequired
    public ResponseEntity<CouCountInfo> total(Integer pageNum, Integer pageSize,String time,
                                              @Value("#{request.getAttribute('currentUser')}") User user){
        return ResponseEntity.success(nxjCouponService.count(user.getId(),pageNum, pageSize, time));
    }
}
