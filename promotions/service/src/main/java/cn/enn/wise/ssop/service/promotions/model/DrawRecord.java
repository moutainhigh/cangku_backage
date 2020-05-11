package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抽奖记录信息表（适用于抽奖活动）
 * @author jiaby
 */
@Data
@Table(name = "draw_record")
public class DrawRecord extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动Id")
    private Long activityBaseId;


    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "用户id")
    private Long userId;

    @Column(name = "nick_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "用户昵称")
    @ApiModelProperty("用户昵称")
    private String nickName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "电话")
    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty(value = "小程序openId")
    @Column(name = "open_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "小程序openId")
    private Long openId;

    @Column(name = "isaward",type = MySqlTypeConstant.TINYINT,comment = "是否中奖,1是 2否")
    @ApiModelProperty(value = "是否中奖,1是 2否")
    private Byte isaward;

    @ApiModelProperty(value = "奖品Id（电子优惠券id）")
    @Column(name = "coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "奖品Id（电子优惠券id）")
    private Long couponId;

    @Column(name = "coupon_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "优惠券名称")
    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "奖品发放/领取记录id(UserOfCouponId)")
    @Column(name = "user_of_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "奖品发放/领取记录id(UserOfCouponId)")
    private Long userOfCouponId;

//    @Column(name = "isget_coupon",type = MySqlTypeConstant.TINYINT,comment = "是否领取奖品(优惠券),1是 2否")
//    @ApiModelProperty(value = "是否领取奖品(优惠券),1是 2否")
//    private Byte isgetCoupon;

    //创建时间就是抽奖时间，也是中奖日期


}
