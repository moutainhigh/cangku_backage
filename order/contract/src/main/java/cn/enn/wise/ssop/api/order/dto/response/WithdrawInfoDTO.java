package cn.enn.wise.ssop.api.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;


/**
 * 提现单详情
 * <p>管理后台查看提现单详情</p>
 */
@Data
@ApiModel("后台查询提现单详情")
public class WithdrawInfoDTO {


    public Long id;

    @ApiModelProperty("分销商名称")
    private String distributorName;

    @ApiModelProperty("分销商类型")
    private String distributorType;

    @ApiModelProperty("必要信息")
    private String requiredInfo;

    @ApiModelProperty("分销商电话")
    private String distributorPhone;

    @ApiModelProperty("申请金额,两位小数")
    private String applyAmount;

    @ApiModelProperty("申请日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyDate;

    @ApiModelProperty("日期范围")
    private String dateRange;

    @ApiModelProperty("账单数量")
    private int orderCount;

    @ApiModelProperty("审核结果 -2: 未审核；1：已审核 -1 未通过")
    private int auditResult;

    @ApiModelProperty("原因描述")
    private String auditDesc;


}
