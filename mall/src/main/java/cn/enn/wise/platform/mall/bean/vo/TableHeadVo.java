package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态表头Vo
 *
 * @author baijie
 * @date 2019-07-22
 */
@Data
@ApiModel("动态表实体")
public class TableHeadVo {

    @ApiModelProperty(value = "列名称")
    private String label;

    @ApiModelProperty(value = "列属性名")
    private String prop;
}
