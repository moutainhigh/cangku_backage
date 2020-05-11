package cn.enn.wise.ssop.service.promotions.controller;

import cn.enn.wise.uncs.base.constant.BusinessEnumInit;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "全局接口" , tags = {"全局接口"})
@RestController
public class GlobalController {

    @ApiOperation("枚举列表")
    @GetMapping("/enumList")
    public R<Map<String,List<SelectData>>> getExampleContentList() {
        return new R<>(BusinessEnumInit.enumMap);
    }
}
