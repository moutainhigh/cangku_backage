package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/23 13:54
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:APP订单信息
 ******************************************/
@Data
@ApiModel
public class ReportParam {


    @ApiModelProperty(value = "开始时间 ")
    private String startDate;

    @ApiModelProperty(value = "结束时间 ")
    private String endDate;



}

