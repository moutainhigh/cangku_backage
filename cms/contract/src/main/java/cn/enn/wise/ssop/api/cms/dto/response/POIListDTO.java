package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "资源点列表DTO")
@Data
public class POIListDTO {

    
    @ApiModelProperty("poi主键")
    private Long poiId;

    @ApiModelProperty("poi名称")
    private String poiName;

    @ApiModelProperty("景区id")
    private Long scenicAreaId;

    @ApiModelProperty("景区名称")
    private String scenicAreaName;


}
