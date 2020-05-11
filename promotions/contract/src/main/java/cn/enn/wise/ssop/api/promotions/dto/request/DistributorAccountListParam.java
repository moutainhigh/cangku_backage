package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分销商账号信息列表
 *
 * @author jiaby
 */
@Data
@ApiModel("分销商账号列表请求参数信息")
public class DistributorAccountListParam {

    @ApiModelProperty("账号")
    private String accountNumber;

    @ApiModelProperty("状态 1正常 2 锁定")
    private Byte state;

    @ApiModelProperty("创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "分销商Id")
    @NotNull
    private Long distributorBaseId;


}
