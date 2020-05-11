package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/12
 */
@Data
@ApiModel("活动列表实体类")
public class GroupPromotionVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("规则名称")
    private String name;

    @ApiModelProperty("规则编码")
    private String code;

    @ApiModelProperty("活动类型")
    private String type;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("负责人")
    private String cost;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("备注：活动规则")
    private String remark;

    @ApiModelProperty("成本部门")
    private String orgName;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("规则id")
    private Long groupRuleId;


    @ApiModelProperty("人群状态：( 1 通用 2 制定人群)")
    private Byte promotionCrowdStatus;

    @ApiModelProperty("( 1 共享 2 互斥)")
    private Byte promotionRejectStatus;

    @ApiModelProperty("互斥活动")
    private String rejectPromotion;

    @ApiModelProperty("人群")
    private String crowdPromotion;

    @ApiModelProperty("原因")
    private String reason;

}
