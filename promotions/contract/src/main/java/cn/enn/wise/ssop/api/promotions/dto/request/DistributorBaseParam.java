package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 分销商基础信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商列表")
public class DistributorBaseParam {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty("分销商名称")
    @NotNull
    private String distributorName;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty(value = "城市Id")
    @NotNull
    private Long cityId;

    @ApiModelProperty("城市名称")
    @NotNull
    private String cityName;

    @ApiModelProperty(value = "区Id")
    @NotNull
    private Long areaId;

    @ApiModelProperty("区名称")
    @NotNull
    private String areaName;

    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty("渠道名称")
    @NotNull
    private String channelName;

    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    @NotNull
    private Byte distributorType;

    @ApiModelProperty(value = "归属分销商Id")
    private Long parentId;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    private Byte verifyState;

    @ApiModelProperty("入驻时间")
    private Timestamp intoTime;

    @ApiModelProperty("未通过原因")
    private String reason;




}
