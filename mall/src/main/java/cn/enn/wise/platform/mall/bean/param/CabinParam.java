package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiaby
 */
@Data
@ApiModel("船舱参数")
public class CabinParam {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("船舱名称")
    private String name;

    @ApiModelProperty("船舱第三方id")
    private String cabinId;

    @ApiModelProperty("船舶主键id")
    private Long thirdId;

    @ApiModelProperty("缩略图路径")
    private String imgUrl;

    @ApiModelProperty("状态 1 启用中 2 未启用")
    private Byte status;

    @ApiModelProperty("载客量")
    private Integer passengerSize;

}
