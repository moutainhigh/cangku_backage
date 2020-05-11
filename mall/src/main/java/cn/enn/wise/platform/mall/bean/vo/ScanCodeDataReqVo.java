package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 扫码数据
 *
 * @author baijie
 * @date 2019-07-18
 */
@Data
@ApiModel("扫码数据统计请求Vo")
public class ScanCodeDataReqVo {


    private List<ScanCodeDataValue> sacnDataList;

    @ApiModelProperty(value = "扫码日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date scanDate;

    @ApiModelProperty(value = "扫码日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date scanDateStart;
    @ApiModelProperty(value = "扫码日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date scanDateEnd;

    @ApiModelProperty(value = "接触点名称")
    private String contactPointName;

    @ApiModelProperty(value = "数据类型 1 扫码次数 2 扫码人次 3 扫码未进入小程序的人数")
    private Integer scanType;

    @ApiModelProperty(value = "页数")
    private Integer pageNum =1;

    @ApiModelProperty(value = "记录数")
    private Integer pageSize =1;

    @ApiModelProperty(value = "companyId")
    private Integer companyId;

}
