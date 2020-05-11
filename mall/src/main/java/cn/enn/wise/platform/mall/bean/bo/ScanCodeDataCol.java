package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 扫码数据具体扫码点表
 *
 * @author baijie
 * @date 2019-07-22
 */
@Data
@ApiModel("动态列实体")
public class ScanCodeDataCol {


    @ApiModelProperty("主键")
    private Long  id;

    @ApiModelProperty("接触点名称")
    private String columnName;

    @ApiModelProperty("appId 西藏小程序为1 ")
    private Integer companyId;

    @ApiModelProperty(value = "总数")
    private String total;
}
