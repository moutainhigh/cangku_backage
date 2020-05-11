package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/8/28 11:22
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description: 订单返回Result
 ******************************************/
@Data
@ApiModel
public class AppOrdersVo {

    @ApiModelProperty(value = "待使用人数")
    private int peopleNumBer = 0;

    @ApiModelProperty(value = "集合")
    private List<AppOrderVo> list;
}
