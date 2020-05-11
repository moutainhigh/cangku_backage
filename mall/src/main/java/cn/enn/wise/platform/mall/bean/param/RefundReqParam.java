package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/7 16:02
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class RefundReqParam {


    @ApiModelProperty(value = "退款流水号")
    private String refundNum;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "申请开始日期")
    private String applyStartTime;

    @ApiModelProperty(value = "申请结束日期")
    private String applyEndTime;

    @ApiModelProperty(value = "操作人姓名")
    private String handleName;

    @ApiModelProperty(value = "申请来源 1.PC端 2.App端 3.小程序")
    private Integer platform;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过 ")
    private Integer approvalsSts;

    @ApiModelProperty(value = "审批开始时间")
    private String approvalsStartTime;

    @ApiModelProperty(value = "审批结束时间")
    private String approvalsEndTime;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer refundSts;

    @ApiModelProperty(value = "页码(默认1)")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大条目数(默认10)")
    private Integer pageSize = 10;
}
