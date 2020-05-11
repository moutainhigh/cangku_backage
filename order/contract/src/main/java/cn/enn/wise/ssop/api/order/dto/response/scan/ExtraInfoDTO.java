package cn.enn.wise.ssop.api.order.dto.response.scan;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("额外信息")
public class ExtraInfoDTO {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("其他信息")
    private String extraInfoValue;
}
