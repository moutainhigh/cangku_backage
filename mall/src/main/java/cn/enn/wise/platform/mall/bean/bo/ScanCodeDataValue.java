package cn.enn.wise.platform.mall.bean.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 扫码数据
 *
 * @author baijie
 * @date 2019-07-18
 */
@Data
@ApiModel("扫码数据值")
public class ScanCodeDataValue extends ScanCodeDataCol{

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "扫码日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date scanDate;

    @ApiModelProperty(value = "数据类型 1 扫码次数 2 扫码人次 3 扫码未进入小程序的人数")
    private Integer scanType;


    @ApiModelProperty(value = "接触点扫码次数")
    private Long scanAmount;

    @ApiModelProperty(value = "列Id")
    private Long colId;


}
