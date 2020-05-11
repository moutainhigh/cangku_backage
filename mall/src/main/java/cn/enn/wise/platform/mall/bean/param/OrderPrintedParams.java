package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/10 16:54
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderPrintedParams {

    @ApiModelProperty(value = "百邦达票号")
    private String orderCode;

    @ApiModelProperty(value = "打印状态 0.未打印 1.已打印")
    private Integer printedSts;
}
