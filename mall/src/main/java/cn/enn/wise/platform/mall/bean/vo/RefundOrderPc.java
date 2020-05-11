package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/7 14:44
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundOrderPc {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "退款流水号")
    private String refundNum;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "操作人姓名")
    private String handleName;

    @ApiModelProperty(value = "申请来源 1.PC端 2.App端 3.小程序")
    private String platform;

    @ApiModelProperty(value = "退款金额")
    private String refundAmount;

    @ApiModelProperty(value = "退款操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过 ")
    private Integer approvalsSts;

    @ApiModelProperty(value = "审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalsTime;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer refundSts;

    @ApiModelProperty(value = "审批人")
    private String approvalsName ="陈绪海";

}
