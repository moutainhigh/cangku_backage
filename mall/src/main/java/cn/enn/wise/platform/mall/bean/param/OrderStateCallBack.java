package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-02-21 13:51
 **/
@Data
@ApiModel("OrderStateCallBack订单状态回调通知")
public class OrderStateCallBack {

    @ApiModelProperty("加密码")
    private String verifyCode;

    @ApiModelProperty("票付通订单号")
    private String order16U;

    @ApiModelProperty("执行时间")
    private Date actionTime;

    @ApiModelProperty("远端订单号")
    private String orderCall;

    @ApiModelProperty("本次消费票数")
    private String tnumber;

    @ApiModelProperty("通知类型")
    private String orderState;

    @ApiModelProperty("总消费数")
    private String allCheckNum;

    @ApiModelProperty("渠道")
    private String source;

    @ApiModelProperty("操作类型")
    private String action;
}
