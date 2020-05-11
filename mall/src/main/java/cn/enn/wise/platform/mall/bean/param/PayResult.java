package cn.enn.wise.platform.mall.bean.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bj
 * @Description
 * @Date19-5-26 下午7:13
 * @Version V1.0
 **/
@Data
@ApiModel("支付返回结果")
public class PayResult {

    /**返回结果的状态值**/
    @ApiModelProperty("返回的结果的状态值 1 成功 0 失败")
    private Integer result;
    /**返回结果的信息**/
    @ApiModelProperty("返回的信息")
    private String message;
    /**返回结果的数据为对象**/
    @ApiModelProperty("orderResult实体")
    private OrderResult value;

}
