package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/26 16:18
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class NoPlayVo {

    @ApiModelProperty(value = "子订单Id")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "份数")
    private Long amount;

    @ApiModelProperty(value = "项目名称+商品名称")
    private String playName;

    @ApiModelProperty(value = "单价")
    private String price;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "单品优惠分摊金额")
    private String couponPrice;

    @ApiModelProperty(value = "百邦达票状态，0:待出票  -1出票失败/已取消  1出票成功  100 已检票  230退成功已结款")
    private Integer ticketStateBbd;

    @ApiModelProperty(value = "百邦达票号")
    private String ticketSerialBbd;


}
