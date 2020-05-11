package cn.enn.wise.ssop.service.order.controller.pc;

import cn.enn.wise.ssop.api.order.facade.OrderSkuNum;
import cn.enn.wise.ssop.service.order.service.OrderGoodsService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
@Api(value = "商品数量查询", tags = {"商品数量查询"})
public class OrderSkuController implements OrderSkuNum {

    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 查询商品数量
     * @param skuId
     * @return Integer
     * */
    @GetMapping("/ticket/amount/list")
    @ApiOperation(value = "商品sku的amount数量",notes="商品sku的amount数量")
    @Override
    public R<Integer> orderSkuList(@RequestParam Long skuId) {
        return new R(orderGoodsService.selectAmount(skuId));
    }
}
