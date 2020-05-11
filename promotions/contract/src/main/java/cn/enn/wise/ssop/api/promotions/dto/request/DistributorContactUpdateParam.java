package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分销商联系人信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商联系人修改信息")
public class DistributorContactUpdateParam {


    @ApiModelProperty("分销商联系人信息集合")
    private List<DistributorContactParam> distributorContactParam;


    @ApiModelProperty("需要删除的联系人信息id")
    private List<Long> deleteContactIds;

}
