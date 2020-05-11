package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 保存第三方订单参数
 *
 * @author baijie
 * @date 2020-02-13
 */
@Data
public class SaveThirdOrderParam {

    @ApiModelProperty("订单类型 7 门票订单")
    private Integer orderType;

    @ApiModelProperty(value = "用户微信昵称")
    private String userWechatName;

    @ApiModelProperty(value = "买票数量")
    private Integer amount;

    @ApiModelProperty(value = "商品拓展Id")
    private Long goodsId;

    @ApiModelProperty(value = "游玩日期 如2020-01-01")
    private String playTime;

    @ApiModelProperty(value = "分销商手机号")
    private String driverMobile;

    @ApiModelProperty("小程序服务通知formId")
    private String formId;

    @ApiModelProperty("小程序服务通知路径")
    private String path;

    @ApiModelProperty("游客手机号")
    private String orderTel;

    @ApiModelProperty("游客姓名")
    private String tourismName;

    @ApiModelProperty("用户领取优惠券记录的Id")
    private Long userOfCouponId;

    @ApiModelProperty("扩展信息")
    private List<Map<String,Object>> extInfo;
}
