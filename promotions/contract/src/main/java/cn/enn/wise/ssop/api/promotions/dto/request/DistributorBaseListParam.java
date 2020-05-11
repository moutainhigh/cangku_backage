package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 分销商列表参数
 * @author 耿小洋
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分销商列表")
public class DistributorBaseListParam extends QueryParam {

    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty("分销商名称")
    private String distributorName;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    private Byte verifyState;

    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    private Byte distributorType;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty(value = "渠道Id")
    private Long channelId;

    @ApiModelProperty("业务对接人")
    private String businessCounterpart;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    private Byte channelType;








}