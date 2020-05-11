package cn.enn.wise.ssop.api.order.dto.request;

import cn.hutool.db.DaoTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 酒店类订单参数：
 * 酒店类业务说明：
 * <p>
 * <p>
 * 订单参数校验规则说明：
 * 1、
 */
@Data
@ApiModel(description = "酒店基本参数")
public class HotelOrderParam extends BaseOrderParam{

    /**
     * 房型
     */
    @ApiModelProperty(value = "房型",required = true)
    private String homeType;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号",required = true)
    private String homeNo;

    /**
     * 入住时间
     */
    @ApiModelProperty(value = "入住时间",required = true)
    private String comeDate;

    /**
     * 离店时间
     */
    @ApiModelProperty(value = "离店时间",required = true)
    private String leaveDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",required = true)
    private String remark;

}
