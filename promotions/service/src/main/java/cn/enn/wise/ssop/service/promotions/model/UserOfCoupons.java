package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;


/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@Table(name = "user_of_coupons")
public class UserOfCoupons extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "coupon_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "优惠券id")
    @ApiModelProperty("优惠券id")
    private Long couponId;

    @Column(name = "coupon_status",type = MySqlTypeConstant.TINYINT, length = 1,comment = "优惠券状态 1 有效 2 无效")
    @ApiModelProperty("优惠券状态 1 有效 2 无效")
    private Byte couponStatus;

    @Column(name = "coupon_type",type = MySqlTypeConstant.TINYINT, length = 1,comment = "优惠券状态 1 有效 2 无效")
    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @Column(name = "coupon_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "券码")
    @ApiModelProperty("券码")
    private String couponCode;

    @Column(name = "grant_time",type = MySqlTypeConstant.TIMESTAMP,comment = "发放日期")
    @ApiModelProperty("发放日期")
    private Timestamp grantTime;

    @Column(name = "receive_time",type = MySqlTypeConstant.TIMESTAMP,comment = "领取日期")
    @ApiModelProperty("领取日期")
    private Timestamp receiveTime;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "用户id(会员id)")
    @ApiModelProperty("用户id(会员id)")
    private Long userId;

    @Column(name = "user_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "用户名称（会员名称）")
    @ApiModelProperty("用户名称（会员名称）")
    private String userName;

    @Column(name = "consumption_time",type = MySqlTypeConstant.TIMESTAMP,comment = "消费日期")
    @ApiModelProperty("消费日期")
    private Timestamp consumptionTime;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT,length = 1,defaultValue = "1",comment = "状态( 1未领取 2领取未使用 3已使用  4已过期 5转让中 6已转让)")
    @ApiModelProperty("状态( 1未领取 2领取未使用 3已使用  4已过期 5转让中 6已转让)")
    private Byte state;

    @ApiModelProperty(value = "活动基础信息Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "活动名称")
    @Column(name = "activity_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动状态 1 活动中 2 未开始 3 结束 4 已失效")
    @Column(name = "activity_status",type = MySqlTypeConstant.TINYINT,length = 1,comment = "活动状态 1 活动中 2 未开始 3 结束 4 已失效")
    private Byte activityStatus;

    @Column(name = "coupon_resource",type = MySqlTypeConstant.TINYINT, defaultValue = "1",length = 1,comment = "优惠券来源 1-自己领取(活动0元领取 ) 2-朋友赠送 3-优惠返券 4-抽奖中券 5-分享活动得券")
    @ApiModelProperty("优惠券来源 1-自己领取(活动0元领取 ) 2-朋友赠送 3-优惠返券 4-抽奖中券 5-分享活动得券")
    private Byte couponResource;

    @Column(name = "validity_time",type = MySqlTypeConstant.TIMESTAMP,comment = "有效期")
    @ApiModelProperty("有效期")
    private Timestamp validityTime;

    @Column(name = "open_id",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "openId-用户信息-用户用户转赠记录")
    @ApiModelProperty("用户信息-用户用户转赠记录")
    private String openId;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    @ApiModelProperty("订单id")
    private Long orderId;

    @Column(name = "order_price",type = MySqlTypeConstant.INT,length = 12,comment = "订单价格")
    @ApiModelProperty("订单价格")
    private Integer orderPrice;

    @Column(name = "replace_price",type = MySqlTypeConstant.INT,length = 12,comment = "抵扣金额")
    @ApiModelProperty("抵扣金额")
    private Integer replacePrice;

}
