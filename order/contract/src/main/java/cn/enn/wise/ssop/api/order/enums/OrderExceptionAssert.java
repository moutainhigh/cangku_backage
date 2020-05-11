package cn.enn.wise.ssop.api.order.enums;

import cn.enn.wise.uncs.base.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * 订单自定义服务异常
 *
 * @author 白杰
 * @date 2019.09.17
 *
 *
 * 用户服务 1000-1099
 * 系统服务 1100-1199
 * 订单服务 1200-1299
 * 商品服务 1300-1399
 * 网关服务 1400-1499
 */
@Getter
@AllArgsConstructor
public enum OrderExceptionAssert implements BusinessExceptionAssert {

    /**
     * 订单服务
     */
    ORDER_NOT_FOUND(1200,"订单不存在"),

    ORDER_STATUS(1201,"该笔订单状态不正确,orderId=[{}]"),

    PAY_STATUS(1202,"该笔订单支付状态不正确,orderId=[{0}],payStatus=[{1}]"),

    ORDER_INSERT_ERROR(1203,"订单保存失败,请稍后重试~"),

    GOOD_NOT_FOUND(1204,"商品未找到!,请检查参数是否正确"),

    GOOD_SKU_NOT_FOUND(1205,"商品SKU未找到!,请检查参数是否正确"),

    THIRD_ORDER_NO_REPEAT(1206,"合作伙伴订单号重复,thirdOrderNo=[{0}]"),

    ORDER_ID_REQUIRED(1207,"订单ID不能为空"),

    PARAM_NOT_AVAILABLE(1208,"参数不正确，请检查[{0}]的值[{1}]"),

    BACK_TICKET_EXCEPTION(1209,"退单异常:orderId=[{}]"),

    BACK_TICKET_CHECK_EXCEPTION(1210,"退单审核异常"),

    FILE_EXPORT_EXCEPTION(1211,"文件导出异常"),

    ORDER_DELETE_EXCEPTION(1212,"订单删除失败"),

    ORDER_TYPE_NOT_EXISTS(1213,"订单类型不存在"),

    ORDER_OF_USER_NOT_ONLINE(1214,"用户信息获取异常"),

    ORDER_GOODS_NOT_ONLINE(1215,"商品没上架"),

    ORDER_GOODS_OVERFLOW_STORE(1216,"商品购买数量超过库存数量"),

    ORDER_OF_RELATEPEOPLE_NOT_EXISTS(1217,"订单中的联系人不存在"),

    ORDER_OF_SALE_NOT_USE(1218,"优惠券不可用"),

    ORDER_OF_SALE_NOT_MATCH_GOODS(1219,"优惠券不能用在该商品上"),

    ORDER_OF_SALE_NOT_MATCH_PEOPLE(1220,"该用户不能使用该优惠券"),

    ORDER_GET_SKU_EXCEPTION(1221,"获取商品信息异常"),

    ORDER_STATUS_UPDATE_EXCEPTION(1222,"订单状态更新异常"),

    ORDER_STATUS_CHECK_FAILURE(1223,"状态校验失败"),

    ORDER_BUYER_NOT_EXISTS(1224,"下单人信息不存在"),

    PHONE_CHECK_ERROR(1225,"手机号错误，请输入正确的手机号"),

    CHANNEL_ID_CHECK_ERROR(1226,"渠道编号校验错误"),

    CHANNEL_NAME_CHECK_ERROR(1227,"渠道名称校验错误"),

    GOODS_CHECK_ERROR(1228,"商品校验错误"),

    GOODS_AMOUNT_CHECK_ERROR(1229,"商品数量错误"),

    PEOPLE_CHECK_ERROR(1230,"联系人信息错误"),

    SOURCE_CHECK_ERROR(1231,"订单来源校验错误"),

    ORDER_SAVE_EXCEPTION(1232,"下单异常"),

    ORDER_WECHAT_UNIFIED_EXCEPTION(1233,"微信统一下单异常"),

    ORDER_WECHAT_REFUND_EXCEPTION(1234,"微信退款异常"),

    ORDER_WECHAT_ORDER_QUERY_EXCEPTION(1235,"微信退款异常"),

    USER_ID_NOT_NULL(1236,"用户id不能为空")
    ;
    /**
     * 返回码
     */
    private int code;
    /** 返回消息
     */
    private String message;
}
