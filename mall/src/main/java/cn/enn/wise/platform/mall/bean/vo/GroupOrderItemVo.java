package cn.enn.wise.platform.mall.bean.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 参团详情
 * @author jiaby
 * @since 2019/9/21
 */

@Data
@ApiModel("参团详情类")
public class GroupOrderItemVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("拼团表外键")
    private Long groupOrderId;

    @ApiModelProperty("拼团编码")
    private String groupOrderCode;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("加入时间")
    private Timestamp joinTime;

    @ApiModelProperty("外键 指向 mall库orders表中的id")
    private Long orderId;

    @ApiModelProperty("是否是团长")
    private Byte isHeader;

    @ApiModelProperty("状态（1 已付款 2已退款 ）")
    private Byte status;

    @ApiModelProperty("退款单号")
    private String refundCode;

    @ApiModelProperty("时段Id")
    private Long goodsExtendId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("退款时间")
    private Timestamp refundTime;

    @ApiModelProperty("参团类型 1好友邀请 2游客参团")
    private Byte groupOrderType;
}
