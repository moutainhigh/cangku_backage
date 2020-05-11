package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/6 16:33
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderRefundVo {

    @ApiModelProperty(value = "游玩名称")
    private String playName;

    @ApiModelProperty(value = "退款金额")
    private String price;

    @ApiModelProperty(value = "优惠金额")
    private String couponPrice;

    @ApiModelProperty(value = "操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "操作人")
    private String handleName;

    @ApiModelProperty(value = "退款商品集合")
    private List<RefundVo> refundVoList;

    @ApiModelProperty(value = "数量")
    private Integer amount;

    @ApiModelProperty(value = "退款原因")
    private String buyerMsg;

    @ApiModelProperty(value = "运营1.景区操作原因 2.不可抗力 3.游客原因")
    private Integer buyerMsgType;

    @ApiModelProperty(value = "联系人")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "退款流水")
    private String refundNum;

    @ApiModelProperty(value = "退款状态: 1:退款处理中 2:退款成功 -1:退款失败 3:需财务操作")
    private Integer refundSts;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

}
