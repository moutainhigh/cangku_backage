package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorRigisterParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorAddDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 耿小洋
 * @date 2020/3/31 4:42 下午
 */
@Api(
        value = "分销商",
        tags = {"分销商"}
)
@FeignClient("promotions-service")
public interface DistributorAppFacade {

    @ApiOperation(value = "App端分销商注册", notes = "App端分销商注册")
    @PostMapping(value = "/app/distributor/registerDistribution")
    R registerDistribution(@Validated @RequestBody DistributorRigisterParam param);


    @ApiOperation(value = "根据分销商id获取补充信息", notes = "根据分销商id获取补充信息")
    @GetMapping(value = "/app/distributor/getDistributionAddByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    R<DistributorAddDTO> getDistributionAddByDistribuBaseId(Long distribuBaseId);


    @ApiOperation(value = "修改分销商补充信息", notes = "修改分销商补充信息")
    @PutMapping(value = "/app/distributor/updateDistributionAdd")
    R updateDistributionAdd(@Validated @RequestBody DistributorAddParam param);


}
