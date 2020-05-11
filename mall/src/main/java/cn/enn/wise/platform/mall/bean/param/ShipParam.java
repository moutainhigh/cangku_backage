package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiaby
 */
@Data
@ApiModel("船舶参数")
public class ShipParam {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("船舶名称")
    private String name;

    @ApiModelProperty("编号")
    private String code;

    @ApiModelProperty("类型 1-客运")
    private Integer shipType;

    @ApiModelProperty("所属机构 1 华南新绎游船")
    private Integer organization;

    @ApiModelProperty("第三方主键id")
    private Long thirdId;

    @ApiModelProperty("状态 1 运营中 2 停运")
    private Byte status;

    @ApiModelProperty("缩略图路径")
    private String imgUrl;

    @ApiModelProperty("最大载客量")
    private Integer maxPassenger;

}
