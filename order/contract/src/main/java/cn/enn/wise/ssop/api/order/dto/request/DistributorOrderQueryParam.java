package cn.enn.wise.ssop.api.order.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author anhui257@163.com
 */
@Data
@ApiModel("分销账单查询参数")
public class DistributorOrderQueryParam extends QueryParam {
    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    @ApiModelProperty("结束时间")
    private Timestamp endTime;

    @ApiModelProperty("订单类型")
    private Integer orderType;

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;
}
