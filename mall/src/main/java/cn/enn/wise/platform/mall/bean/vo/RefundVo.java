package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/26 16:22
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundVo {

    @ApiModelProperty(value = "体验名称")
    private String playName;

    @ApiModelProperty(value = "金额")
    private String price;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "退款金额合计")
    private String totalPrice;

    @ApiModelProperty(value = "退款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date refundTime;

    @ApiModelProperty(value = "退款ID")
    private Integer refundId;

    @ApiModelProperty(value = "退款流水")
    private String refundNum;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "申请人")
    private String handleName;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer returnMoneySts;

    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

}
