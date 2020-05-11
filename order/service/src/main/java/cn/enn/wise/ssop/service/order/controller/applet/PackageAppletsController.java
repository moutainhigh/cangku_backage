package cn.enn.wise.ssop.service.order.controller.applet;

import cn.enn.wise.ssop.api.order.dto.request.PackageOrderParam;
import cn.enn.wise.ssop.service.order.service.impl.PackageOrderServiceImpl;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 套餐下单接口
 * @author baijie
 * @date 2020-05-03
 */
@RestController
@RequestMapping("/applets/package")
@Api(value = "小程序套餐订单API", tags = {"小程序套餐订单API"})
@Slf4j
public class PackageAppletsController {

    @Autowired
    private PackageOrderServiceImpl packageOrderService;

    @PostMapping("/save")
    @ApiOperation(value = "统一下单入口",notes = "统一下单入口,不同商品，不同客户端下单的入口")
    public R<Long> saveHotelOrder(@RequestBody @Valid PackageOrderParam hotelOrderParam){
        hotelOrderParam.setDistributorPhone(hotelOrderParam.getDistributorMobile());

        Long orderId= packageOrderService.saveOrder(hotelOrderParam);

        return R.success(orderId);
    }
}
