package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.param.HotelOrderParam;
import cn.enn.wise.platform.mall.bean.param.HotelOrderParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderAdminVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.HotelOrderService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 酒店订单
 *
 * @author anhui
 * @since 2019-09-22
 */
@RestController
@RequestMapping("/pc/hotel/order")
@Api(value = "后台管理酒店订单", tags = {"后台管理酒店订单"})
public class HotelAdminController extends BaseController {


    @Autowired
    HotelOrderService hotelOrderService;

    @PostMapping("/list")
    @ApiOperation("获取项目列表")
    public ResponseEntity<ResPageInfoVO<List<HotelOrderAdminVo>>> getHotelOrderList(@RequestBody ReqPageInfoQry<HotelOrderParams> orderParams,@RequestHeader("token") String token) {
        this.getUserByToken(token);
        return hotelOrderService.listHotelOrder(orderParams);

    }

    @PostMapping("/refund")
    @ApiOperation("退单")
    public ResponseEntity refundHotelOrder(@RequestBody HotelOrderParam param, @RequestHeader("token") String token) {
        this.getUserByToken(token);
        return hotelOrderService.refundOrderById(param.getOrderCode());
    }

    @PostMapping("/check/in")
    @ApiOperation("入住")
    public ResponseEntity checkIn(@RequestBody HotelOrderParam param ,@RequestHeader("token") String token) {
        this.getUserByToken(token);
        return hotelOrderService.checkIn(param.getOrderCode());
    }

}
