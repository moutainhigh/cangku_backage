package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.GroupOrderBo;
import cn.enn.wise.platform.mall.bean.bo.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
public class GroupOrderDetailVo {

    @ApiModelProperty(value = "团详情")
     private GroupOrderBo groupInfo;

    @ApiModelProperty(value = "订单列表")
     private List<Order> orderList;

}
