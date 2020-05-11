package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/23 13:54
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:APP订单信息
 ******************************************/
@Data
@ApiModel
public class AppOrderBean {

    @ApiModelProperty(value = "姓名或者订单号或者手机号")
    private String searchParameter;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer state;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private List<Integer> states;

    @ApiModelProperty(value = "时间段")
    private String timeSpan;

    @ApiModelProperty(value = "0.今天 1.明天")
    private Integer times;

    @ApiModelProperty(value = "偏移量",name = "offset",required=true)
    private Integer offset;

    @ApiModelProperty(value = "页数",name = "limit",required=true)
    private Integer limit;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

}

