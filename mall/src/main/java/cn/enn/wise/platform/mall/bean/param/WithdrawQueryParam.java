package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("后台查询参数")
@Data
public class WithdrawQueryParam {

    @ApiModelProperty("当前页码")
    private int pageNum = 1;

    @ApiModelProperty("每页条数")
    private int pageSize = 30;

    private int offset;

    @ApiModelProperty("分销商手机号")
    private String cellphone;

    @ApiModelProperty("申请日期，格式：2016-01-01/2016-01-02")
    private String applyDate;

    private String applyDateStart;

    private String applyDateEnd;

    @ApiModelProperty("分销人姓名")
    private String distributor;

    @ApiModelProperty("发放状态，-2：未分发；1：已分发，「什么都不填」：全部")
    private Byte putOut;

    @ApiModelProperty("提现单序列号")
    private String serial;

    @ApiModelProperty("审核日期，格式：2016-01-01/2016-01-02")
    private String auditDate;

    private String auditDateStart;

    private String auditDateEnd;

    @ApiModelProperty("审核状态，-2: 未审核；1：已通过,-1：未通过，「什么都不填」：全部")
    private Byte permit;

    @ApiModelProperty("审批人姓名")
    private String auditor;



}
