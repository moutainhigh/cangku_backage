package cn.enn.wise.ssop.api.cms.dto.response;

import cn.enn.wise.uncs.base.pojo.response.Location;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "位置")
@Data
public class LocationDTO extends Location {

    @ApiModelProperty("标签")
    private String tags;

    @ApiModelProperty("距离")
    private Double distance;
}
