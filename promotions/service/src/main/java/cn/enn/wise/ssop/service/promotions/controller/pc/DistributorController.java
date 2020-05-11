package cn.enn.wise.ssop.service.promotions.controller.pc;


import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.model.DistributorAccount;
import cn.enn.wise.ssop.service.promotions.model.DistributorBank;
import cn.enn.wise.ssop.service.promotions.service.*;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 耿小洋
 * 分销商管理
 */
@RestController
@Api(value = "分销商管理接口", tags = {"分销商管理接口"})
@RequestMapping("/distributor")
public class DistributorController {

    @Autowired
    DistributorBaseService distributorBaseService;

    @Autowired
    DistributorContactService distributorContactService;

    @Autowired
    DistributorBankService distributorBankService;

    @Autowired
    DistributorBusinessService distributorBusinessService;

    @Autowired
    DistributorAddService distributorAddService;

    @Autowired
    DistributorAccountService distributorAccountService;

    @Autowired
    ChannelPriceService channelPriceService;


    @ApiOperation(value = "获取分销商列表", notes = "获取分销商列表")
    @GetMapping(value = "/getDistributorList")
    public R<QueryData<List<DistributorListDTO>>> getDistributorList(@Validated DistributorBaseListParam distributorBaseListParam) {
        return distributorBaseService.listByPage(distributorBaseListParam);
    }

    @ApiOperation(value = "获取分销商id和名称列表", notes = "获取分销商id和名称列表")
    @GetMapping(value = "/getDistributorIdAndNameList")
    public R<List<DistributorBaseDTO>> getDistributorIdAndNameList() {
        List<DistributorBaseDTO> distributorIdAndNameList = distributorBaseService.getDistributorIdAndNameList();
        return R.success(distributorIdAndNameList);
    }


    @ApiOperation(value = "保存分销商基础信息", notes = "保存分销商基础信息")
    @PostMapping(value = "/saveDistributionBase")
    public R saveDistributionBase(@Validated @RequestBody DistributorBaseParam DistributorBaseParam) {
        return distributorBaseService.saveDistributionBase(DistributorBaseParam);
    }

    @ApiOperation(value = "修改分销商基础信息", notes = "修改分销商基础信息")
    @PutMapping(value = "/updateDistributionBase")
    public R updateDistributionBase(@Validated @RequestBody DistributorBaseParam param) {
        Boolean flag = distributorBaseService.updateDistributionBase(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("修改分销商基础信息失败");

    }

    @ApiOperation(value = "根据分销商id获取基础信息", notes = "根据分销商id获取基础信息")
    @GetMapping(value = "/getDistributionBaseByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<DistributorBaseDTO> getDistributionBaseByDistribuBaseId(Long distribuBaseId) {
        return distributorBaseService.getDistributionBaseByDistribuBaseId(distribuBaseId);
    }


    @ApiOperation(value = "保存分销商联系人", notes = "保存分销商联系人")
    @PostMapping(value = "/saveDistributionContact")
    public R saveDistributionContact(@Validated @RequestBody List<DistributorContactParam> distributorContactParam) {
        Boolean flag = distributorContactService.saveDistributionContact(distributorContactParam);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商联系人信息失败");
    }

    @ApiOperation(value = "修改分销商联系人", notes = "修改分销商联系人")
    @PostMapping(value = "/updateDistributionContact")
    public R updateDistributionContact(@Validated @RequestBody DistributorContactUpdateParam param) {
        Boolean flag = distributorContactService.updateDistributionContact(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("修改分销商联系人信息失败");
    }

    @ApiOperation(value = "根据分销商id获取联系人信息", notes = "根据分销商id获取联系人信息")
    @GetMapping(value = "/getDistributionContactByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<List<DistributorContactDTO>> getDistributionContactByDistribuBaseId(Long distribuBaseId) {
        return distributorContactService.getDistributionContactByDistribuBaseId(distribuBaseId);
    }


    @ApiOperation(value = "保存分销商财务信息", notes = "保存分销商财务信息")
    @PostMapping(value = "/saveDistributorBank")
    public R saveDistributorBank(@Validated @RequestBody List<DistributorBankParam> param) {
        Boolean flag = distributorBankService.saveDistributorBank(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商财务信息失败");
    }

    @ApiOperation(value = "修改分销商财务信息", notes = "修改分销商财务信息")
    @PostMapping(value = "/updateDistributionBank")
    public R updateDistributionBank(@Validated @RequestBody DistributorBankUpdateParam param) {
        Boolean flag = distributorBankService.updateDistributionBank(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("修改分销商财务信息");
    }

    @ApiOperation(value = "根据分销商id获取财务信息", notes = "根据分销商id获取财务信息")
    @GetMapping(value = "/getDistributionBankByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true),
            @ApiImplicitParam(name = "state", value = "状态 1 正常 2 停用", required = false)
    })
    public R<List<DistributorBankDTO>> getDistributionBankByDistribuBaseId(Long distribuBaseId,Integer state) {
        return distributorBankService.getDistributionBankByDistribuBaseId(distribuBaseId,state);
    }


    @ApiOperation(value = "保存分销商业务信息", notes = "保存分销商业务信息")
    @PostMapping(value = "/saveDistributionBussiness")
    public R saveDistributionBussiness(@Validated @RequestBody DistributorBusinessParam param) {
        Boolean flag = distributorBusinessService.saveDistributionBussiness(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商业务信息失败");
    }

    @ApiOperation(value = "修改分销商业务信息", notes = "修改分销商业务信息")
    @PutMapping(value = "/updateDistributionBussiness")
    public R updateDistributionBussiness(@Validated @RequestBody DistributorBusinessParam param) {
        Boolean flag = distributorBusinessService.updateDistributionBussiness(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商业务信息失败");
    }

    @ApiOperation(value = "根据分销商id获取业务信息", notes = "根据分销商id获取业务信息")
    @GetMapping(value = "/getDistributionBussinessByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<DistributorBusinessDTO> getDistributionBussinessByDistribuBaseId(Long distribuBaseId) {
        return distributorBusinessService.getDistributionBussinessByDistribuBaseId(distribuBaseId);
    }

    @ApiOperation(value = "保存分销商补充信息", notes = "保存分销商补充信息")
    @PostMapping(value = "/saveDistributionAdd")
    public R saveDistributionAdd(@Validated @RequestBody DistributorAddParam param) {
        Boolean flag = distributorAddService.saveDistributionAdd(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商补充信息失败");
    }

    @ApiOperation(value = "修改分销商补充信息", notes = "修改分销商补充信息")
    @PutMapping(value = "/updateDistributionAdd")
    public R updateDistributionAdd(@Validated @RequestBody DistributorAddParam param) {
        Boolean flag = distributorAddService.updateDistributionAdd(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存分销商补充信息失败");
    }

    @ApiOperation(value = "根据分销商id获取补充信息", notes = "根据分销商id获取补充信息")
    @GetMapping(value = "/getDistributionAddByDistribuBaseId")
    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    public R<DistributorAddDTO> getDistributionAddByDistribuBaseId(Long distribuBaseId) {
        R distributionAddByDistribuBaseId = distributorAddService.getDistributionAddByDistribuBaseId(distribuBaseId);
        return distributionAddByDistribuBaseId;
    }


    @ApiOperation(value = "根据分销商id验证该分销商是否可以提现", notes = "根据分销商id验证该分销商是否可以提现")
    @GetMapping(value = "/getDistrbutorIsCashWithdrawal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distribuBaseId", value = "分销商id", required = true)
    })
    public R<DistributorIsCashWithdrawalDTO> getDistrbutorIsCashWithdrawal(Long distribuBaseId) {
        return R.success(distributorBaseService.getDistrbutorIsCashWithdrawal(distribuBaseId));
    }


    @ApiOperation(value = "根据分销商id获取分销商账号信息", notes = "根据分销商id获取分销商账号信息")
    @GetMapping(value = "/getDistrbutorAccoultListByDistribuBaseId")
    public R<List<DistributorAccountListDTO>> getDistrbutorAccoultListByDistribuBaseId(@Validated DistributorAccountListParam distributorAccountListParam) {
        return R.success(distributorAccountService.getDistributionAccountByDistribuBaseId(distributorAccountListParam));
    }


    @ApiOperation(value = "保存或修改分销商账号信息", notes = "保存或修改分销商账号信息")
    @PostMapping(value = "/saveOrDistributionAccount")
    public R saveOrDistributionAccount(@Validated @RequestBody DistributorAccountParam param) {
        Boolean flag = distributorAccountService.saveOrDistributionAccount(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("保存或修改分销商账号信息失败（可能账号重复 请换一个账号试试）");
    }

    @ApiOperation(value = "修改分销商账号状态", notes = "修改分销商账号状态")
    @PostMapping(value = "/updateDistributionAccountState")
    public R updateDistributionAccountState(@Validated @RequestBody DistributorAccountParam param) {
        Boolean flag = distributorAccountService.updateDistributionAccountState(param);
        if (flag) {
            return R.success(null);
        }
        return R.error("修改分销商账号状态失败（可能账号重复 请换一个账号试试）");
    }


    @ApiOperation(value = "根据分销商账号id账号详细信息", notes = "根据分销商账号id账号详细信息")
    @GetMapping(value = "/getDistributionAccountById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributionAccountId", value = "分销商账号id", required = true)
    })
    public R<DistributorAccountDTO> BooleangetDistributionAccountById(Long distributionAccountId) {
        DistributorAccountDTO distributionAccountById = distributorAccountService.getDistributionAccountById(distributionAccountId);
        return R.success(distributionAccountById);
    }


    @ApiOperation(value = "获取商品渠道销售价格", notes = "获取商品渠道销售价格")
    @GetMapping(value = "/getChannelPrice")
    public R<ChannelSellPriceDTO> getChannelPrice(@Validated ChannelSellPriceParam channelSellPriceParam) {
        ChannelSellPriceDTO channelPrice = channelPriceService.getChannelPrice(channelSellPriceParam);
        return R.success(channelPrice);
    }


    @ApiOperation(value = "获取商品渠道结算价格", notes = "获取商品渠道结算价格")
    @PostMapping(value = "/getSettlementPrice")
    public R<ChannelSettlementPriceDTO> getSettlementPrice(@RequestBody ChannelSettlementPriceParam channelSettlementPriceParam) {
        ChannelSettlementPriceDTO channelSettlementPriceDTO = channelPriceService.getSettlementPrice(channelSettlementPriceParam);
        return R.success(channelSettlementPriceDTO);
    }


}
