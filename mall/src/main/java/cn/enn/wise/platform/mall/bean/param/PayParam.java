package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author bj
 * @Description
 * @Date19-5-26 下午4:29
 * @Version V1.0
 **/
@Data
@ApiModel("PayParam,支付参数实体")
public class PayParam {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer state;


    /**
     * 用户微信昵称
     */
    @ApiModelProperty(value = "用户微信昵称")
    private String userWechatName;

    /**
     * 购票身份证号
     */
    @ApiModelProperty(value = "购票身份证号")
    private String IdNumber;
    /**
     * 购票人姓名
     */
    @ApiModelProperty(value = "购票人姓名")
    private String name;

    /**
     * 景点Id
     */
    @ApiModelProperty(value = "景点Id")
    private Long scenicId;

    /**
     * 买票数量
     */
    @ApiModelProperty(value = "买票数量")
    private Integer amount;

    /**
     * 时段Id
     */
    @ApiModelProperty(value = "时段Id")
    private Integer periodId;
    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    /**
     * 支付方式 weixin
     */
    @ApiModelProperty(value = "支付方式 weixin")
    private String payType;

    /**
     * 商品总价
     */
    @ApiModelProperty(value = "商品总价,单位元")
    private String totalPrice;
    /**
     *  日期1 今天,2 明天,3 后天
     */
    @ApiModelProperty(value = "日期1 今天,2 明天,3 后天")
    private Integer timeFrame;

    /**
     * 商品查询条件
     */
    @ApiModelProperty(value = "goodsReqParam")
    private GoodsReqParam goodsReqParam;

    /**
     * userId
     */
    @ApiModelProperty(value = "userId")
    private Long userId;
    /**
     * openId
     */
    @ApiModelProperty(value = "openId,小程序用户标识openId")
    private String openId;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "分销商手机号")
    private String driverMobile;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "是否分时段售卖")
    private Integer isByPeriodOperation;


    @ApiModelProperty(value = "拼团活动Id")
    private Long promotionId;

    @ApiModelProperty(value = "拼团团Id")
    private Long groupOrderId;

    @ApiModelProperty("小程序服务通知formId")
    private String formId;

    @ApiModelProperty("小程序服务通知路径")
    private String path;

    @ApiModelProperty("用户领取优惠券记录的Id")
    private Long userOfCouponId;

    @ApiModelProperty("短信验证码")
    private String messageCode;

    @ApiModelProperty("联系人Id")
    private List<Long> contacts;

    @ApiModelProperty("订单备注")
    private String remark;

    @ApiModelProperty("订单类型")
    private Integer orderType;

    @ApiModelProperty("扩展信息")
    private List<Map<String,Object>> extInfo;

}
