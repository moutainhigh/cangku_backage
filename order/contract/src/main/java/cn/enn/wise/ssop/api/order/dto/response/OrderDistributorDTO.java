package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 分销商订单信息
 *
 */
@Data
public class OrderDistributorDTO {

    /**
     * 创建用户ID
     */
    @ApiModelProperty(value = "创建的用户ID")
    private Long creatorId;

    /**
     * 用户更新ID
     */
    @ApiModelProperty(value = "更新的用户ID")
    private Long updatorId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("分销商ID")
    private Long distributorId;

    @ApiModelProperty("分销商名称")
    private String distributorName;

    @ApiModelProperty("折扣额")
    private BigDecimal rebate;

    @ApiModelProperty("分销商手机号")
    private String distributorCellphone;

    @ApiModelProperty("分销商角色信息（等）")
    private String distributorMessage;

    @ApiModelProperty("此分销单利润额")
    private BigDecimal profit;




}
