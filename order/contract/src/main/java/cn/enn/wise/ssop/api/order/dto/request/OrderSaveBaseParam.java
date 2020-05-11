package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 订单保存参数基类
 *
 * @author baijie
 * @date 2019-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单保存基本参数")
public class OrderSaveBaseParam {
    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 会员昵称
     */
    @ApiModelProperty(value = "会员昵称")
    private String memberName;

    /**
     * 合作伙伴id
     */
    @ApiModelProperty(value = "渠道id")
    private Long channelId;

    /**
     * 订单联系人手机号
     */
    @NotBlank(message = "联系人信息不能为空")
    @ApiModelProperty(value = "订单联系人手机号",required = true)
    private String phone;

    /**
     * 客人姓名
     */
    @NotBlank(message = "游客姓名不能为空")
    @ApiModelProperty(value = "客人姓名",required = true)
    private String touristName;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(value = "合作伙伴用户ID")
    private Long threeMemberId;

    /**
     * 第三方订单号
     */
    @NotBlank(message = "第三方订单号不能为空")
    @ApiModelProperty(value = "第三方订单号",required = true)
    private String threeOrderNo;

    @ApiModelProperty(value = "请求流水号")
    private String appId;

    @ApiModelProperty(value = "订单来源")
    private Byte orderSource;
}
