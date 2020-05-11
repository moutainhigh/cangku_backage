package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 用户领取优惠券
 *
 * @author jiabaiye
 * @since 2019/12/16
 */
@Data
public class CouponParam {

    @ApiModelProperty("用户拥有的优惠券关联信息id")
    private Long userOfCouponId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券规则id")
    private Long ruleId;

    @ApiModelProperty("活动id")
    private Long promotionId;

    @ApiModelProperty("活动和商品关联id")
    private Long pagId;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("使用平台 1-小程序 2-微信 0-任何平台")
    private Long usePlatform;

    @ApiModelProperty("费用承担方")
    private String orgName;

    @ApiModelProperty("优惠券类型 1-抵用券 2-折扣券")
    private Integer couponType;

    @ApiModelProperty("面值 抵用券的时候代表多少元，折扣券的时候是折扣力度（7折，输入70）")
    private Integer price;

    @ApiModelProperty("领取限制数量")
    private Integer getLimit;

    @ApiModelProperty("已经领取数量")
    private Integer getedSize;

    @ApiModelProperty("发放数量")
    private Integer initSize;

    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;

    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp validityTime;

    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @ApiModelProperty("业务标签（规则描述）")
    private String tag;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态 1 可用 2 不可用")
    private Byte status;

    @ApiModelProperty("使用条件 1 订单总价 2 商品单价")
    private Integer useRule;

    @ApiModelProperty("达到限额可用（满足多少钱可用）")
    private Integer minUse;

    @ApiModelProperty("是否可以转增 1可以 2 不可以")
    private Integer isSend;

    @ApiModelProperty("是否优惠共享 1可以 2 不可以")
    private Integer isShare;

    @ApiModelProperty("叠加使用优惠券id")
    private String overlayUseCouponId;

    @ApiModelProperty("是否可以叠加 1可以 2 不可以")
    private Integer isOverlay;

    @ApiModelProperty("活动中是否项目专用 1是 2 不是")
    private Integer isProjectUse;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("优惠券活动编号")
    private String code;

    @ApiModelProperty("景区Id")
    private Long companyId;

    @ApiModelProperty("景区名称")
    private String companyName;

    @ApiModelProperty("活动景点")
    private String scenicSpots;

    @ApiModelProperty("预计成本")
    private BigDecimal cost;

    @ApiModelProperty("活动类型 1 拼团 2 营销")
    private String promotionType;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("活动对象类型( 1 通用 2 制定人群)")
    private Byte promotionCrowdType;

    @ApiModelProperty("指定人群 1 新人 2 分销商")
    private String promotionCrowd;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("活动状态( 1未开始 2活动中 3已结束 4 已失效)")
    private Byte promotionStatus;

    @ApiModelProperty("优惠券来源 1-自己领取 2-朋友赠送")
    private Integer couponResource;

    @ApiModelProperty("领取方式：1 不限量 2 限量")
    private Byte getType;

    @ApiModelProperty("分销商手机号")
    private String phone;

    @ApiModelProperty("发放方式：1 不限量 2 限量")
    private Byte initType;

    public static ResponseEntity validateUserOfCouponParam(CouponParam userOfCouponParam) {
        if (userOfCouponParam == null) {
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"请求参数不正确");
        }else{
            return  new ResponseEntity(GeneConstant.SUCCESS_CODE,"请求参数正确");
        }
    }

}
