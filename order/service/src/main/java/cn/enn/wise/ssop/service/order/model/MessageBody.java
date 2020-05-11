package cn.enn.wise.ssop.service.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 安辉
 */
@Data
@ApiModel("消息体")
public class MessageBody {
    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("数据：JSON")
    private String data;

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("时间戳")
    private Timestamp timestamp;
}
