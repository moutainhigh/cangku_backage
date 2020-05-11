package cn.enn.wise.ssop.api.promotions.consts;

import cn.enn.wise.uncs.base.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author shizhai
 * @date 2019.09.17
 *
 *
 * 会员服务 10000-10999
 * 权限服务 11000-11999
 * 订单服务 12000-12999
 * 商品服务 13000-13999
 * 网关服务 14000-14999
 * 支付服务 15000-15999
 * 营销服务 16000-16999
 * 场景服务 17000-17999
 *
 *
 */
@Getter
@AllArgsConstructor
public enum PromotionsExceptionAssert implements BusinessExceptionAssert {

    /**
     *营销渠道
     */
    CHANNEL_NAME_ISEXIST(16000, "该渠道已存在，请核准后操作！"),
    CHANNEL_NULL_ISEXIST(16001, "渠道不存在,id=[{0}]"),
    ACTIVITY_IS_EXIST(16002, "活动已经存在"),
    PARAM_ERROR(16003, "参数错误"),
    ACTIVITY_RULE_IS_EXIST(16004, "活动基础规则已经存在,ActivityBaseId=[{0}],ActivityRuleiD=[{1}]"),
    SAVE_RULE_ERROR(16005,"添加活动规则失败,ActivityBaseId=[{0}]"),
    SAVE_ACTIVITY_ERROR(16006,"添加活动失败"),
    ACTIVITY_IS_NOT_EXIST(16007,"活动不存在"),
    ACTIVITY_BASE_IS_EXIST(16008, "活动基础信息已经存在,ActivityBaseId=[{0}]"),
    ACTIVITY_BASE_IS_NOT_EXIST(16009, "活动基础信息不存在,ActivityBaseId=[{0}]"),
    SAVE_PLATFORM_ERROR(16010,"添加活动投放渠道失败,ActivityRuleiD=[{1}]"),
    SAVE_GOODS_ERROR(16011,"添加活动商品失败,ActivityRuleiD=[{1}]"),
    ACTIVITY_SHARE_EXIST(16012,"活动分享已经存在"),
    ACTIVITY_SHARE_IS_NOT_EXIST(16013,"活动分享不存在"),
    ACTIVITY_RULE_IS_NOT_EXIST(16014, "活动基础规则不存在,ActivityBaseId=[{0}]"),
    DISTRIBUTION_NAME_ISEXIST(16015, "分销商名称已经存在,name=[{0}]"),
    DINFORMATION_NOT_EXIST(16016, "查询信息不存在"),
    ACTIVITY_GROUP_INVALID(16017,"该拼团活动已失效"),
    COUPON_STATE_NO(16018, "优惠卷无效或与当前用户不匹配,CouponName=[{0}]"),
    COUPON_GOODS_NO(16019, "优惠卷和商品不匹配,CouponName=[{0}]"),
    COUPON_PRICE_NO(16020, "优惠卷未达到满减价格,CouponName=[{0}],CouponFullRulePrice=[{1}]"),
    ACTIVITY_STATE_NO(16021, " 活动状态异常,ActivityName=[{0}]"),
    ACTIVITY_PRICE_NO(16022, "活动未达到使用规则,ActivityName=[{0}]"),
    ACTIVITY_GOODS_NO(16023, "活动和商品不匹配,ActivityName=[{0}]"),
    ACTIVITY_COUPON_NO(16024, "活动或优惠券不存在或数据错误"),
    ACTIVITY_NOT(16025, "活动数据错误"),
    COUPON_DATA_NO(16026, "优惠券数据错误"),
    USER_DATA_NO(16027, "用户不存在"),
    GOODS_NOT_IN_ACTIVITY_GROUP(16028,"该商品暂时没有参加拼团活动"),
    GOODS_LIMIT(16029,"该商品购买限制 limit=[{0}] 次"),
    INSERTED_GROUP(16030,"您已经参与这个拼团活动了"),
    INSERT_GROUP_NUMBER_USED(16031,"您参团的次数,[{0}]次已经用完！"),
    GROUP_IS_FULL(16032,"手慢了，此团已满，自动为您创建新团"),
    DRAW_IS_DISCONTENT(16033,"当前的用户不满足抽奖"),
    DRAW_IS_NOT_STARTED(16034,"当前抽奖活动未开始请等待"),
    GOODS_NOT_IS_ACTIVE(16035,"当前商品不是活动商品"),
    GROUP_IS_EXPIRED(16036,"拼团已失效"),
    ACTIVITY_COST_ERROR(16037, "活动预计成本需要大于0"),
    ACTIVITY_COUNT_ERROR(16037, "活动总量需要大于0"),
    ACTIVITY_AIM_ERROR(16037, "请填写有效的活动目标")
    ;



    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
}