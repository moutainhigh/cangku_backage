package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户手动取消订单的原因实体
 *
 * @author baijie
 * @date 2019-08-28
 */
@Data
@ApiModel("退单原因实体")
@NoArgsConstructor
@AllArgsConstructor
public class RefundReason {

    @ApiModelProperty(value = "主键")
    private Long  id;

    @ApiModelProperty("原因")
    private String reason;

}
