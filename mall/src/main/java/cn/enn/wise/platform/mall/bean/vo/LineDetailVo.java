package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/23
 */
@Data
@ApiModel("详情")
public class LineDetailVo {

    @ApiModelProperty("票")
    private LineTicketVo ticket;

    @ApiModelProperty("仓")
    private List<LineCabinVo> cabin;

}
