/*
package cn.enn.wise.ssop.service.order.controller;

import cn.enn.wise.ssop.service.order.service.WithdrawalService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(value = "中心API", tags = {"订单中心API"})
public class ShortMessageController {

    @Autowired
    private WithdrawalService service;

    @GetMapping("/send-complement")
    @ApiOperation(value = "补发短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerials", value = "补发短信,使用半角逗号分隔",required = true,paramType = "query"),
    })
    public R<String> sendComplement(String withdrawSerials){
        service.sendComplementMessage(withdrawSerials);
        service.clearWithdrawPermitStatus(withdrawSerials);
        return R.success("OK");
    }
}
*/
