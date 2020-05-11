package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/19 11:44
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class ComposeOrderVo {

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer orderSts;

    @ApiModelProperty(value = "订单编号")
    private String orderNum;

    @ApiModelProperty(value = "单价/人数")
    private String priceNum;

    @ApiModelProperty(value = "单价")
    private String price;

    @ApiModelProperty(value = "数量")
    private Long amount;

    @ApiModelProperty(value = "使用时间")
    private String timeSpan;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "项目集合")
    private List<ComposeItemVo> composeItemVoList;
}
