package cn.enn.wise.ssop.api.order.dto.response.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupOrderInfoBean {

    @ApiModelProperty("团ID")
    private int id;//团ID
    @ApiModelProperty("商品编码")
    private String goodsNum;//商品编码
    @ApiModelProperty("状态(1待成团 2 拼团中 3拼团成功 4拼团失败)")
    private int status;//状态(1待成团 2 拼团中 3拼团成功 4拼团失败)
    @ApiModelProperty("状态（1 已付款 2已退款 ）")
    private int state;//状态（1 已付款 2已退款 ）
    @ApiModelProperty("订单ID")
    private int orderId;//订单ID
    @ApiModelProperty("使用时段")
    private String timespan;//使用时段
    @ApiModelProperty("拼团编码")
    private String groupOrderCode;//拼团编码
}
