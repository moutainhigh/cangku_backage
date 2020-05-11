package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/11
 */
@Data
public class AddGoodsCouponParam {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("规则id")
    private Long goodsCouponRuleId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("使用平台 1-小程序 2-微信 0-任何平台")
    private Long usePlatform;

    @ApiModelProperty("分摊单位")
    private List<OrgParam> orgList;

    @ApiModelProperty("优惠券类型 1-抵用券 2-折扣券")
    private Integer couponType;

    @ApiModelProperty("面值 抵用券的时候代表多少元，折扣券的时候是折扣力度（7折，输入70）")
    private Integer price;

    @ApiModelProperty("领取限制数量")
    private Integer getLimit;

    @ApiModelProperty("发放数量")
    private Integer initSize;

    @ApiModelProperty("有效期")
    private String[] validityTime;

    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @ApiModelProperty("业务标签")
    private String tag;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态：1 可用 2 不可用")
    private Byte status;

    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @ApiModelProperty("领取方式：1 不限量 2 限量")
    private Byte getType;

    @ApiModelProperty("发放方式：1 不限量 2 限量")
    private Byte initType;

    @ApiModelProperty("优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;
}
