package cn.enn.wise.ssop.api.order.dto.request;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 默认保存订单参数接收
 *
 * @author lishuiquan
 * @date 2019-12-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultOrderSaveParam {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "下单人的会员id")
    private Long memberId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id",required = true)
    private Long activityBaseId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id",required = true)
    private Long channelId;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称",required = true)
    private String channelName;

    /**
     * 第三方订单号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 订单来源 1小程序 2 APP 3 第三方订单 4  H5
     */
    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5",required = true)
    private Byte orderSource;

    /**
     * 附加信息
     */
    @ApiModelProperty(value = "附加信息")
    private Map extraInformation;

    /**
     * 联系人商品列表
     */
    @ApiModelProperty(value = "用户购买商品列表",required = true)
    private List<GoodsInfoParam> goodsInfoParamList;

    /**
     * 下单人姓名
     */
    @ApiModelProperty("下单人姓名")
    private String customerName;

    /**
     * 下单人手机号
     */
    @ApiModelProperty("下单人手机号")
    private String phone;

    /**
     * 下单人证件号
     */
    @ApiModelProperty("下单人证件号")
    private String certificateNo;

    /**
     * 下单人手机号
     */
    @ApiModelProperty("下单人证件类型")
    private Byte certificateType;

    /**
     * 分销商手机号
     */
    @ApiModelProperty("分销商手机号")
    private String distributorPhone;

    /**
     * 订单Id
     */
    @ApiModelProperty("订单Id")
    private Long orderId;
}
