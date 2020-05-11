package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户领取优惠券
 *
 * @author jiabaiye'
 * @since 2019/12/13
 */
@Data
public class UserOfCouponParam {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("优惠券id")
    private Long goodsCouponId;

    @ApiModelProperty("活动id")
    private Long promotionId;

    @ApiModelProperty("优惠券来源 1-自己领取 2-朋友赠送")
    private Integer couponResource;

    @ApiModelProperty("有效期")
    private Timestamp validityTime;

    @ApiModelProperty("活动状态( 1领取未使用 2已使用 3已过期)")
    private Byte status;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    @ApiModelProperty("更改人id")
    private Long updateUserId;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("修改时间")
    private Timestamp updateTime;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("分销商手机号")
    private String phone;

    public static ResponseEntity validateUserOfCouponParam(UserOfCouponParam userOfCouponParam) {
        if (userOfCouponParam == null) {
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"请求参数不正确");
        }else if(userOfCouponParam.getGoodsCouponId()==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券id不能为空");
        }else if(userOfCouponParam.getCouponResource()==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"优惠券来源不能为空");
        }else if(userOfCouponParam.getPromotionId()==null){
            return new ResponseEntity(GeneConstant.PARAM_INVALIDATE,"活动id不能为空");
        }else{
            return  new ResponseEntity(GeneConstant.SUCCESS_CODE,"请求参数正确");
        }
    }

}
