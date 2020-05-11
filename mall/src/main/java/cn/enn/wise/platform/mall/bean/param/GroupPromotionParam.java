package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.bean.vo.GroupPromotionGoodsVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019-09-12
 */
@Data
@ApiModel("活动请求参数")
public class GroupPromotionParam {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("景点名称")
    private String scenicSpots;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("活动类型 1 拼团 2 营销")
    private List<Integer> promotionType;

    @ApiModelProperty("活动状态")
    private List<Byte> status;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("策略id")
    private String ruleId;

    @ApiModelProperty("备注：活动规则")
    private String remark;

    @ApiModelProperty("商品id列表")
    List<GroupPromotionGoodsVo> goodsList;

    @ApiModelProperty("成本")
    private BigDecimal cost;

    @ApiModelProperty("成本部门")
    private List<String> orgName;

    @ApiModelProperty("活动对象 1 通用对象 2 指定人群")
    private Byte promotionCrowd;

    @ApiModelProperty("活动互斥 1 共享 2 互斥")
    private Byte promotionReject;


    @ApiModelProperty("互斥活动")
    private List<RejectPromotion> rejectPromotion;

    @ApiModelProperty("人群")
    private List<Integer> promotionCrowdList;

    @ApiModelProperty("原因")
    private String reason;


}


