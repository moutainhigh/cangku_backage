package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/26 10:34
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class DistinctOrderDetail {

    @ApiModelProperty(value = "单品数据")
    private Order order;

    @ApiModelProperty(value = "组合数据")
    private ComposeOrderVo composeOrderVo;

    @ApiModelProperty(value = "单品,组合区分 1.单品 2.组合")
    private Integer orderType;
}
