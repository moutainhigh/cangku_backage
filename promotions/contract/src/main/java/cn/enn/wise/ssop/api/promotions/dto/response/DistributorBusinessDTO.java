package cn.enn.wise.ssop.api.promotions.dto.response;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商业务信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商业务信息返回参数")
public class DistributorBusinessDTO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty("综合等级  1 初级 2 中级 3 高级")
    private Byte level;

    @ApiModelProperty("渠道类型  1 直营 2 分销")
    private Byte channelType;

    @ApiModelProperty("资源类型  1 酒店 2 导游 3 租车")
    private String resourceType;

    @ApiModelProperty(value = "积分")
    private Long grade;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("合作方式  1 净价销售 2 正价返佣")
    private Byte cooperationMethod;

    @ApiModelProperty("结算方式  1 线上结算 2 线下结算")
    private Byte settlementMethod;

    @ApiModelProperty("结算周期（类型）  1 定期 2 日结 3 月结 4 周结")
    private Byte settlementType;

    @ApiModelProperty("结算时间")
    private String settlementTime;

    @ApiModelProperty("享受服务")
    private String enjoyService;

    @ApiModelProperty("业务范围")
    private String businessScope;

    @ApiModelProperty("业务对接人")
    private String businessCounterpart;

    @ApiModelProperty("优惠力度")
    private String preferentialStrength;


}
