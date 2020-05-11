package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分销商财务信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商财务账号修改信息")
public class DistributorBankUpdateParam {


    @ApiModelProperty("分销商财务信息集合")
    private List<DistributorBankParam> distributorBankParams;


    @ApiModelProperty("需要删除的财务信息id")
    private List<Long> deleteBankIds;

}
