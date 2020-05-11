package cn.enn.wise.ssop.service.promotions.controller;

import cn.enn.wise.ssop.api.goods.dto.response.GoodsExtendOrderDTO;
import cn.enn.wise.ssop.api.goods.facade.GoodsExtendFacade;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author wangliheng
 * @date 2020/4/16 7:51 下午
 */
@RestController
@Api(value = "test", tags = {"test"})
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private GoodsExtendFacade goodsExtendFacade;


    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/test")
    public R<List<GoodsExtendOrderDTO>> test() {
        R<List<GoodsExtendOrderDTO>> lists = goodsExtendFacade.getGoodsExtendListOrder("257,258");
        log.info("result : " +lists);
        return new R<>();
    }
}
