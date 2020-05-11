package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/19 11:45
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class ComposeItemVo {

    @ApiModelProperty(value = "项目Id")
    private Integer itemId;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "项目商品名称")
    private String shopName;

    @ApiModelProperty(value = "数量")
    private Long amount;


 }
