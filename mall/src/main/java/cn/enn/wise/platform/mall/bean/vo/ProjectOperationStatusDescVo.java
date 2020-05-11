package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 运营因素描述VO
 *
 * @author baijie
 * @date 2019-08-27
 */
@Data
public class ProjectOperationStatusDescVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("状态文字描述")
    private String descValue;

    @ApiModelProperty("运营状态1 正常 2 停止")
    private Integer operationStatus;

    @ApiModelProperty("原因")
    private List<String> reasons;

    @ApiModelProperty("影响范围程度")
    private List<String> degreeOfInfluences;
}
