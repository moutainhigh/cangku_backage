package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/4/1 17:44
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class UserDrawVo {

    @ApiModelProperty(value = "1.未领取 2.已领取")
    private Integer whetherDraw;

    @ApiModelProperty(value = "优惠券明细集合")
    private List<UserDrawInfoVo> userDrawInfoVoList;
}
