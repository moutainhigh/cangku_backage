package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.api.cms.dto.request.StrategySaveParam;
import cn.enn.wise.ssop.api.cms.dto.request.StrateryQueryParam;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleStrategyDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyDTO;
import cn.enn.wise.ssop.api.cms.facade.StrategyFacade;
import cn.enn.wise.ssop.service.cms.service.StrategyService;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author shiz
 * 攻略接口
 */
@RestController
@Api(value = "攻略管理", tags = {"攻略管理"})
@RequestMapping("/strategy")
public class StrategyController implements StrategyFacade {

    @Autowired
    StrategyService strategyService;



    @ApiOperation(value = "分页查询攻略", notes = "分页查询攻略")
    @GetMapping(value = "/list")
    @Override
    public R<QueryData<SimpleStrategyDTO>> getStrategyList(@Validated StrateryQueryParam QueryParam) {
        return new R<>(strategyService.getStrategyList(QueryParam));
    }

    @ApiOperation(value = "攻略详情", notes = "根据id获取攻略详情")
    @GetMapping(value = "/detail/{id}")
    @Override
    public R<StrategyDTO> getStrategyDetailById(@PathVariable Long id) {
        StrategyDTO detail = strategyService.getStrategyrDetail(id);
        return detail!=null?R.success(detail):R.error("没有查到");
    }

    @ApiOperation(value = "保存攻略", notes = "增加或修改攻略")
    @PostMapping(value = "/save")
    @Override
    public R<Long> save(@Validated @RequestBody StrategySaveParam StrategyAddParam) {
        return new R<>(strategyService.saveStrategy(StrategyAddParam));
    }

    @ApiOperation(value = "删除攻略", notes = "根据id删除攻略")
    @DeleteMapping(value = "/delete")
    @Override
    public R<Boolean> delete(@RequestParam("id") Long id) {
        return R.success(strategyService.delStrategy(id));
    }

    @ApiOperation(value = "禁用攻略", notes = "禁用攻略")
    @PutMapping(value = "/lock")
    @Override
    public R<Boolean> lock(@RequestParam("id") Long id) {
        return R.success(strategyService.editState(id, Byte.valueOf("2")));
    }

    @ApiOperation(value = "启用攻略", notes = "启用攻略")
    @PutMapping(value = "/unlock")
    @Override
    public R<Boolean> unlock(@RequestParam("id") Long id) {
        return R.success(strategyService.editState(id, Byte.valueOf("1")));
    }


//    @Autowired
//    ScenicAreaFacade scenicAreaFacade;
//    @Autowired
//    GoodsExtendFacade goodsExtendFacade;

    @ApiOperation(value = "test", notes = "test")
    @GetMapping(value = "/test")
    public R test() {
//        StrategyMapper baseMapper = strategyService.getBaseMapper();
//        R<List<SelectData>> scenicAreaSelectList = scenicAreaFacade.getScenicAreaSelectList();
//        System.out.println(goodsExtendFacade);

        strategyService.test();
        return R.success("");
    }

}
