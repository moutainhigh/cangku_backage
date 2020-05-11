package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分销商基础信息表
 * @author 耿小洋
 */
@Data
@ApiModel("分销商基础信息返回参数")
public class DistributorBaseDTO {

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("分销商名称")
    private String distributorName;

    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty(value = "城市Id")
    private Long cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty(value = "区Id")
    private Long areaId;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty(value = "渠道Id")
    private Long channelId;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    private Byte distributorType;

    @ApiModelProperty(value = "归属分销商Id")
    private Long parentId;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    private Byte verifyState;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    private Byte channelType;

    @ApiModelProperty("归属分销商名称")
    private String parentScenicName;


    @ApiModelProperty("未通过原因")
    private String reason;

    @ApiModelProperty("审核类型：1-草稿 2-保存成功的可用数据")
    private Byte verifyType;

    @ApiModelProperty("分销商保存轨迹：1 基础信息 2联系人信息 3财务信息 4业务信息 5补充信息")
    private List<Integer> saveTrajectory;

    @ApiModelProperty("分销商保存轨迹：1 基础信息 2联系人信息 3财务信息 4业务信息 5补充信息")
    private Byte editStep;


}
