package cn.enn.wise.ssop.api.promotions.dto.response;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抽奖记录
 * @author 安辉
 */
@Data
@ApiModel("抽奖记录返回类型")
public class DrawRecordDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动Id")
    private Long activityBaseId;

    @ApiModelProperty(value = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty(value = "小程序openId")
    private Long openId;

    @ApiModelProperty(value = "是否中奖,1是 2否")
    private Byte isaward;

    @ApiModelProperty(value = "奖品Id（电子优惠券id）")
    private Long couponId;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "奖品发放/领取记录id(UserOfCouponId)")
    private Long userOfCouponId;
}
