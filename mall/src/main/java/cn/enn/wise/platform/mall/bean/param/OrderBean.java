package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 19:34
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@ApiModel
public class OrderBean {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer state;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private List<Integer> states;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "时间段")
    private String timeSpan;

    @ApiModelProperty(value = "开始时间段")
    private String startTimeSpan;

    @ApiModelProperty(value = "结束时间段")
    private String endTimeSpan;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "是否分销订单 1是 0 否")
    private Integer isDistributeOrder;

    @ApiModelProperty(value = "页码(默认1)")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大条目数(默认10)")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "优惠券号")
    private String couponNo;
}
