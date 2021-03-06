package cn.enn.wise.ssop.api.order.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;




/**
 * 提现申请单
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
@ApiModel("提现申请单")
@Data
public class WithdrawApplyParam {


    @ApiModelProperty(value = "分销商ID",required = true)
    private Long distributorId;

    @ApiModelProperty(value = "分销商手机号",required = true)
    private String cellphone;

    @ApiModelProperty(value = "短信验证码",required = true)
    private String verifyCode;

    @ApiModelProperty("账户类型，值为：1002、1003")
    private String accountSign;

    @ApiModelProperty("银行卡开户人姓名")
    private String accountUser;

    @ApiModelProperty("账户类型，0：微信；1：银行卡")
    private int accountType;

    @ApiModelProperty("账号（微信号或银行卡号）")
    private String accountNum;

    @ApiModelProperty(value = "分销订单ID",required = true)
    private List<String> orderId;

    @ApiModelProperty(value = "起始日期，格式：2016-01-01",required = true)
    private String startDate;

    @ApiModelProperty(value = "终止日期，格式：2016-01-01",required = true)
    private String endDate;



}
