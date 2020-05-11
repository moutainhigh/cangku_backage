package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 分销商基础信息表
 * @author jiaby
 */
@Data
@ApiModel("分销商列表信息返回参数")
public class DistributorListDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分销商名称")
    private String distributorName;

    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    private Byte distributorType;


    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;


    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    private Byte verifyState;


    @ApiModelProperty("审核状态：0-待审核 1-审核通过 2-审核未通过，补充信息审核")
    private Byte distributorAddVerifyState;

    @ApiModelProperty(value = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3公众号注册")
    private String registerResource;

    @ApiModelProperty("渠道类型  1 直营 2 分销")
    private Byte channelType;

    @ApiModelProperty("资源类型  1 酒店 2 导游 3 租车")
    private String resourceType;

    @ApiModelProperty("业务对接人")
    private String businessCounterpart;

    @ApiModelProperty("综合等级  1 初级 2 中级 3 高级")
    private Byte level;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd")
    @ApiModelProperty("入驻时间")
    private Timestamp intoTime;

    @ApiModelProperty("审核类型：1-草稿 2-保存成功的可用数据")
    private Byte verifyType;

    @ApiModelProperty("分销商保存轨迹：1 基础信息 2联系人信息 3财务信息 4业务信息 5补充信息")
    private Byte editStep;

}
