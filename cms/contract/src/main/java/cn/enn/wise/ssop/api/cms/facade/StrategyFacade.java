package cn.enn.wise.ssop.api.cms.facade;

import cn.enn.wise.ssop.api.cms.dto.request.StrategySaveParam;
import cn.enn.wise.ssop.api.cms.dto.request.StrateryQueryParam;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleStrategyDTO;
import cn.enn.wise.ssop.api.cms.dto.response.StrategyDTO;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 合作伙伴接口
 * @author lishuiquan
 * @since 2019-11-18
 */
@Api(value = "攻略", tags = {"攻略"})
@FeignClient(value = "cms-service")
public interface StrategyFacade {


    @ApiOperation(value = "分页查询攻略", notes = "分页查询攻略")
    @GetMapping(value = "/strategy/list")
    R<QueryData<SimpleStrategyDTO>> getStrategyList(@Validated StrateryQueryParam QueryParam);

    @ApiOperation(value = "攻略详情", notes = "根据id获取攻略详情")
    @GetMapping(value = "/strategy/detail/{id}")
    R<StrategyDTO> getStrategyDetailById(@PathVariable Long id);

    @ApiOperation(value = "保存攻略", notes = "增加或修改攻略")
    @PostMapping(value = "/strategy/save")
    R<Long> save(@Validated @RequestBody StrategySaveParam StrategyAddParam);

    @ApiOperation(value = "删除攻略", notes = "根据id删除攻略")
    @DeleteMapping(value = "/strategy/delete/{id}")
    R<Boolean> delete(@PathVariable Long id);

    @ApiOperation(value = "禁用攻略", notes = "禁用攻略")
    @PutMapping(value = "/strategy/lock/{id}")
    R<Boolean> lock(@PathVariable Long id);

    @ApiOperation(value = "启用攻略", notes = "启用攻略")
    @PutMapping(value = "/strategy/unlock/{id}")
    R<Boolean> unlock(@PathVariable Long id);
}
