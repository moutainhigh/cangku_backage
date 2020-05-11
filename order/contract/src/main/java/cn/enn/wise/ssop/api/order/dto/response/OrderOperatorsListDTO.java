package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 订单列表展示返回的参数
 *
 * @author yangshuaiquan
 * @date 2020-04-24
 */

@Data
@ApiModel("运营人员的返回参数")
public class OrderOperatorsListDTO {

    @ApiModelProperty("供应商名字")
    private String supplierName;

    @ApiModelProperty("供应商电话")
    private String supplierPhone;


    @ApiModelProperty("运营人员名字")
    private String operatorsName;

    @ApiModelProperty("运营人员电话")
    private String operatorsPhone;


    @ApiModelProperty("分销商姓名")
    private String distributorName;

    @ApiModelProperty("分销商电话")
    private String distributorPhone;

    @ApiModelProperty("分销来源")
    private String distributorSource;

    @ApiModelProperty("分销渠道")
    private String distributorChannel;

    @ApiModelProperty("分销等级")
    private String distributorLevel;

}
