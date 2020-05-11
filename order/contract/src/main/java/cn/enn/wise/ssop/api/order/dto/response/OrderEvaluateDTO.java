package cn.enn.wise.ssop.api.order.dto.response;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(description="评价参数")
public class OrderEvaluateDTO {

    /**
     * 主键id
     */
   @ApiModelProperty(value = "主键")
   private Long id;

    /**
     * 订单Id
     */
   @ApiModelProperty(value = "订单id")
   private Long orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /**
     * 分数
     */
    @ApiModelProperty(value = "分数")
    private Byte score;

    /**
     * 评价描述
     */
    @ApiModelProperty(value = "评价描述")
    private String description;

    /**
     * 备注
     */
    @ApiModelProperty(value = "评价备注")
    private String remark;


    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String label;

    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价时间")
    private Date evaluateTime;

}
