package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 耿小洋
 * @date 2020/3/31 4:42 下午
 */
@Api(
        value = "分销商",
        tags = {"分销商"}
)
@FeignClient("promotions-service")

public interface DistributorFacade {

    @ApiOperation(value = "获取分销商列表", notes = "获取分销商列表")
    @GetMapping(value = "/distributor/getDistributorList")
    R<QueryData<List<DistributorListDTO>>> getDistributorList(@RequestParam("distributorBaseListParam") DistributorBaseListParam distributorBaseListParam);

    @ApiOperation(value = "保存分销商基础信息", notes = "保存分销商基础信息")
    @PostMapping(value = "/distributor/saveDistributionBase")
    R<DistributorBaseDTO> saveDistributionBase(@Validated @RequestBody DistributorBaseParam DistributorBaseParam);

    @ApiOperation(value = "修改分销商基础信息", notes = "修改分销商基础信息")
    @PutMapping(value = "/distributor/updateDistributionBase")
    R updateDistributionBase(@Validated @RequestBody DistributorBaseParam param);

    @ApiOperation(value = "根据分销商id获取基础信息", notes = "根据分销商id获取基础信息")
    @GetMapping(value = "/distributor/getDistributionBaseByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    R<DistributorBaseDTO> getDistributionBaseByDistribuBaseId(@RequestParam("distribuBaseId")Long distribuBaseId);

    @ApiOperation(value = "保存分销商联系人", notes = "保存分销商联系人")
    @PostMapping(value = "/distributor/saveDistributionContact")
    R saveDistributionContact(@Validated @RequestBody List<DistributorContactParam> param);


    @ApiOperation(value = "修改分销商联系人", notes = "修改分销商联系人")
    @PostMapping(value = "/distributor/updateDistributionContact")
    R updateDistributionContact(@Validated @RequestBody DistributorContactUpdateParam param);

    @ApiOperation(value = "根据分销商id获取联系人信息", notes = "根据分销商id获取联系人信息")
    @GetMapping(value = "/distributor/getDistributionContactByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    R<List<DistributorContactDTO>> getDistributionContactByDistribuBaseId(@RequestParam("distribuBaseId")Long distribuBaseId);

    @ApiOperation(value = "保存分销商财务信息", notes = "保存分销商财务信息")
    @PostMapping(value = "/distributor/saveDistributorBank")
    R saveDistributorBank(@Validated @RequestBody List<DistributorBankParam> param);


    @ApiOperation(value = "修改分销商财务信息", notes = "修改分销商财务信息")
    @PostMapping(value = "/distributor/updateDistributionBank")
    R updateDistributionBank(@Validated @RequestBody DistributorBankUpdateParam param);

    @ApiOperation(value = "根据分销商id获取财务信息", notes = "根据分销商id获取财务信息")
    @GetMapping(value = "/distributor/getDistributionBankByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    R<List<DistributorBankDTO>> getDistributionBankByDistribuBaseId(@RequestParam("distribuBaseId") Long distribuBaseId);

    @ApiOperation(value = "保存分销商业务信息", notes = "保存分销商业务信息")
    @PostMapping(value = "/distributor/saveDistributionBussiness")
    R saveDistributionBussiness(@Validated @RequestBody DistributorBusinessParam param);

    @ApiOperation(value = "修改分销商业务信息", notes = "修改分销商业务信息")
    @PutMapping(value = "/distributor/updateDistributionBussiness")
    R updateDistributionBussiness(@Validated @RequestBody DistributorBusinessParam param);

    @ApiOperation(value = "根据分销商id获取业务信息", notes = "根据分销商id获取业务信息")
    @GetMapping(value = "/distributor/getDistributionBussinessByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    R<DistributorBusinessDTO> getDistributionBussinessByDistribuBaseId( @RequestParam("distribuBaseId")Long distribuBaseId);


    @ApiOperation(value = "保存分销商补充信息", notes = "保存分销商补充信息")
    @PostMapping(value = "/distributor/saveDistributionAdd")
    R saveDistributionAdd(@Validated @RequestBody DistributorAddParam param);


    @ApiOperation(value = "修改分销商补充信息", notes = "修改分销商补充信息")
    @PutMapping(value = "/distributor/updateDistributionAdd")
    R updateDistributionAdd(@Validated @RequestBody DistributorAddParam param);


    @ApiOperation(value = "根据分销商id获取补充信息", notes = "根据分销商id获取补充信息")
    @GetMapping(value = "/distributor/getDistributionAddByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    R<DistributorAddDTO> getDistributionAddByDistribuBaseId(@RequestParam("distribuBaseId") Long distribuBaseId);


    @ApiOperation(value = "根据分销商id验证该分销商是否可以提现", notes = "根据分销商id验证该分销商是否可以提现")
    @GetMapping(value = "/distributor/getDistrbutorIsCashWithdrawal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    R<DistributorIsCashWithdrawalDTO> getDistrbutorIsCashWithdrawal(@RequestParam("distribuBaseId") Long distribuBaseId);


    @ApiOperation(value = "根据分销商id获取分销商账号信息", notes = "根据分销商id获取分销商账号信息")
    @GetMapping(value = "/distributor/getDistrbutorAccoultListByDistribuBaseId")
    R<List<DistributorAccountListDTO>> getDistrbutorAccoultListByDistribuBaseId(@Validated @RequestParam("distributorAccountListParam") DistributorAccountListParam distributorAccountListParam);


    @ApiOperation(value = "保存或修改分销商账号信息", notes = "保存或修改分销商账号信息")
    @PostMapping(value = "/distributor/saveOrDistributionAccount")
    R saveOrDistributionAccount(@Validated @RequestBody DistributorAccountParam param);

    @ApiOperation(value = "修改分销商账号状态", notes = "修改分销商账号状态")
    @PostMapping(value = "/distributor/updateDistributionAccountState")
    R updateDistributionAccountState(@Validated @RequestBody DistributorAccountParam param);


    @ApiOperation(value = "根据分销商账号id账号详细信息", notes = "根据分销商账号id账号详细信息")
    @GetMapping(value = "/distributor/getDistributionAccountById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributionAccountId", value = "分销商账号id", required = true)
    })
    R<DistributorAccountDTO> BooleangetDistributionAccountById(@RequestParam("distributionAccountId") Long distributionAccountId);


    @ApiOperation(value = "获取商品渠道价格", notes = "获取商品渠道价格")
    @PostMapping(value = "/distributor/getChannelPrice")
    R<ChannelSellPriceDTO> getChannelPrice(@RequestBody ChannelSellPriceParam channelSellPriceParam);


}
