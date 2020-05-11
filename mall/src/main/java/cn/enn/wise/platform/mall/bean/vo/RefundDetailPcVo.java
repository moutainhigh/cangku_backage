package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/7 17:06
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Builder
public class RefundDetailPcVo {


    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "退款流水号")
    private String refundNum;

    @ApiModelProperty(value = "操作人姓名")
    private String handleName;

    @ApiModelProperty(value = "审批界面单独使用 退款金额")
    private String refundOrderAmount;

    @ApiModelProperty(value = "退款金额")
    private String refundAmount;

    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "申请原因")
    private String buyerMsg;

    @ApiModelProperty(value = "退款类型1.景区操作原因 2.不可抗力 3.游客原因")
    private Integer buyerMsgType;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

    @ApiModelProperty(value = "退款明细")
    private List<RefundVo> refundVoList;

}
