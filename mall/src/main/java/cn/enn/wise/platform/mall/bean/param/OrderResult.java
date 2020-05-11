package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author bj
 * @Description
 * @Date19-5-26 下午6:12
 * @Version V1.0
 **/
@Data
@ApiModel("OrderResult实体")
public class OrderResult {
    /**
     * 支付凭证
     */
    @ApiModelProperty("支付凭证Id")
    private String prepayId;
    /**
     * 微信支付响应参数
     */
    @ApiModelProperty("微信响应参数")
    private Map<String,Object> obj;
}
