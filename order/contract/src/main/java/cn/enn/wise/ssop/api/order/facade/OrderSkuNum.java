package cn.enn.wise.ssop.api.order.facade;

import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service")
public interface OrderSkuNum {

    /**
     * 查询商品数量
     * @param skuId 商品skuid
     * @return Integer 数量
     * */
    @GetMapping("/order/ticket/amount/list")
    @ApiOperation(value = "商品id搜索数量",notes="商品id搜索数量")
    R<Integer> orderSkuList(@RequestParam(value = "skuId") Long skuId);

}
